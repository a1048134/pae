package pae.team8.flickrminr.data;

import java.util.ArrayList;

import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.comments.Comment;

public class Author {
	private User _author;
	private ArrayList<CommentItem> _comments;
	
	public Author(){}
	
	public void setAuthor(User author){
		_author = author;
	}
	public User getAuthor(){
		return _author;
	}
	
	public void addComment(Comment comment){
		CommentItem commentItem = new CommentItem(comment.getText(), comment.getDateCreate());
		_comments.add(commentItem);
	}	
	
	public ArrayList<CommentItem> getComments(){
		return _comments;
	}
	public CommentItem getComment(int commentId){
		return _comments.get(commentId);
	}
	
	public String toString(){
		return new String(_author.getUsername());
	}
}
