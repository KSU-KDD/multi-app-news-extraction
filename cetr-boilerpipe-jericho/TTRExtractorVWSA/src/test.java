import java.io.BufferedReader;
import java.util.Vector;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.htmlparser.*;

import javax.swing.text.html.HTMLEditorKit.Parser;

import org.htmlparser.util.ParserException;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import edu.illinois.dais.ttr.*;
import net.htmlparser.jericho.*;
public class test
{
	public static void main(String[] args) throws Exception {
	      URL url = new URL("http://www.cnn.com/");
	      String textOfWeb = Main.getHTMLSourceOfURL(url);
	      String Text =  Main.getTextFromHTMLJericho(textOfWeb);
	      System.out.println(Text);
	      ExtractHTMLLinks extractHTML = new ExtractHTMLLinks();
	      String TEST_LINK = "http://www.google.com";
	      
	      Vector<HtmlLink> links = extractHTML.grabHTMLLinks(html);
	      for (int i = 0; i < links.size(); i++) {
				HtmlLink htmlLinks = links.get(i)
	     
	   }
	     
	      public void List<String> getLinksOnPage(final String url) {
	    	    final Parser htmlParser = new Parser(url);
	    	    final List<String> result = new LinkedList<String>();

	    	    try {
	    	        final NodeList tagNodeList = htmlParser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
	    	        for (int j = 0; j < tagNodeList.size(); j++) {
	    	            final LinkTag loopLink = (LinkTag) tagNodeList.elementAt(j);
	    	            final String loopLinkStr = loopLink.getLink();
	    	            result.add(loopLinkStr);
	    	        }
	    	    } catch (ParserException e) {
	    	        e.printStackTrace(); // TODO handle error
	    	    }

	    	    return result;
	    	}
	
	
	
}