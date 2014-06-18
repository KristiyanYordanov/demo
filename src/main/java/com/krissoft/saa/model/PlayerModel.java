package com.krissoft.saa.model;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.krissoft.saa.bean.PlayerString;
import com.krissoft.saa.config.PlayerDoc;
import com.krissoft.saa.util.MyUtil;

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
			} else {
				String[] headerNotUsed = beanReader.getHeader(true);
				for (int i = 0; i < header.length; i++) {
					if (header[i].equals("None")) {
						header[i] = null;
					}
				}
			}

			System.out.println("last header = " + Arrays.toString(header)
					+ ", with size = " + header.length);
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
		CsvListReader parser = null;
		String res = "";
		try {
			parser = new CsvListReader(new FileReader(file),
					CsvPreference.EXCEL_PREFERENCE);
			List<String> values = parser.read();
			StringBuffer sb = new StringBuffer();
			while (values != null) {
				sb.append(MyUtil.printListValues(values));
				values = parser.read();
			}
			res = sb.toString();
			res = res.substring(0, res.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (parser != null) {
				parser.close();
			}
		}

		return res;
	}

	public static void main(String[] argc) throws Exception {
		CsvListReader parser = null;
		String res = "";
		try {
			parser = new CsvListReader(new FileReader(
					"H:\\rivals\\rivals_college 2013.csv"),
					CsvPreference.EXCEL_PREFERENCE);
			List<String> values = parser.read();
			StringBuffer sb = new StringBuffer();
			while (values != null) {
				sb.append(MyUtil.printListValues(values));
				values = parser.read();
			}
			res = sb.toString();
			res = res.substring(0, res.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (parser != null) {
				parser.close();
			}
		}

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

		if (s.getStatGP() != null) {
			try {
				res.setStatGP(Double.parseDouble(s.getStatGP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatAvg() != null) {
			try {
				res.setStatAvg(Double.parseDouble(s.getStatAvg()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatOBP() != null) {
			try {
				res.setStatOBP(Double.parseDouble(s.getStatOBP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatH() != null) {
			try {
				res.setStatH(Double.parseDouble(s.getStatH()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatRBI() != null) {
			try {
				res.setStatRBI(Double.parseDouble(s.getStatRBI()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatR() != null) {
			try {
				res.setStatR(Double.parseDouble(s.getStatR()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatSB() != null) {
			try {
				res.setStatSB(Double.parseDouble(s.getStatSB()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatAB() != null) {
			try {
				res.setStatAB(Double.parseDouble(s.getStatAB()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatFP() != null) {
			try {
				res.setStatFP(Double.parseDouble(s.getStatFP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatPA() != null) {
			try {
				res.setStatPA(Double.parseDouble(s.getStatPA()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatK() != null) {
			try {
				res.setStatK(Double.parseDouble(s.getStatK()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}
		if (s.getStatIP() != null) {
			try {
				res.setStatIP(Double.parseDouble(s.getStatIP()));
			} catch (NumberFormatException e) {
				logger.info(e.toString());
			}
		}

		return res;
	}

}
