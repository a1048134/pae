package pae.team8.flickrminr.analyzer;

import pae.team8.flickrminr.data.DataSet;
import pae.team8.flickrminr.data.ResultSetter;
import pae.team8.flickrminr.exceptions.MinrException;

public class AnalyzerThread extends Thread{
	private Analyzer _a;
	private ResultSetter _setter;
	public AnalyzerThread(Analyzer a, ResultSetter setter){
		_a = a;
		_setter = setter;
	}
	public void run(){
		DataSet data=null;
		try {
			data = _a.doAnalyze();
		} catch (MinrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_setter.setResult(data);
	}
}