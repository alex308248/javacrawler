package com.javacrawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CrawleLogin {
    public static void main(String[] args) throws Exception {
        String user_info_url = "http://www.eyny.com/space-uid-16221227.html";

        String login_url = "http://www.eyny.com/member.php?mod=logging&action=login";

//        new CrawleLogin().setCookies(user_info_url);
          new CrawleLogin().jsoupLogin(login_url,user_info_url);
//        new CrawleLogin().httpClientLogin(login_url,user_info_url);
    }

    /**
     * set cookies
     * login and find cookies in request headers
     * @param url
     * @throws IOException
     */
    public void setCookies(String url) throws IOException {

        Document document = Jsoup.connect(url)
                .header("Cookie", "push_noty_num=0; push_doumail_num=0; __utmv=30149280.15096; douban-profile-remind=1; _vwo_uuid_v2=D6B70333B716FC46F4E58135BBDB4414F|684457b0372138eeedc1d3828e244a55; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1570525814%2C%22https%3A%2F%2Fcn.bing.com%2F%22%5D; _pk_ses.100001.8cb4=*; ap_v=0,6.0; __utma=30149280.933949496.1568948318.1569824493.1570525816.5; __utmc=30149280; __utmz=30149280.1570525816.5.4.utmcsr=cn.bing.com|utmccn=(referral)|utmcmd=referral|utmcct=/; dbcl2=\"150968577:MMn7VZsaPqU\"; ck=oPH9; _pk_id.100001.8cb4=b366dc92e126c244.1568948315.4.1570526614.1569824689.; __utmb=30149280.7.10.1570525816")
                .get();
        if (document != null) {
            Element element = document.select(".info h1").first();
            if (element == null) {
                System.out.println("Cannot find .info h1 label");
                return;
            }
            String userName = element.ownText();
            System.out.println( userName);
        } else {
            System.out.println("error");
        }
    }

    /**
     * Jsoup login first and login personal center 
     * login in with a wrong password and get cookies
     * Set request cookies and request again
     * @param loginUrl login url
     * @param userInfoUrl personal center url
     * @throws IOException
     */
    public void jsoupLogin(String loginUrl,String userInfoUrl)  throws IOException {

        Map<String,String> data = new HashMap<>();
        data.put("name","your_account");
        data.put("password","your_password");
        data.put("remember","false");
        data.put("ticket","");
        data.put("ck","");
        Connection.Response login = Jsoup.connect(loginUrl)
                .ignoreContentType(true)
                .followRedirects(false)
                .postDataCharset("utf-8")
                .header("Upgrade-Insecure-Requests","1")
                .header("Accept","application/json")
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("X-Requested-With","XMLHttpRequest")
                .header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .data(data)
                .method(Connection.Method.POST)
                .execute();
        login.charset("UTF-8");
        
        Document document = Jsoup.connect(userInfoUrl)
                .cookies(login.cookies())
                .get();
        System.out.println("===>" + document);
        if (document != null) {
            Element element = document.select(".info h1").first();
            if (element == null) {
                System.out.println("Cannot find .info h1 label");
                return;
            }
            String userName = element.ownText();
            System.out.println(userName);
        } else {
            System.out.println("error");
        }
    }
}



