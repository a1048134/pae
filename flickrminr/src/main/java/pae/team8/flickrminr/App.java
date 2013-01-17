package pae.team8.flickrminr;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

import pae.team8.flickrminr.nlp.NLPConnector;
import pae.team8.flickrminr.ui.UI;

public class App{
	static SplashScreen mySplash;                   // instantiated by JVM we use it to get graphics
    static Graphics2D splashGraphics;               // graphics context for overlay of the splash image
    static Rectangle2D.Double splashTextArea;       // area where we draw the text
    static Rectangle2D.Double splashProgressArea;   // area where we draw the progress bar
    static Font font;                               // used to draw our text
	public static NLPConnector _nlp;

    public static void main(String[] args)
    {
    	System.out.println("Should show splash screen!");
        splashInit();           // initialize splash overlay drawing parameters
        appInit();              // simulate what an application would do before starting
        if (mySplash != null)   // check if we really had a spash screen
            mySplash.close();   // we're done with it
        System.out.println("Starting Application");
        UI.ShowUI();
        // begin with the interactive portion of the program
    }
    /**
     * just a stub to simulate a long initialization task that updates
     * the text and progress parts of the status in the Splash
     */
    private static void appInit(){
        splashText("Loading Configuration");
        try{
        	Thread.sleep(1000);
        }catch(InterruptedException e){}
        splashProgress(30);
        
        splashText("Starting Stanford NLP");
        try{
        	NlpInit t_nlp = new NlpInit();
        	t_nlp.start();
        	int i=0;
        	while(t_nlp.isAlive()){
        		if(i % 2 == 0){
        			splashText("Starting Stanford NLP ...");
        		}else{
        			splashText("Starting Stanford NLP");
        		}
        		Thread.sleep(1000);
        		i++;
        	}
        }
        catch (InterruptedException ex){}
        splashProgress(80);        
 
        splashText("Starting App");        
        try{
            Thread.sleep(1000);
        }        
        catch (InterruptedException ex){}
        splashProgress(100);
    }

    /**
     * Prepare the global variables for the other splash functions
     */
    private static void splashInit()
    {
        // the splash screen object is created by the JVM, if it is displaying a splash image
        
        mySplash = SplashScreen.getSplashScreen();
        // if there are any problems displaying the splash image
        // the call to getSplashScreen will returned null

        if (mySplash != null)
        {
            // get the size of the image now being displayed
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;

            // stake out some area for our status information
            splashTextArea = new Rectangle2D.Double(100., height * 0.60, width * .5, 20.);
            splashProgressArea = new Rectangle2D.Double(108., height* 0.67, width * .5, 5. );

            // create the Graphics environment for drawing status info
            splashGraphics = mySplash.createGraphics();
            font = new Font("Dialog", Font.BOLD, 12);
            splashGraphics.setFont(font);

            // initialize the status info
            splashText("Starting");
            splashProgress(0);
        }
    }
    
    /**
     * Display text in status area of Splash.  Note: no validation it will fit.
     * @param str - text to be displayed
     */
    public static void splashText(String str)
    {
        if (mySplash != null && mySplash.isVisible()){
        	
        	splashGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
            splashGraphics.fill(splashTextArea);
            splashGraphics.setComposite(AlphaComposite.SrcOver);

            // draw the text
            splashGraphics.setPaint(Color.BLACK);
            splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));

            // make sure it's displayed
            mySplash.update();
        }
    }
    /**
     * Display a (very) basic progress bar
     * @param pct how much of the progress bar to display 0-100
     */
    public static void splashProgress(int pct){
        if (mySplash != null && mySplash.isVisible()){

            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            splashGraphics.setPaint(Color.white);
            splashGraphics.fill(splashProgressArea);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct*wid/100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

            // make sure it's displayed
            mySplash.update();
        }
    }
}
