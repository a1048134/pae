package pae.team8.flickrminr.data;

import java.util.Date;

import com.aetrion.flickr.photos.comments.Comment;

public class CommentItem {
	private String _text;
	private Date _date;
	
	public CommentItem(String comment, Date date){
		_text = comment;
		_date = date;
	}
	
	public String getText(){
		return _text;
	}
	
	public Date getDate(){
		return _date;
	}
}
