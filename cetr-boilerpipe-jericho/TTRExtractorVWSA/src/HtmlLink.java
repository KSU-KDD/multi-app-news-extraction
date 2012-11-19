import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.jcp.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlLink {
  private HtmlLink() {}

  public static List<String>extractLinks(String url) throws IOException {
    final ArrayList<String> result = new ArrayList<String>();

    Document doc = Jsoup.connect(url).get();

    Elements links = doc.select("a[href]");
    Elements media = doc.select("[src]");
    Elements imports = doc.select("link[href]");

    // href ...
    for (Element link : links) {
      result.add(link.attr("abs:href"));
    }

    // img ...
    for (Element src : media) {
      result.add(src.attr("abs:src"));
    }

    // js, css, ...
    for (Element link : imports) {
      result.add(link.attr("abs:href"));
    }
    return result;
  }


  public final static void main(String[] args) throws Exception{
    String site = "http://www.cnn.com/";
    List<String> links = HtmlLink.extractLinks(site);
    for (String link : links) {
      System.out.println(link);
    }
  }
}