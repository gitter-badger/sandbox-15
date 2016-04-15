package net.roryclaasen.sandbox.crash;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import net.roryclaasen.Bootstrap;

public class CrashWindow {
	public static void show(final CrashMessage message) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CrashWindow window = new CrashWindow(message);
				window.show();

			}
		}).start();
        
	}

	private JFrame frame;

	public CrashWindow(CrashMessage message) {
		frame = new JFrame(Bootstrap.TITLE);
		frame.setSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextPane text = new JTextPane();
		text.setText(message.getAll());

		JScrollPane pane = new JScrollPane(text);
		frame.add(pane);
	}

	public void show() {
		frame.setVisible(true);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public void dispose() {
		frame.dispose();
	}
}
