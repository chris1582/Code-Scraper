package linkscod;



import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Example program to list links from a URL.
 */
public class LinksCOD {
    //String url = "http://library.municode.com/toc.aspx?clientId=10620&checks=false";
    public static void main(String[] args){
        
        FindLinks fl = new FindLinks();
        fl.findAllLinks(0, "http://library.municode.com/toc.aspx?clientId=10620&checks=false");
        SortedSet<String> list = fl.getTree();
        for (String link : list)
        {
            System.out.println(link);
        }

        
    }

    

    
    

}