package com.krissoft.saa.model;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.krissoft.saa.bean.PlayerString;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.util.Util;

public class PlayerModel {

	private static final Logger logger = LoggerFactory
			.getLogger(PlayerModel.class);

	public List<PlayerDoc> readWithCsvBeanReaderForPlayerDoc(File File,
			String[] header) throws Exception {
		List<PlayerDoc> res = new ArrayList<PlayerDoc>();
		ICsvBeanReader beanReader = null;
		try {
			beanReader = new CsvBeanReader(new FileReader(File),
					CsvPreference.STANDARD_PREFERENCE);
			
			if (header.length == 0) {
				// Important! skip reading first row
				header = beanReader.getHeader(true);
			}
			else {
				for (int i = 0; i < header.length; i++) {
					if (header[i].equals("None")) {
						header[i] = null;
					}
				}
			}
			
			PlayerString playerString;
			while ((playerString = beanReader.read(PlayerString.class, header)) != null) {
				PlayerDoc player = loadPlayerDoc(playerString);
				res.add(player);
			}

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
		return res;
	}

	public String readCsvToString(File file) throws Exception {
		CsvListReader parser = new CsvListReader(new FileReader(file),
				CsvPreference.EXCEL_PREFERENCE);
		List<String> values = parser.read();
		StringBuffer sb = new StringBuffer();
		while (values != null) {
			sb.append(Util.printListValues(values));
			values = parser.read();
		}
		String res = sb.toString();
		res = res.substring(0, res.length() - 1);
		return res;
	}

	public static void main(String[] argc) throws Exception {

		CsvListReader parser = new CsvListReader(new FileReader(
				"H:\\rivals\\rivals_college 2013.csv"),
				CsvPreference.EXCEL_PREFERENCE);
		List<String> values = parser.read();
		StringBuffer sb = new StringBuffer();
		while (values != null) {
			sb.append(Util.printListValues(values));
			values = parser.read();
		}
		String res = sb.toString();
		res = res.substring(0, res.length() - 1);
		System.out.println(res);
	}


	public static PlayerDoc loadPlayerDoc(PlayerString s) {
		PlayerDoc res = new PlayerDoc();
		if (s.getFortyDash() != null) {
			try {
				res.setFortyDash(Double.parseDouble(s.getFortyDash()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getGradYear() != null) {

			try {
				res.setGradYear(Integer.parseInt(s.getGradYear()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getRating() != null) {
			try {
				res.setRating(Double.parseDouble(s.getRating()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStars() != null) {
			try {
				res.setStars(Integer.parseInt(s.getStars()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}

		if (s.getWeight() != null) {
			try {
				res.setWeight(Integer.parseInt(s.getWeight()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}

		if (s.getHeight() != null) {
			res.setHeight(s.getHeight());
		}
		if (s.getLocation() != null) {
			res.setLocation(s.getLocation());
		}
		if (s.getPos() != null) {
			res.setPos(s.getPos());
		}
		if (s.getName() != null) {
			res.setName(s.getName());
		}
		if (s.getState() != null) {
			res.setState(s.getState());
		}
		if (s.getSchoolCity() != null) {
			res.setSchoolCity(s.getSchoolCity());
		}
		if (s.getSchoolName() != null) {
			res.setSchoolName(s.getSchoolName());
		}
		if (s.getMaxprepsUrl() != null) {
			res.setMaxprepsUrl(s.getMaxprepsUrl());
		}
		 
		
		if (s.getGP() != null) {
			try {
				res.setGP(Double.parseDouble(s.getGP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getAvg() != null) {
			try {
				res.setAvg(Double.parseDouble(s.getAvg()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getOBP() != null) {
			try {
				res.setOBP(Double.parseDouble(s.getOBP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getH() != null) {
			try {
				res.setH(Double.parseDouble(s.getH()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getRBI() != null) {
			try {
				res.setRBI(Double.parseDouble(s.getRBI()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getR() != null) {
			try {
				res.setR(Double.parseDouble(s.getR()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getSB() != null) {
			try {
				res.setSB(Double.parseDouble(s.getSB()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getAB() != null) {
			try {
				res.setAB(Double.parseDouble(s.getAB()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getFP() != null) {
			try {
				res.setFP(Double.parseDouble(s.getFP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getPA() != null) {
			try {
				res.setPA(Double.parseDouble(s.getPA()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getK() != null) {
			try {
				res.setK(Double.parseDouble(s.getK()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getIP() != null) {
			try {
				res.setIP(Double.parseDouble(s.getIP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		
		return res;
	}

}
