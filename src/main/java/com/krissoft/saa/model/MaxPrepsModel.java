package com.krissoft.saa.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.controller.PlayerController;
import com.krissoft.saa.util.Util;

public class MaxPrepsModel {

	protected ICsvBeanWriter data = null;

	private static final Logger logger = LoggerFactory
			.getLogger(MaxPrepsModel.class);
	@Async
	public void run() {
		logger.info("start maxpreps scrapping.\n");
		// File csvFile = new File(cacheRoot + "../" + filename);
		File csvFile = new File("maxpreps.txt");
		if (!csvFile.exists()) {
			try {
				csvFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			String baseUrl = "http://www.maxpreps.com/search/school.aspx?gendersport=boys,baseball&all_seasons=1&state=";
			String baseMatch = "<a href=\"/high-schools/([^\"]*/[^/]*baseball[^/]*)/home.htm\"";

			List<Object[]> source = new ArrayList<Object[]>();
			for (int i = 0; i < PlayerDoc.STATE.length; i++) {
				source.add(new Object[] {
						new URL(baseUrl + PlayerDoc.STATE[i].toLowerCase()),
						baseMatch });
			}
			data = new CsvBeanWriter(new FileWriter(csvFile),
					CsvPreference.STANDARD_PREFERENCE);

			final String[] header = new String[] { "name", "schoolName",
					"schoolCity", "state", "gradYear", "pos", "maxprepsUrl",
					"weight", "height" };

			data.writeHeader(header);
			data.flush();
			String NULL = null;
			String base = "http://www.maxpreps.com/";
			final CellProcessor[] processors = getProcessors();
			for (int i = 0; i < source.size(); i++) {
				// System.out.println("---------------------");
				URL urlStates = (URL) source.get(i)[0];
				Document docWithStates = Jsoup.connect(urlStates.toString())
						.timeout(0).get();
				Elements schoolUrls = docWithStates.select("li a");
				StringBuffer sb = new StringBuffer();
				String schoolUrl = "";
				for (Element schoolUrlElement : schoolUrls) {
					if (schoolUrlElement.toString().contains("Var")) {
						int start = schoolUrlElement.toString().indexOf(
								"a href=\"") + 9;
						int end = schoolUrlElement.toString().indexOf("\">Var");
						schoolUrl = base
								+ schoolUrlElement.toString()
										.substring(start, end)
										.replace("home", "roster");
						// System.out.println(schoolUrl);

						String schoolName = "";
						String schoolCity = "";
						String state = "";

						int startSchool = schoolUrl.indexOf("schools/") + 8;
						int endSchool = schoolUrl.indexOf("-(");
						schoolName = schoolUrl
								.substring(startSchool, endSchool);
						// System.out.println("schoolName = " + schoolName);

						int startCity = schoolUrl.indexOf("(") + 1;
						int endCity = schoolUrl.indexOf(",");
						schoolCity = schoolUrl.substring(startCity, endCity);
						if (schoolCity.contains("-")) {
							schoolCity = schoolCity.replaceAll("-", " ");
						}
						// System.out.println("cityName = " + cityName);

						int startState = schoolUrl.indexOf(",") + 1;
						int endState = schoolUrl.indexOf(")");
						state = schoolUrl.substring(startState, endState)
								.toUpperCase();
						// System.out.println("state = " + state);

						Document docSchoolUrl = null;
						String schoolUrlBase = schoolUrl.toString();
						try {
							if (!schoolUrlBase.startsWith("http://")) {
								schoolUrlBase = "http://" + schoolUrlBase;
							}
							docSchoolUrl = Jsoup.connect(schoolUrl.toString())
									.timeout(0).get();
							Elements players = docSchoolUrl.select("th a");
							System.out.println("++++++++++++++++ "
									+ players.size() + " +++++++++++++++++++");

							for (Element playerElement : players) {
								if (!playerElement.toString().contains("#")) {
									PlayerDoc p = new PlayerDoc();
									p.setSchoolName(schoolName);
									p.setSchoolCity(schoolCity);
									p.setState(state);
									String aHtml = playerElement.toString();
									Document docName = Jsoup.parse(aHtml);
									String name = docName.body().text();
									// System.out.println("name = " + name);
									p.setName(name);
									Element link = docName.select("a").first();
									String linkHref = link.attr("href");
									String maxprepsUrl = base + linkHref;
									// System.out.println("urll = " + urll);
									p.setMaxprepsUrl(maxprepsUrl);
									Document docPlayer = Jsoup
											.connect(maxprepsUrl).timeout(0)
											.get();
									Elements height = docPlayer
											.select(".height");
									for (Element ee : height) {
										// System.out.println(ee);
										String h = "100";
										p.setHeight(h);
									}
									Elements weight = docPlayer
											.select(".weight");
									for (Element ee : weight) {
										// System.out.println(ee);
										p.setWeight(1);
									}
									Elements grade = docPlayer.select(".grade");
									for (Element ee : grade) {
										// System.out.println(ee);
									}
									Elements graduationYear = docPlayer
											.select(".graduation-year");
									for (Element ee : graduationYear) {
										// System.out.println(ee);
										p.setGradYear(2014);
									}

									Elements pos = docPlayer.select("dd span");
									for (Element ee : pos) {
										// System.out.println("pos = " + ee);
										String position = "pos";
										p.setPos(position);
									}

									Map<String, String> map = new HashMap<String, String>();
									Elements stat = docPlayer
											.select("li span.stat-name");
									for (Element ee : stat) {
										// System.out.println("stat = " + ee);
									}

									Elements statValue = docPlayer
											.select("li span.stat-value");
									for (Element ee : statValue) {
										// System.out.println("statValue = " +
										// ee);
									}

									System.out.println(p);
									data.write(p, header, processors);
									data.flush();
								}
							}
							long delay = 200L + ((long) (Math.random() * 1800.0));
							Thread.sleep(delay);
							// System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
							sb.append(schoolUrl);
							sb.append("\t\n");
						} catch (Exception e) {
							logger.info("my error :", e);
							e.printStackTrace();
						}
					}
				}
				File file = new File("scoolUrl.txt");
				if (!file.exists()) {
					Util.writeInFileBuffered(sb.toString(), file);
				}
				long delay = 200L + ((long) (Math.random() * 4800.0));
				Thread.sleep(delay);
			}

			PlayerController pc = new PlayerController();
			pc.importCsvFile(csvFile, header);
			System.out.println(csvFile.getPath());
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			logger.info("my error1 :", ex);
		} finally {
			if (data != null) {
				try {
					data.flush();
					data.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	private static CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] {
				new Optional(), // firstName
				new Optional(), // lastName
				new Optional(), // birthDate
				new Optional(), // mailingAddress
				new Optional(), // married
				new Optional(), // numberOfKids
				new Optional(), // favouriteQuote
				new Optional(), // email
				new Optional() // loyaltyPoints
		};

		return processors;
	}

	public static void main(String[] args) throws Exception {
		File file = new File(System.getProperty("java.io.tmpdir"),
				"www.maxpreps.com.csv");
		MaxPrepsModel model = new MaxPrepsModel();
		model.run();
	}

}
