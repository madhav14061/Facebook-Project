package Test2;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@WebServlet(name = "FormServlet", urlPatterns = { "/LikeCounter" })

public class LikeCounter extends HttpServlet 
{
	public String changeStringToNormal(String data)
	{ 
		data = data.replace("%21", "!"); 
		data = data.replace("%22", "\""); 
		data = data.replace("%23", "#"); 
		data = data.replace("%24", "$"); 
		data = data.replace("%25", "%"); 
		data = data.replace("%26", "&"); 
		data = data.replace("%27", "\'"); 
		data = data.replace("%28", "("); 
		data = data.replace("%29", ")"); 
		data = data.replace("%2B", "+"); 
		data = data.replace("%2C", ","); 
		data = data.replace("%2D", "-"); 
		data = data.replace("%2E", "."); 
		data = data.replace("%2F", "/"); 
		data = data.replace("%3A", ":"); 
		data = data.replace("%3B", ";"); 
		data = data.replace("%3C", "<"); 
		data = data.replace("%3D", "="); 
		data = data.replace("%3E", ">"); 
		data = data.replace("%3F", "?"); 
		data = data.replace("%40", "@");
		data = data.replace("%5B", "["); 
		data = data.replace("%5C", "\\"); 
		data = data.replace("%5D", "]"); 
		data = data.replace("%7B", "{"); 
		data = data.replace("%7C", "|"); 
		data = data.replace("%7D", "}");  
		for(int m=1;m<=25;m++)
		{
			String l = "limit="+m+"&";
			data = data.replace("limit=10&", "");
		}
		
		System.out.println("DATA = > "+data);
		return data;
	}
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // reading the user input
    	String access_token = "CAACEdEose0cBALWbfL0KNff8es9a2dBtxq6ZA3NpDpIdZCnO7ZCzyrZCdjVJSGv1kJf0mN86rbAqkVnZAneEGEtDlrgZCHS98VZCnVnMiLGEP4y9YSZCgpXuTdM5KL8kYNXVdd4j7djjGUHx6Hr6QvIHeUZAD072HT1ZB4zq1V8OqyTTLzZBM3KEfzxsj8y1qF23NgZBKhX1I5xVvgZDZD";
    	access_token = Redirected.accessToken;
    	if(access_token=="" || access_token==null)
    	{
    		System.out.println("Access Token problem");
    	}
    	else
    	{
    		System.out.println("Access Token is :"+access_token);
    	}
    	String page = "https://graph.facebook.com/me/posts?fields=likes{name},id,created_time&limit=10&key=value&" + access_token;
       // URLEncoder.encode(page,"UTF")
    	PrintWriter out = response.getWriter();
    	int i=0;
        while(true)
//    	for(i=0;i<3;i++)
	     {	
        	try {
        		System.out.println("Page"+i);
        		i++;
        				URL oracle = new URL(page);
        	            // read the json file
	            		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        	            JSONParser jsonParser = new JSONParser();
        	            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
        	            
        	            JSONObject pagingposts = (JSONObject) jsonObject.get("paging");

        	            JSONArray post= (JSONArray) jsonObject.get("data");
        	            if(!post.isEmpty())
        	            	break;
        	            for(int k=0;k<post.size();k++)
        	            {
        	            	JSONObject post2 = (JSONObject) post.get(k);
        	            	String POSTID = (String) post2.get("id");
        	            	JSONObject likes = null;
        	            	JSONArray likedata = null;
        	            	int likes2 = 0;
        	            	if(post2.containsKey("likes"))
        	            	{
        	            		likes = (JSONObject) post2.get("likes");
        	            		likedata = (JSONArray) likes.get("data");
        	            		for(int j=0;j<likedata.size();j++)
        	            		{
        	            			JSONObject likedata2 = (JSONObject) likedata.get(j);
        	            			String x = (String) likedata2.get("name");
 //       	            		System.out.println(x + " " + j);
        	            		}
        	            		JSONObject paging = (JSONObject) likes.get("paging");
        	            		JSONObject next1 = null;
        	            		
        	            		if(paging.containsKey("next"))
        	            		{
        	            			likes2  = GetPageLikes((String)paging.get("next"));
        	            		}
        	           		}
        	            	if(likedata!=null)
        	            		System.out.println(likedata.size() + likes2);
        	            	else
        	            		System.out.println("0");
        	            }
        	            if(pagingposts.containsKey("next"))
        	            {
        	            	page = (String) pagingposts.get("next");
        	            	System.out.println("page->"+page);
//        	            	page.replaceAll("%7B", "{");
//        	            	page.replaceAll("%7b", "{");
//        	            	page.replaceAll("%7D", "}");
//        	            	page.replaceAll("%7d", "}");
        	            	
//        	            	System.out.println("tr = "+tr);
//        	            	page.replaceAll(regex, replacement);
        	            	System.out.println(page);
        	            	
        	            	String k =changeStringToNormal(page);
        	            	page = k;
        	            }
        	            else
        	            	break;
        	            

        	            //System.out.println(lang.size());
	        	}
        	catch (Exception ex) 
        	{
	            ex.printStackTrace();
	        }
        } //while(true) ends here


    }

	private int GetPageLikes(String s) throws Exception {
		// TODO Auto-generated method stub
		int likes = 0;
		while(true){
			URL oracle = new URL(s);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
            JSONArray post= (JSONArray) jsonObject.get("data");
            JSONObject paging = (JSONObject) jsonObject.get("paging");
            likes = likes + post.size();
            if(paging.containsKey("next")){
            	
            	s = (String) paging.get("next");
            }
            else {
            	break;
            }
            
		}
		return likes;
	}


}