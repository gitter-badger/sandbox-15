/* Copyright 2016 Rory Claasen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.roryclaasen.sandbox.crash;

import java.awt.Dimension;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import net.gogo98901.log.Log;
import net.roryclaasen.Bootstrap;

public class CrashWindow {
	private static final String SEPERATOR = "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n";
	private JFrame frame;

	public CrashWindow(Map<Thread, List<Throwable>> uncought) {
		frame = new JFrame(Bootstrap.TITLE);
		frame.setSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextPane text = new JTextPane();
		text.setFont(text.getFont().deriveFont(12f));
		text.setText(formatCrashMessages(uncought));
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

	private String formatCrashMessages(Map<Thread, List<Throwable>> uncought) {
		String title = Bootstrap.TITLE + " (" + Bootstrap.VERSION + ") by Rory Claasen\n" + new Date();
		String log = "Game log:\n\n";
		for (String s : Log.getLogged()) {
			log+=s+"\n";
		}
		String crashesUncought = "Errors: ";
		if (uncought.size() == 0) {
			crashesUncought += "No errors found";
		} else {
			crashesUncought += uncought.size() + " error(s) found\n";
			for (Thread t : uncought.keySet()) {
				crashesUncought += "\nThread: " + t.getName() + " (" + t.getId() + ")\nThread Status: " + t.getState() + "\n";
				for (Throwable e : uncought.get(t)) {
					crashesUncought += new ErrorMessage(e).get() + "\n\n";
				}
			}
		}
		String crashes = "List of Game errors: \n" + crashesUncought;
		return title + SEPERATOR + /*last + SEPERATOR +*/ log + SEPERATOR + crashes;
	}
}
