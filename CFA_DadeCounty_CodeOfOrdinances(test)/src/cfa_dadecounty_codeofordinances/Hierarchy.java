package cfa_dadecounty_codeofordinances;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hierarchy {
    

    private URL url;
    private InputStream stream;
    private BufferedReader buf;
    private StringBuilder sb;
    private String src;
    private Pattern checkRegex;
    private String regex;
    private Matcher rmatcher;
    String test;
    String level;
    private String baseURL;
    String title = "";
    String sectionNum = "";
    String parentTitle = "";
    ArrayList<String> listOfSections = new ArrayList<String>();
    String links;
    String pageTitle;
    ArrayList<String> listofTitles = new ArrayList<String>();
    private String source;

//    public Hierarchy() {
//        try{this.url = new URL("http://library.municode.com/HTML/10620/book.html");}
//        catch(Exception e){e.printStackTrace();}
//        test = "";
//        baseURL = "http://library.municode.com/HTML/10620/";
//        src = "";
//    }
    
    public Hierarchy(String Source) {
        this.source = Source; 
        baseURL = "http://library.municode.com/HTML/10620/";
        level = "";
        getSrc();
        getLevel();
        trimLinksInfo();
        getParentTitleLine();
        getPageTitle();
        
        createXML();
        
    }
    //gets page information from source page
    public void getSrc() {
        try {
            //stream = url.openStream();
            URL site = new URL(source);
            URLConnection cod = site.openConnection();
            buf = new BufferedReader(new InputStreamReader(cod.getInputStream(), "UTF-8"));
            sb = new StringBuilder();
            while (true) {
                int data = buf.read();
                if (data == -1) break;
                else sb.append((char) data);
            }
            src = sb.toString();
        } catch (Exception e) {}
    }
    // Trims lines that contain links and titles
    public void trimLinksInfo(){//
        
        regex = "href=(\\\"?|\\#?)(?!.*(javascript|target|html#|book|ref\\.)).+(\\\"?)>(?!.*(gt;)).+";
        checkRegex = Pattern.compile(regex);
        rmatcher = checkRegex.matcher(sb.toString());
        while(rmatcher.find()){
            links = getLinks(rmatcher.group().trim());
            title = getTitle(rmatcher.group().trim());
            
            sectionNum = getSectionNum(title);
            listOfSections.add(sectionNum);
            
        }
    }
    //retrive links form line
    public String getLinks(String s){
        String regex2 = "(?!.*(f=)|([A-Za-z])|(/))([/level?]|[\\#?])[A-Za-z].+?[\\\"?]";
        Pattern checkRegex2 = Pattern.compile(regex2);
        Matcher rmatcher2 = checkRegex2.matcher(s);
        while(rmatcher2.find()){
            if(rmatcher2.group().trim().substring(0,rmatcher2.group().length()-1).charAt(0) == '#')
            {
                return url + rmatcher2.group().trim().substring(0,rmatcher2.group().length()-1);
            }
            else
                return baseURL + rmatcher2.group().trim().substring(1,rmatcher2.group().length()-1);
        }
        return "";
    }
    //get section Titles from Page 
    public String getTitle(String s){ 
        String regex2 = "(>[A-Za-z\\[].+?([<]))|(>[A-Za-z\\[](.+?)(.+))";
        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(s);
        
        if(matcher.find())
        {
            listofTitles.add(matcher.group().trim().substring(1));
           return matcher.group().trim().substring(1);
        }
        
        return "";
    }
    //get the section number for the page
    public String getSectionNum(String s)
    {
        
        String regex = "[Ss][Ee][Cc][Tt]?[Ii]?[Oo]?[Nn]?[\\.]\\s\\d.+\\.?\\d+\\.?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find())
        {
            return matcher.group().trim();
            //System.out.println(sectionNum);
        }
        return "";
    }
    //gets line that contains Parent Title
    public void getParentTitleLine()
    {
        String src = sb.toString();
        String regex = "([Mm][Ee][Tt][Aa]\\s.+?\\=\\\")([Pp][Aa][Rr].+)(\\\")";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);
        String line = "";
        if(matcher.find())
        {
           line = matcher.group().trim();
           getParentTitle(line);
        }
    }
    //Gets the Parent Title: <unit></unit>
    public void getParentTitle(String s)
    {
       
        String regex = "[Tt][Ee][Nn][Tt]\\=\\\"?.+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if(matcher.find())
        {
            parentTitle = matcher.group().trim().substring(6, matcher.group().length()-1);
            //System.out.println(parentTitle);
        }
           
    }
    //gets the Page Title
    public void getPageTitle()
    {
        String src = sb.toString(); //(\\<title>)(\\n\\r)?(.+)(\\n\\r)?\\s+?(\\<.?title>)
        String regex = "(?s)(<title>)(.+)(\\<.?title>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);
        if(matcher.find())
            pageTitle = matcher.group().substring(7, matcher.group().length()-8).trim().replace("\n", "").replace("  ", "");
    }
    
    
    //gets level from page
    public void getLevel(){
        String src = sb.toString();
        String regex2 = "crumb.+";
        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(src);
        level = "";
        if(matcher.find()){
            level = matcher.group().trim();
        }
        
        String [] test = level.split("crumb");
        
        
        //System.out.println(test[test.length-1]);
        regex2 = "level[0-9]{1}";
        pattern = Pattern.compile(regex2);
        matcher = pattern.matcher(test[test.length-1]);
        while(matcher.find()){
            level = matcher.group().trim();
            //System.out.println(level.substring(5, level.length()));
        }
    }
    
    public void getSectionInfo()
    {
        SectionRetrieval sr = new SectionRetrieval(listOfSections, src, listofTitles);
    }
    private void createXML()
    {
        System.out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        System.out.println("<law>");
        System.out.println("\t<structure>");
        System.out.println("\t\t<unit label=\"title\"\" identifier=\""+ sectionNum+ "\" order_by=\""+sectionNum+"\" level=\"\">"+parentTitle+"</unit>");
        System.out.println("\t\t<unit label=\"chapter\"\" identifier=\""+ sectionNum+ "\" order_by=\""+sectionNum+"\" level=\"\">"+pageTitle+"</unit>");
        System.out.println("\t</structure>");
        getSectionInfo();
        
    }
    
    

    
}
