package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.github.jreddit.action.SubmitActions;
import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Comments;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.retrieval.params.UserOverviewSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class Bot {

	public final static String USER_AGENT = "arocketmanbot v0.1a by arocketman";
	public final static String SUBREDDIT = "videos";
	
	private String username;
	private String password;
	private RestClient restClient;
	private User user;
	
	public Bot(String user, String pass){
		username = user;
		password = pass;
		
		//Setting up the restClient
		restClient = new HttpRestClient(); 
		restClient.setUserAgent(Bot.USER_AGENT);
		this.user = getUser();
	}
	
	/**
	 * Builds up the message that the bot is going to post.
	 * @param theName video name.
	 * @param theAuthor video author.
	 * @param theViews video views.
	 * @param theLikes video likes.
	 * @param theDislikes video dislikes.
	 * @return
	 */
	public String createMessage(String theName, String theAuthor, String theViews , String theLikes , String theDislikes){
		String message = "Hello, I am a bot that retrieves stats and information about Youtube videos when they're posted on Reddit.\n\n";
		message += "Created by : /u/arocketman , for malfunctioning and information refer to : https://github.com/arocketman/arocketmanbot\n\n";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		message += "**Stats retrieval on** : " + dateFormat.format(date) + "\n\n";
		message += "**Name**: " + theName + "\n\n";
		message += "**Author**: " + theAuthor + "\n\n";
		message += "**Views** : " + theViews + "\n\n";
		message += "**Likes** : " + theLikes + "\n\n";
		message += "**Dislikes** : " + theDislikes + "\n\n";
		return message;
	}
	
	/**
	 * Posts the stats for a given video.
	 * @param video The YoutubeVideo to be posted.
	 * @param s the submission (where to be posted)
	 * @return true if successfully posted.
	 */
	public boolean post(YoutubeVideo video, Submission s){
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
	public User getUser() {
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
		return restClient;
	}

	/**
	 * Checks for wheter or not the bot did already post on a submission.
	 * @param submissionID The submission to check for.
	 * @return true if already posted.
	 */
	public boolean alreadyPosted(String submissionID) {
		Comments coms = new Comments(restClient, user);
		List<Comment> commentsUser = coms.ofUser(this.username, UserOverviewSort.NEW, TimeSpan.ALL, -1, 80, null, null, true);
		for(Comment comment : commentsUser){
			if(comment.getParentId().equals(submissionID)) 
				return true;
		}
		return false;
	}
}
