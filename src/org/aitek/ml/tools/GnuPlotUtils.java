package org.aitek.ml.tools;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;

public class GnuPlotUtils {

	private static final String KMEANS_FILE = "resources/kmeans.cfg";

	private static void writeFile(String content, String filename) throws Exception {

		FileWriter writer = null;
		try {
			writer = new FileWriter(filename);
			writer.write(content.toString());
		}
		finally {
			writer.close();
		}
	}

	public static void launch() throws Exception {

		ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "/usr/bin/gnuplot", "kmeans.cfg" });
		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();
		InputStreamReader isr = new InputStreamReader(process.getErrorStream());
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}

	public static void plot(Reader reader) throws Exception {

		IntWritable key = new IntWritable();
		WeightedVectorWritable value = new WeightedVectorWritable();

		Map<String, Set<Double[]>> clusters = new HashMap<String, Set<Double[]>>();

		while (reader.next(key, value)) {
			System.out.println(value.toString() + " belongs to cluster " + key.toString());
			if (clusters.get(key.toString()) == null) {
				clusters.put(key.toString(), new HashSet<Double[]>());
			}

			Double[] coords = new Double[2];
			coords[0] = new Double(value.getVector().get(0));
			coords[1] = new Double(value.getVector().get(1));
			clusters.get(key.toString()).add(coords);
		}

		StringBuffer data = new StringBuffer("set xr[0:10]\nset yr[0:10]\nunset key\nset xlabel \"X\"\nset ylabel \"Y\"\nset pointsize 2\nplot");
		for (int j = 0; j < clusters.keySet().size(); j++) {
			data.append(" '-' w p ls ").append(j + 1).append(",");
		}
		data.deleteCharAt(data.length() - 1);
		data.append("\n");

		for (String clusterKey : clusters.keySet()) {
			for (Double[] values : clusters.get(clusterKey)) {
				data.append(values[0]).append(" ").append(values[1]).append("\n");
			}
			data.append("e\n");
		}
		data.append("pause -1  \"Hit return to continue\"");

		writeFile(data.toString(), KMEANS_FILE);
		// launch();
	}

}
