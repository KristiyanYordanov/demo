package com.krissoft.saa.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.krissoft.saa.bean.Player;
import com.krissoft.saa.config.DataTableJsonObject;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.model.PlayerModel;
import com.krissoft.saa.repository.PlayerRepository;

@Controller
@RequestMapping("players")
public class PlayerController {

	@Autowired
	PlayerRepository playerRepository;

	@RequestMapping(value = "/playersjson", method = RequestMethod.GET)
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

		Sort sort = null;
		Page<PlayerDoc> page = null;
		DataTableJsonObject jsonObject = new DataTableJsonObject();

		int start = new Integer(iDisplayStart);
		int pageRows = new Integer(iDisplayLength);
		int size = 0;
		if (iSortCol_0.equals("0") && sSortDir_0.equals("asc")) {
			sort = new Sort(Sort.Direction.ASC, "name");
		} else if (iSortCol_0.equals("0") && sSortDir_0.equals("desc")) {
			sort = new Sort(Sort.Direction.DESC, "name");
		}

		if (pageRows == -1 && sSearch.equals("")) {
			size = (int) playerRepository.count();
			page = playerRepository.findAll(new PageRequest(start, size, sort));
		} else if (sSearch != null && !sSearch.equals("") && pageRows == -1) {
			System.out.println("2");
			size = (int) playerRepository.count(sSearch);
			page = playerRepository.findByNameRegex(sSearch, new PageRequest(
					start, size, sort));
		} else if (sSearch != null && !sSearch.equals("") && pageRows != -1) {
			System.out.println("3");
			size = (int) playerRepository.count(sSearch);
			int pageNumber = start / pageRows;
			page = playerRepository.findByNameRegex(sSearch, new PageRequest(pageNumber,pageRows, sort));
		} else {
			System.out.println("4");
			size = (int) playerRepository.count();
			int pageNumber1 = start / pageRows;
			if (pageNumber1 == 0) {
				pageNumber1 = 1;
			}
			page = playerRepository.findAll(new PageRequest(pageNumber1,
					pageRows, sort));
		}
		jsonObject.setSEcho(sEcho);
		jsonObject.setAaData(page);
		jsonObject.setITotalRecords(size);
		jsonObject.setITotalDisplayRecords(size);
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/user/players", method = RequestMethod.GET)
	public String home2(ModelMap model) {
		return "user/players";
	}
	
	@RequestMapping(value = "/user/csv", method = RequestMethod.GET)
	public String player(ModelMap model) throws Exception {
		PlayerModel playerModel = new PlayerModel();
		// List<Player> res = playerModel.readWithCsvBeanReader();
		List<PlayerDoc> res = playerModel.readWithCsvBeanReaderForPlayerDoc();
		System.out.println("res size= " + res.size());

		for (PlayerDoc entity : res) {
			// playerService.create(entity);
			playerRepository.save(entity);
		}

		model.addAttribute("players", res);
		model.addAttribute("fileHeader",
				Arrays.toString(playerModel.getHeaders()));

		return "user/csv";
	}

	@RequestMapping(value = "/user/import", method = RequestMethod.POST)
	public View uploadPlayer(@RequestParam("myFile") final MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("test" + "-uploaded")));
				stream.write(bytes);
				stream.close();
				Reader reader = new InputStreamReader(new ByteArrayInputStream(
						bytes));
				BufferedReader bf = new BufferedReader(reader);
				String line = bf.readLine();
				while (line != null) {
					String[] players = line.split(",");
					Player player = new Player();
					line = bf.readLine();
				}
				return new RedirectView("/raviser/Player");
			} catch (Exception e) {
				return new RedirectView("/raviser/Player");
			}
		} else {
			return new RedirectView("/raviser/Player");
		}
	}
}
