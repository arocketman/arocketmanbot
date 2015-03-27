package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.jreddit.action.SubmitActions;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class Bot {

	public final static String USER_AGENT = "arocketmanbot v0.1a by arocketman";
	public final static String SUBREDDIT = "videos";
	
	private String username;
	private String password;
	
	public Bot(String user, String pass){
		username = user;
		password = pass;
	}
		
	public String createMessage(String theName, String theAuthor, String theViews , String theLikes , String theDislikes){
		String message = "Hello, I am a bot that retrieves stats and informations about youtube videos when they're posted on Reddit.\n\n";
		message += "Created by : /u/arocketman , for malfunctioning and information refer to : https://github.com/arocketman\n\n";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		message += "**Posted on** : " + dateFormat.format(date) + "\n\n";
		message += "**Name**: " + theName + "\n\n";
		message += "**Author**: " + theAuthor + "\n\n";
		message += "**Views** : " + theViews + "\n\n";
		message += "**Likes** : " + theLikes + "\n\n";
		message += "**Dislikes** : " + theDislikes + "\n\n";
		return message;
	}
	
	public boolean post(RestClient restClient, User user, YoutubeVideo video, Submission s){
		SubmitActions submitAction = new SubmitActions(restClient, user);
		String message = createMessage(video.getName(), video.getAuthor(), video.getViews(), video.getLikes(), video.getDislikes());
		if(submitAction.comment(s.getFullName(), message)){
			System.out.println("I commented on " + video.getUrl() + " , " + s.getTitle());
			System.out.println("I wrote: " + message);
			System.out.println("=========================================");
			return true;
		}
		else{
			System.out.println("There was something wrong");
			return false;
		}
	}
	
	/**
	 * Given a restClient it creates and returns the bot User class.
	 * @param restClient the restClient needed for the User constructor.
	 * @return the Bot User.
	 */
	public User getUser(RestClient restClient) {
		// Connect the user 
		User user = new User(restClient, username, password);
		try {
		    user.connect();
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
		return user;
	}
	
	/**
	 * Utility method that creates a RestClient for the bot.
	 * @return the restClient for the bot
	 */
	public RestClient getRestClient(){
		RestClient restClient = new HttpRestClient(); 
		restClient.setUserAgent(Bot.USER_AGENT);
		return restClient;
	}
}
