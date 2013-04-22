package org.aitek.ml.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Utils {

	/**
	 * reads a text file
	 * 
	 * @param f the file to read
	 * @param encoding the encoding of the file
	 * @return the content of the file
	 * @throws Exception
	 */
	public static String readTextFile(File f, String encoding) throws Exception {

		InputStreamReader reader = null;
		StringBuilder content = new StringBuilder();
		try {
			reader = new InputStreamReader(new FileInputStream(f), encoding);
			char data[] = new char[4096];
			while ((reader.read(data)) != -1) {
				content.append(data);
			}
		}
		finally {
			if (reader != null) reader.close();
		}
		return content.toString();
	}

}
