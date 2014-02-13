
package cfa_dadecounty_codeofordinances;


import java.io.BufferedReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;







/**
 *
 * @author Cristhian
 */
public class SectionRetrieval {

    public String line;
    String section;
    String History;
    String Prefix;
    String experation;
    
    StringBuilder sb = new StringBuilder();
    BufferedReader buffer;
    
    
    int mainSec = 0;
    String startingSection = "";
    String SBline;
    ArrayList<String> textSections = new ArrayList<>();
    ArrayList<String[]> r = new ArrayList<>();
    String[] c;
    private int size = 0;
    

    public SectionRetrieval(ArrayList list, String source, ArrayList titles) {
        try {
            StringReader s = new StringReader(source);
            buffer = new BufferedReader(s);
            line = buffer.readLine();
            
                
            int j = list.size() - 1;
            if (!list.isEmpty())
            {
            while (!line.contains(list.get(j).toString())) {
                line = buffer.readLine();
            }

            
           //
            //goes through titles
            for (int i = 0; i <= list.size(); i++) {
                 
                if (i + 1 < list.size()) 
                {
                    System.out.println("<catch_line>"+titles.get(i).toString()+"</catch_line>");
                      line = buffer.readLine();
                      //System.out.println("\t<section_number></section_number>");
                     
                    //read until next title
                    while (!line.contains(list.get(i + 1).toString())) 
                    {
                        
                     
                         sb.append(line + "\n");
                         line = buffer.readLine();
                         
                    }
                    
                    
                   textTrim(sb);
                  determineTextOrder(textSections);
                } 
                if (i == list.size())
                {
                    System.out.println("<catch_line>"+titles.get(titles.size()-1).toString()+"</catch_line>");
                    line = buffer.readLine();
                    while (!line.contains("/html")) 
                    {
                        
                     
                         sb.append(line + "\n");
                         line = buffer.readLine();
                         
                    }
                    
                    textTrim(sb);
                  determineTextOrder(textSections);
                }
                
                  System.out.println("");
                 sb.setLength(0);
                 textSections.clear();
            }
            }
            else
            {
                while(!line.contains("crumb"))
                    {
                        line = buffer.readLine();
                    }
                while(line != null)
                {
                    sb.append(line + "\n");
                    line = buffer.readLine();
                    
                }
               textTrim(sb);
                System.out.println("<catch_line>"+"</catch_line>");
               determineTextOrder(textSections);
            }
             System.out.println("</law>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    

    }
    
    private void textTrim(StringBuilder sb){
        
        
        String html = sb.toString();
        Document doc = Jsoup.parse(html);
        
        Elements para = doc.select("p, table");
       // Elements tables = doc.select("table");
        for (Element ptext : para)
        {
//            textSections.add(ptext.toString());
            Elements lineText = ptext.select("p");
            for (Element lText : lineText)
                textSections.add(lText.text());
            Elements tableText = ptext.select("table");
            for (Element tText : tableText)
                textSections.add(tText.toString());
        }
//        for (Element table : tables)
//        {
//            textSections.add(table.toString());
//        }
        
        //>[\?\!\[\@\'\,\&\]]?[\n\r]?\s+?.[A-Za-z][A-Za-z](.*?)([^<]*)<|>[A-Za-z].?([A-Za-z0-9].?(.*?)([^<]*))<|>\(([A-Za-z0-9]\\)([^<]*))<|>(\(.+)  >(\\(.+)
//        String regex2 = ">[\\?\\!\\[\\@\\'\\,\\&\\]]?[\\n\\r]?\\s+?[A-Za-z][A-Za-z](.*?)([^<]*)<|>[A-Za-z].?([A-Za-z0-9].?(.*?)([^<]*))<|>\\(([A-Za-z0-9]\\)([^<]*))<|(?s)>\\(.+?<|\\s(?s)<table.+?</table>";
//        Pattern patternInfo = Pattern.compile(regex2);
//        Matcher matcher2 = patternInfo.matcher(sb.toString().replaceAll("[^\\x00-\\x7F]", "")); //remove all non ASCII chars);
//
//
//        //find matches to regex between tags
//        while (matcher2.find()) {
//        
//               
//                String Match = matcher2.group();
//               // System.out.println(Match);
//                
//                    textSections.add(Match.substring(1, matcher2.group().length() - 1));

      //  }
        
    }

    private void determineTextOrder(ArrayList<String> text)
    {
        System.out.println("<text>");
        int count = 0; 
        
        
        for(int i = 0 ; i < text.size() ; i++)
       {
           //System.out.println(text.get(i).toString());
            if(i==0){continue;} //skip incomplete title
            if (text.get(i).toString().contains("<table"))
                   {
                       int columnWidth = 20;
                       //System.out.println("**************************");
                       String html = text.get(i).toString();
                       Document doc = Jsoup.parse(html);
                       for (Element table : doc.select("table")) 
                       {
                           
                        for (Element row : table.select("tr")) 
                        {
                            
                            System.out.println("\n");
                            for  (Element tds : row.select("td"))
                            { 
                             
                                c = (tds.text()).split("\\,");
                            System.out.format("%-" + columnWidth + "s", "\t"+Arrays.toString(c));
                                size++;
                            }
                            
                            
                            
                        }   
                           System.out.println("\n");
                          
        
                        }
                   System.out.println("\n");
                   }
            else if(text.get(i).matches("\\([0-9]{1,}\\)|[0-9]\\.?")){
                System.out.println("<section prefix = " + text.get(i) + ">" + "\n");
                System.out.println(text.get(i+1)+ "\n");
                System.out.println("</section>"+ "\n");
                i++;
            }
            else if(text.get(i).matches("\\([A-Za-z]{1,}\\)")){
                if(i != 1 || i == text.size())
                    System.out.println("</section>"+ "\n");
                System.out.println("<section prefix = " + text.get(i) + ">" + "\n");
                if(i < text.size()-1)
                    System.out.println(text.get(i+1)+ "\n");
                i++;
            }
            else if (text.get(i).toString().contains("Ord."))
                      {
                       
                           History = text.get(i).toString().trim();
                           System.out.println("<history>" + History + "</history>"+ "\n");
                       
                      }
            else if (text.get(i).toString().contains("FOOTNOTE"))
                   {
                       
                           i = text.size()-1;
                   }
            else if(i == text.size()-1){
                System.out.println("</section>"+ "\n");
            }
            else{
                System.out.println(text.get(i)+ "\n");
            }
        }
        
        System.out.println("</text>"+ "\n");
        }
       
       
        
   // }
    

   
    
}
