package br.com.consoft.application.app;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;

import br.com.consoft.application.service.DetectaWebCam;
import br.com.consoft.application.service.WebCamService;

public class WebcamDiscoveryListenerExample implements WebcamDiscoveryListener {

	public WebcamDiscoveryListenerExample() {
		for (Webcam webcam : Webcam.getWebcams()) {
			System.out.println("Webcam detected: " + webcam.getName());
		}
		Webcam.addDiscoveryListener(this);
		System.out.println("\n\nPlease connect additional webcams, or disconnect already connected ones. Listening for events...");
	}

	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		System.out.format("Webcam connected: %s \n", event.getWebcam().getName());
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		System.out.format("Webcam disconnected: %s \n", event.getWebcam().getName());
	}

	public static void main(String[] args) throws Throwable {
		new WebcamDiscoveryListenerExample();
		Webcam webCam = DetectaWebCam.webCamInstance();
		WebCamService.viewWebCam(webCam);
	}
}