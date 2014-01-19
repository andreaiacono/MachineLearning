package org.aitek.ml.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

	public static double getMax(double[] data) {

		double max = 0;
		for (int i = 0; i < data.length; i++) {

			if (data[i] > max) {
				max = data[i];
			}
		}

		return max;
	}

	public static double getMin(double[] data) {

		double min = 1000000;
		for (int i = 0; i < data.length; i++) {

			if (data[i] < min) {
				min = data[i];
			}
		}

		return min;
	}

	public static void changeEncoding(String filename, String from, String to) throws Exception {

		BufferedReader reader = null;
		StringBuffer content = new StringBuffer();
		try {

			// reads from file
			reader = new BufferedReader(new FileReader(filename));
			String line = null;
			do {
				line = reader.readLine();
				if (line == null) break;
				content.append(new String(line.getBytes(), from)).append("\n");
			} while (true);
		}
		finally {
			if (reader != null) reader.close();
		}

		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(filename));
			writer.write(content.toString());
		}
		finally {
			if (writer != null) writer.close();
		}
	}

	public static void main(String[] args) throws Exception {

		changeEncoding("/home/andrea/poinx/app/views/pages/privacy3.cpt", "US-ASCII", "UTF-8");
	}
}
