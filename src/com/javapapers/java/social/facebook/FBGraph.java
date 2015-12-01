package com.javapapers.java.social.facebook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

public class FBGraph {
	private String accessToken;

	public FBGraph(String accessToken) {
		System.out.println("4");
		this.accessToken = accessToken;
	}

	public String getFBGraph() {
		System.out.println("5");
		String graph = null;
		try {

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
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

	public Map<String, String> getGraphData(Map fbGraph) {
		System.out.println("6");
		Map<String, String> fbProfile = new HashMap<String, String>();
		try {
//			JSONObject s = new JSONObject();
			System.out.println("In getGraphData.try");
		JSONObject json = new JSONObject(fbGraph);
		System.out.println("JSON: -> "+json.toString());
		fbProfile.put("id", json.get("id").toString());
		System.out.println("Got the ID!");
		fbProfile.put("first_name", json.get("first_name").toString());
//		json.containsKey("email")
		if (json.containsKey("email"))
			fbProfile.put("email", json.get("email").toString());
		if (json.containsKey("gender"))
			fbProfile.put("gender", json.get("gender").toString());
	} catch (JsonException e) {
		e.printStackTrace();
		throw new RuntimeException("ERROR in parsing FB graph data. " + e);
	}
		return fbProfile;
	}
}
