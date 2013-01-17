package pae.team8.flickrminr.ui.presenter;

import net.logicdevelopment.biscotti.Presenter;
import pae.team8.flickrminr.analyzer.Analyzer;
import pae.team8.flickrminr.analyzer.AnalyzerThread;
import pae.team8.flickrminr.data.DataSet;
import pae.team8.flickrminr.data.ResultSetter;
import pae.team8.flickrminr.ui.model.MainModel;
import pae.team8.flickrminr.ui.view.MainView;

public class MainPresenter extends Presenter{
	private MainView view;
	private MainModel model;
	private DataSet data;
	private Analyzer analyzer;
	private ResultSetter setter;
	
	public void doInit(){
		analyzer = new Analyzer();
		view = (MainView) views.get("main");
		model = (MainModel) models.get("main");
		data = null;	
		view.show();
		setter = new ResultSetter() {  
		    public void setResult(DataSet result) {  
		      data = result;  
		    }  
		};
	}
	
	public void doAnalyze(){
		String id = view.getImageId();
		System.out.println(id);
		analyzer.setPhotoId(id);
		if("".equals(id)){
			view.showInfo("Please enter a photo id.");
		}else{			
			view.switchButton();
			Thread t_analyze = new Thread(new AnalyzerThread(analyzer, setter));
			t_analyze.start();
			while(t_analyze.isAlive()){
				try {
					Thread.sleep(1000);					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			view.setImage(data.getRealImage());
			model.setData(data);
			view.switchButton();
			//view.setImage(data.getRealImage());
			display();
		}	
	}
	
	
}