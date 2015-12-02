package Test2;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.javafx.collections.MappingChange.Map;


@WebServlet(name = "FormServlet", urlPatterns = { "/LikeCounter" })

public class LikeCounter extends HttpServlet 
{
	ArrayList<String> idLikes = new ArrayList<String>();
	ArrayList<String> idStatus = new ArrayList<String>();
	ArrayList<String> idPhoto = new ArrayList<String>();
	ArrayList<String> idLink = new ArrayList<String>();
	ArrayList<String> idVideo = new ArrayList<String>();
	public static HashMap<String, ArrayList<String>> wordLikerHashMap = new HashMap<String, ArrayList<String>>();
	public static HashMap<String, ArrayList<Integer>> statusHashMap = new HashMap<String, ArrayList<Integer>>();
	public static HashMap<String, ArrayList<Integer>> photoHashMap = new HashMap<String, ArrayList<Integer>>();
	public static HashMap<String, ArrayList<Integer>> linkHashMap = new HashMap<String, ArrayList<Integer>>();
	public static HashMap<String, ArrayList<Integer>> videoHashMap = new HashMap<String, ArrayList<Integer>>();
	public static int statusavg;
	public static int videoavg;
	public static int linkavg;
	public static int photoavg;
	public static int statuses;
	public static int photos;
	public static int links;
	public static int videos;
	
	public static HashMap <String,Integer> chepppl = new HashMap<String,Integer>(); 
	public String changeStringToNormal(String data)
	{
//		data = data.replace("%20", " "); 
		data = data.replace("%21", "!"); 
		data = data.replace("%22", "\""); 
		data = data.replace("%23", "#"); 
		data = data.replace("%24", "$"); 
		data = data.replace("%25", "%"); 
		data = data.replace("%26", "&"); 
		data = data.replace("%27", "\'"); 
		data = data.replace("%28", "("); 
		data = data.replace("%29", ")"); 
//		data = data.replace("%2A", "*"); 
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
//		data = data.replace("%60", "`"); 
		data = data.replace("%7B", "{"); 
		data = data.replace("%7C", "|"); 
		data = data.replace("%7D", "}"); 
//		data = data.replace("%7E", "~"); 
//		data = data.replace("%80", "`"); 
		data = data.replace("limit=10&", "");
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
    		System.out.println("YOLO");
    	}
    	BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

    	
    	System.out.println("PLEASE ENTER THE PERCENTAGE OF POSTS YOU WANT TO PREDICT LIKES FOR");
    	Scanner in2= new Scanner(System.in);
    	Integer perc = in2.nextInt();
    	String page = "https://graph.facebook.com/me/posts?fields=type,message,story,likes{name},id,created_time&limit=10&key=value&" + access_token;
       // URLEncoder.encode(page,"UTF")
    	String page2=page;
    	ArrayList<Integer> st = new ArrayList<Integer>();
    	ArrayList<Integer> ph = new ArrayList<Integer>();
    	ArrayList<Integer> vid = new ArrayList<Integer>();
    	ArrayList<Integer> lnk = new ArrayList<Integer>();
    	System.out.println(page);
    	PrintWriter out = response.getWriter();
    	int i=0;
    	int sum=0;
    	while(true){
    		try 
        	{
        		System.out.println("Page"+i);
        		i++;
        				URL oracle = new URL(page);
        	            // read the json file
	            		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        	            JSONParser jsonParser = new JSONParser();
        	            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
        	            
        	            

        	            JSONArray post= (JSONArray) jsonObject.get("data");
        	            if(post.isEmpty())
        	            {
        	            	break;
        	            }
        	            sum = sum + post.size();
        	            JSONObject pagingposts = (JSONObject) jsonObject.get("paging");
        	            if(pagingposts.containsKey("next"))
        	            {
        	            	page = (String) pagingposts.get("next");
        	            	System.out.println("page->"+page);
//        	            	page.replaceAll("%7B", "{");
//        	            	page.replaceAll("%7b", "{");
//        	            	page.replaceAll("%7D", "}");
//        	            	page.replaceAll("%7d", "}");
        	            	String tr = URLEncoder.encode(page, "UTF-8");
        	            	System.out.println("tr = "+tr);
//        	            	page.replaceAll(regex, replacement);
        	            	System.out.println(page);
        	            	
        	            	String k =changeStringToNormal(page);
        	            	page = k;
        	            }
        	            else
        	            {
        	            	break;
        	            }
        	            
        	}catch(Exception E){
        		E.printStackTrace();
        	}
    	}
    	
