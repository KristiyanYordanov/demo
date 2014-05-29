package com.krissoft.saa.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.supercsv.exception.SuperCsvException;
import org.supercsv.exception.SuperCsvReflectionException;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.google.gson.Gson;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.repository.PlayerRepository;
import com.krissoft.saa.util.MyUtil;

/**
 * 
 * @author kris
 */
public class HudlModel {

	@Autowired
	PlayerRepository playerRepository;

	public HudlModel(PlayerRepository playerRepository) {
		super();
		this.playerRepository = playerRepository;
	}
	
	public HudlModel() {
		super();
	}
	public static void processPages() throws Exception {
		ICsvBeanWriter beanWriter = null;
		final String[] header = new String[] { "name","schoolName", "state", "schoolCity", "pos", "gradYear",
				"height", "weight" };
		try {
			File file = new File(System.getProperty("java.io.tmpdir")
					+ System.getProperty("file.separator") + "hudl.csv");
			beanWriter = new CsvBeanWriter(new FileWriter(file),
					CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(header);
			beanWriter.flush();
			for (int i = 0; i < teams.length; i++) {
				String link = "http://www.hudl.com/team/";
				String teamId = teams[i];
				URL url = new URL("http://www.hudl.com/teamprofile/section");
				String postParams = "{\"teamId\":" + teamId
						+ ",\"tabId\":\"roster\"}";
				
				SchoolType school = getSchool(new URL(link+teamId));
				String jsonText = MyUtil.fullPost(url, postParams);
				List<PlayerDoc> players = getElements(jsonText, link + teamId);
				System.out.println("Found " + players.size() + " in " + teamId);
				if (players.size() > 0) {
					for (PlayerDoc player : players) {
						try {
							player.setSchoolName(school.getShoolName());
							player.setState(school.getSchoolState());
							player.setSchoolCity(school.getSchoolCity());
							beanWriter.write(player, header);
							//playerRepository.save(player);
							beanWriter.flush();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (NullPointerException e) {
							e.printStackTrace();
						} catch (SuperCsvReflectionException e) {
							e.printStackTrace();
						} catch (SuperCsvException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}

	}

	@Async
	public static void run() {
		try {
			processPages();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] argc) throws Exception {
		 run();
	}

	public static SchoolType getSchool(URL link) throws Exception {
		String page = MyUtil.get(link).replaceAll("\r\n", "");
		//System.out.println(page);
		Pattern titlePatter = Pattern.compile("<h1 title=\"(.*?)\">(.*?)</h1>");
		Matcher titleMatcher = titlePatter.matcher(page);
		String schoolName = "";
		while (titleMatcher.find()) {
			schoolName = titleMatcher.group(1).replace(" High School", "").replace(" HS", "");
		}
		HudlModel outClass = new HudlModel();
		HudlModel.SchoolType inner = outClass.new SchoolType(); 
		inner.setShoolName(schoolName);
		
		Pattern stateAndCityPatter = Pattern.compile("<p class=\"school\">(.*?)<span>(.*?)</span>");
		Matcher stateAndCityMatcher = stateAndCityPatter.matcher(page);
		String state = "";
		String city = "";
		while (stateAndCityMatcher.find()) {
			String res = stateAndCityMatcher.group(2).replaceAll("\\s+", "").replaceAll("-", "");
			String[] r = res.split(",");
			state = r[1];
			city = r[0];
			inner.setSchoolState(state);
			inner.setSchoolCity(city);
		}
		return inner;
	}

	public boolean isBaseballProfile(String page) throws Exception {
		boolean isBaseballProfile = page.toLowerCase().contains("baseball")
				&& page.toLowerCase().contains("high school");
		return isBaseballProfile;
	}

	class SchoolType {
		String shoolName;
		String schoolState;
		String schoolCity;

		public String getShoolName() {
			return shoolName;
		}

		public void setShoolName(String shoolName) {
			this.shoolName = shoolName;
		}

		public String getSchoolState() {
			return schoolState;
		}

		public void setSchoolState(String schoolState) {
			this.schoolState = schoolState;
		}

		public String getSchoolCity() {
			return schoolCity;
		}

		public void setSchoolCity(String schoolCity) {
			this.schoolCity = schoolCity;
		}
		
		
	}

	public static List<PlayerDoc> getElements(String jsonText, String url)
			throws JSONException, Exception {
		List<PlayerDoc> res = new ArrayList<PlayerDoc>();
		if (jsonText.contains("seasons")) {
			Gson gson = new Gson();
			Map jSONMap = new HashMap();
			jSONMap = (Map) gson.fromJson(jsonText, jSONMap.getClass());

			List header = (List) jSONMap.get("seasons");
			Iterator seasonsIter = header.iterator();
			while (seasonsIter.hasNext()) {
				Map season = (Map) seasonsIter.next();
				boolean currentSeason = (Boolean) season.get("IsCurrent");
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
								pos = pos + posIt.next() + ",";
							}
							if (pos.equals("")) {
								pos = "HUDL";
							}
							subject.setPos(pos);

							if (user.get("GraduationYear") != null) {
								Double yearStr = (Double) user
										.get("GraduationYear");
								int gradYear = Integer.parseInt(Double
										.toString(yearStr).substring(
												0,
												Double.toString(yearStr)
														.length() - 2));
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
							res.add(subject);
						}
					}
				}
			}
		}
		return res;
	}
	
	private final static String[] teams = { "100003", "100018", "100114",
			"100329", "10039", "100438", "100555", "100561", "10062", "100705",
			"100774", "10083", "100870", "100885", "100902", "100955", "10110",
			"10138", "10158", "101646", "101680", "101736", "101878", "101886",
			"101891", "101904", "102010", "102014", "10202", "102127", "10219",
			"102192", "102339", "102498", "10250", "102527", "102572",
			"102574", "102649", "102877", "102919", "102965", "102972",
			"102978", "103022", "103061", "103173", "103175", "103211",
			"103239", "10329", "103333", "103343", "103411", "10348", "103489",
			"103506", "103555", "103556", "103570", "103608", "103618",
			"10366", "103680", "103727", "103753", "103857", "103880",
			"103932", "104024", "104088", "10410", "104168", "104184",
			"104185", "104195", "104225", "104256", "104387", "104409",
			"104426", "104442", "104492", "104497", "104499", "104509",
			"104540", "104545", "104621", "104641", "104668", "104692",
			"10472", "104726", "104755", "104807", "104873", "104951",
			"104980", "104982", "105000", "105047", "105105", "105109",
			"105152", "105170", "105174", "105258", "105297", "105356",
			"105396", "105404", "105435", "105455", "105482", "10556",
			"105642", "105688", "105774", "105819", "105910", "105911",
			"105912", "105946", "105955", "105970", "10606", "106124",
			"106164", "10624", "106241", "10636", "106497", "106517", "106601",
			"106653", "10669", "106835", "106848", "10688", "106982", "106985",
			"107066", "107075", "107076", "107081", "10710", "107102",
			"107117", "10717", "10724", "10733", "107349", "10735", "107382",
			"107405", "10743", "107486", "107746", "107761", "107762",
			"107963", "108012", "108018", "108022", "108035", "108040",
			"108105", "108130", "108233", "108264", "108288", "108310",
			"10834", "108429", "108449", "10850", "10853", "108581", "108654",
			"108683", "108750", "108765", "108766", "108821", "108973",
			"108993", "109067", "109224", "109353", "109398", "10946",
			"109468", "109516", "109755", "109766", "109902", "109968",
			"110113", "110130", "110171", "110174", "110180", "110201",
			"11023", "110298", "110321", "110347", "110429", "110432", "11053",
			"110567", "110644", "110654", "110775", "110782", "110900",
			"110965", "110996", "111141", "111147", "111155", "111169",
			"11119", "111200", "11128", "111291", "11149", "111509", "111586",
			"111682", "111703", "111770", "111797", "111984", "111991",
			"112030", "11205", "112077", "112098", "112151", "112224",
			"112271", "112276", "112411", "112501", "112504", "11252",
			"112538", "112591", "112596", "112668", "112723", "112765",
			"112806", "112816", "11285", "112870", "112956", "11300", "113032",
			"113036", "113062", "113079", "113276", "113313", "113535",
			"113542", "113582", "113590", "113606", "113616", "113623",
			"113627", "11370", "113709", "113744", "113745", "113746",
			"113761", "113776", "113824", "113868", "113879", "113916",
			"113920", "113921", "113991", "113995", "114022", "114048",
			"114063", "114064", "114075", "114097", "114098", "114100",
			"114111", "114115", "114138", "114144", "114154", "114156",
			"114175", "11441", "11442", "114497", "114498", "11451", "114585",
			"114618", "114626", "114673", "114678", "114679", "114751",
			"11493", "11496", "11512", "115702", "115722", "115730", "115792",
			"115799", "115821", "115833", "115834", "11584", "115845",
			"115846", "115847", "11585", "115893", "115906", "115907",
			"115910", "115913", "115916", "115917", "115919", "115920",
			"115931", "115934", "115939", "115944", "115964", "11599",
			"116031", "116043", "116065", "11607", "116091", "116161",
			"116165", "116205", "116221", "116226", "116230", "116254",
			"116288", "116320", "116338", "116347", "11639", "116409",
			"116480", "116529", "116533", "116534", "116535", "116548",
			"116561", "116569", "116581", "116587", "116597", "116620",
			"116625", "116637", "116671", "116680", "116696", "116733",
			"116734", "116735", "116736", "116737", "116738", "116739",
			"116753", "116759", "116767", "116768", "116773", "116776",
			"11682", "116854", "116856", "116860", "116883", "116889",
			"116901", "116916", "116927", "116935", "116936", "116996",
			"116997", "117042", "117046", "117056", "117066", "117069",
			"117137", "117149", "11716", "117187", "117212", "117224",
			"117254", "117256", "117258", "117259", "117276", "117289",
			"117290", "117291", "117292", "117309", "117311", "117314",
			"117339", "117398", "117399", "117407", "117415", "11742",
			"117429", "117433", "117434", "117436", "117438", "117440",
			"117471", "117472", "117473", "117475", "117476", "117503",
			"117508", "11755", "117594", "117689", "117721", "117728",
			"117737", "117752", "117763", "117772", "1178", "117805", "117806",
			"117833", "117834", "117867", "117870", "11800", "118001",
			"118026", "118027", "118081", "118083", "118094", "118095",
			"118104", "118116", "118138", "118183", "118195", "118201",
			"118214", "118230", "118256", "118282", "118285", "118294",
			"118295", "118302", "118308", "118325", "118360", "118369",
			"118400", "118402", "118420", "118437", "118439", "118443",
			"118447", "118461", "11847", "118551", "118574", "118578",
			"118588", "118635", "118636", "118637", "118659", "118664",
			"118700", "118705", "118706", "118708", "118709", "118764",
			"118765", "118774", "118780", "118826", "118827", "118864",
			"118950", "118962", "118966", "118998", "119001", "119004",
			"119005", "119006", "119015", "119023", "119065", "119071",
			"119100", "119138", "119151", "119152", "119162", "119182",
			"119184", "119185", "11920", "119207", "119235", "119260",
			"119265", "119271", "119308", "119317", "119321", "119322",
			"119324", "119358", "119361", "119362", "119393", "119399",
			"119413", "119441", "119443", "119456", "119463", "119465",
			"119481", "119486", "119509", "119524", "119539", "119558",
			"119584", "119586", "119587", "11964", "119648", "119654",
			"119662", "119673", "119698", "119724", "119731", "119772",
			"119787", "119798", "119820", "119832", "119876", "11990",
			"119921", "119938", "119948", "119957", "119972", "119978",
			"119991", "120064", "120088", "120111", "120228", "120236",
			"120245", "120286", "120327", "120374", "120387", "120390",
			"120391", "120411", "120417", "120418", "120420", "120429",
			"120430", "120455", "120459", "120478", "120480", "120488",
			"120495", "120503", "120508", "120528", "120577", "120582",
			"120627", "120648", "120677", "120678", "120700", "120741",
			"120795", "120854", "120867", "120875", "120876", "120877",
			"120892", "120931", "120954", "121050", "121059", "121070",
			"121138", "121161", "121173", "121201", "121252", "121262",
			"121273", "121281", "12131", "121345", "121366", "121379",
			"121466", "121467", "121508", "121546", "121558", "121573",
			"121577", "121580", "121669", "121771", "121788", "121915",
			"121925", "121972", "121985", "12210", "12231", "12270", "12298",
			"12340", "12365", "12412", "12427", "12457", "12468", "12469",
			"12483", "12512", "12570", "12572", "12670", "12686", "12746",
			"12767", "12791", "12792", "12841", "12847", "12897", "12935",
			"12944", "12963", "13023", "13050", "13073", "13091", "13146",
			"13147", "13148", "13198", "13221", "13243", "13245", "13259",
			"1330", "13302", "13307", "13358", "13434", "13459", "13475",
			"13490", "13509", "13518", "13597", "13643", "13658", "13673",
			"13727", "13758", "13788", "14021", "14048", "14068", "14074",
			"14097", "14112", "14131", "14172", "14190", "14249", "14281",
			"14282", "14311", "14343", "14547", "14637", "14657", "1466",
			"14678", "14698", "14774", "14856", "14882", "14905", "14932",
			"15113", "15141", "15248", "15293", "15324", "15362", "15393",
			"15420", "15453", "15476", "15508", "15533", "1556", "15602",
			"15654", "15662", "15688", "15716", "15826", "15893", "15909",
			"15922", "15940", "15983", "15996", "16003", "16071", "16116",
			"16167", "16244", "1627", "16332", "16371", "16403", "1645",
			"16570", "16668", "16843", "17428", "17558", "17571", "17640",
			"17676", "17722", "17770", "17789", "17847", "17877", "18020",
			"18122", "18141", "18205", "18261", "18321", "18399", "18489",
			"18507", "18655", "18731", "18751", "18838", "18902", "18995",
			"190", "19049", "19098", "19104", "19120", "19154", "19191",
			"19252", "19268", "19368", "19515", "19520", "19685", "19814",
			"19909", "19920", "20007", "20042", "20079", "2020", "20208",
			"20220", "20230", "20261", "20307", "20478", "20484", "20525",
			"20639", "20640", "20661", "20677", "20708", "20722", "2081",
			"20857", "20949", "20990", "2106", "21069", "21104", "21140",
			"21180", "21353", "21405", "215", "21507", "2151", "21518",
			"21537", "21639", "21671", "21677", "21736", "21863", "21949",
			"2213", "2236", "22531", "2262", "227", "23211", "23355", "2340",
			"23451", "23478", "23508", "2357", "23603", "23684", "2370",
			"23801", "23956", "23990", "240", "24008", "24159", "24194",
			"24346", "2439", "24412", "24426", "24444", "24554", "24611",
			"24693", "24779", "248", "24813", "24850", "24920", "25093",
			"25137", "25147", "25298", "25317", "25506", "25538", "25548",
			"25652", "25679", "25835", "25925", "26127", "26249", "2632",
			"26321", "26439", "26545", "2656", "26631", "26801", "26807",
			"26861", "26925", "26981", "27052", "27220", "27240", "27241",
			"27331", "27392", "27441", "27454", "27532", "2763", "27639",
			"27657", "27666", "27815", "2783", "27837", "27945", "27947",
			"28041", "28099", "28149", "28199", "28232", "28447", "28487",
			"2849", "28534", "28578", "28591", "28639", "28651", "3034",
			"3055", "3091", "3180", "3210", "3272", "3434", "3481", "3616",
			"37402", "37433", "3744", "37467", "37478", "37489", "37491",
			"37519", "37549", "37595", "37608", "37611", "37689", "37720",
			"37735", "37805", "37848", "37867", "37893", "379", "37901",
			"37929", "38079", "38119", "38173", "38201", "38237", "38283",
			"38371", "38373", "3906", "39208", "39235", "39461", "39470",
			"39485", "39504", "39514", "39623", "39667", "39714", "39771",
			"39818", "39833", "39864", "39908", "39928", "39968", "39969",
			"39976", "40021", "40054", "40077", "40093", "40114", "40133",
			"40141", "40273", "40426", "40481", "40510", "40558", "40570",
			"40597", "40670", "40677", "40702", "40707", "40759", "40822",
			"40832", "40906", "40958", "40988", "40997", "41002", "41013",
			"41028", "41034", "41102", "41131", "41191", "4148", "415",
			"41566", "41571", "41615", "41639", "41707", "41718", "41722",
			"41750", "41752", "41796", "41797", "41800", "41857", "41867",
			"41890", "41894", "41908", "41924", "41936", "4200", "42008",
			"42038", "42081", "42089", "42106", "42168", "42182", "42219",
			"42305", "42308", "42315", "42332", "42359", "42360", "42369",
			"42393", "4242", "42427", "42460", "42464", "42491", "42510",
			"42536", "42573", "42585", "42626", "42629", "42630", "42641",
			"42654", "42729", "42734", "42737", "42743", "42758", "42825",
			"4289", "42912", "42913", "42914", "42935", "42936", "42943",
			"42949", "42957", "42973", "42995", "43012", "43016", "43047",
			"43062", "43064", "43085", "43089", "43098", "43099", "43151",
			"43169", "4317", "43170", "43183", "43206", "43239", "43255",
			"43267", "43276", "43338", "43339", "43340", "43345", "43359",
			"43370", "43385", "43398", "43483", "43484", "43500", "43503",
			"43530", "43537", "43557", "43584", "43597", "43647", "43668",
			"43721", "43744", "43750", "43791", "43799", "43808", "43840",
			"43848", "43867", "43873", "43914", "43994", "44008", "44027",
			"44044", "44055", "44091", "44107", "44120", "44122", "44123",
			"44124", "44126", "44133", "44143", "44153", "44161", "44176",
			"44379", "44388", "44414", "44422", "44460", "44464", "44488",
			"4451", "44520", "44537", "44556", "44563", "44583", "44585",
			"44603", "44618", "44629", "44649", "44656", "44661", "4467",
			"44725", "44744", "44768", "44785", "44799", "44845", "44919",
			"44930", "44935", "44985", "45007", "4505", "45055", "45056",
			"45067", "45068", "45076", "45082", "45104", "45190", "45208",
			"45209", "45254", "45316", "45383", "45386", "45391", "45443",
			"4546", "45506", "45518", "45538", "45570", "45594", "45604",
			"45665", "45691", "45705", "45736", "45809", "45824", "4586",
			"45880", "46003", "46010", "46019", "46042", "46058", "46098",
			"46121", "46137", "46174", "46223", "46249", "46256", "46308",
			"46361", "46369", "46384", "46399", "46410", "46452", "46463",
			"46488", "46554", "4656", "46593", "46602", "46618", "46724",
			"46733", "46752", "46841", "46903", "46966", "46974", "46982",
			"46995", "47009", "47020", "47042", "47051", "47147", "47223",
			"47246", "47256", "47284", "47341", "47362", "47380", "47405",
			"47413", "47441", "47489", "47490", "47509", "47545", "47573",
			"47580", "47622", "47697", "47780", "47829", "47855", "47898",
			"47925", "47964", "47998", "48014", "48032", "48104", "48133",
			"48213", "48228", "48303", "48321", "48334", "4837", "48387",
			"48408", "48460", "48554", "48588", "48631", "48650", "48670",
			"48707", "4876", "48763", "48807", "48808", "48833", "48844",
			"48899", "48914", "48940", "48975", "4899", "48995", "49088",
			"49096", "49173", "49247", "49259", "49287", "49354", "49387",
			"49398", "4948", "49506", "49524", "49586", "49620", "49650",
			"49699", "49715", "49746", "49761", "49786", "49817", "49895",
			"49907", "49937", "49947", "49974", "49992", "5001", "50054",
			"50156", "50162", "50182", "50200", "50277", "50317", "50329",
			"50336", "50364", "50446", "50472", "50476", "50498", "50542",
			"50564", "50608", "50614", "50632", "50709", "50746", "50760",
			"50772", "50880", "50884", "50976", "50997", "51011", "51041",
			"51083", "51215", "5124", "51329", "51366", "51431", "51456",
			"51471", "51483", "51531", "5155", "51667", "51727", "51759",
			"51855", "51898", "51946", "51960", "51968", "5199", "51990",
			"52035", "52125", "52141", "52269", "52301", "52306", "52317",
			"52354", "52395", "52401", "52409", "52427", "52478", "52523",
			"5262", "52632", "52633", "52634", "52659", "52687", "52748",
			"52772", "52790", "52821", "52878", "52952", "53030", "53043",
			"53048", "53061", "53080", "53123", "53209", "53210", "53284",
			"53324", "53329", "53366", "53370", "53588", "53626", "53628",
			"53645", "53663", "53733", "53843", "53910", "53966", "53968",
			"53986", "54009", "54023", "54114", "54184", "54217", "5424",
			"54244", "54261", "54295", "54526", "54567", "54633", "54670",
			"54680", "54703", "54846", "54875", "54887", "54899", "54942",
			"550", "55088", "55150", "55264", "55288", "5530", "55350",
			"55380", "55394", "55407", "55452", "55506", "55519", "55551",
			"55659", "55705", "55948", "56175", "56256", "56286", "56342",
			"56383", "56542", "56631", "56715", "56754", "56786", "57043",
			"57125", "57198", "5729", "57534", "57557", "57668", "57669",
			"57672", "57673", "57707", "57728", "57788", "57814", "57878",
			"57951", "57984", "58003", "58021", "58231", "58295", "5860",
			"5889", "5931", "59463", "59552", "5959", "59758", "5977", "59784",
			"59806", "59851", "5987", "59904", "59905", "59940", "59960",
			"59996", "60021", "60084", "60124", "60166", "60249", "60316",
			"60328", "60367", "60409", "60496", "60554", "60655", "60660",
			"60666", "60694", "60773", "60811", "60832", "60868", "60911",
			"60930", "60950", "60956", "61041", "61052", "61059", "61104",
			"61157", "6122", "61272", "61451", "61463", "6152", "61612",
			"6165", "61651", "61711", "61921", "61958", "61984", "62044",
			"62064", "62155", "62192", "62258", "62276", "62312", "62365",
			"62471", "62487", "6265", "62740", "62810", "62837", "62847",
			"62856", "62869", "62870", "62883", "62908", "62979", "63098",
			"63138", "63163", "63192", "63217", "63387", "63407", "63445",
			"63468", "6356", "63583", "63584", "63633", "63644", "63647",
			"63837", "63849", "63969", "64066", "64080", "64099", "64126",
			"64131", "64251", "64291", "64332", "64487", "64550", "64581",
			"64592", "64594", "64634", "6474", "64751", "64752", "64753",
			"64829", "64842", "64999", "65010", "65011", "65012", "65043",
			"65179", "65190", "65254", "6526", "65318", "65319", "65386",
			"65421", "65458", "65469", "65506", "65646", "65750", "65779",
			"65799", "65918", "65921", "66012", "66081", "66100", "66103",
			"66142", "66242", "66307", "66387", "66430", "66525", "66538",
			"66567", "66587", "66588", "66592", "66667", "66689", "66722",
			"66759", "66760", "66771", "66892", "66902", "6691", "66915",
			"66960", "67064", "6707", "67101", "67121", "67134", "67168",
			"67190", "6727", "67279", "67293", "67326", "67597", "67737",
			"67755", "67861", "67900", "67929", "67947", "67967", "67993",
			"68099", "68129", "68326", "68385", "6844", "68447", "68544",
			"68606", "68653", "68683", "68704", "68716", "68740", "68784",
			"68817", "68861", "69019", "69068", "69145", "69174", "69204",
			"69274", "69298", "69311", "69318", "69323", "69335", "69386",
			"69424", "69447", "69461", "69573", "6959", "69594", "69674",
			"69677", "69714", "69763", "69869", "69891", "69895", "69897",
			"69898", "69931", "69943", "69973", "69984", "69988", "70003",
			"70033", "70163", "70189", "70275", "70347", "70576", "7059",
			"70600", "70673", "70676", "70690", "70967", "71092", "71365",
			"71403", "71405", "71471", "7159", "71731", "71774", "71807",
			"71844", "72002", "72030", "72050", "72051", "72052", "72053",
			"72054", "72071", "72095", "72160", "72205", "72398", "72406",
			"72447", "72482", "72635", "72683", "72828", "72861", "72882",
			"72894", "72984", "72990", "73048", "73064", "73105", "73119",
			"73175", "73184", "73273", "73343", "73383", "73421", "73442",
			"73464", "73479", "73485", "7349", "73494", "73544", "73545",
			"73547", "73593", "73647", "7365", "73680", "73706", "738",
			"73805", "73806", "73865", "73884", "73910", "73945", "74120",
			"74125", "74135", "74150", "74202", "74253", "74282", "74283",
			"74290", "74314", "74357", "74394", "74441", "74442", "74476",
			"74486", "74519", "74520", "74538", "74610", "74615", "74622",
			"74627", "74651", "74671", "74676", "74679", "74680", "7472",
			"74808", "74826", "74834", "74848", "74871", "74885", "74898",
			"74922", "74936", "74937", "74960", "74968", "74999", "75066",
			"75087", "75093", "75095", "75097", "75098", "75124", "75214",
			"75281", "75286", "75326", "75335", "75443", "75444", "75463",
			"75488", "75493", "75526", "75554", "75616", "75646", "75658",
			"75669", "75685", "75690", "75696", "75697", "75698", "75705",
			"75708", "75711", "75736", "75764", "75768", "75772", "75780",
			"75792", "75807", "75816", "75820", "75842", "75876", "75881",
			"75882", "75892", "75897", "75931", "75945", "75975", "75982",
			"75983", "75999", "76004", "76008", "76036", "76046", "76047",
			"76054", "76055", "76081", "76089", "76095", "76106", "76119",
			"76170", "76221", "76238", "76243", "76248", "76262", "76267",
			"76279", "76321", "76322", "76323", "76363", "76369", "76440",
			"76472", "765", "76525", "7654", "76586", "76597", "76628",
			"76634", "76637", "76732", "76741", "76749", "76764", "76771",
			"76778", "76808", "76809", "76823", "76862", "76891", "76902",
			"77046", "77081", "77135", "77158", "77180", "77199", "77200",
			"77211", "77260", "77261", "77273", "77312", "77313", "77315",
			"77316", "77317", "77320", "77376", "77417", "77455", "7747",
			"77494", "77504", "77515", "77538", "77550", "77611", "77628",
			"7763", "77652", "77653", "77654", "77668", "77669", "77670",
			"77674", "77681", "77698", "77743", "77761", "77795", "77796",
			"77797", "77798", "77804", "77820", "77827", "77844", "77866",
			"77880", "77881", "77907", "77914", "77939", "78006", "78008",
			"78052", "78056", "78059", "78183", "78217", "78221", "78230",
			"78259", "78281", "78282", "78330", "78331", "78332", "78333",
			"78357", "78412", "78465", "78466", "78482", "78490", "78493",
			"78521", "78558", "78661", "78677", "78845", "78921", "78927",
			"78936", "78937", "78949", "78986", "78999", "7902", "79042",
			"79084", "79137", "79144", "79154", "79217", "79232", "79265",
			"79273", "79316", "79336", "79339", "79355", "79386", "79399",
			"79410", "79430", "79452", "79454", "79465", "79479", "79545",
			"79600", "79645", "79702", "79727", "79756", "79779", "79856",
			"79893", "79941", "79948", "79963", "79976", "80006", "80022",
			"80077", "80087", "80093", "80115", "80121", "80173", "80193",
			"80237", "80244", "80245", "80263", "80285", "80398", "80415",
			"80506", "80591", "80610", "80642", "80708", "80740", "80750",
			"80770", "80781", "80788", "8081", "80829", "80896", "80901",
			"81074", "81080", "81267", "81295", "81296", "81309", "81312",
			"81326", "81355", "81358", "81382", "81407", "81413", "81438",
			"81471", "81520", "81522", "81524", "81591", "81664", "81740",
			"81760", "81770", "81821", "81833", "81861", "81941", "81966",
			"81978", "81990", "82010", "82038", "82043", "8213", "82171",
			"822", "82204", "82251", "82252", "82264", "82280", "82291",
			"82306", "82345", "82375", "82389", "82444", "82487", "82492",
			"82746", "82923", "82924", "82967", "83056", "83129", "83158",
			"83163", "83180", "83310", "83363", "83438", "83470", "83518",
			"83521", "83584", "83591", "83682", "83709", "83731", "83737",
			"83833", "83834", "83842", "84175", "84184", "84190", "84219",
			"8425", "84312", "84323", "84355", "84527", "84562", "84589",
			"846", "84612", "84636", "84650", "84708", "84722", "84759",
			"84782", "84882", "84895", "84962", "84976", "85035", "85155",
			"85176", "85184", "85210", "85219", "85222", "85225", "85233",
			"85245", "85282", "85284", "85286", "85291", "85328", "85339",
			"85348", "85422", "85588", "85676", "85692", "85729", "85742",
			"85784", "86081", "86563", "86647", "86692", "86737", "86759",
			"86853", "86868", "86961", "86965", "87105", "87185", "87234",
			"87235", "87241", "87260", "87288", "87362", "87425", "87516",
			"87555", "87628", "87667", "87834", "87880", "87901", "88099",
			"88202", "88218", "88235", "88246", "88256", "88257", "88279",
			"88305", "88396", "88444", "88473", "88495", "88578", "88592",
			"88618", "88640", "88662", "88702", "88737", "88746", "88772",
			"88821", "88973", "89006", "89050", "89065", "89080", "89096",
			"89101", "89104", "89148", "89151", "89213", "89241", "89247",
			"89263", "89277", "89323", "89364", "89379", "89402", "8945",
			"89559", "8957", "89579", "89639", "89669", "89685", "89724",
			"89749", "89759", "89776", "89784", "89951", "89984", "90020",
			"90064", "90087", "90104", "90246", "90278", "90334", "9036",
			"90365", "90374", "90401", "90430", "90454", "90513", "90650",
			"90656", "90714", "90794", "90811", "90849", "90865", "90911",
			"9103", "91073", "91137", "91156", "91227", "91248", "91266",
			"91298", "91360", "91445", "9159", "91718", "91749", "91777",
			"91865", "91889", "9191", "9196", "91975", "91987", "92065",
			"92089", "92162", "92183", "92234", "92340", "92423", "92469",
			"92594", "92742", "92775", "92807", "92840", "92871", "92878",
			"92913", "92935", "92982", "93092", "93102", "93206", "93215",
			"93249", "93331", "93383", "93437", "93517", "93766", "93818",
			"93827", "9391", "93946", "93950", "94004", "94007", "94008",
			"94066", "9407", "94117", "94137", "94321", "9435", "94350",
			"94477", "94510", "94533", "94558", "94560", "94627", "94653",
			"94885", "94928", "94931", "95020", "95080", "95092", "95165",
			"95197", "9521", "95302", "95356", "9541", "95431", "95483",
			"95568", "95627", "95789", "9589", "95961", "95982", "95996",
			"96028", "9611", "96149", "96169", "96250", "963", "96362",
			"96469", "96489", "96542", "9658", "96704", "9684", "96843",
			"96850", "96863", "96882", "96964", "9699", "97007", "97031",
			"97151", "9717", "97230", "97263", "97357", "97501", "97507",
			"97532", "97626", "97653", "97684", "97718", "9774", "97741",
			"9787", "97882", "97948", "9804", "98102", "98108", "98116",
			"9813", "98193", "98310", "98334", "98473", "98496", "98498",
			"98526", "98557", "98650", "98660", "98668", "98674", "98675",
			"98746", "98794", "98944", "98993", "99", "9903", "99031", "9904",
			"99077", "99170", "99183", "99217", "99271", "9933", "99330",
			"99440", "9948", "99541", "99552", "99600", "99735", "99835",
			"99944" };
}
