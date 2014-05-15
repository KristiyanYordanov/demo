package com.krissoft.saa.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
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
			final String[] header = new String[] { "name","location","pos","stars","height","fortyDash","weight","gradYear","rating","state","schoolName","schoolCity","maxprepsUrl","GP","Avg","OBP","H","RBI","R","SB","AB","SLG","PA","FP","K","IP" }	;
			data.writeHeader(header);
			data.flush();
			String NULL = null;
			String base = "http://www.maxpreps.com/";
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
						System.out.println(schoolUrl);

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
//									System.out.println("urll = " + maxprepsUrl);
									 
									p.setMaxprepsUrl(maxprepsUrl);
									Document docPlayer = Jsoup
											.connect(maxprepsUrl).timeout(0)
											.get();
									Elements height = docPlayer
											.select(".height");
									for (Element ee : height) {
										String h = ee.text().replace("&quot;", "\"");
										p.setHeight(h);
//										System.out.println("height= " + p.getHeight());
									}
									Elements weight = docPlayer
											.select(".weight");
									for (Element ee : weight) {
										String w = ee.text();
										w = w.replaceAll("\\D", "");
										int ww = Integer.parseInt(w);
										p.setWeight(ww);
//										System.out.println("weight=" + p.getWeight());
									}
									Elements graduationYear = docPlayer
											.select(".graduation-year");
									for (Element ee : graduationYear) {
										String g = ee.text();
										int year = Integer.parseInt(g.replaceAll("\\D", ""));
										p.setGradYear(year);
//										System.out.println("year = " + p.getGradYear());
									}

									Elements pos = docPlayer.select("dd span");
									for (Element ee : pos) {
										String pp = ee.text();
										String[] arr = PlayerDoc.POSITIONS;
										String position= "";
										for (int j = 0; j < arr.length; j++) {
											String string = arr[j];
											if (pp.contains(string)) {
												position = position + string + ",";
												p.setPos(position);
											}
										}
//										System.out.println("pos = " + p.getPos());
									}

									Elements stat = docPlayer
											.select("li span.stat-name");
									Elements statValue = docPlayer
											.select("li span.stat-value");
									
									for (int j = 0; j < stat.size(); j++) {
										if (stat.get(j).text().equals("GP")) {
											p.setGP(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("GP = " + p.getGP());
										}
										else if (stat.get(j).text().equals("Avg")) {
											p.setAvg(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("Avg = " + p.getAvg());
										}
										else if (stat.get(j).text().equals("OBP")) {
											p.setOBP(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("OBP = " + p.getOBP());		
										}
										else if (stat.get(j).text().equals("H")) {
											p.setH(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("H = " + p.getH());
										}
										else if (stat.get(j).text().equals("RBI")) {
											p.setRBI(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("RBI = " + p.getRBI());
										}
										else if (stat.get(j).text().equals("R")) {
											p.setR(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("R = " + p.getR());
										}
										else if (stat.get(j).text().equals("SB")) {
											p.setSB(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("SB = " + p.getSB());
										}
										else if (stat.get(j).text().equals("AB")) {
											p.setAB(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("AB = " + p.getAB());
										}
										else if (stat.get(j).text().equals("SLG")) {
											p.setSLG(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("SLG = " + p.getSLG());
										}
										else if (stat.get(j).text().equals("PA")) {
											p.setPA(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("PA = " + p.getPA());
										}
										else if (stat.get(j).text().equals("FP")) {
											p.setFP(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("FP = " + p.getFP());
										}
										else if (stat.get(j).text().equals("AB")) {
											p.setAB(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("AB = " + p.getAB());
										}
										else if (stat.get(j).text().equals("K")) {
											p.setH(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("K = " + p.getK());
										}
										else if (stat.get(j).text().equals("IP")) {
											p.setIP(Double.parseDouble(statValue.get(j).text()));
//											System.out.println("IP = " + p.getIP());
										}
									}
									
									//System.out.println(p);
									data.write(p, header);
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


	public static void main(String[] args) throws Exception {
		MaxPrepsModel model = new MaxPrepsModel();
		model.run();
	}

}
