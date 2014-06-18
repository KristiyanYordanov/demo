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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.repository.PlayerRepository;

public class MaxPrepsModel {

	private static final Logger logger = LoggerFactory
			.getLogger(MaxPrepsModel.class);
	@Autowired
	PlayerRepository playerRepository;

	public MaxPrepsModel(PlayerRepository playerRepository) {
		super();
		this.playerRepository = playerRepository;
	}

	@Async
	public void run() {
		logger.info("start maxpreps scrapping.\n");
		// File csvFile = new File(cacheRoot + "../" + filename);
		ICsvBeanWriter data = null;
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
			String baseUrl = "http://www.maxpreps.com/search/school.aspx?gendersport=boys%2Cbaseball&state=";
			String baseMatch = "<a href=\"/high-schools/([^\"]*/[^/]*baseball[^/]*)/home.htm\"";

			List<Object[]> source = new ArrayList<Object[]>();
			for (int i = 0; i < PlayerDoc.STATE.length; i++) {
				source.add(new Object[] {
						new URL(baseUrl + PlayerDoc.STATE[i].toLowerCase()),
						baseMatch });
			}
			data = new CsvBeanWriter(new FileWriter(csvFile),
					CsvPreference.STANDARD_PREFERENCE);
			final String[] header = new String[] { "name", "pos", "stars",
					"height", "fortyDash", "weight", "gradYear", "rating",
					"state", "schoolName", "schoolCity", "maxprepsUrl",
					"statGP", "statAvg", "statOBP", "statH", "statRBI",
					"statR", "statSB", "statAB", "statSLG", "statPA", "statFP",
					"statK", "statIP" };
			data.writeHeader(header);
			data.flush();
			String base = "http://www.maxpreps.com/";
			for (int i = 0; i < source.size(); i++) {
				URL urlStates = (URL) source.get(i)[0];
//				 System.out.println("urlStates=" + urlStates);
				Document docWithStates = Jsoup.connect(urlStates.toString())
						.timeout(0).get();
				Elements schoolUrls = docWithStates.select("li a");
				String schoolUrl = "";
				for (Element schoolUrlElement : schoolUrls) {
					if (schoolUrlElement.toString().contains("Var")
							|| schoolUrlElement.toString().contains("JV")
							|| schoolUrlElement.toString().contains("Fr")) {
						int start = schoolUrlElement.toString().indexOf(
								"a href=\"") + 9;
						int endVar = schoolUrlElement.toString().indexOf(
								"\">Var");
						int endJV = schoolUrlElement.toString()
								.indexOf("\">JV");
						int endFr = schoolUrlElement.toString()
								.indexOf("\">Fr");

						List<Integer> urlList = new ArrayList<Integer>();
						if (endVar > 0) {
							urlList.add(new Integer(endVar));
						} else if (endJV > 0) {
							urlList.add(new Integer(endJV));
						} else if (endFr > 0) {
							urlList.add(new Integer(endFr));
						}

						for (int k = 0; k < urlList.size(); k++) {
							schoolUrl = base
									+ schoolUrlElement.toString()
											.substring(start, urlList.get(k))
											.replace("home", "roster");

							String schoolName = "";
							String schoolCity = "";
							String state = "";

							int startSchool = schoolUrl.indexOf("schools/") + 8;
							int endSchool = schoolUrl.indexOf("-(");
							schoolName = schoolUrl.substring(startSchool,
									endSchool);
							// System.out.println("schoolName = " + schoolName);

							int startCity = schoolUrl.indexOf("(") + 1;
							int endCity = schoolUrl.indexOf(",");
							schoolCity = schoolUrl
									.substring(startCity, endCity);
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
								try {
									docSchoolUrl = Jsoup
											.connect(schoolUrl.toString())
											.timeout(0).get();

									Elements players = docSchoolUrl
											.select("th a");
									// System.out.println("++++++++++++++++ "
									// + players.size()
									// + " +++++++++++++++++++");
									if (players.size() > 0) {
										System.out.println("" + schoolUrl);
										
										for (Element playerElement : players) {
											if (!playerElement.toString()
													.contains("#")) {
												PlayerDoc p = new PlayerDoc();
												p.setSchoolName(schoolName);
												p.setSchoolCity(schoolCity);
												p.setState(state);
												String aHtml = playerElement
														.toString();
												Document docName = Jsoup
														.parse(aHtml);
												String name = docName.body()
														.text();
												// System.out.println("name = "
												// +
												// name);
												p.setName(name);
												Element link = docName.select(
														"a").first();
												String linkHref = link
														.attr("href");
												String maxprepsUrl = base
														+ linkHref;

												p.setMaxprepsUrl(maxprepsUrl);
												Document docPlayer = Jsoup
														.connect(maxprepsUrl)
														.timeout(0).get();
												Elements height = docPlayer
														.select(".height");
												for (Element ee : height) {
													String h = ee.text()
															.replace("&quot;",
																	"\"");
													p.setHeight(h);
													// System.out.println("height= "
													// +
													// p.getHeight());
												}
												Elements weight = docPlayer
														.select(".weight");
												for (Element ee : weight) {
													String w = ee.text();
													w = w.replaceAll("\\D", "");
													int ww = Integer
															.parseInt(w);
													p.setWeight(ww);
													// System.out.println("weight="
													// +
													// p.getWeight());
												}
												Elements graduationYear = docPlayer
														.select(".graduation-year");
												for (Element ee : graduationYear) {
													String g = ee.text();
													int year = Integer
															.parseInt(g
																	.replaceAll(
																			"\\D",
																			""));
													p.setGradYear(year);
													// System.out.println("year = "
													// +
													// p.getGradYear());
												}

												Elements pos = docPlayer
														.select("dd span");
												for (Element ee : pos) {
													String pp = ee.text();
													String[] arr = PlayerDoc.POSITIONS;
													String position = "";
													for (int j = 0; j < arr.length; j++) {
														String string = arr[j];
														if (pp.contains(string)) {
															position = position
																	+ string
																	+ ",";
															p.setPos(position);
														}
													}
													// System.out.println("pos = "
													// +
													// p.getPos());
												}

												Elements stat = docPlayer
														.select("li span.stat-name");
												if (stat.size() > 0) {
//													System.out
//															.println("platyer url = "
//																	+ maxprepsUrl);
//													System.out.println("stat="
//															+ stat);
												}

												Elements statValue = docPlayer
														.select("li span.stat-value");

												for (int j = 0; j < stat.size(); j++) {
													if (stat.get(j).text()
															.equals("GP")) {
														p.setStatGP(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("GP = "
														// + p.getStatGP());
													} else if (stat.get(j)
															.text()
															.equals("Avg")) {
														p.setStatAvg(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("Avg = "
														// + p.getStatAvg());
													} else if (stat.get(j)
															.text()
															.equals("OBP")) {
														p.setStatOBP(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("OBP = "
														// + p.getStatOBP());
													} else if (stat.get(j)
															.text().equals("H")) {
														p.setStatH(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("H = "
														// + p.getStatH());
													} else if (stat.get(j)
															.text()
															.equals("RBI")) {
														p.setStatRBI(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("RBI = "
														// + p.getStatRBI());
													} else if (stat.get(j)
															.text().equals("R")) {
														p.setStatR(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("R = "
														// + p.getStatR());
													} else if (stat.get(j)
															.text()
															.equals("SB")) {
														p.setStatSB(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("SB = "
														// + p.getStatSB());
													} else if (stat.get(j)
															.text()
															.equals("AB")) {
														p.setStatAB(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("AB = "
														// + p.getStatAB());
													} else if (stat.get(j)
															.text()
															.equals("SLG")) {
														p.setStatSLG(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("SLG = "
														// + p.getStatSLG());
													} else if (stat.get(j)
															.text()
															.equals("PA")) {
														p.setStatPA(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("PA = "
														// + p.getStatPA());
													} else if (stat.get(j)
															.text()
															.equals("FP")) {
														p.setStatFP(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("FP = "
														// + p.getStatFP());
													} else if (stat.get(j)
															.text()
															.equals("AB")) {
														p.setStatAB(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("AB = "
														// + p.getStatAB());
													} else if (stat.get(j)
															.text().equals("K")) {
														p.setStatH(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("K = "
														// + p.getStatK());
													} else if (stat.get(j)
															.text()
															.equals("IP")) {
														p.setStatIP(Double
																.parseDouble(statValue
																		.get(j)
																		.text()));
														// System.out.println("IP = "
														// + p.getStatIP());
													}
												}
												System.out.println(p);
												data.write(p, header);
											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								logger.info("my error :", e);
								e.printStackTrace();
							}
						}
					}
				}
				long delay = 200L + ((long) (Math.random() * 4800.0));
				Thread.sleep(delay);
			}

			importCsvFile(csvFile, header);
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

	// used by MaxprepMmodel class
	public void importCsvFile(File file, String[] header) throws Exception {
		PlayerModel playerModel = new PlayerModel();
		List<PlayerDoc> res = playerModel.readWithCsvBeanReaderForPlayerDoc(
				file, header);
		for (PlayerDoc p : res) {
			playerRepository.save(p);
		}
		System.out.println(file.getAbsolutePath() + " is imported.");
	}

}
