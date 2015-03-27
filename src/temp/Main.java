package temp;

import java.util.List;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;

import core.Bot;
import core.YoutubeVideo;

public class Main {
	
	public static void main(String[] args) {

		Bot arocketmanbot = new Bot("arocketmanbot","va");
		
		Submissions submissions = new Submissions(arocketmanbot.getRestClient());
		
		List<Submission> submissionsSubreddit = submissions.ofSubreddit(Bot.SUBREDDIT, SubmissionSort.RISING, -1, 100, null, null, true);
		
		for(Submission s : submissionsSubreddit){
			if(s.getUrl().contains("youtu")){
				//Getting video information.
				YoutubeVideo video = new YoutubeVideo(s.getUrl());
				//Posting the video.
				if(!arocketmanbot.alreadyPosted(s.getFullName()))
					arocketmanbot.post(video, s);
			}
		}
		arocketmanbot.deleteNegativeComments();
		
	}
	
}
