package com.krissoft.saa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.krissoft.saa.config.DataTableJsonObject;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.model.PlayerModel;
import com.krissoft.saa.repository.PlayerRepository;
import com.krissoft.saa.util.UploadedFile;

@Controller
@RequestMapping("players")
public class PlayerController {

	private static final Logger logger = LoggerFactory
			.getLogger(PlayerController.class);

	@Autowired
	PlayerRepository playerRepository;
	UploadedFile ufile;

	public PlayerController() {
		ufile = new UploadedFile();
	}

	@RequestMapping(value = "/playersjson", method = RequestMethod.GET)
	public @ResponseBody
	String getPlayers(HttpServletRequest request,
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
			size = (int) playerRepository.count(sSearch);
			page = playerRepository.findByNameRegex(sSearch, new PageRequest(
					start, size, sort));
		} else if (sSearch != null && !sSearch.equals("") && pageRows != -1) {
			size = (int) playerRepository.count(sSearch);
			int pageNumber = start / pageRows;
			page = playerRepository.findByNameRegex(sSearch, new PageRequest(
					pageNumber, pageRows, sort));
		} else {
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
		return "user/csv";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(ModelMap model) throws Exception {
		return "test";
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody
	String importPlayers(@RequestBody String[] json) throws Exception {
		PlayerModel playerModel = new PlayerModel();
		File upLoadedfile = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + ufile.name);
		List<PlayerDoc> res = playerModel.readWithCsvBeanReaderForPlayerDoc(
				upLoadedfile, json);
		for (PlayerDoc p : res) {
			playerRepository.save(p);
		}
		return "{\"imported\":\"imported\" }";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	String upload(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Iterator<String> itr = request.getFileNames();
		String res = "";
		MultipartFile mpf = request.getFile(itr.next());
		if (!mpf.isEmpty()) {
			System.out.println(mpf.getOriginalFilename() + " uploaded!");
			try {
				// just temporary save file info into ufile
				ufile.length = mpf.getBytes().length;
				ufile.bytes = mpf.getBytes();
				ufile.type = mpf.getContentType();
				ufile.name = mpf.getOriginalFilename();

				File file = new File(System.getProperty("java.io.tmpdir")
						+ System.getProperty("file.separator")
						+ mpf.getOriginalFilename());

				File upLoadedfile = new File(
						System.getProperty("java.io.tmpdir")
								+ System.getProperty("file.separator")
								+ ufile.name);
				System.out.println(upLoadedfile.getAbsolutePath());

				upLoadedfile.createNewFile();
				FileOutputStream fos = new FileOutputStream(upLoadedfile);
				fos.write(ufile.bytes);
				fos.close(); // setting the value of fileUploaded variable

				PlayerModel playerModel = new PlayerModel();
				res = playerModel.readCsvToString(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	public static String toJsArray(String[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i + 1 < arr.length) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
