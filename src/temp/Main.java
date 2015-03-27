package temp;

import java.util.List;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.RestClient;

import core.Bot;
import core.YoutubeVideo;

public class Main {
	
	public static void main(String[] args) {

		Bot arocketmanbot = new Bot("arocketmanbot","pass");
		
		RestClient restClient = arocketmanbot.getRestClient();
		User user = arocketmanbot.getUser(restClient);
		
		Submissions submissions = new Submissions(restClient);
		
		//TODO: Edit with the 'bot' parameters later.
		List<Submission> submissionsSubreddit = submissions.ofSubreddit(Bot.SUBREDDIT, SubmissionSort.RISING, -1, 5, null, null, true);
		
		for(Submission s : submissionsSubreddit){
			if(s.getUrl().contains("youtu")){
				//Getting video information
				YoutubeVideo video = new YoutubeVideo(s.getUrl());
				//TODO: MAKE SURE IT DOESN'T POST TWICE ON THE SAME SUBMISSION.
				arocketmanbot.post(restClient, user, video, s);
			}
		}
		
	}
	
}
