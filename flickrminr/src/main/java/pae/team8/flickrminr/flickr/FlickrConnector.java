package pae.team8.flickrminr.flickr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import pae.team8.flickrminr.data.DataSet;
import pae.team8.flickrminr.exceptions.MinrException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.Parameter;
import com.aetrion.flickr.people.User;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.comments.Comment;
import com.aetrion.flickr.test.TestInterface;

public class FlickrConnector {
	private boolean _connected = false;
	private Flickr _f = null;
	private String _apiKey;
	private static final Logger logger = LoggerFactory.getLogger(FlickrConnector.class);

	public FlickrConnector(String apiKey){
		_apiKey = apiKey;
	}	
	
	public DataSet fetchData(String photoId) throws MinrException{
		DataSet data = new DataSet();
		
		Photo photo;
		try {
			photo = getImage(photoId);
			data.set_image(photo);
			data.set_owner();
			data.set_realImage(getRealImage(photo));
			data.set_comments(getComments(photoId));
		} catch (FlickrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;		
	}

	private Photo getImage(String photoId) throws FlickrException{
		Photo photo = null;
		
		try {
			photo = _f.getPhotosInterface().getPhoto(photoId);			
		} catch (IOException e) {
			logger.error("IOException: {}", e.getMessage());
		} catch (SAXException e) {
			logger.error("SAXException: {}", e.getMessage());
		}
		
		return photo;
	}
	
	private BufferedImage getRealImage(Photo image){
		BufferedImage realImage = null;
		try {
			realImage = _f.getPhotosInterface().getImage(image, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlickrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return realImage;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Comment> getComments(String photoId){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		try {			
			comments = (ArrayList<Comment>) _f.getCommentsInterface().getList(photoId);
		} catch (IOException e) {
			logger.error("IOException: {}", e.getMessage());
		} catch (SAXException e) {
			logger.error("SAXException: {}", e.getMessage());
		} catch (FlickrException e) {
			logger.error("FlickrException: {}", e.getErrorMessage());
		}
		
		return comments;
	}
	
	private ArrayList<User> getAuthors(ArrayList<Comment> comments){
		ArrayList<User> authors = new ArrayList<User>();
		
		for(Comment comment : comments){			
			User user = getAuthor(comment.getAuthor());
			if(!exists(authors, user)){
				authors.add(user);
			}
		}
		
		return authors;
	}
	
	private boolean exists(ArrayList<User> authors, User author){
		boolean exists = false;
		
		for(User user: authors){
			if(author.equals(user)){
				exists = true;
			}
		}
		
		return exists;
	}
	
	private User getAuthor(String authorInfo) {
		User author = null;
		
		try {
			author = _f.getPeopleInterface().getInfo(authorInfo);
		} catch (IOException e) {
			logger.error("IOException: {}", e.getMessage());
		} catch (SAXException e) {
			logger.error("SAXException: {}", e.getMessage());
		} catch (FlickrException e) {
			logger.error("FlickrException: {}", e.getErrorCode() + " " + e.getErrorMessage() + " " + authorInfo);
		}
		return author;
	}
		
	public boolean Connect(){		
		if(!(_f instanceof Flickr)){
			try {
				_f = new Flickr(_apiKey);
				_connected = TestConnection();
			} catch (IOException e) {
				logger.error("IOException: {}", e.getMessage());
			} catch (SAXException e) {
				logger.error("SAXException: {}", e.getMessage());
			} catch (FlickrException e) {
				logger.error("FlickrException: {}", e.getErrorMessage());
			}
		}
		return _connected;
	}
	
	
	private boolean TestConnection() throws IOException, SAXException, FlickrException{
		logger.debug("Testing connection to flickr.");
		TestInterface testInterface = _f.getTestInterface();
		ArrayList<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("test","test"));
		Collection result = testInterface.echo(params);
		
		if(result == null){
			logger.debug("Flickr could not be connected.");
			return false;
		}else{
			logger.debug("Flickr is connected.");
			return true;
		}
	}
}
