package Test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Redirected
 */
@WebServlet("/Redirected")
public class Redirected extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public static final String FB_APP_ID = "1006896549368304";
	public static final String FB_APP_SECRET = "43559a5965b4528bdfc85efd6bc4bc4b";
	public static final String REDIRECT_URI = "http://localhost:8085/Trial/Redirected";
	public static String code=null;
	public static String accessToken = "";
	
	public String getFBGraphUrl(String code) {
		System.out.println("2");
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + Redirected.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(Redirected.REDIRECT_URI, "UTF-8")
					+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
        System.out.println("-------");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2];
        System.out.println("I was called by a method named: " + element.getMethodName());
        System.out.println("That method is in class: " + element.getClassName());
        System.out.println("-------");
        System.out.println("fbGraphUrl: "+fbGraphUrl);
		return fbGraphUrl;
	}

	public String getAccessToken(String code) 
	{
        System.out.println("-------");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2];
        System.out.println("I was called by a method named: " + element.getMethodName());
        System.out.println("That method is in class: " + element.getClassName());
        System.out.println("-------");
		System.out.println("1");
		if ("".equals(accessToken)) 
		{
			URL fbGraphURL;
			try 
			{
				System.out.println("3");
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				System.out.println("4");
				System.out.println("fbGraphURL = "+fbGraphURL);
				fbConnection = fbGraphURL.openConnection();
				System.out.println("Connection opened.");
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				System.out.println("Getting input stream");
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ accessToken);
			}
		}
		System.out.println("accessToken: "+accessToken);
		return accessToken;
	}
	
    public Redirected() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		code = request.getParameter("code");
		System.out.println("Code:" +code);
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		else
			System.out.println("Code: "+code);
		
		Redirected rd = new Redirected();
		String x = rd.getAccessToken(code);
		accessToken = x;
		System.out.println("x="+x);
		System.out.println("AccessToken = "+accessToken);
		PrintWriter w = response.getWriter();
		w.println("Code: "+code);
		w.println("AccessToken = "+accessToken);
		
		w.println("<form action='LikeCounter' method='get'>");
		w.println("<input type='submit' value='continue'");
		w.println("</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
