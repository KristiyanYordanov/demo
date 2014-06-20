package com.krissoft.saa.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.krissoft.saa.bean.PlayerDoc;
import com.krissoft.saa.config.DataTableEditorJsonObject;
import com.krissoft.saa.config.DataTableEditorJsonObjectList;
import com.krissoft.saa.model.PlayerModel;
import com.krissoft.saa.repository.PlayerRepository;
import com.krissoft.saa.util.UploadedFile;
import com.mongodb.MongoServerSelectionException;

@Controller
@RequestMapping("players")
public class PlayerController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(PlayerController.class);
	List<PlayerDoc> pageExport;
	String[] header;
	int size;
	int start;
	Sort sort;

	@Autowired
	PlayerRepository playerRepository;
	UploadedFile ufile;

	@Autowired
	MongoTemplate mongoTemplate;

	public PlayerController() {
		ufile = new UploadedFile();
	}

	@RequestMapping(value = "/playersjson", method = RequestMethod.GET)
	public @ResponseBody
	String getPlayers(
			HttpServletRequest request,
			@RequestParam(value = "length") String length,
			@RequestParam(value = "start") String startStr,
			@RequestParam(value = "draw") String draw,
			@RequestParam(value = "order[0][dir]") String sSortDir_0,
			@RequestParam(value = "order[0][column]") String iSortCol_0,
			@RequestParam(value = "columns[0][search][value]") String name,
			@RequestParam(value = "columns[1][search][value]") String state,
			@RequestParam(value = "columns[2][search][value]") String schoolName,
			@RequestParam(value = "columns[3][search][value]") String schoolCity,
			@RequestParam(value = "columns[4][search][value]") String pos)
			throws JSONException {
		Query query = new Query();
		if (!name.equals("")) {
			query.addCriteria(Criteria.where("name").regex(name, "i"));
		}
		if (!state.equals("")) {
			query.addCriteria(Criteria.where("state").regex(state, "i"));
		}
		if (!schoolName.equals("")) {
			query.addCriteria(Criteria.where("schoolName").regex(schoolName,
					"i"));
		}
		if (!schoolCity.equals("")) {
			query.addCriteria(Criteria.where("schoolCity").regex(schoolCity,
					"i"));
		}
		if (!pos.equals("")) {
			query.addCriteria(Criteria.where("pos").regex(pos, "i"));
		}

		String res = "";
		Sort sort = null;
		List<PlayerDoc> list = null;
		Page<PlayerDoc> page = null;
		boolean isFilterOn = isFilterOn(name, state, schoolName, schoolCity,
				pos);
		int start = new Integer(startStr);
		int pageRows = new Integer(length);
		int size = 0;

		// TODO save header in DB and drop down list in view
		header = new String[] { "name", "state", "schoolName", "schoolCity",
				"maxprepsUrl", "pos", "height", "fortyDash", "weight", "stars",
				"rating", "gradYear", "statGP", "statAvg", "statOBP", "statH", "statRBI", "statR",
				"statSB", "statAB", "statSLG", "statPA", "statFP", "statK", "statIP" };
		int sortCol = Integer.parseInt(iSortCol_0) - 1;
		for (int i = 0; i < header.length; i++) {
			if (sortCol == i && sSortDir_0.equals("asc")) {
				sort = new Sort(Sort.Direction.ASC, header[i]);
			} else if (sortCol == i && sSortDir_0.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, header[i]);
			}
		}
		try {
			if (pageRows == -1 && !isFilterOn) {
				long startTimer = new Date().getTime();
				size = (int) playerRepository.count();
				page = playerRepository.findAll(new PageRequest(start, size,
						sort));
				pageExport = page.getContent();
				long endTimer = new Date().getTime();
				System.out.println("if time=" + (endTimer - startTimer) / 1000);
			} else if (isFilterOn) {
				list = mongoTemplate.find(query, PlayerDoc.class);
				size = list.size();
				if (size > pageRows && pageRows != -1) {
					int pageLength = pageRows + start;
					if (pageRows + start > size) {
						pageLength = size;
					}
					list = list.subList(start, pageLength);
				}
				pageExport = list;
			} else {
				long startTimer = new Date().getTime();
				size = (int) playerRepository.count();
				if (size < pageRows) {
					page = playerRepository.findAll(new PageRequest(start,
							size, sort));
					pageExport = page.getContent();
				} else {
					int pageNumber = start / pageRows;
					page = playerRepository.findAll(new PageRequest(pageNumber,
							pageRows, sort));
					this.size = size;
					;
					this.start = start;
					this.sort = sort;
				}
				long endTimer = new Date().getTime();
				System.out.println("else time=" + (endTimer - startTimer)
						/ 1000);
			}
		} catch (MongoServerSelectionException ex) {
			System.out.println("No connection with DB  " + ex);
		}
		if (page != null) {
			DataTableEditorJsonObject jsonObject = new DataTableEditorJsonObject();
			jsonObject.setAaData(page);
			jsonObject.setDraw(draw);
			jsonObject.setRecordsFiltered(size);
			jsonObject.setRecordsTotal(size);
			res = jsonObject.toString();
		} else {
			DataTableEditorJsonObjectList jsonObject = new DataTableEditorJsonObjectList();
			jsonObject.setAaData(list);
			jsonObject.setDraw(draw);
			jsonObject.setRecordsFiltered(size);
			jsonObject.setRecordsTotal(size);
			res = jsonObject.toString();
		}
		return res;
	}

	@RequestMapping(value = "/csvexport", method = RequestMethod.GET)
	public void csvexport(HttpServletResponse response) throws IOException {
		System.out.println("csvexport method");
		ICsvBeanWriter beanWriter = null;
		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition",
					"attachment; filename=players.csv");
			beanWriter = new CsvBeanWriter(response.getWriter(),
					CsvPreference.STANDARD_PREFERENCE);
			beanWriter.writeHeader(header);
			pageExport = playerRepository.findAll(
					new PageRequest(start, size, sort)).getContent();
			for (final PlayerDoc customer : pageExport) {
				beanWriter.write(customer, header);
			}
			beanWriter.flush();
		} finally {
			if (beanWriter != null) {
				beanWriter.close();
			}
		}
	}

	private boolean isFilterOn(String zero, String one, String two,
			String three, String four) {
		boolean noFilter = false;
		if (!zero.equals("")) {
			noFilter = true;
		} else if (!one.equals("")) {
			noFilter = true;
		} else if (!two.equals("")) {
			noFilter = true;
		} else if (!three.equals("")) {
			noFilter = true;
		} else if (!four.equals("")) {
			noFilter = true;
		}
		return noFilter;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@RequestBody String create) throws IllegalAccessException,
			InvocationTargetException {
		System.out.println("create method");
		create = create.replaceAll("data", "").replaceAll("%5D", "")
				.replaceAll("%3A", "").replaceAll("%5B", "")
				.replaceAll("\\+", " ");
		//System.out.println("create method = " + create);

		PlayerDoc p = new PlayerDoc();
		for (Field field : PlayerDoc.class.getDeclaredFields()) {
			String name = field.getName();
			if (create.contains(field.getName())) {
				int index = create.indexOf(name + "=") + name.length() + 1;
				int valueIndex;
				// System.out.println("name="+name);
				String last = create.substring(index, create.length());
				if (last.contains("&")) {
					valueIndex = last.indexOf("&");
				} else {
					valueIndex = last.length();
				}
				String value = last.substring(0, valueIndex);
				// System.out.println("value="+value);
				try {
					BeanUtils.setProperty(p, name, value);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		playerRepository.save(p);
		String res = "{\"row\":"+p+"}";
//		System.out.println("res =" + res);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public @ResponseBody
	String edit(@RequestBody String action) throws IllegalAccessException,
			InvocationTargetException {
		System.out.println("edit method = " + action);
		action = action.replaceAll("data", "").replaceAll("%5D", "")
				.replaceAll("3A=", "").replaceAll("%5B", "")
				.replaceAll("\\+", " ");

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
				String value = last.substring(0, valueIndex);
				BeanUtils.setProperty(p, name, value);
			}
		}
		System.out.println("p =" + p);
		playerRepository.save(p);
		String res = "{\"row\":"+p+"}";
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
	public String getPlayersPage(ModelMap model) {
		return "user/players";
	}

	@RequestMapping(value = "/user/csv", method = RequestMethod.GET)
	public String player(ModelMap model) throws Exception {
		return "user/csv";
	}

	@RequestMapping(value = "/user/schedule", method = RequestMethod.GET)
	public String schedule(ModelMap model) throws Exception {
		return "user/schedule";
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public @ResponseBody
	String importPlayers(@RequestBody String[] header) throws Exception {
		System.out.println(Arrays.toString(header));
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
		System.out.println("upload method!");
		Iterator<String> itr;
		String res = "";
		try {
			itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			if (!mpf.isEmpty()) {
				System.out.println(mpf.getOriginalFilename() + " uploaded!");
				// just temporary save file info into ufile
				ufile.length = mpf.getBytes().length;
				ufile.bytes = mpf.getBytes();
				ufile.type = mpf.getContentType();
				ufile.name = mpf.getOriginalFilename();

				File file = new File(System.getProperty("java.io.tmpdir")
						+ System.getProperty("file.separator") + ufile.name);

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
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}
