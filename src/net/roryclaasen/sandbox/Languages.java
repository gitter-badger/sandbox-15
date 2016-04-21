package net.roryclaasen.sandbox;

import java.io.IOException;

import net.gogo98901.log.Log;
import net.roryclaasen.language.LangUtil;
import net.roryclaasen.language.LanguageFile;
import net.roryclaasen.sandbox.util.config.Config;

public class Languages {

	private static String langDir = "assets/lang/";
	public static LanguageFile en_UK = new LanguageFile(langDir + "en_UK.lang");

	public static void setFromConfig() {
		String lang = Config.language.get().toLowerCase();
		if (lang.equals("en_uk")) set(en_UK);
		else{
			Log.warn("Bad config type for language. No language found, Setting to UK");
			Config.language.reset();
			set(en_UK);
		}
	}

	public static void set(LanguageFile file) {
		try {
			LangUtil.setLanguageFile(file);
			Log.info("Set language file to " + file.getPath().replace(langDir, ""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
