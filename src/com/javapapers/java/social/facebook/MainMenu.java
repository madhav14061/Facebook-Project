package com.javapapers.java.social.facebook;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainMenu extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code="";

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		System.out.println("7 ");
		
		code = req.getParameter("code");
		System.out.println("Code:" +code);
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FBConnection fbConnection = new FBConnection();
		System.out.println("eral;sdjfds");
		String accessToken = fbConnection.getAccessToken(code);

		FBGraph fbGraph = new FBGraph(accessToken);
		String graph = fbGraph.getFBGraph();
//		Map<String, String> fbProfileData = fbGraph.getGraphData(fbGraph);
		ServletOutputStream out = res.getOutputStream();
		out.println("<h1>Facebook Login using Java</h1>");
		out.println("<h2>Application Main Menu</h2>");
//		out.println("<div>Welcome "+fbProfileData.get("first_name"));
//		out.println("<div>Your Email: "+fbProfileData.get("email"));
//		out.println("<div>You are "+fbProfileData.get("gender"));		
	}

}
