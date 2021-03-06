package hack.facebook.habitdroid.desktop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

/**
 * Main panel
 * @author maesierra
 *
 */
public class MainDesktopApp extends JFrame {
	
	class WebcamPanel extends JPanel {  
		private static final long serialVersionUID = 4232526268631213129L;
		private BufferedImage image;  
	      // Create a constructor method  
	      public WebcamPanel(){  
	           super();   
	      }  
	      /**  
	       * Converts/writes a Mat into a BufferedImage.  
	       *   
	       * @param matrix Mat of type CV_8UC3 or CV_8UC1  
	       * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY  
	       */  
	      public boolean matToBufferedImage(final Mat matBGR){  
	           long startTime = System.nanoTime();  
	           int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;  
	           byte[] sourcePixels = new byte[width * height * channels];  
	           matBGR.get(0, 0, sourcePixels);  
	           // create new image and get reference to backing data  
	           image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);  
	           final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();  
	           System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);  
	           long endTime = System.nanoTime();  
	           //System.out.println(String.format("Elapsed time: %.2f ms", (float)(endTime - startTime)/1000000));  
	           return true;  
	      }  
	      public void paintComponent(Graphics g){  
	           super.paintComponent(g);   
	           if (this.image==null) return;  
	            g.drawImage(this.image,10,10,this.image.getWidth(),this.image.getHeight(), null);  
	      }  
	 }

	private VideoCapture videocapture;
	private WebcamPanel webcamPanel;

	public MainDesktopApp(final VideoCapture videocapture) {
		super("HabitDroid");
		this.videocapture = videocapture;
		webcamPanel = new WebcamPanel();
		add(webcamPanel);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateImage(final Mat matBGR) {
		webcamPanel.matToBufferedImage(matBGR);
		webcamPanel.repaint();
	}

	public void start() {
		pack();
		setVisible(true);
	}
}
