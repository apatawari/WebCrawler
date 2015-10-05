package com.project.InfomationIntegration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	//created to eliminate Spider Traps
	ArrayList<String> urls=new ArrayList<String>();
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://losangeles.craigslist.org/") && href.contains("bia") && !urls.contains(href);
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);
         
         urls.add(url);
         
         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String text = htmlParseData.getText();
             String html = htmlParseData.getHtml();
             List<WebURL> links = htmlParseData.getOutgoingUrls();
             
             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
             
             Object[] arrayView = links.toArray();

             for (int i = 0; i < links.size(); i++) {
            	 if(arrayView[i].toString().contains("bik") && !urls.contains(arrayView[i].toString()))
            	 {
            		 urls.add(arrayView[i].toString());
                     //System.out.println(arrayView[i]);
                     
                     
                     boolean x=false;
                     x=ToFetchOrNot(arrayView[i].toString());


                     //checking if the wep page given the above url makes sense or not by satisfying the condition it should
                     if(x)
                     {
                    	 System.out.println("Fetch Me Please\n");
                      
                    	 //get the html content and write it to the file
                    	 try {
							writeToFile(arrayView[i].toString());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                     
                     }
                     
                     
            	 
            	 }
             }
         }
    }
     
     public boolean ToFetchOrNot(String url)
     {
         boolean flag=false;
         String str=null;
         int price=0;
          try
             {
               TestHtmlParse thp = new TestHtmlParse(new URL(url));

                 List divs = thp.getDivsByClass("postingtitletext");
                 
                 List divs1 = thp.getDivsByClass("price");
                 
                 for (Iterator iterator = divs.iterator(); iterator.hasNext();)
                 {
                     TagNode divElement = (TagNode) iterator.next();
                      str=divElement.getText().toString().toLowerCase();
                       //flag1=( divElement.getText().toString().toLowerCase().contains("crusier") || divElement.getText().toString().toLowerCase().contains("bicycle") || divElement.getText().toString().toLowerCase().contains("bike")) && !(divElement.getText().toString().toLowerCase().contains("part") || divElement.getText().toString().toLowerCase().contains("seat") || divElement.getText().toString().toLowerCase().contains("tire"));
                 }
                 for (Iterator iterator = divs1.iterator(); iterator.hasNext();)
                 {
                     TagNode divElement = (TagNode) iterator.next();
                      price=Integer.parseInt(divElement.getText().toString().replace("$", ""));

                    flag=price<5000 && price>100 ;
                 }
                                
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
          if (flag==false){
         return false;}
          else{
              System.out.println(str+"  "+price);
              
              
              return true;}
         
     }
     
     public void    writeToFile(String url) throws IOException
     {
         Writer writer = null;

         //getting the html content
         URL url1 = new URL(url);
         InputStream is = (InputStream) url1.getContent();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line = null;
         String htmlContent=null;
         StringBuffer sb = new StringBuffer();
         while((line = br.readLine()) != null){
              sb.append(line);
                htmlContent = sb.toString();
             }
              
        //Writing to file
            String fileName = url.substring( url.lastIndexOf('/')+1, url.length() );

             System.out.println(fileName);
          
             writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("html/"+fileName), "utf-8"));
            
             writer.write(htmlContent);
             writer.close();
                


     }    
}