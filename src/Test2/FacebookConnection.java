package Test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FacebookConnection 
{
	public static final String FB_APP_ID = "1006896549368304";
	public static final String FB_APP_SECRET = "43559a5965b4528bdfc85efd6bc4bc4b";
	public static final String REDIRECT_URI = "http://localhost:8085/Facebook_Login/Redirected";

	static String accessToken = "";

	public String getFBAuthUrl()  //on opening the login page. 
	{
		System.out.println("b");
		System.out.println("In Auth URL");
		String fbLoginUrl = "";
		try {
			String encoded = URLEncoder.encode(FacebookConnection.REDIRECT_URI, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try 
		{
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ FacebookConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FacebookConnection.REDIRECT_URI, "UTF-8")
					+ "&scope=email";
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		System.out.println("AuthURL: "+fbLoginUrl);
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) 
	{
		System.out.println("a");
		String fbGraphUrl = "";
		try 
		{
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + FacebookConnection.FB_APP_ID + "&redirect_uri="
					+ URLEncoder.encode(FacebookConnection.REDIRECT_URI, "UTF-8")
					+ "&client_secret=" + FB_APP_SECRET + "&code=" + code;
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		System.out.println("FB GRAPH URL: "+fbGraphUrl);
		return fbGraphUrl;
	}

	public String getAccessToken(String code) 
	{
		if ("".equals(accessToken)) 
		{
			URL fbGraphURL;
			try 
			{
				fbGraphURL = new URL(getFBGraphUrl(code));
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try 
			{
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(
						fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook "+ e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) 
			{
				throw new RuntimeException("ERROR: Access Token Invalid: "+ accessToken);
			}
		}
		System.out.println("accessToken: "+accessToken);
		return accessToken;
	}
}
