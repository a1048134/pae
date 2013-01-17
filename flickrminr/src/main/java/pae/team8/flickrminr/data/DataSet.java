package pae.team8.flickrminr.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.comments.Comment;

public class DataSet {
	Logger logger = LoggerFactory.getLogger("pae.team8.flickrminr.data.DataSet");
	private ArrayList<User> _authors;
	private ArrayList<Comment> _comments;
	private Photo _image;
	private User _owner;
	private BufferedImage _realImage;
	
	public DataSet() {
		_authors = new ArrayList<User>();
		_comments = new ArrayList<Comment>();
	}

	public void set_authors(ArrayList<User> authors) {
		this._authors = authors;
	}
	
	public void set_comments(ArrayList<Comment> comments) {
		for(Comment comment : comments){
			if(comment != null && comment.getText().length() >= 10){
				_comments.add(comment);
			}
		}
		
	}

	public void set_image(Photo image) {
		_image = image;
	}
	
	public void set_realImage(BufferedImage realImage) {
		_realImage = realImage;
	}
	
	public ArrayList<User> getAuthors(){
		return _authors;
	}
	
	public ArrayList<Comment> getComments(){
		return _comments;
	}
	
	public BufferedImage getRealImage(){
		return _realImage;
	}
	public Photo getImage(){
		return _image;
	}
	
	public User getOwner(){
		return _owner;
	}
	
	public void set_owner() {
		if(_image!=null)
		this._owner = _image.getOwner();
	}
}