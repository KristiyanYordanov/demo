package kris.java.config;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
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
	
	@RequestMapping(value = "/getjsoneditor", method = RequestMethod.GET)
	public @ResponseBody String getShopInJSON1222(){
		String res = "";
		String filename = "exampl2.json";
		res = Util.readFromFileBuffered(new File(filename));
		return res;
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
		String res = "";
		Sort sort = null;
		List<PlayerDoc> list = null;
		
		if (iSortCol_0.equals("0") && sSortDir_0.equals("asc")) {
			sort = new Sort(Sort.Direction.ASC, "name");
		}
		else if (iSortCol_0.equals("0") && sSortDir_0.equals("desc")){
			sort = new Sort(Sort.Direction.DESC, "name");
		}
		if (sSearch != null && !sSearch.equals("")) {
			list = playerRepository.findByNameRegex(sSearch, sort);
//			Query query2 = new Query(Criteria.where("tweet").elemMatch(Criteria.where("tweetId").is(sSearch)));
//			query2.fields().position("tweet", 1);
//			Query query = new Query();
//			Query q =  query.addCriteria(Criteria.where("name").regex(sSearch,"i"));
		}
		else {
			list = playerRepository.findAll(sort);
		}

		DataTableJsonObject e = new DataTableJsonObject();
		e.setITotalRecords(list.size());
		e.setITotalDisplayRecords(list.size());
		e.setSEcho(sEcho);
		
		int start = new Integer(iDisplayStart);
		int length = new Integer(new Integer(iDisplayLength)+new Integer(iDisplayStart));
		if (length == -1) {
			e.setAaData(list);
		}
		else if (list.size() > length) {
			e.setAaData(list.subList(start, length));
		}
		else {
			e.setAaData(list);
		}
		res = e.toString();
		
		return res;
	}

}
