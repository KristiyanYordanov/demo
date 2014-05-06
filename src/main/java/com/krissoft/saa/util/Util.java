package com.krissoft.saa.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Util {
	private final static int MAX_RETRY = 3;
	public final static String ENCODING = "UTF-8";

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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	public static String postWithParams(URL url, String postParams)
			throws Exception {
		StringWriter sw = null;
		int retry = 1;
		while (++retry <= MAX_RETRY) {
			HttpURLConnection con = null;
			try {
				sw = new StringWriter();
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setUseCaches(false);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Connection", "Keep-Alive");
				con.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0");
				OutputStreamWriter wr = new OutputStreamWriter(
						con.getOutputStream(), ENCODING);
				wr.write(postParams);
				wr.flush();
				if (con.getResponseCode() == 200) {
					InputStream in = con.getInputStream();
					byte[] buf = new byte[32 * 1024];
					int read;
					while ((read = in.read(buf)) != -1) {
						sw.write(new String(buf, 0, read));
					}
				}
				retry = MAX_RETRY;
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("Retry (" + retry + ") download url " + url);
			} finally {
				if (sw != null) {
					try {
						sw.close();
					} catch (Exception ex) {
					}
				}

				if (con != null) {
					try {
						con.disconnect();
					} catch (Exception ex) {
					}
				}
			}
			long delay = 200L + ((long) (Math.random() * 1800.0));
			System.out.println("POST url " + url);
			System.out.println("Waiting for " + delay + "ms...");
			Thread.sleep(delay);
		}
		return sw.toString();
	}

	public static String get(URL url) throws Exception {
		StringWriter content = new StringWriter();
		int retry = 1;
		while (++retry <= MAX_RETRY) {
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setUseCaches(false);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
				con.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				con.setRequestProperty("Accept-Encoding", "gzip, deflate");
				con.connect();
				if (con.getResponseCode() == 200) {
					InputStream in = con.getInputStream();
					byte[] buf = new byte[32 * 1024];
					int read;
					while ((read = in.read(buf)) != -1) {
						content.write(new String(buf, 0, read, ENCODING));
					}
				}
				retry = MAX_RETRY + 1;
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
				System.out.println("Retry (" + retry + ") download url " + url);
			} finally {
				if (content != null) {
					try {
						content.close();
					} catch (Exception ex) {
					}
				}

				if (con != null) {
					try {
						con.disconnect();
					} catch (Exception ex) {
					}
				}
			}
			long delay = 200L + ((long) (Math.random() * 21800.0));
			System.out.println("GET url " + url);
			System.out.println("Waiting for " + delay + "ms...");
			Thread.sleep(delay);
		}
		if (retry == MAX_RETRY + 1) {
			System.out.println("Error on downloading url " + url);
		}
		return content.getBuffer().toString();
	}

	public static String loadPageRivals(URL url) throws Exception {
		int retry = 1;
		StringWriter content = new StringWriter();
		while (++retry <= MAX_RETRY) {
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setUseCaches(false);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
				con.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				con.connect();
				if (con.getResponseCode() == 200) {
					InputStream in = con.getInputStream();
					byte[] buf = new byte[32 * 1024];
					int read;
					while ((read = in.read(buf)) != -1) {
						content.write(new String(buf, 0, read, ENCODING));
					}
				}
				retry = MAX_RETRY + 1;
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
				System.out.println("Retry (" + retry + ") download url " + url);
			} finally {
				if (content != null) {
					try {
						content.close();
					} catch (Exception ex) {
					}
				}

				if (con != null) {
					try {
						con.disconnect();
					} catch (Exception ex) {
					}
				}
			}
			long delay = 200L + ((long) (Math.random() * 32000.0));
			System.out.println("Downloaded url " + url);
			System.out.println("Waiting for " + delay + "ms...");
			Thread.sleep(delay);
		}
		if (retry == MAX_RETRY + 1) {
			System.out.println("Error on downloading url " + url);
		}
		return content.getBuffer().toString();
	}

	public static void PrintAllRequestParams(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		System.out.println("params: " + parameters.size());
		for (String key : parameters.keySet()) {
			System.out.println(key);
			String[] vals = parameters.get(key);
			for (String val : vals) {
				System.out.println(" -> " + val);
			}
		}
	}
	//java array to javascript array
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
	
	public static String printListValues(List<String> as) {
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
	
	public static String replaceAllNonDigit(String string) {
		String res = string.replaceAll("\\D", "");
		return res;
	}
}
