package br.com.consoft.application.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;

public class WebCamService {
	
	public static void captura(Webcam webCam,String suffix, String fileName) throws IOException {
		BufferedImage image = webCam.getImage();
		ImageIO.write(image, suffix, new File(fileName + "." + suffix.toLowerCase()));
	}
	
	public static void viewWebCam(Webcam webCam) {
		webCam.setViewSize(WebcamResolution.VGA.getSize());
		
		WebcamPanel panel = new WebcamPanel(webCam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);
		
		
		JFrame window = new JFrame("WEB SEXY CONSOFT LTDA");
		window.add(panel);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}


	public static void capturando(Webcam webcam) {
		
		WebcamUtils.capture(webcam, "test1", ImageUtils.FORMAT_BMP);
		WebcamUtils.capture(webcam, "test1", ImageUtils.FORMAT_GIF);
		WebcamUtils.capture(webcam, "test1", ImageUtils.FORMAT_JPG);
		WebcamUtils.capture(webcam, "test1", ImageUtils.FORMAT_PNG);
		WebcamUtils.capture(webcam, "test1", ImageUtils.FORMAT_WBMP);

		WebcamUtils.capture(webcam, "test2", "bmp");
		WebcamUtils.capture(webcam, "test2", "gif");
		WebcamUtils.capture(webcam, "test2", "jpg");
		WebcamUtils.capture(webcam, "test2", "png");
		WebcamUtils.capture(webcam, "test2", "wbmp");

		byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
		System.out.println("Bytes length: " + bytes.length);

		ByteBuffer buffer = WebcamUtils.getImageByteBuffer(webcam, "jpg");
		System.out.println("Buffer length: " + buffer.capacity());
	}
}
