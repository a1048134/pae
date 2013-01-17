package pae.team8.flickrminr;

import pae.team8.flickrminr.nlp.NLPConnector;

public class NlpInit extends Thread{

	@Override
	public void run() {
		App._nlp = new NLPConnector();		
	}

}
