/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linkscod;

import java.util.ArrayList;
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
    int level;
SortedSet<String> linkset = new TreeSet<String>(new LinkComaprator<String>());

    public void findAllLinks(int lvl, String l)
    {
        level = lvl;
        try
        {

                    String url = l;
                    Document doc = Jsoup.connect(url).get();
                    Elements links = doc.select("a[href]");
                
                    for (Element link : links) 
                    {
                        if (!link.attr("abs:href").contains("#") && !link.attr("abs:href").isEmpty() && !link.attr("abs:href").contains(".png"))
                        {
                            if (!linkset.contains(link.attr("abs:href"))){
                                 linkset.add(link.attr("abs:href"));
                                // System.out.println(link.attr("abs:href"));
                                  findAllLinks(level++,link.attr("abs:href"));
                            }
                            
                        }
                        
                    }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public SortedSet<String> getTree()
    {
        return linkset;
                }
}
