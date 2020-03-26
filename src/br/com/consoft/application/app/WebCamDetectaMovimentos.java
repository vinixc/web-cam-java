package br.com.consoft.application.app;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;

import br.com.consoft.application.service.DetectaWebCam;
import br.com.consoft.application.service.WebCamService;

public class WebCamDetectaMovimentos implements WebcamMotionListener {

	public WebCamDetectaMovimentos() {
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(500);
		detector.addMotionListener(this);
		detector.start();
	}

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		System.out.println("PARA DE SE MEXER VAGABUNDO!!");
		System.out.println("SORRIA PARA A FOTO ");
		
		try {
			WebCamService.captura(DetectaWebCam.webCamInstance(), "PNG",
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		new WebCamDetectaMovimentos();
		System.in.read();
	}
}