    	System.out.println("The Number Of Posts is "+ sum);
    	page = page2;
    	i=0;
    	int predic = (perc*sum)/100;
    	int j2=0;
    	int page1posts = 0;
       while(true)
//    	for(i=0;i<3;i++)
	     {	
        	try 
        	{
        		System.out.println("Page"+i);
        		i++;
        				URL oracle = new URL(page);
        	            // read the json file
	            		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        	            JSONParser jsonParser = new JSONParser();
        	            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
        	            
        	            

        	            JSONArray post= (JSONArray) jsonObject.get("data");
        	            if(post.isEmpty())
        	            {
        	            	break;
        	            }
        	            if(i==1){
	            			page1posts = post.size();
	            		}
        	            JSONObject pagingposts = (JSONObject) jsonObject.get("paging");
        	            for(int k=0;k<post.size();k++)
        	            {
        	            	idStatus = new ArrayList<String>();
        	            	idPhoto = new ArrayList<String>();
        	            	idLink = new ArrayList<String>();
        	            	idVideo = new ArrayList<String>();
        	            	JSONObject post2 = (JSONObject) post.get(k);
        	            	String type = (String) post2.get("type");
        	            	String message = "";
        	            	if(post2.containsKey("message"))
        	            		message = (String) post2.get("message");
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
        	            			String x = (String) likedata2.get("id");
        	            			if(j2>predic)
        	            			{

        	            			if(type.equals("status"))
        	            				idStatus.add(x);
        	            			if(type.equals("photo"))
        	            				idPhoto.add(x);
        	            			if(type.equals("link"))
        	            				idLink.add(x);
        	            			if(type.equals("video"))
        	            				idLink.add(x);
        	            			}
        	            			
        	            			if(i==1)
        	            			{
        	            				if(chepppl.containsKey(x))
        	            				{
        	            					//A.put(x, A.get(x)++);
        	            					Integer z = chepppl.get(x);
        	            					chepppl.put(x, z+1);
        	            					
        	            				}
        	            				else
        	            				{
        	            					chepppl.put(x, 1);
        	            				}
        	            			}
        	            		//	System.out.println(x + " " + j);
        	            		}
        	            		JSONObject paging = (JSONObject) likes.get("paging");
        	            		//JSONObject next1 = null;
        	            		
        	            		if(paging.containsKey("next"))
        	            		{
        	            			likes2  = GetPageLikes((String)paging.get("next") , i, type, message);
        	            		}
        	            	}
							int totalLikes;
        	            	if(likedata!=null)
        	            		{
        	            			totalLikes = likedata.size() + likes2;
        	            			System.out.println(totalLikes);
        	            		}
        	            	else{
        	            		totalLikes = 0;
        	            		System.out.println("0");
        	            	}
        	            		
        	            	
        	            	if(type.equals("status")){
        	            		statusavg = statusavg + totalLikes;
        	            		statuses++;
        	            	}
        	            	else if(type.equals("video")){
        	            		videoavg = videoavg + totalLikes;
        	            		videos++;
        	            	}
        	            	else if(type.equals("link")){
        	            		linkavg = linkavg + totalLikes;
        	            		links++;
        	            	}
        	            	else if(type.equals("photo")){
        	            		photoavg = photoavg + totalLikes;
        	            		photos++;
        	            		System.out.println(photoavg/photos);
        	            	}
        	            	int size = 0;
        	            	if(!idStatus.isEmpty())
        	            		size = idStatus.size();
        	            	else if(!idPhoto.isEmpty())
        	            		size = idPhoto.size();
        	            	else if(!idLink.isEmpty())
        	            		size = idLink.size();
        	            	else if(!idVideo.isEmpty())
        	            		size = idVideo.size();
        	            	
//        	            	for(int msgLen=0;msgLen<message.length();msgLen++)
//        	            	{
        	            	if(!message.isEmpty())
        	            	{
//        	            		System.out.println("message:"+message);
        	            		message = message.replace(",", " ");
        	            		message = message.replace("\n", " ");
        	            		message = message.replace(".", " ");
        	            		message = message.replace("(", " ");
        	            		message = message.replace(")", " ");
        	            		message = message.replace("?", " ");
        	            		message = message.replace("-", " ");
        	            		String words[] = message.split(" ");
        	            		ArrayList<Integer> get;
        	            		ArrayList<String> keepingTrack= new ArrayList<String>();
        	            		for(String w : words)
        	            		{
        	            			if(w.length()>2 && !keepingTrack.contains(w) && j2>predic)
        	            			{
        	            				keepingTrack.add(w);
        	            				if(type.equals("status"))
        	            				{
        	            					get = statusHashMap.get(w);
        	            					if(get==null)
        	            					{
        	            						get=new ArrayList<Integer>();
        	            						get.add(size);
        	            						statusHashMap.put(w, get);
        	            					}
        	            					else
        	            					{
        	            						get.add(size);
        	            						statusHashMap.replace(w,get);
        	            					}
        	            				}
        	            				else if(type.equals("photo"))
            	            			{
        	            					get = photoHashMap.get(w);
        	            					if(get==null)
        	            					{
        	            						get=new ArrayList<Integer>();
        	            						get.add(size);
        	            						photoHashMap.put(w, get);
        	            					}
        	            					else
        	            					{
        	            						get.add(size);
        	            						photoHashMap.replace(w,get);
        	            					}
        	            				}
        	            				else if(type.equals("link"))
            	            			{
        	            					get = linkHashMap.get(w);
        	            					if(get==null)
        	            					{
        	            						get=new ArrayList<Integer>();
        	            						get.add(size);
        	            						linkHashMap.put(w, get);
        	            					}
        	            					else
        	            					{
        	            						get.add(size);
        	            						linkHashMap.replace(w,get);
        	            					}
        	            					continue;
        	            				}
        	            				else if(type.equals("video"))
            	            			{
        	            					get = videoHashMap.get(w);
        	            					if(get==null)
        	            					{
        	            						get=new ArrayList<Integer>();
        	            						get.add(size);
        	            						videoHashMap.put(w, get);
        	            					}
        	            					else
        	            					{
        	            						get.add(size);
        	            						videoHashMap.replace(w,get);
        	            					}
        	            				}   	            				
        	            			}
        	            		}
        	            	}
        	            	j2++;
//        	            	}
        	            }
        	            if(pagingposts.containsKey("next"))
        	            {
        	            	page = (String) pagingposts.get("next");
        	            	System.out.println("page->"+page);
        	            	page = page.replace("%7B", "{");
        	            	page = page.replace("%7b", "{");
        	            	page = page.replace("%7D", "}");
        	            	page = page.replace("%7d", "}");
        	            	String tr = URLEncoder.encode(page, "UTF-8");
        	            	System.out.println("tr = "+tr);
//        	            	page.replaceAll(regex, replacement);
        	            	System.out.println(page);
        	            	
        	            	String k =changeStringToNormal(page);
        	            	page = k;
        	            }
        	            else
        	            {
        	            	break;
        	            }

