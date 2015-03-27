package core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class YoutubeVideo {
	
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
		likes = getLikesDislikes("#watch-like");
		dislikes = getLikesDislikes("#watch-dislike");
	}
	
	/**
	 * Gets the author of the video searching the DOM.
	 * @return
	 */
	private String getAuthorFromDoc(){
		Elements resultLinks = doc.select("div.yt-user-info a"); 
		return resultLinks.text();
	}
	
	/**
	 * Gets the number of views of the video searching the DOM.
	 * @return
	 */
	private String getViewsFromDoc(){
		Elements resultLinks = doc.select("div.watch-view-count"); 
		return resultLinks.text();
	}
	
	/**
	 * Gets likes/dislikes based on the div ID . Scrapes the DOM.
	 * @param divID the div ID for the like/dislike button
	 * @return the amount of likes/dislikes
	 */
	private String getLikesDislikes(String divID){
		Elements resultLinks = doc.select(divID); 
		String tempLikes = resultLinks.text();
		String likes = tempLikes.split("\\s")[0];
		return likes;
	}
		
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
