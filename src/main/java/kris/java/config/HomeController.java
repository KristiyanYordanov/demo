package kris.java.config;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	PlayerRepository playerRepository;

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

		PlayerDoc list1 = playerRepository.findByName("Wesley Scott");
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
			@RequestParam(value = "sEcho") String sEcho) throws IOException, JSONException {
		System.out.println("sortColumn = " + sortColumn );//number of sorting column zero basec
		System.out.println("sSearch = " + sSearch ); //search string every column
		System.out.println("sSortDir = " + sSortDir ); //sorting direction asc or desc
		System.out.println("iDisplayLength = " + iDisplayLength ); //chosen from drop down menu 10, 25, 50, 100
		System.out.println("iDisplayStart = " + iDisplayStart ); //every page increase with  iDisplayLength
		System.out.println("sEcho = " + sEcho );
		
		Map<String, String[]> parameters = request.getParameterMap();
	    for(String key : parameters.keySet()) {
//	        System.out.println(key);
	        String[] vals = parameters.get(key);
	        for(String val : vals)
	        {
//	            System.out.println(" -> " + val);
	        }
	    }
		List<PlayerDoc> list = playerRepository.findAll();
		model.addAttribute("players", list);
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
		for (PlayerDoc p: list) {
			innerArray.put(p);
		}
		
		Example e = new Example();
		e.setITotalRecords(list.size());
		e.setITotalDisplayRecords( new Integer(iDisplayLength));
		e.setSEcho("1");
		List<List<PlayerDoc>> aaData1 = new ArrayList<List<PlayerDoc>>();
		aaData1.add(list);
		e.setAaData(aaData1);
		
		
		Util.writeInFileBuffered(jsonObject.toString(), new File("C:\\Users\\kris\\Desktop\\jsonObject.txt"));
		Util.writeInFileBuffered(jsonArray.toString(), new File("C:\\Users\\kris\\Desktop\\jsonArray.txt"));
		Util.writeInFileBuffered(e.toString().toString(), new File("C:\\Users\\kris\\Desktop\\json.txt"));
//		System.out.println("jsonObject = " + jsonObject );
//		System.out.println("jsonArray = " + jsonArray );
		String res = "";
		res =  Util.readFromFileBuffered(new File("C:\\Users\\kris\\Desktop\\exampejson.json"));
//		System.out.println(res);
//	res = e.toString();
		return res;
	}

}
