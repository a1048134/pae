package pae.team8.flickrminr.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private BufferedImage img;
	public ImagePanel(BufferedImage image){
		img = image;
	}
	public void loadImage(BufferedImage image){
		img = image;
		this.repaint();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, ((super.getWidth()/2) - (img.getWidth()/2)),((super.getHeight()/2) - (img.getHeight()/2)),this);
	}
}
