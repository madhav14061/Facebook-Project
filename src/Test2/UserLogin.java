package Test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserLogin
 */
@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet 
{
	
	String code=null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogin() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static final String FB_APP_ID = "1006896549368304";
	public static final String FB_APP_SECRET = "43559a5965b4528bdfc85efd6bc4bc4b";
	public static final String REDIRECT_URI = "http://localhost:8085/Trial/Redirected";
	static String accessToken = "";

	
	
	public String getAuthUrl()  //on opening the login page. 
	{
		System.out.println("UserLogin.getAuthURL");
		String fbLoginUrl = "";
		try 
		{
			String x = URLEncoder.encode(UserLogin.REDIRECT_URI, "UTF-8");
//			System.out.print
			System.out.println("Encoded String is :"+x);
//			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
//					+ UserLogin.FB_APP_ID + "&redirect_uri="
//					+ URLEncoder.encode(UserLogin.REDIRECT_URI, "UTF-8")
//					+ "&scope=email";
			
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ UserLogin.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(UserLogin.REDIRECT_URI, "UTF-8")
					+ "&scope=user_about_me,user_friends,user_likes,user_posts,user_location,user_posts,user_status";
//			fbLoginUrl = "Wow";
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		System.out.println(super.toString());
		System.out.println(this.toString());
		System.out.println("::::");
		System.out.println("AuthURL: "+fbLoginUrl);
		
        System.out.println("-------");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2];
        System.out.println("I was called by a method named: " + element.getMethodName());
        System.out.println("That method is in class: " + element.getClassName());
        System.out.println("-------");
		return fbLoginUrl;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		System.out.println("In Get");
		UserLogin l = new UserLogin();
		String redirect = l.getAuthUrl();
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter w = response.getWriter();
		w.println("<html>");
		w.println("<head>");
		w.println("<title>Java Facebook Login</title>");
		w.println("</head>");
		w.println("<body style=\"text-align: center; margin: 0 auto;\">");
//		w.println("<form action='UserLogin' method='post'>");
//		w.println("<input type='submit' value='Login using Facebook' href='"+redirect+"/>");
//		w.print("</form>");
//		w.println("<a href=\"<%=loginObj.getFBAuthUrl()%>\"> Click here to login</a>");
//		w.println("<a href=\"<%=\"l.getAuthUrl()%>\"> Click here to login</a>");
		w.println("<a href='"+redirect+"'> Click here to Login </a>");
		w.println("</body>");
		w.println("</html>");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter w = response.getWriter();
		w.flush();
		w.println("should have cleared");
		System.out.println("In Post");
		UserLogin l = new UserLogin();
		String x = l.getAuthUrl();
		System.out.println("x = "+x);
		URL u = new URL(x);
		URLConnection c = u.openConnection();
		System.out.println("stream: "+c.getInputStream().toString());
		System.out.println("URL IS : "+c.getURL().toString());
		request.getParameter("code");
		if(code==null||code=="")
			System.out.println("code is null/empty");
		else
			System.out.println("code: "+code);
		doGet(request, response);
	}

}
