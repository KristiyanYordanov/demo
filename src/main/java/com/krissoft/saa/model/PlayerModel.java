package com.krissoft.saa.model;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.krissoft.saa.bean.Player;
import com.krissoft.saa.bean.PlayerString;
import com.krissoft.saa.config.PlayerDoc;

public class PlayerModel {

	private String[] headers;

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public List<Player> readWithCsvBeanReader() throws Exception {
		List<Player> res = new ArrayList<Player>();
		ICsvBeanReader beanReader = null;
		try {
			beanReader = new CsvBeanReader(new FileReader(
					"H:\\rivals\\rivals_college 2012.csv"),
					CsvPreference.STANDARD_PREFERENCE);

			// the header elements are used to map the values to the bean (names
			// must match)
			final String[] header = beanReader.getHeader(true);
			setHeaders(header);
			// TODO change header with custom header form view

			// final CellProcessor[] processors = getProcessors();

			PlayerString playerString;
			while ((playerString = beanReader.read(PlayerString.class, header)) != null) {
				Player player = loadPlayer(playerString);
				// System.out.println(player);
				res.add(player);
			}

		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
		return res;
	}

	public List<PlayerDoc> readWithCsvBeanReaderForPlayerDoc(File File,
			String[] header) throws Exception {
		List<PlayerDoc> res = new ArrayList<PlayerDoc>();
		ICsvBeanReader beanReader = null;
		try {
			beanReader = new CsvBeanReader(new FileReader(File),
					CsvPreference.STANDARD_PREFERENCE);
			// Important! skip reading first row
			final String[] headerNoUsed = beanReader.getHeader(true);
			// setHeaders(header);

			for (int i = 0; i < header.length; i++) {
				if (header[i].equals("None")) {
					header[i] = null;
				}
			}

			PlayerString playerString;
			while ((playerString = beanReader.read(PlayerString.class, header)) != null) {
				PlayerDoc player = loadPlayerDoc(playerString);
				// System.out.println(player);
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
			sb.append(printValues(values));
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
			sb.append(printValues(values));
			values = parser.read();
		}
		String res = sb.toString();
		res = res.substring(0, res.length() - 1);
		System.out.println(res);
	}

	private static String printValues(List<String> as) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Iterator<String> it = as.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext() == true) {
				sb.append(",");
			}
		}
		sb.append("],");
		String res = sb.toString().trim();
		return res;
	}

	public static Player load(Object obj, Object stringObj) {
		System.out
				.println("////////////////////////////////////////////////////");
		Player player = new Player();

		Class playerStringClass = stringObj.getClass();
		Method[] stringMethods = playerStringClass.getMethods();

		Class playerClass = obj.getClass();
		Method[] methods = playerClass.getMethods();

		for (Method methodString : stringMethods) {
			for (Method method : methods) {
				String methodNameString = methodString.getName();
				String methodName = method.getName();
				if (methodName.equals(methodNameString)) {
					System.out.println("method name = " + methodName);

				}
				Class[] params = method.getParameterTypes();
				if (params.length > 0) {
					// System.out.println(params[0]);
				}
			}

		}
		return player;
	}

	public static Player loadPlayer(PlayerString s) {
		Player res = new Player();
		try {
			res.setFortyDash(Double.parseDouble(s.getFortyDash()));
		} catch (NumberFormatException e) {
		}
		try {
			res.setGradYear(Integer.parseInt(s.getGradYear()));
		} catch (NumberFormatException e) {
		}
		try {
			res.setRating(Double.parseDouble(s.getRating()));
		} catch (NumberFormatException e) {
		}
		try {
			res.setStars(Integer.parseInt(s.getStars()));
		} catch (NumberFormatException e) {
		}
		try {
			res.setWeight(Integer.parseInt(s.getWeight()));
		} catch (NumberFormatException e) {
		}
		res.setHeight(s.getHeight());
		res.setLocation(s.getLocation());
		res.setName(s.getName());
		res.setPos(s.getPos());
		return res;
	}

	public static PlayerDoc loadPlayerDoc(PlayerString s) {
		PlayerDoc res = new PlayerDoc();
		if (s.getFortyDash() != null) {
			try {

				res.setFortyDash(Double.parseDouble(s.getFortyDash()));
			} catch (NumberFormatException e) {
			}
		}
		if (s.getGradYear() != null) {

			try {
				res.setGradYear(Integer.parseInt(s.getGradYear()));
			} catch (NumberFormatException e) {
			}
		}
		if (s.getRating() != null) {
			try {
				res.setRating(Double.parseDouble(s.getRating()));
			} catch (NumberFormatException e) {
			}
		}
		if (s.getStars() != null) {
			try {
				res.setStars(Integer.parseInt(s.getStars()));
			} catch (NumberFormatException e) {
			}
		}

		if (s.getWeight() != null) {
			try {
				res.setWeight(Integer.parseInt(s.getWeight()));
			} catch (NumberFormatException e) {
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

		return res;
	}

}
