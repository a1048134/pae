package pae.team8.flickrminr.analyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aetrion.flickr.photos.comments.Comment;

import pae.team8.flickrminr.App;
import pae.team8.flickrminr.data.DataSet;
import pae.team8.flickrminr.exceptions.MinrException;
import pae.team8.flickrminr.flickr.FlickrConnector;

public class Analyzer {
	Logger logger = LoggerFactory.getLogger("pae.team8.flickrminr.analyzer.Analyzer");
	private FlickrConnector _fc;
	private String _photoId;
	private DataSet _data;
	
	public Analyzer(){		
		_fc = new FlickrConnector("337296f4fb6da564847336c103e31248");
		_fc.Connect();
	}
	
	public void setPhotoId(String photoId){
		_photoId = photoId;
		_data = new DataSet();
		try {
			_data = _fc.fetchData(_photoId);
		} catch (MinrException e) {
			e.printStackTrace();
		}
	}
	public String getPhotoId(){
		return _photoId;
	}
	
	public DataSet doAnalyze() throws MinrException{
		for(Comment comment : _data.getComments()){
			App._nlp.createNLPObject(comment.getText());
		}
		
		return _data;
	}
}