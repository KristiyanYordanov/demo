package com.krissoft.saa.controller;

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

import com.krissoft.saa.bean.School;
import com.krissoft.saa.config.DataTableEditorJsonObject;
import com.krissoft.saa.repository.SchoolRepository;
import com.mongodb.MongoServerSelectionException;

@Controller
@RequestMapping("schools")
public class SchoolController {
	
	@Autowired
	SchoolRepository schoolRepository;
	Page<School> pageExport;
	String[] header;
	
	@RequestMapping(value = "/schools", method = RequestMethod.GET)
	public String schools(ModelMap model) {
		return "user/schools";
	}
	
	@RequestMapping(value = "/getschools", method = RequestMethod.GET)
	public @ResponseBody String getSchools(	@RequestParam(value = "length") String length,
			@RequestParam(value = "start") String startStr,
			@RequestParam(value = "draw") String draw,
			@RequestParam(value = "order[0][dir]") String sSortDir_0,
			@RequestParam(value = "order[0][column]") String iSortCol_0) {
		String res = "";
		header = new String[] { "ets", "schoolName", "schoolAddress", "schoolCity",
				"schoolState", "schoolZip", "schoolAreaCode", "schoolPhone", "schoolCoachOffice", "schoolFax"};
		Sort sort = null;
		int sortCol = Integer.parseInt(iSortCol_0) - 1;
		for (int i = 0; i < header.length; i++) {
			if (sortCol == i && sSortDir_0.equals("asc")) {
				sort = new Sort(Sort.Direction.ASC, header[i]);
			} else if (sortCol == i && sSortDir_0.equals("desc")) {
				sort = new Sort(Sort.Direction.DESC, header[i]);
			}
		}
		DataTableEditorJsonObject jsonObject = new DataTableEditorJsonObject();
		int start = new Integer(startStr);
		int pageRows = new Integer(length);
		int size = 0;
		size = (int) schoolRepository.count();
		Page<School> page = schoolRepository.findAll(new PageRequest(start, size,
				sort));
		boolean noFilter = false;
		try {
			if (pageRows == -1 && !noFilter) {
				size = (int) schoolRepository.count();
				page = schoolRepository.findAll(new PageRequest(start, size,
						sort));
				pageExport = page;
			} else if (noFilter) {
				size = (int) schoolRepository.count();
				size = page.getNumberOfElements();
				pageExport = page;
			} else {
				size = (int) schoolRepository.count();
				int pageNumber1 = start / pageRows;
				if (size < pageRows) {
					pageNumber1 = size;
					pageRows = size;
					size = (int) schoolRepository.count();
					page = schoolRepository.findAll(new PageRequest(start,
							size, sort));
					pageExport = page;
				} else {
					page = schoolRepository.findAll(new PageRequest(
							pageNumber1, pageRows, sort));
					pageExport = schoolRepository.findAll(new PageRequest(
							start, size, sort));
				}

			}
		} catch (MongoServerSelectionException ex) {
			System.out.println("No connection with DB  " + ex);
		}
		jsonObject.setAaData(page);
		jsonObject.setDraw(draw);
		jsonObject.setRecordsFiltered(size);
		jsonObject.setRecordsTotal(size);
		res = jsonObject.toString();
//		System.out.println("res = " + res);
		return res;
	}
	
	

}
