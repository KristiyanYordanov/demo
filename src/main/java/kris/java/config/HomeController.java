package kris.java.config;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

		Page<PlayerDoc> list1 = playerRepository.findByName("Wesley Scott",
				new PageRequest(0, 1));
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
	public @ResponseBody
	String getShopInJSON1222() {
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
		Page<PlayerDoc> page = null;
		List<PlayerDoc> list = null;
		DataTableJsonObjectPage p = new DataTableJsonObjectPage();

		int start = new Integer(iDisplayStart);
		int pageRows = new Integer(iDisplayLength) + start;
		int size = 0;
		if (iSortCol_0.equals("0") && sSortDir_0.equals("asc")) {
			sort = new Sort(Sort.Direction.ASC, "name");
		} else if (iSortCol_0.equals("0") && sSortDir_0.equals("desc")) {
			sort = new Sort(Sort.Direction.DESC, "name");
		}

		if (pageRows == -1 && sSearch == null) {
			System.out.println("1");
			list = playerRepository.findAll(sort);
			size = list.size();
			DataTableJsonObjectList returnAll = new DataTableJsonObjectList();
			returnAll.setSEcho(sEcho);
			returnAll.setAaData(list);
			returnAll.setITotalRecords(size);
			returnAll.setITotalDisplayRecords(size);
			// return all rows
			return returnAll.toString();
		} else if (sSearch != null && !sSearch.equals("") && pageRows == -1) {
			System.out.println("2");
			size = (int) playerRepository.count(sSearch);
			page = playerRepository.findByNameRegex(sSearch, new PageRequest(
					start, size, sort));
		} else if (sSearch != null && !sSearch.equals("") && pageRows != -1) {
			System.out.println("3");
			size = (int) playerRepository.count(sSearch);
			if (size < pageRows + start) {
				list = playerRepository.findByNameRegex(sSearch, sort).subList(
						start, size);
				System.out.println("31");
				DataTableJsonObjectList returnAll = new DataTableJsonObjectList();
				returnAll.setSEcho(sEcho);
				returnAll.setAaData(list);
				returnAll.setITotalRecords(size);
				returnAll.setITotalDisplayRecords(size);
				return returnAll.toString();
			} else {
				System.out.println("32");
				list = playerRepository.findByNameRegex(sSearch, sort).subList(
						start, pageRows);
				DataTableJsonObjectList returnAll = new DataTableJsonObjectList();
				returnAll.setSEcho(sEcho);
				returnAll.setAaData(list);
				returnAll.setITotalRecords(size);
				returnAll.setITotalDisplayRecords(size);
				return returnAll.toString();
			}

		} else {
			System.out.println("4");
			size = (int) playerRepository.count();
			System.out.println("start = " + start);
			System.out.println("size= " + size);
			System.out.println("pageRows = " + new Integer(iDisplayLength));
			page = playerRepository.findAll(new PageRequest(start, new Integer(
					iDisplayLength), sort));

		}
		p.setSEcho(sEcho);
		p.setAaData(page);
		p.setITotalRecords(size);
		p.setITotalDisplayRecords(size);
		res = p.toString();
		return res;
	}
}
