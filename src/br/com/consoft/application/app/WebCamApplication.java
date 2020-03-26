package br.com.consoft.application.app;

import java.io.IOException;

import com.github.sarxos.webcam.Webcam;

import br.com.consoft.application.service.DetectaWebCam;
import br.com.consoft.application.service.WebCamService;

public class WebCamApplication {
	
	public static void main(String[] args) throws IOException {
		
		if(DetectaWebCam.existWebCam()) {
			
			Webcam webCam = DetectaWebCam.webCamInstance();
			
			WebCamService.viewWebCam(webCam);
			
			WebCamService.capturando(webCam);
			WebCamService.captura(webCam, "PNG", "testando");
			
		}else {
			return;
		}
	}

}
