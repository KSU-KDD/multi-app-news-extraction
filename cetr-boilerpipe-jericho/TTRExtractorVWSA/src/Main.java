import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import edu.illinois.dais.ttr.*;
import net.htmlparser.jericho.*;

public class Main {
	static boolean DEBUG = true;
	
	static String separator = "Comments:";
	final static String url = "http://www.dslreports.com/comments/2568";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				sb.append(getTextFromURL(args[i]));
			}
		}
		else {
			sb.append(getTextFromURL(url));
		}
		System.out.print(sb.toString());
		String s = sb.toString();
		s = s.replaceAll(separator, "\n====\n====\n");
		System.out.print(s);
		int x = 0;
	}
	private static String getTextFromURL(String url) {
		String HTML;
		StringBuilder sb = new StringBuilder();
		try {
			HTML = getUrlSource(url);
			if (DEBUG) System.out.println(HTML);
			HTML = insertNewlines(HTML);
			if (DEBUG) System.out.println(HTML);
			
			String[] text = TTRExtractor1D.extractText(HTML, 3);
			for (int i = 0; i < text.length; i++) {
				sb.append("\n\n"+stripTags(text[i]));
			}
			
			
			HTML = "";
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return sb.toString();
	}
	private static String getUrlSource(String url) throws IOException {
		URL page = new URL(url);
		URLConnection yc = page.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();

		return a.toString();
	}
	private static String insertNewlines(String HTML) {
		StringBuilder newHTML = new StringBuilder(HTML);
		int i = 60;
		
		while (i+60 < newHTML.length()) {
			while (newHTML.charAt(i) != '>') i++;
			newHTML.insert(++i, '\n');
			i += 60;
		}
		return newHTML.toString();
	}
	private static String stripTags(String HTML) {
		return HTML.replaceAll("<[^>]*>", "");
	}
}
