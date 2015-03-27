package core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class YoutubeVideo {

	//https://www.googleapis.com/youtube/v3/videos?id=_76lPzXf8To&key=AIzaSyAy6jdXarVv3t_QAOd3cu9zEhGbWZcQ9Rw&part=snippet
	
	private String Name;
	private String Url;
	private String Author;
	private String views;
	private String likes;
	private String dislikes;
	
	private Document doc;
	
	public YoutubeVideo(String videoURL){
		try {
			doc = Jsoup.connect(videoURL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Name = doc.title();
		Url = videoURL;
		Author = getAuthorFromDoc();
		views = getViewsFromDoc();
		likes = getLikesFromDoc();
		dislikes = getDislikesFromDoc();
	}
	
	private String getAuthorFromDoc(){
		Elements resultLinks = doc.select("div.yt-user-info a"); // direct a after h3
		return resultLinks.text();
	}
	
	private String getViewsFromDoc(){
		Elements resultLinks = doc.select("div.watch-view-count"); // direct a after h3
		return resultLinks.text();
	}
	
	private String getLikesFromDoc(){
		Elements resultLinks = doc.select("#watch-like"); // direct a after h3
		String tempLikes = resultLinks.text();
		String likes = tempLikes.split("\\s")[0];
		return likes;
	}
	
	private String getDislikesFromDoc(){
		Elements resultLinks = doc.select("#watch-dislike"); // direct a after h3
		String tempLikes = resultLinks.text();
		String dislikes = tempLikes.split("\\s")[0];
		return dislikes;
	}
	
	/*private String getVideoNameFromSubmission(Submission s){
		try {
			Document doc = Jsoup.connect(s.getUrl()).get();
			return doc.title();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getLikes() {
		return likes;
	}

	public void setLikes(String likes) {
		this.likes = likes;
	}

	public String getDislikes() {
		return dislikes;
	}

	public void setDislikes(String dislikes) {
		this.dislikes = dislikes;
	}




}
