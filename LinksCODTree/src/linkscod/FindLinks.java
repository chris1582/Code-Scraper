/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linkscod;

import java.util.SortedSet;
import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Cristhian
 */
public class FindLinks {
    
SortedSet<String> linkset = new TreeSet<String>(new LinkComaprator<String>());
    public void findAllLinks(int level, String l)
    {
        //String url = "http://library.municode.com/toc.aspx?clientId=10620&checks=false";
        //print("Fetching %s...", url);
        try{
        Document doc = Jsoup.connect(l).get();
        Elements links = doc.select("a[href]");
        


        
        if (level < 5){
        for (Element link : links) {
            
           // print( link.attr("abs:href"));
           // System.out.println(link.attr("abs:href"));
            linkset.add(link.attr("abs:href"));
          //  findAllLinks(level+1, link.attr("abs:href"));
            
        }
        for (String lin : linkset)
        {
            //System.out.println("level= "+level + " "+ lin );
            findAllLinks(level+1, lin);
        }
        }
        
        
        }catch(Exception e)
        {
            //e.printStackTrace();
        }
    }
    public SortedSet<String> getTree()
    {
        return linkset;
                }
}
