package com.krissoft.saa.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.google.gson.Gson;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.util.Util;

/**
 * 
 * @author kris
 */
public class HudlModel {

	Calendar now = Calendar.getInstance();
	int month = now.get(Calendar.MONTH);
	int year = now.get(Calendar.YEAR);
	static String NULL = null;

	public void processPages() throws Exception {
		for (int i =0; i < 1; i++) {
			//String teamId = Integer.toString(i);
			String teamId = "1645";
			String link = "http://www.hudl.com/team/";
			String result = Util.postWithParams(new URL(link + teamId), "");
			if (isBaseballProfile(result)) {
				System.out.println("team = " + i + "is baseball team");
				
				URL url = new URL("http://www.hudl.com/teamprofile/section");
				String postParams = "{\"teamId\":" + teamId
						+ ",\"tabId\":\"roster\"}";
				System.out.println(postParams);
				String jsonText = Util.postWithParams(url, postParams);
				File file = new File("test" + teamId + ".txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				Util.writeInFileBuffered(jsonText, file);
				
				List<PlayerDoc> players = getElements(jsonText, link + teamId);
				System.out.println("Found " + players.size() + "in " + url);
				if (players.size() > 0) {
					for (PlayerDoc player : players) {
						try {
							if (player.getName() != null) {
								data.write(player.getName());
							} else {
								data.write(NULL);
							}
							if (player.getLocation() != null) {
								data.write(player.getLocation());
							} else {
								data.write(NULL);
							}
							data.flush();
							
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		}
	}

	public static void main(String[] argc) throws Exception {
		HudlModel hudlModel = new HudlModel();
		hudlModel.processPages();
	}

	public String getSchoolName(String link) throws Exception {
		String page = Util.get(new URL(link));
		Pattern titlePatter = Pattern.compile("<h1 title=\"(.*?)\">(.*?)</h1>");
		Matcher titleMatcher = titlePatter.matcher(page);
		String schoolName = "";
		while (titleMatcher.find()) {
			schoolName = titleMatcher.group(1);
		}
		// SchoolFilter schoolFilter = new SchoolFilter();
		// schoolFilter.setFilterSchoolName(schoolName);
		// SchoolModel schoolModel = new SchoolModel(login);
		// InfoList<School> schools = schoolModel.getBeanList(schoolFilter);
		// School school;
		// if (schools.size() > 0) {
		// school = schools.get(0);
		// } else {
		// school = new School();
		// school.setName(schoolName);
		// school = schoolModel.insertBean(school);
		// }
		// SchoolType schoolType = new SchoolType();
		// schoolType.setSchoolId(school.getSchoolId());
		// schoolType.setShoolName(schoolName);
		return "";
	}

	public boolean isBaseballProfile(String page) throws Exception {
		boolean isBaseballProfile = page.toLowerCase().contains("baseball")
				&& page.toLowerCase().contains("high school");
		return isBaseballProfile;
	}

	ICsvBeanWriter data = null;

	// @Override
	public void run() {
		// File csvFile = new File(cacheRoot + "../" + filename);
		File csvFile = new File("");
		try {
			// important
			data = new CsvBeanWriter(new FileWriter(
					"target/writeWithCsvBeanWriter.csv"),
					CsvPreference.STANDARD_PREFERENCE);

			final String[] header = new String[] { "firstName", "lastName",
					"school", "pos", "gradYear", "height", "weight", "hudlUrl",
					"newHudlVideo", "hudlVideos" };
			data.writeHeader(header);
			data.flush();
			String NULL = null;
			Map<URL, File> target = new HashMap<URL, File>();
			//processPages(target);
			Iterator<URL> iter = target.keySet().iterator();
			while (iter.hasNext()) {
				URL url = iter.next();
				File file = target.get(url);
				// CharSequence content = load(file, "UTF-8");
				CharSequence content = "";
				if (content != null) {
					List<PlayerDoc> players = getElements(content.toString(),
							url.toString());
					System.out.println("Found " + players.size() + "in " + url);
					if (players.size() > 0) {
						for (PlayerDoc player : players) {
							try {
								if (player.getName() != null) {
									data.write(player.getName());
								} else {
									data.write(NULL);
								}
								if (player.getLocation() != null) {
									data.write(player.getLocation());
								} else {
									data.write(NULL);
								}
								data.flush();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception ex) {
		}
		// if (isAutoimport()) {
		// try {
		// importFile(csvFile, this);
		// ScheduleModel scheduleModel = new ScheduleModel(null);
		// Schedule schedule = new Schedule();
		// schedule.setScheduleId(getId());
		// scheduleModel.udpateLastRun(schedule);
		// } catch (Exception ex) {
		// logger.error("Cannot hudl import data", ex);
		// ex.printStackTrace(System.err);
		// } finally {
		// endW3Extractor(getId());
		// }
		// }
	}

	public void processPlayers(List<String> players, String school)
			throws IOException {
		String NULL = null;
		if (players.size() == 7) {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(0).length() > 2) {
					if (!Character.isUpperCase(players.get(0).charAt(1))) {
						if (i == 2) {
							data.write(school);
							continue;
						}
						if (i == 4) {
							if (players.get(4).trim().equals("Sr.")
									&& month < 6) {
								data.write(year);
							} else if (players.get(4).trim().equals("Sr.")
									&& month > 6) {
								data.write(year + 1);
							} else if (players.get(4).trim().equals("Jr.")
									&& month < 6) {
								data.write(year + 1);
							} else if (players.get(4).trim().equals("Jr.")
									&& month > 6) {
								data.write(year + 2);
							} else if (players.get(4).trim().equals("So.")
									&& month < 6) {
								data.write(year + 2);
							} else if (players.get(4).trim().equals("So.")
									&& month > 6) {
								data.write(year + 3);
							} else if (players.get(4).trim().equals("Fr.")
									&& month < 6) {
								data.write(year + 3);
							} else if (players.get(4).trim().equals("Fr.")
									&& month > 6) {
								data.write(year + 4);
							} else if (players.get(4).trim().equals("8th")
									&& month < 6) {
								data.write(year + 4);
							} else if (players.get(4).trim().equals("8th")
									&& month > 6) {
								data.write(year + 5);
							} else {
								data.write(NULL);
							}
							continue;
						}
						String el = (String) players.get(i);
						if (el == null) {
							el = NULL;
						} else if (el.equals("-")) {
							el = NULL;
						} else if (el.equals("")) {
							el = NULL;
						} else if (el.contains("[")) {
							el = el.replace("[", "").replace("]", "");
						}
						data.write(el);
					}
				}
			}
		}
	}

	private class SchoolType {
		String shoolName;
		Long schoolId;

		public String getShoolName() {
			return shoolName;
		}

		public void setShoolName(String shoolName) {
			this.shoolName = shoolName;
		}

		public Long getSchoolId() {
			return schoolId;
		}

		public void setSchoolId(Long schoolId) {
			this.schoolId = schoolId;
		}
	}

	public List<PlayerDoc> getElements(String jsonText, String url)
			throws JSONException, Exception {
		List<PlayerDoc> res = new ArrayList<PlayerDoc>();
		if (jsonText.contains("seasons")) {
			Gson gson=new Gson(); 
			Map jSONMap = new HashMap();
			jSONMap = (Map) gson.fromJson(jsonText, jSONMap.getClass());
			
			List header =  (List) jSONMap.get("seasons");
			Iterator seasonsIter = header.iterator();
			while (seasonsIter.hasNext()) {
				Map season = (Map) seasonsIter.next();
				boolean currentSeason =  (boolean) season.get("IsCurrent");
				String currentSeasonId = (String) season.get("ID");
				if (currentSeason) {
					Map content = (Map) jSONMap.get("content");
					List list = (List) content.get(currentSeasonId);
					if (list != null) {
						Iterator userIter = list.iterator();
						// SchoolType schoolType = getSchoolName(url);
						while (userIter.hasNext()) {
							PlayerDoc subject = new PlayerDoc();
							// subject.setSchoolName(schoolType.getShoolName());
							// subject.setSchoolId(schoolType.getSchoolId());
							Map user = (Map) userIter.next();
							if (user.get("FirstName") == null) {
								continue;
							} else {
								subject.setName((String) user.get("FirstName")
										+ " " + (String) user.get("LastName"));
							}

							ArrayList<String> positions = (ArrayList<String>) user
									.get("Positions");
							Iterator<String> posIt = positions.iterator();
							String pos = "";
							while (posIt.hasNext()) {
								pos = pos + ";" + posIt.next();
							}
							if (pos.equals("")) {
								pos = "HUDL";
							}
							subject.setPos(pos);

							if (user.get("GraduationYear") != null) {
								Double yearStr = (Double) user
										.get("GraduationYear");
								int gradYear = Integer.parseInt(Double.toString(yearStr).substring(0,Double.toString(yearStr).length()-2 ));
								subject.setGradYear(gradYear);
							}
							if (user.get("ImperialHeight") != null) {
								String height = (String) user
										.get("ImperialHeight");
								subject.setHeight(height.replaceAll("[^0-9.]",
										""));
							}
							if (user.get("ImperialWeight") != null) {
								String weightStr = (String) user
										.get("ImperialWeight");
								weightStr = weightStr.replaceAll("[^0-9.]", "");
								if (!weightStr.equals("")) {
									int weight = new Integer(weightStr);
									subject.setWeight(weight);
								}
							}
						}
					}
				}
			}
		}
		return res;
	}
}
