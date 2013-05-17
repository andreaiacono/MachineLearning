package org.aitek.ml.tools;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

public class HadoopUtils {

	/**
	 * converts a data file into a valid sequence hadoop file. Input data file must have this
	 * format:
	 * 
	 * Category\tId\tDescription\n
	 * 
	 * @param inputFileName
	 * @param outputDirName
	 * @throws Exception
	 */
	public static void convertToSeq(String inputFileName, String outputDirName) throws Exception {

		Writer writer = null;
		BufferedReader reader = null;

		try {
			Configuration configuration = new Configuration();
			writer = new SequenceFile.Writer(FileSystem.get(configuration), configuration, new Path(outputDirName + "/chunk-0"), Text.class, Text.class);

			int count = 0;
			String line;
			reader = new BufferedReader(new FileReader(inputFileName));
			Text key = new Text(), value = new Text();

			while ((line = reader.readLine()) != null) {

				// checks for correct format of input file
				String[] tokens = line.split("\t", 3);
				if (tokens.length != 3) {
					System.out.println("Skip line: " + line);
					continue;
				}

				key.set("/" + tokens[0] + "/" + tokens[1]);
				value.set(tokens[2]);
				writer.append(key, value);
				count++;
			}
			System.out.println("Wrote " + count + " entries.");
		}
		finally {
			if (writer != null) writer.close();
			if (reader != null) reader.close();
		}
	}
}
