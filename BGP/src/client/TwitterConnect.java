package client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnect {
	
	public static final String CONSUMER_KEY = "WhEJD98QuNriXyxFEERf5k48u";
	public static final String CONSUMER_SECRET = "wKnKwjjrF9GJwIJopFyj1bMC9ykmk2OzGnjXhxrqsrffaeFtJx";
	private String Token;
	private String userToken;
	private String secretToken;
	private String userSecretToken;
	private AccessToken accessToken =null;
	private RequestToken requestToken = null;
	private String authorizationURL = null;
	Twitter twitter;
	
	SimpleDateFormat sdf;
	
	public TwitterConnect() {
		// TODO Auto-generated constructor stub
		////////////////////////////
		sdf = new SimpleDateFormat("yy년 MM월 dd일 hh시 mm분");
	}
	
	public boolean createTWittter(){
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(null).setOAuthAccessTokenSecret(null);
		TwitterFactory tf = new TwitterFactory(builder.build());

		twitter = tf.getInstance();
		accessToken = null;
		requestToken = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
			Token = requestToken.getToken();
			secretToken = requestToken.getTokenSecret();
				
			
		}catch (TwitterException e) {
			// TODO Auto-generated catch block
			return false;
			//e.printStackTrace();
		}

		return true;
	}
	
	public boolean connTwitter(String url){
		
		userToken = url.substring(url.indexOf("=") + 1, url.indexOf("&"));
		userSecretToken = url.substring(url.lastIndexOf("=")+1);
		
		if(Token.equals(userToken)){
			try {
				accessToken = twitter.getOAuthAccessToken(requestToken, userSecretToken);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return false;
			}
			//accessToken = new AccessToken(token1, secretToken);
		}

		
		twitter.setOAuthAccessToken(accessToken);
		
		return true;
	}
	
	public String getName(){
		return accessToken.getScreenName();
	}
	
	public boolean sendMsg(String msg){
		Date now = new Date();
		Status status = null;
		try {
		   status = twitter.updateStatus(sdf.format(now) +"에 " +msg);
		} catch (TwitterException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorMessage());
			System.out.println(e.getLocalizedMessage());
			return false;
		}
		 return true;
	}
	
	public void usingBrowser(){

		authorizationURL = requestToken.getAuthorizationURL();
		try {
			Desktop.getDesktop().browse(new URI(authorizationURL));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	
}
