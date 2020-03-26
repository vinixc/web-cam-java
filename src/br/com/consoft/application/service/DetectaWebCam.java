package br.com.consoft.application.service;

import com.github.sarxos.webcam.Webcam;

public class DetectaWebCam {
	
	public static boolean existWebCam(){
		Webcam webcam = Webcam.getDefault();
		if (webcam != null) {
			System.out.println("Webcam: " + webcam.getName());
			return true;
		} else {
			System.out.println("No webcam detected");
			return true;
		}
	}
	
	public static Webcam webCamInstance() {
		return Webcam.getDefault();
	}
}
