package br.com.consoft.application.app;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.sarxos.webcam.Webcam;

public class WebCamConcurrentThreadsExemplo {

	private static AtomicInteger counter = new AtomicInteger(0);

	private static final class Capture extends Thread {

		private static final AtomicInteger number = new AtomicInteger(0);

		public Capture() {
			super("capture-" + number.incrementAndGet());
		}

		@Override
		public void run() {

			Webcam webcam = Webcam.getDefault();
			webcam.open();

			while (true) {

				if (!webcam.isOpen()) {
					break;
				}

				BufferedImage image = webcam.getImage();
				if (image == null) {
					break;
				}

				int n = counter.incrementAndGet();
				if (n != 0 && n % 100 == 0) {
					System.out.println(Thread.currentThread().getName() + ": Frames captured: " + n);
				}
			}
		}
	}

	public static void main(String[] args) throws Throwable {

		int n = Runtime.getRuntime().availableProcessors() * 4;
		for (int i = 0; i < n; i++) {
			System.out.println("Thread: " + i);
			new Capture().start();
		}

		Thread.sleep(1 * 60 * 1000); // 5 minutes

		System.exit(1);
	}
}
