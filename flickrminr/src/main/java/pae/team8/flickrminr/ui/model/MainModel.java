package pae.team8.flickrminr.ui.model;

import pae.team8.flickrminr.data.DataSet;
import net.logicdevelopment.biscotti.Model;

public class MainModel implements Model{
	DataSet data;
	String imageId;
	
	public void setData(DataSet data){
		this.data = data;
	}
	
	public DataSet getData(){
		return data;
	}
	
	public String getImageId(){
		return imageId;
	}
	
	public void setImageId(String imageId){
		this.imageId = imageId;
	}
}
