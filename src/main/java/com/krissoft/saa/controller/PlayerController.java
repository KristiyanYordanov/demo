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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.krissoft.saa.config.DataTableEditorJsonObject;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.model.PlayerModel;
import com.krissoft.saa.repository.PlayerRepository;
import com.krissoft.saa.util.UploadedFile;
import com.mongodb.MongoServerSelectionException;

@Controller
@RequestMapping("players")
public class PlayerController {

	//private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

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
			@RequestParam(value = "draw") String draw,
			@RequestParam(value = "order[0][dir]") String sSortDir_0,
			@RequestParam(value = "order[0][column]") String iSortCol_0,
			@RequestParam(value = "columns[0][search][value]") String searchColZero,
			@RequestParam(value = "columns[1][search][value]") String searchColOne,
			@RequestParam(value = "columns[2][search][value]") String searchColTwo,
			@RequestParam(value = "columns[3][search][value]") String searchColTree,
			@RequestParam(value = "columns[4][search][value]") String searchColFour)
			throws JSONException {
		//System.out.println("length = " + length);
		System.out.println("searchColZero = " + searchColZero);
		System.out.println("searchColOne = " + searchColOne);
		System.out.println("searchColTwo = " + searchColTwo);
		System.out.println("searchColTree = " + searchColTree);
		System.out.println("searchColFour = " + searchColFour);
		//MyUtil.PrintAllRequestParams(request);
		String res = "";
		Page<PlayerDoc> page = null;
		DataTableEditorJsonObject jsonObject = new DataTableEditorJsonObject();
		Sort sort = null;

		boolean noFilter = isFilterOn(searchColZero,searchColOne,searchColTwo,searchColTree,searchColFour );
		System.out.println("noFilter = " + noFilter);
		int start = new Integer(startStr);
		int pageRows = new Integer(length);
		int size = 0;
		
		// TODO save header in DB and drop down list in view
		String[] header = new String[] { "name", "state", "schoolName",
				"schoolCity", "maxprepsUrl", "pos", "height", "fortyDash",
				"weight", "stars", "rating", "gradYear", "GP", "Avg", "OBP",
				"H", "RBI", "R", "SB", "AB", "SLG", "PA", "FP", "K", "IP" };
		int sortCol = Integer.parseInt(iSortCol_0) - 1;
		for (int i = 0; i < header.length; i++) {
			if (sortCol == i && sSortDir_0.equals("asc")) {
				sort = new Sort(Sort.Direction.ASC, header[i]);
			} else if (sortCol == i && sSortDir_0.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, header[i]);
			}
		}
		try {
			if (pageRows == -1 && !noFilter) {
				size = (int) playerRepository.count();
				page = playerRepository.findAll(new PageRequest(start, size,
						sort));
			} else if (noFilter) {
				size = (int) playerRepository.count();
				page = playerRepository.findByNameLikeAndStateLikeAndSchoolNameLikeAndSchoolCityLikeAndPosLike(searchColZero, searchColOne, searchColTwo,searchColTree,searchColFour, new PageRequest(start, size,
						sort) );
				size = page.getNumberOfElements();
			} else {
				size = (int) playerRepository.count();
				int pageNumber1 = start / pageRows;
				if (size < pageRows) {
					pageNumber1 = size;
					pageRows = size;
					size = (int) playerRepository.count();
					page = playerRepository.findAll(new PageRequest(start, size,
							sort));
				}
				else {
					page = playerRepository.findAll(new PageRequest(pageNumber1,
							pageRows, sort));
				}
				
			}
		} catch (MongoServerSelectionException ex) {
			System.out.println("No connection with DB  " + ex);
		}
		jsonObject.setAaData(page);
		//System.out.println("page size = " + page.getNumberOfElements());
		jsonObject.setDraw(draw);
		jsonObject.setRecordsFiltered(size);
		jsonObject.setRecordsTotal(size);
		res = jsonObject.toString();
		System.out.println("res = " + res);
		return res;
	}

	
	
	
	private boolean isFilterOn(String zero, String one, String two, String three, String four) {
		boolean noFilter = false;
		if (!zero.equals("")) {
			noFilter = true;
		} else if (!one.equals("")) {
			noFilter = true;
		} else if (!two.equals("")) {
			noFilter = true;
		} else if (!three.equals("")) {
			noFilter = true;
		}else if (!four.equals("")) {
			noFilter = true;
		}
		return noFilter;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@RequestBody String create) throws IllegalAccessException,
			InvocationTargetException {
		System.out.println("create method");
		create = create.replaceAll("data", "").replaceAll("%5D", "").replaceAll("%3A", "")
				.replaceAll("%5B", "").replaceAll("\\+", " ");
		System.out.println("create method = " + create);

		PlayerDoc p = new PlayerDoc();
		for (Field field : PlayerDoc.class.getDeclaredFields()) {
			String name = field.getName();
			if (create.contains(field.getName())) {
				int index = create.indexOf(name) + name.length() + 1;
				int valueIndex;
				String last = create.substring(index, create.length());
				if (last.contains("&")) {
					valueIndex = last.indexOf("&");
				} else {
					valueIndex = last.length();
				}
				String value = last.substring(0, valueIndex);
				BeanUtils.setProperty(p, name, value);
			}
		}
		System.out.println("p =" + p);
		playerRepository.save(p);

		String res = "{\"row\":{ \"DT_RowId\":\"" + p.getId()
				+ "\",\"name\":\"" + p.getName() + "\",\"state\":\""
				+ p.getState() + "\",\"schoolName\":\"" + p.getSchoolName()
				+ "\",\"schoolCity\":\"" + p.getSchoolCity() + "\"}}";
		System.out.println("res =" + res);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public @ResponseBody
	String edit(@RequestBody String action) throws IllegalAccessException,
			InvocationTargetException {
		action = action.replaceAll("data", "").replaceAll("%5D", "").replaceAll("3A=", "")
				.replaceAll("%5B", "").replaceAll("\\+", " ");
		System.out.println("edit method = " + action);

		PlayerDoc p = new PlayerDoc();
		for (Field field : PlayerDoc.class.getDeclaredFields()) {
			String name = field.getName();
			if (action.contains(field.getName())) {
				int index = action.indexOf(name) + name.length() + 1;
				int valueIndex;
				String last = action.substring(index, action.length());
				if (last.contains("&")) {
					valueIndex = last.indexOf("&");
				} else {
					valueIndex = last.length();
				}
				// System.out.println("test =" + action.substring(index,
				// action.length()));
				// System.out.println("index =" + index);
				// System.out.println("valueIndex =" + valueIndex);
				// System.out.println("value =" + value);

				String value = last.substring(0, valueIndex);
				BeanUtils.setProperty(p, name, value);
			}
		}
		System.out.println("p =" + p);
		playerRepository.save(p);

		String res = "{\"row\":{ \"DT_RowId\":\"" + p.getId()
				+ "\",\"name\":\"" + p.getName() + "\",\"state\":\""
				+ p.getState() + "\",\"schoolName\":\"" + p.getSchoolName()
				+ "\",\"schoolCity\":\"" + p.getSchoolCity() + "\"}}";
		System.out.println("res =" + res);
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody
	// id[]:536cf040523be7a00e1e6f08
	String delete(@RequestBody String action) {
		System.out.println("delete method");
		action = action.replaceAll("action", "").replaceAll("%5D", "")
				.replaceAll("remove", "").replaceAll("=", "")
				.replaceAll("%5B", "").replaceAll("&id", "");
		System.out.println("id:" + action);
		PlayerDoc p = new PlayerDoc();
		p.setId(action);
		playerRepository.delete(p);
		return "[]";
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

	@RequestMapping(value = "/schools", method = RequestMethod.GET)
	public String schools(ModelMap model) {
		return "user/schools";
	}
}
