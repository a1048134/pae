package pae.team8.flickrminr.data;

import com.aetrion.flickr.photos.Photo;

public class Image {
	private Photo _image;
	public Image(Photo image){
		_image = image;
	}
	public Photo getImage(){
		return _image;
	}
}
