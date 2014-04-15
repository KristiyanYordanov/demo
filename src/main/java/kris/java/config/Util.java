package kris.java.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Util {

	public static void writeInFileBuffered(String content, File file) {
		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFromFileBuffered(File file) {
		BufferedReader br = null;
		String res = "";
		try {
			br = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			res = sb.toString();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public static void PrintAllRequestParams(HttpServletRequest request)
	{
		Map<String, String[]> parameters = request.getParameterMap();
		for (String key : parameters.keySet()) {
			 System.out.println(key);
			String[] vals = parameters.get(key);
			for (String val : vals) {
				 System.out.println(" -> " + val);
			}
		}
	}
}
