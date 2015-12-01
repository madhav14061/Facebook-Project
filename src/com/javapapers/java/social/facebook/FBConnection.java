package com.javapapers.java.social.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FBConnection {
	public static final String FB_APP_ID = "1006896549368304";
	public static final String FB_APP_SECRET = "43559a5965b4528bdfc85efd6bc4bc4b";
	public static final String REDIRECT_URI = "http://localhost:8085/Facebook_Login/fbhome";

	static String accessToken = "";

	public String getFBAuthUrl() {
		System.out.println("3");
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
					+ "&scope=email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("AuthURL: "+fbLoginUrl);
		
        System.out.println("-------");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2];
        System.out.println("I was called by a method named: " + element.getMethodName());
        System.out.println("That method is in class: " + element.getClassName());
        System.out.println("-------");
        
		
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		System.out.println("2");
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FBConnection.REDIRECT_URI, "UTF-8")
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
        
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
        System.out.println("-------");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement element = stackTrace[2];
        System.out.println("I was called by a method named: " + element.getMethodName());
        System.out.println("That method is in class: " + element.getClassName());
        System.out.println("-------");
		System.out.println("1");
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(
						fbConnection.getInputStream()));
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
}
