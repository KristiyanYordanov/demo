package com.krissoft.saa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.krissoft.saa.util.Util;

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
	String getPlayers1(HttpServletRequest request,
			@RequestParam(value = "length") String length,
			@RequestParam(value = "start") String startStr,
			@RequestParam(value = "draw") String draw) throws JSONException {
		Util.PrintAllRequestParams(request);
		String sSortDir_0 = "";
		String iSortCol_0 = "";
		String sSearch = "";
		System.out.println("length = " + length);
		System.out.println("startStr = " + startStr);
		System.out.println("search = " + sSearch);
		System.out.println("enter in getPlayers1");
		String res = "";
		Page<PlayerDoc> page = null;
		DataTableJsonObject jsonObject = new DataTableJsonObject();
		Sort sort = null;

		int start = new Integer(startStr);
		int pageRows = new Integer(length);
		int size = 0;
//		page = playerRepository.findAll(new PageRequest(start, pageRows));
		// (1, size); 
//		int size = (int) playerRepository.count();
//		System.out.println("size size = " + size);
//		if (size != 0) {//if size = 0 -> exception
//			page = playerRepository.findAll(new PageRequest(start-1, size));
//		}
		
		// TODO save header in DB and drop down list in view
		String[] header = new String[] { "name", "state", "schoolName",
				"schoolCity", "maxprepsUrl", "pos", "height", "fortyDash",
				"weight", "stars", "rating", "gradYear", "GP", "Avg", "OBP",
				"H", "RBI", "R", "SB", "AB", "SLG", "PA", "FP", "K", "IP" };
		for (int i = 0; i < header.length; i++) {
			if (iSortCol_0.equals(Integer.toString(i))
					&& sSortDir_0.equals("asc")) {
				sort = new Sort(Sort.Direction.ASC, header[i]);
			} else if (iSortCol_0.equals(Integer.toString(i))
					&& sSortDir_0.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, header[i]);
			}
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
		jsonObject.setAaData(page);
		System.out.println("page size = " + page.getNumberOfElements());
		jsonObject.setDraw(draw);
		jsonObject.setRecordsFiltered(size);
		jsonObject.setRecordsTotal(size);
		res = jsonObject.toString();
		System.out.println("res = " + res);
		return res;
	}

	@RequestMapping(value = "/playersjson1", method = RequestMethod.GET)
	public @ResponseBody
	String getPlayers(HttpServletRequest request
			
) throws JSONException {
		Util.PrintAllRequestParams(request);
		System.out.println("enter in getPlayers");
		String sSearch = "";
		String iDisplayLength = "";
		String iDisplayStart = "";
		String iSortCol_0 = "";
		String sSortDir_0 = "";
		String sEcho = "";

		logger.info("start playercontroller.");
		logger.debug("start playercontroller!");

		System.out.println(System.getProperty("catalina.base"));
		Sort sort = null;
		Page<PlayerDoc> page = null;
		DataTableJsonObject jsonObject = new DataTableJsonObject();

		int start = new Integer(iDisplayStart);
		int pageRows = new Integer(iDisplayLength);
		int size = 0;

		// TODO save header in DB and drop down list in view
		String[] header = new String[] { "name", "state", "schoolName",
				"schoolCity", "maxprepsUrl", "pos", "height", "fortyDash",
				"weight", "stars", "rating", "gradYear", "GP", "Avg", "OBP",
				"H", "RBI", "R", "SB", "AB", "SLG", "PA", "FP", "K", "IP" };
		for (int i = 0; i < header.length; i++) {
			if (iSortCol_0.equals(Integer.toString(i))
					&& sSortDir_0.equals("asc")) {
				sort = new Sort(Sort.Direction.ASC, header[i]);
			} else if (iSortCol_0.equals(Integer.toString(i))
					&& sSortDir_0.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, header[i]);
			}
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

		page.getContent().get(0).header = header;
		for (PlayerDoc pd : page.getContent()) {
			pd.header = header;
		}
//		jsonObject.setSEcho(sEcho);
		jsonObject.setAaData(page);
//		jsonObject.setITotalRecords(size);
//		jsonObject.setITotalDisplayRecords(size);
		System.out.println(jsonObject.toString());
		String res = jsonObject.toString();
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody  String create(ModelMap model) {
		System.out.println("create method");
		return "";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public @ResponseBody String edit(@RequestBody  String action) throws IllegalAccessException, InvocationTargetException {
		action = action.replaceAll("data", "").replaceAll("%5D", "").replaceAll("%5B", "").replaceAll("\\+", " ");
		System.out.println("edit method = "  + action);
		
		PlayerDoc p =  new PlayerDoc();
		 for (Field field : PlayerDoc.class.getDeclaredFields()) {
			    String name = field.getName();
			    if (action.contains(field.getName())) {
			    	int index = action.indexOf(name)+name.length()+1;
			    	int valueIndex;
			    	String last = action.substring(index, action.length());
			    	if (last.contains("&")) {
			    		valueIndex =last.indexOf("&");
			    	}
			    	else {
			    		valueIndex = last.length();
			    	}
//			    	System.out.println("test =" + action.substring(index, action.length()));
//			    	System.out.println("index =" + index);
//			    	System.out.println("valueIndex =" + valueIndex);
//			    	System.out.println("value =" + value);
			    	
			    	String value = last.substring(0, valueIndex);			    	
					BeanUtils.setProperty(p, name, value);
			    }
		 }
		 System.out.println("p =" + p);
		 playerRepository.save(p);
		 
		String res =  "{\"row\":{ \"DT_RowId\":\""+ p.getId()+ "\",\"name\":\""+p.getName()+"\",\"state\":\"" + p.getState() + "\",\"schoolName\":\""+p.getSchoolName()+"\",\"schoolCity\":\""+p.getSchoolCity()+"\"}";
		 System.out.println("res =" + res);
		return res;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody String delete(ModelMap model) {
		System.out.println("delete method");
		return "";
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
	String importPlayers(@RequestBody String[] header) throws Exception {
		PlayerModel playerModel = new PlayerModel();
		File upLoadedfile = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + ufile.name);
		List<PlayerDoc> res = playerModel.readWithCsvBeanReaderForPlayerDoc(
				upLoadedfile, header);
		for (PlayerDoc p : res) {
			playerRepository.save(p);
		}
		return "{\"imported\":\"File Imported.\" }";
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

	public void importCsvFile(File file, String[] header) throws Exception {
		PlayerModel playerModel = new PlayerModel();
		File upLoadedfile = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator") + ufile.name);
		List<PlayerDoc> res = playerModel.readWithCsvBeanReaderForPlayerDoc(
				upLoadedfile, header);
		for (PlayerDoc p : res) {
			playerRepository.save(p);
		}
		System.out.println(file.getAbsolutePath() + " is imported.");
	}

	@RequestMapping(value = "/schoolsjson", method = RequestMethod.GET)
	public @ResponseBody
	String getSchools(HttpServletRequest request,
			@RequestParam(value = "iSortCol_0") String sortColumn,
			@RequestParam(value = "sSearch") String sSearch,
			@RequestParam(value = "sSortDir_0") String sSortDir,
			@RequestParam(value = "iDisplayLength") String iDisplayLength,
			@RequestParam(value = "iDisplayStart") String iDisplayStart,
			@RequestParam(value = "iSortCol_0") String iSortCol_0,
			@RequestParam(value = "sSortDir_0") String sSortDir_0,
			@RequestParam(value = "sEcho") String sEcho) throws IOException,
			JSONException {
		logger.info("start playercontroller.");
		logger.debug("start playercontroller!");

		System.out.println(System.getProperty("catalina.base"));
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
//		jsonObject.setSEcho(sEcho);
//		jsonObject.setAaData(page);
//		jsonObject.setITotalRecords(size);
//		jsonObject.setITotalDisplayRecords(size);
		return jsonObject.toString();
	}

	@RequestMapping(value = "/schools", method = RequestMethod.GET)
	public String schools(ModelMap model) {
		return "user/schools";
	}
}
