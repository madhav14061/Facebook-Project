package Test2;


import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainMenu extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
	private String code="";

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		System.out.println("In Main");
		code = request.getParameter("code");
		if (code == null || code.equals("")) 
		{
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		FacebookConnection fbConnection = new FacebookConnection();
		String accessToken = fbConnection.getAccessToken(code);
		System.out.println("ACCESS TOKEN (MainMenu) = "+accessToken);
//		FacebookGraph fbGraph = new FacebookGraph(accessToken);
//		String graph = fbGraph.getFBGraph();
//		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
//		ServletOutputStream out = res.getOutputStream();
//		out.println("<h1>Facebook Login using Java</h1>");
//		out.println("<h2>Application Main Menu</h2>");
//		out.println("<div>Welcome "+fbProfileData.get("first_name"));
//		out.println("<div>Your Email: "+fbProfileData.get("email"));
//		out.println("<div>You are "+fbProfileData.get("gender"));		
	}

}
