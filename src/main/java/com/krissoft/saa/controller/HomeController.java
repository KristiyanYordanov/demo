package com.krissoft.saa.controller;

import java.io.File;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.repository.PlayerRepository;
import com.krissoft.saa.util.Util;

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
}