        	            //System.out.println(lang.size());
	        }
        	catch (Exception ex) 
        	{
	            ex.printStackTrace();
	        }
        }
        for (Entry<String, Integer> entry : chepppl.entrySet())
        {
//        	System.out.println(entry.getKey()+"----------->"+entry.getValue());
        	System.out.println(entry.getKey()+"----------->"+((double) entry.getValue()/ (double) page1posts)*100);
        }
        
        System.out.println(statusHashMap.toString());
        System.out.println("####################");
        System.out.println(photoHashMap.toString());
        System.out.println("####################");
        System.out.println(linkHashMap.toString());
        System.out.println("####################");
        System.out.println(videoHashMap.toString());
        page = page2;
        j2=0;
        int flag=0;
        statusavg = statusavg/statuses;
        photoavg = photoavg/photos;
        linkavg = linkavg/links;
        videoavg = videoavg/videos;
        while(true & flag==0)
//        	for(i=0;i<3;i++)
    	     {	
            	try 
            	{
            		
            				URL oracle = new URL(page);
            	            // read the json file
    	            		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            	            JSONParser jsonParser = new JSONParser();
            	            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
            	            
            	            

            	            JSONArray post= (JSONArray) jsonObject.get("data");
            	            if(post.isEmpty())
            	            {
            	            	break;
            	            }
            	            int likes2 = 0;
            	            JSONObject pagingposts = (JSONObject) jsonObject.get("paging");
            	            for(int k=0;k<post.size();k++)
            	            {
            	            	if(j2>predic){
            	            		flag=1;
            	            		break;
            	            	}
            	            	JSONObject post2 = (JSONObject) post.get(k);
            	            	String type = (String) post2.get("type");
            	            	String message = "";
            	            	if(post2.containsKey("message"))
            	            		message = (String) post2.get("message");
            	              Predictor p = new Predictor();
            	              int predict = p.predictLikes(message, type);
            	              System.out.println("Predicted Likes: "+predict);
            	              JSONObject likes = null;
            	              JSONArray likedata = null;
            	              if(post2.containsKey("likes"))
  							{
          	            		likes = (JSONObject) post2.get("likes");
          	            		likedata = (JSONArray) likes.get("data");
  							
            	            
            	            	  JSONObject paging = (JSONObject) likes.get("paging");
							
      	            		//JSONObject next1 = null;
      	            		
      	            		if(paging.containsKey("next"))
      	            		{
      	            			likes2 = GetPageLikes((String)paging.get("next") , i, type, message);
      	            		}
  							}
      	            		if(likedata!=null)
        	            		{
      	            			System.out.println("LIKES = " + (likedata.size() + likes2));
        	            		}
        	            	else
        	            		System.out.println("LIKES = 0");
      	            		
      	            		 
      	            		j2++;
            	              
            	            	
            	            }
            	            if(flag==1){
            	            	break;
            	            }
            	            if(pagingposts.containsKey("next"))
             	            {
             	            	page = (String) pagingposts.get("next");
             	            	System.out.println("page->"+page);
//             	            	page.replaceAll("%7B", "{");
//             	            	page.replaceAll("%7b", "{");
//             	            	page.replaceAll("%7D", "}");
//             	            	page.replaceAll("%7d", "}");
             	            	String tr = URLEncoder.encode(page, "UTF-8");
             	            	System.out.println("tr = "+tr);
//             	            	page.replaceAll(regex, replacement);
             	            	System.out.println(page);
             	            	
             	            	String k2 =changeStringToNormal(page);
             	            	page = k2;
             	            }
             	            else
             	            {
             	            	break;
             	            }
            	}catch(Exception E){
            		E.printStackTrace();
            	}
    	     }
    	     
        System.out.println("DO YOU WANT TO ADD A NEW POST BASED ON THE FIRST "+ perc + " PERCENTAGE OF YOUR POSTS");
        Scanner n2 = new Scanner(System.in);
        String n3 = n2.nextLine();
        if(n3.toLowerCase().equals("yes")){
        	System.out.println("Type Of Post?");
        	n3 = n2.nextLine();
        	if(n3.toLowerCase().equals("status")){
        		Predictor p = new Predictor();
        		System.out.println("Enter the status");
        		String s = n2.nextLine();
        		System.out.println(p.predictLikes(s,n3));
        	}
        	else if(n3.toLowerCase().equals("photo")){
        		Predictor p = new Predictor();
        		System.out.println("Enter the caption");
        		String s = n2.nextLine();
        		System.out.println(p.predictLikes(s,n3));
        	}
        	else if(n3.toLowerCase().equals("link")){
        		Predictor p = new Predictor();
        		System.out.println("Enter the status");
        		String s = n2.nextLine();
        		System.out.println(p.predictLikes(s,n3));
        	}
        	else if(n3.toLowerCase().equals("video")){
        		Predictor p = new Predictor();
        		System.out.println("Enter the caption");
        		String s = n2.nextLine();
        		System.out.println(p.predictLikes(s,n3));
        	}
        }
        



    }

	private int GetPageLikes(String s, int i, String type, String message) throws Exception {
		// TODO Auto-generated method stub
		int likes = 0;
		while(true){
			URL oracle = new URL(s);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
            JSONArray post= (JSONArray) jsonObject.get("data");
            JSONObject paging = (JSONObject) jsonObject.get("paging");
            String x = null;

//        	String type = (String) jsonObject.get("type");
            for(int j=0;j<post.size();j++)
            {
            	JSONObject y = (JSONObject) (post.get(j));
            	x = (String) (y.get("id"));
            	
            	if(type.equals("status"))
    				idStatus.add(x);
    			if(type.equals("photo"))
    				idPhoto.add(x);
    			if(type.equals("link"))
    				idLink.add(x);
    			if(type.equals("video"))
    				idLink.add(x);
            	
            	if(i==1){
    				if(chepppl.containsKey(x)){
    					//A.put(x, A.get(x)++);
    					Integer z = chepppl.get(x);
    					chepppl.put(x, z+1);
    					
    				}
    				else{
    					chepppl.put(x, 1);
    				}
    			}
            	
            }
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