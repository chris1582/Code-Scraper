/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa_dadecounty_codeofordinances;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Cristhian
 */
public class Hierarchy 
{
 //URL page;
    private URL url;
    private InputStream stream;
    private BufferedInputStream buf;
    private StringBuilder sb;
    private String src;
    private Pattern checkRegex;
   // private String [] x;
    private String regex;
    private Matcher rmatcher;
    String s;
    String sr;
    String URLPrefix = "http://library.municode.com/";
    
    
    public void getSrc(String URL) {
        //URLkey = url.substring(url.indexOf("level")+6, url.indexOf("level")+15);
           // System.out.println(URLPrefix+URL);
        try {
            url = new URL(URLPrefix+URL);
            stream = url.openStream();
            buf = new BufferedInputStream(stream);
            sb = new StringBuilder();
            while (true) {
                int data = buf.read();
                if (data == -1) break;
                else sb.append((char) data);
            }
             trim();
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
      public void trim(){
        src = sb.toString();
        regex = "href=(\\\"?|\\#?)(?!.*(javascript|target|html#|book|ref\\.)).+(\\\"?)>(?!.*(gt;)).+";
        checkRegex = Pattern.compile(regex);
        rmatcher = checkRegex.matcher(src);
        String link = "";
        while(rmatcher.find()){
            s = rmatcher.group().trim();
            System.out.println("\t\t\t\t"+s);
            getLinks("\t\t\t\t"+s);
            getTitle(s);
           // return link;
        }
       // return link;
    }
    
    public void getLinks(String s){
        String regex2 = "(?!.*(f=))([/level?]|[\\#?])[A-Za-z].+?[\\\"?]";
        Pattern checkRegex2 = Pattern.compile(regex2);
        Matcher rmatcher2 = checkRegex2.matcher(s);
        String links="";
        while(rmatcher2.find()){
            System.out.println("\t\t\t\t"+rmatcher2.group().trim().substring(1,rmatcher2.group().length()-1));
          //  links = rmatcher2.group().trim().substring(1,rmatcher2.group().length()-1);
           // return links;
        }
       // return links;
    }
     public void getTitle(String s){ //>.+?(<|.+)      ====    >.+(</a>|\\n*)
        String regex2 = "(>[A-Za-z\\[].+?(<))|(>[A-Za-z\\[](.+?)(.+))";
        Pattern checkRegex2 = Pattern.compile(regex2);
        Matcher rmatcher2 = checkRegex2.matcher(s);
        while(rmatcher2.find()){
            System.out.println("\t\t\t\t"+rmatcher2.group().trim().substring(1,rmatcher2.group().length()));
        }
    }
    
}
