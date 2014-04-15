package kris.java.config;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	MongoTemplate mongoTemplate;
	
	// @Autowired
	// PlayerService playerService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");

		return "home";

	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(ModelMap model, Principal principal) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Object custom = (Object) authentication == null ? null : authentication
				.getPrincipal();
		String name = principal.getName();
		model.addAttribute("username", name);
		model.addAttribute("message", "Spring Security Custom Form example");
		return "home";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/home1", method = RequestMethod.GET)
	public String home1(ModelMap model) {
		// List<Player> list = playerService.findAll();
		// model.addAttribute("players", list);
		List<PlayerDoc> list = playerRepository.findAll();
		model.addAttribute("players", list);

		List<PlayerDoc> list1 = playerRepository.findByName("Wesley Scott");
		model.addAttribute("player", list1);
		return "home1";
	}

	@RequestMapping(value = "/home2", method = RequestMethod.GET)
	public String home2(ModelMap model) {
		return "home2";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		return "register";
	}

	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessdenied(ModelMap model) {
		return "accessdenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}

	@RequestMapping(value = "/admin/adminList", method = RequestMethod.GET)
	public String adminList(ModelMap model) {
		return "admin/adminList";
	}

	@RequestMapping(value = "/user/userList", method = RequestMethod.GET)
	public String userList(ModelMap model) {
		return "user/userList";
	}

	@RequestMapping(value = "/error500", method = RequestMethod.GET)
	public String error500(ModelMap model) {
		return "error500";
	}

	@RequestMapping(value = "/error404", method = RequestMethod.GET)
	public String error404(ModelMap model) {
		return "error404";
	}

	@RequestMapping(value = "/returnjsonpage", method = RequestMethod.POST)
	public @ResponseBody
	PlayerDoc getShopInJSON(ModelMap model) {
		return new PlayerDoc("kris", "fuenlabrada");
	}

	@RequestMapping(value = "/getjson", method = RequestMethod.GET)
	public @ResponseBody
	PlayerDoc getShopInJSON2(ModelMap model) {
		return new PlayerDoc("kris", "fuenlabrada");
	}

	@RequestMapping(value = "/tryjson", method = RequestMethod.GET)
	public String getShopInJSON1(ModelMap model) {
		return "tryjson";
	}

	@RequestMapping(value = "/getjsonexample", method = RequestMethod.GET)
	public @ResponseBody
	String getShopInJSON122(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "iSortCol_0") String sortColumn,
			@RequestParam(value = "sSearch") String sSearch,
			@RequestParam(value = "sSortDir_0") String sSortDir,
			@RequestParam(value = "iDisplayLength") String iDisplayLength,
			@RequestParam(value = "iDisplayStart") String iDisplayStart,
			@RequestParam(value = "iSortCol_0") String iSortCol_0,
			@RequestParam(value = "sSortDir_0") String sSortDir_0,
			@RequestParam(value = "sEcho") String sEcho) throws IOException,
			JSONException {
//		System.out.println("sortColumn = " + sortColumn);// number of sorting
															// column zero basec
//		System.out.println("sSearch = " + sSearch); // search string every
													// column
//		System.out.println("sSortDir = " + sSortDir); // sorting direction asc
														// or desc
//		System.out.println("iDisplayLength = " + iDisplayLength); // chosen from
																	// drop down
																	// menu 10,
																	// 25, 50,
																	// 100
//		System.out.println("iDisplayStart = " + iDisplayStart); // every page
																// increase with
																// iDisplayLength
		System.out.println("sEcho = " + sEcho);
		

		Map<String, String[]> parameters = request.getParameterMap();
		for (String key : parameters.keySet()) {
//			 System.out.println(key);
			String[] vals = parameters.get(key);
			for (String val : vals) {
//				 System.out.println(" -> " + val);
			}
		}
		
		System.out.println("sSortDir_0 = " + sSortDir_0);
		System.out.println("iSortCol_0 = " + iSortCol_0);
		
		
		Sort sort = null;
		List<PlayerDoc> list = null;
		if (iSortCol_0.equals("0") && sSortDir_0.equals("asc")) {
//			Sort sortByNameAsc = new Sort(Sort.Direction.ASC, "name"); 
			sort = new Sort(Sort.Direction.ASC, "name");
			System.out.println("asc");
		}
		else if (iSortCol_0.equals("0") && sSortDir_0.equals("desc")){
//			Sort sortByNameDesc = new Sort(Sort.Direction.DESC, "name");
			sort = new Sort(Sort.Direction.DESC, "name");
			System.out.println("desc");
		}
		if (sSearch != null && !sSearch.equals("")) {
			list = playerRepository.findByNameRegex(sSearch);
		}
		else {
			list = playerRepository.findAll(sort);
		}
		
		 
//		model.addAttribute("players", list.subList(new Integer(iDisplayStart), new Integer(iDisplayLength)));
		String[] cols = { "id", "firstName", "lastName", "gender", "address",
				"grade" };
		String indexColumn = "id";
		String sTable = "example";

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sEcho", "1");
		jsonObject.put("iTotalRecords", list.size());
		jsonObject.put("iTotalDisplayRecords", new Integer(iDisplayLength));
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(list);
		jsonObject.put("aaData", jsonArray);
		JSONArray innerArray = new JSONArray();
		for (PlayerDoc p : list) {
			innerArray.put(p);
		}

		
		DataTableJsonObject e = new DataTableJsonObject();
		e.setITotalRecords(list.size());
		e.setITotalDisplayRecords(list.size());
		e.setSEcho(sEcho);
		int start = new Integer(iDisplayStart);
		int length = new Integer(new Integer(iDisplayLength)+new Integer(iDisplayStart));
		System.out.println("iDisplayStart = " + start);
		System.out.println("iDisplayLength +iDisplayStart = " +  length);
		if (list.size() > length) {
			e.setAaData(list.subList(start, length));
		}
		else {
			e.setAaData(list);
		}
		

//		Util.writeInFileBuffered(jsonObject.toString(), new File(
//				"C:\\Users\\kris\\Desktop\\jsonObject.txt"));
//		Util.writeInFileBuffered(jsonArray.toString(), new File(
//				"C:\\Users\\kris\\Desktop\\jsonArray.txt"));
//		Util.writeInFileBuffered(e.toString().toString(), new File(
//				"C:\\Users\\kris\\Desktop\\json.txt"));
		// System.out.println("jsonObject = " + jsonObject );
		// System.out.println("jsonArray = " + jsonArray );
		String res = "";
//		res = Util.readFromFileBuffered(new File(
//				"C:\\Users\\kris\\Desktop\\exampejson.json"));
		// System.out.println(res);
		 res = e.toString();
		return res;
	}

}
