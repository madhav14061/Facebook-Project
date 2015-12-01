package Test2;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

public class FacebookGraph 
{
	private String accessToken;

	public FacebookGraph(String accessToken) 
	{
		System.out.println("d");
		this.accessToken = accessToken;
	}

	public String getFBGraph() 
	{
		System.out.println("c");
		System.out.println("In getGraph");
		String graph = null;
		try 
		{

			String g = "https://graph.facebook.com/me?" + accessToken;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
			System.out.println("graph is :" + graph);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

}
