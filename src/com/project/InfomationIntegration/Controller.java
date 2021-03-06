package com.project.InfomationIntegration;


import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {


	static String line;

    public static void main(String[] args) throws Exception {
    //	org.apache.log4j.BasicConfigurator.configure();
    	
        String crawlStorageFolder = "data/crawl/root";
        int numberOfCrawlers = 10;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        
        
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
      
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
        int politenessDelay=20000;
        config.setPolitenessDelay(politenessDelay);
        
        config.setUserAgentString("CSCI548");
        System.out.println(config.toString());
        

        
        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */

        controller.addSeed("http://losangeles.craigslist.org/search/bia");
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
        
    }
}