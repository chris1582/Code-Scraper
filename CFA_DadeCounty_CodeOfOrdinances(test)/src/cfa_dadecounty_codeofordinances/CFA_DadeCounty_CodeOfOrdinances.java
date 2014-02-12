/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa_dadecounty_codeofordinances;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Marco
 */
public class CFA_DadeCounty_CodeOfOrdinances {

    
    public static void main(String[] args) {
        String line;
        int count = 0;
        int newcount = 0;
        Hierarchy h = new Hierarchy();
        
        String Link = "";
        try{
            String Source = "http://library.municode.com/toc.aspx?clientId=10620&checks=false";
            URL site = new URL(Source);
            URLConnection cod = site.openConnection();
            //FileReader file = new FileReader("ordinances.txt");
            //FileInputStream file = new FileInputStream("cod.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(cod.getInputStream(), "UTF-8"));
            //StringBuilder sb = new StringBuilder();
            line = br.readLine();
            
            
            while(line != null)
            {
            while(notContainsCollapse(line)) //reads next line until it find a {[Callapse]
            {
               line = br.readLine();
               
            }
            count +=1; // counter for [Callapse]
            //line = br.readLine();//reads next Line
            newcount= 0; //counter for the first item after Collapse
            if (notContainsCollapse(line) && count >= 1) //if it reads a least on [Collapse] 
            {
                //while a line does not contain [Collapse] continue reading and find the first anchor tag
                //set the first anchor as a parent and everything else as a child.
                while(notContainsCollapse(line)) 
            {
                
               line = br.readLine();
               newcount ++;
               
              
               //find the very first Root Parent
                
               if (determineParent(newcount) && count == 1)
               {
                   String regex = "href\\s?=\\s?(.+?)(\\s+)";
               Pattern pattern = Pattern.compile(regex);
               Matcher matcher = pattern.matcher(line);
                if(matcher.find())
               {
                   if (matcher.group().length() != 0 )
                       
                       Link = matcher.group().trim().toString().substring(matcher.group().indexOf("=")+1, matcher.group().length()-1).trim();
                       
                  // System.out.println(Link);
               }
                    if (getItem(line)) 
                    {
                    System.out.println("<ROOT Link=" + Link +"> " + line.substring(line.indexOf(">")+1, line.length()-4) + "</ROOT>");
                    
                    line = br.readLine();
                    
                           // h.getSrc(Link);
                    }
               }
               
               if (determineParent(newcount))
               {
                   String regex = "href\\s?=\\s?(.+?)(\\s+)";
               Pattern pattern = Pattern.compile(regex);
               Matcher matcher = pattern.matcher(line);
                if(matcher.find())
               {
                   if (matcher.group().length() != 0 )
                       
                       Link = matcher.group().trim().toString().substring(matcher.group().indexOf("=")+1, matcher.group().length()-1).trim();
                       
                  // System.out.println(Link);
               }
                   if (getItem(line))
                   {
                    System.out.println("\t<PARENT Link=" + Link + "> " + line.substring(line.indexOf(">")+1, line.length()-4) + "</PARENT>");
                     line = br.readLine();
                     
                          //  h.getSrc(Link);
                   }
               }

               int subcount= 0;
               if (hasSubItem(line))
                   {
                      
                       while(!getItem(line))
                        { 
                            String regex = "href\\s?=\\s?(.+?)(\\s+)";
               Pattern pattern = Pattern.compile(regex);
               Matcher matcher = pattern.matcher(line);
                if(matcher.find())
               {
                   if (matcher.group().length() != 0 )
                       
                       Link = matcher.group().trim().toString().substring(matcher.group().indexOf("=")+1, matcher.group().length()-1).trim();
                       
                  // System.out.println(Link);
               }
                            line = br.readLine();
                        }
                            
                        if (getItem(line)){
                            System.out.println("\t\t<SUB Link=" + Link +"> " + line.substring(line.indexOf(">")+1, line.length()-4) + "</SUB>");
                            
                          //  h.getSrc(Link);
                        }

                   }
                   else
                       if (getItem(line))
                       System.out.println("\t\t<Child Link=" + Link +"> " + line.substring(line.indexOf(">")+1, line.length()-4) + "</Child>");
                   
            }
            }
            line = br.readLine();
            }
            

            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    public static Boolean getItem(String line)
    {
        if(line.contains("</a>"))
            return true; 
        return false;
    }
    public static Boolean notContainsCollapse(String line)
    {
        if (!line.contains("[Collapse]"))
            return true;
        return false;
    }
    public static Boolean determineParent( int count)
    {
        if (count < 3)
            return true;
        return false;
    }
    
    public static Boolean hasSubItem(String line)
    {
        if(line.contains("[Expand]"))
            return true;
        return false;
    }
        
}
