package br.com.consoft.application.app;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

import br.com.consoft.application.service.DetectaWebCam;
import br.com.consoft.application.service.WebCamService;

public class WebCamApplicationV2 extends JFrame implements WebcamMotionListener ,Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener, WebcamDiscoveryListener {

	private static final long serialVersionUID = 1L;

	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private WebcamPicker picker = null;

	@Override
	public void run() {

		Webcam.addDiscoveryListener(this);

		setTitle("WEB SEXY CONSOFT LTDA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		addWindowListener(this);

		picker = new WebcamPicker();
		picker.addItemListener(this);

		webcam = picker.getSelectedWebcam();

		if (webcam == null) {
			System.out.println("WEBCAM NOT FOUND");
			System.exit(1);
		}

		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(WebCamApplicationV2.this);

		panel = new WebcamPanel(webcam, false);
		panel.setFPSDisplayed(true);

		add(picker, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);

		pack();
		setVisible(true);

		Thread t = new Thread() {

			@Override
			public void run() {
				panel.start();
			}
		};
		t.setName("WEB CAM CONSORT LTDA START");
		t.setDaemon(true);
		t.setUncaughtExceptionHandler(this);
		t.start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new WebCamApplicationV2());
	}

	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam open");
		
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(500);
		detector.addMotionListener(this);
		detector.start();
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam closed");
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("webcam disposed");
	}

	@Override
	public void webcamImageObtained(WebcamEvent we) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		webcam.close();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("WEB VIEW RESUMO");
		panel.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("WEB VIEW PAUSA");
		panel.pause();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.err.println(String.format("Exception in thread %s", t.getName()));
		e.printStackTrace();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getItem() != webcam) {
			if (webcam != null) {

				panel.stop();

				remove(panel);

				webcam.removeWebcamListener(this);
				webcam.close();

				webcam = (Webcam) e.getItem();
				webcam.setViewSize(WebcamResolution.VGA.getSize());
				webcam.addWebcamListener(this);

				System.out.println("SELECT " + webcam.getName());

				panel = new WebcamPanel(webcam, false);
				panel.setFPSDisplayed(true);

				add(panel, BorderLayout.CENTER);
				pack();

				Thread t = new Thread() {

					@Override
					public void run() {
						panel.start();
					}
				};
				t.setName("CONSOFT STOP");
				t.setDaemon(true);
				t.setUncaughtExceptionHandler(this);
				t.start();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void webcamFound(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.addItem(event.getWebcam());
		}
	}

	@Override
	public void webcamGone(WebcamDiscoveryEvent event) {
		if (picker != null) {
			picker.removeItem(event.getWebcam());
		}
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
}
