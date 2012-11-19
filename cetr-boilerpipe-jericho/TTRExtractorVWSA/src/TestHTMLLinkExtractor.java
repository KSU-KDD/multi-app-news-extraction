import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;

import javax.swing.text.html.parser.ParserDelegator;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.MutableAttributeSet;

public class TestHTMLLinkExtractor {
	
  private TestHTMLLinkExtractor() {}

  public static List<String> extractLinks(Reader reader) throws IOException {
    final ArrayList<String> list = new ArrayList<String>();

    ParserDelegator parserDelegator = new ParserDelegator();
    ParserCallback parserCallback = new ParserCallback() {
      public void handleText(final char[] data, final int pos) { }
      public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) {
        if (tag == Tag.A) {
          String address = (String) attribute.getAttribute(Attribute.HREF);
          list.add(address);
        }
      }
      public void handleEndTag(Tag t, final int pos) {  }
      public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) { }
      public void handleComment(final char[] data, final int pos) { }
      public void handleError(final java.lang.String errMsg, final int pos) { }
    };
    parserDelegator.parse(reader, parserCallback, false);
    return list;
  }

  public final static void main(String[] args) throws Exception{
    FileReader reader = new FileReader("http://www.cnn.com/");
    List<String> links = TestHTMLLinkExtractor.extractLinks(reader);
    for (String link : links) {
      System.out.println(link);
    }
  }
}