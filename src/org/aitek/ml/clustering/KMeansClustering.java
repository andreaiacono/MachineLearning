package org.aitek.ml.clustering;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.aitek.ml.tools.GnuPlotUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

public class KMeansClustering {

	private final String DIRECTORY_PREFIX = "resources/clustering/";

	public List<Vector> getPoints(double[][] raw) {

		List<Vector> points = new ArrayList<Vector>();
		for (int i = 0; i < raw.length; i++) {
			double[] fr = raw[i];
			Vector vec = new RandomAccessSparseVector(fr.length);
			vec.assign(fr);
			points.add(vec);
		}
		return points;
	}

	public void cluster(double[][] points, int clusters) throws Exception {

		List<Vector> vectors = getPoints(points);

		File testData = new File(DIRECTORY_PREFIX + "testdata");
		if (!testData.exists()) {
			testData.mkdir();
		}
		testData = new File(DIRECTORY_PREFIX + "testdata/points");
		if (!testData.exists()) {
			testData.mkdir();
		}

		// writes points data to HDFS
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		ClusterHelper.writePointsToFile(vectors, conf, new Path(DIRECTORY_PREFIX + "testdata/points/file1"));

		Path path = new Path(DIRECTORY_PREFIX + "testdata/clusters/part-00000");
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, Kluster.class);

		for (int i = 0; i < clusters; i++) {
			Vector vec = vectors.get(i);
			Kluster cluster = new Kluster(vec, i, new EuclideanDistanceMeasure());
			writer.append(new Text(cluster.getIdentifier()), cluster);
		}
		writer.close();

		// deletes existing output before computing
		Path output = new Path(DIRECTORY_PREFIX + "k-means_output");
		HadoopUtil.delete(conf, output);

		// runs the clustering algorithm on hadoop
		KMeansDriver.run(conf, new Path(DIRECTORY_PREFIX + "testdata/points"), new Path(DIRECTORY_PREFIX + "testdata/clusters"), output, new EuclideanDistanceMeasure(), 0.001, 10, true, 0.0, false);

		// reads the results
		SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(DIRECTORY_PREFIX + "k-means_output/" + Kluster.CLUSTERED_POINTS_DIR + "/part-m-00000"), conf);

		GnuPlotUtils.plot(reader);
		reader.close();

	}
}
