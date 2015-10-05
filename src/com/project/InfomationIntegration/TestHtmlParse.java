package com.project.InfomationIntegration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

/**
 * @author Fernando Miguélez Palomo <fernandoDOTmiguelezATgmailDOTcom>
 */
public class TestHtmlParse
{
   // static final String className = "postingtitletext";
   // static final String url = "http://losangeles.craigslist.org/lac/bik/5206769641.html";

    TagNode rootNode;

    public TestHtmlParse(URL htmlPage) throws IOException
    {
        HtmlCleaner cleaner = new HtmlCleaner();
        rootNode = cleaner.clean(htmlPage);
    }

    List getDivsByClass(String CSSClassname)
    {
        List divList = new ArrayList();

        TagNode divElements[] = rootNode.getElementsByName("span", true);
        for (int i = 0; divElements != null && i < divElements.length; i++)
        {
            String classType = divElements[i].getAttributeByName("class");
            if (classType != null && classType.equals(CSSClassname))
            {
                divList.add(divElements[i]);
            }
        }

        return divList;
    }

 
}