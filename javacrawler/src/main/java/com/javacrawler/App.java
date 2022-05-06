package com.javacrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Connection;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        int pages = 1;
        String url = "http://www.eyny.com/forum.php?mod=viewthread&tid=10892610&extra=&highlight=%E4%BF%AE%E7%9C%9F%E8%81%8A%E5%A4%A9%E7%BE%A4&page=";
        System.out.println( "Starting right now" );
        for(int i = 1; i <= pages; i++) {
            Document document = Jsoup.connect(url + i).get();
            System.out.println( document );
            Elements items = document.getElementsByClass("t_fsz");
            System.out.println( items );
            for(Element item : items) {
                String text = item.getElementsByTag("td").get(0).text();
                File file = new File("./output.txt");
                FileWriter fw = new FileWriter(file);
                fw.write(text);
                fw.close();
                System.out.println("Write Successfully!");
            }
        }
        
    }
}

