import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import edu.illinois.dais.ttr.*;
import net.htmlparser.jericho.*;

public class Main {
	static boolean DEBUG = true;
	enum ExtractionMethod{CETR,BOILERPIPE,JERICHO};
	static ExtractionMethod method = ExtractionMethod.CETR;
	
	static String separator = "Comments:";
	final static String url = "http://www.dslreports.com/comments/2568";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				try {
//					getTextFromURLCETR(url);
//					getTextFromURLJericho(url);
					getTextFromURLBoilerPipe(args[i]);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				sb.append(getTextFromURLCETR(args[i]));
			}
		}
		else {
			try {
				sb.append(getTextFromURLCETR(url));
			}
			catch (MalformedURLException e) {
				e.toString();
			}
		}
		System.out.print(sb.toString());
		String s = sb.toString();
		s = s.replaceAll(separator, "\n====\n====\n");
		System.out.print(s);
	}
	
	public static String getTextFromURLBoilerPipe(String url) throws MalformedURLException {
		String result = null;
		try {
			//proposed -- "" will be HTML Source
			String s = ArticleExtractor.getInstance().getText("");
			//current
			result = ArticleExtractor.getInstance().getText(new URL(url));
		}
		catch (BoilerpipeProcessingException e) {
			e.toString();
		}
		return result;
	}
	public static String getTextFromHTMLBoilerPipe(String HTML) {
		String result = null;
		try {
			result = ArticleExtractor.getInstance().getText("");
		}
		catch (BoilerpipeProcessingException e) {
			e.toString();
		}
		return result;
	}
	public static String getTextFromURLCETR(String url) throws MalformedURLException {
		String HTML;
		String result = null;
		try {
			HTML = getHTMLSourceOfURL(url);
			result = getTextFromHTMLCETR(HTML);
		}
		catch (MalformedURLException e) {
			throw e;
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	public static String getTextFromHTMLCETR(String HTML) {
		StringBuilder sb = new StringBuilder();
		try {
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
	public static String getTextFromURLJericho(String url) throws MalformedURLException {
		String result = null;
		try {
			result = (new TextExtractor(new Source(new URL(url)))).toString();
		}
		catch (IOException e) {
			e.toString();
		}
		return result;
	}
	public static String getTextFromHTMLJericho(String HTML) {
		String result = null;
		try {
			result = (new TextExtractor(new Source(new StringReader(HTML)))).toString();
		}
		catch (IOException e) {
			e.toString();
		}
		return result;
	}
	
	public static String getHTMLSourceOfURL(String url) throws IOException{
		URL input = new URL(url);
		return getHTMLSourceOfURL(input);
	}
	
	public static String getHTMLSourceOfURL(URL url) throws IOException {
		
		URLConnection yc = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();

		return a.toString();
	}
	public static String insertNewlines(String HTML) {
		StringBuilder newHTML = new StringBuilder(HTML);
		int i = 60;
		
		while (i+60 < newHTML.length()) {
			while (newHTML.charAt(i) != '>') i++;
			newHTML.insert(++i, '\n');
			i += 60;
		}
		return newHTML.toString();
	}
	
	public static String stripTags(String HTML) {
		return HTML.replaceAll("<[^>]*>", "");
	}
}
