package mapreduce;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CustomerLikes {

	public static class StatMapper extends
			Mapper<Object, Text, Text, FloatWritable> {
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split("::");
			context.write(new Text(tokens[1]),
					new FloatWritable(Float.parseFloat(tokens[2])));
		}
	}

	public static class StatReducer extends
			Reducer<Text, FloatWritable, Text, FloatWritable> {
		private FloatWritable result = new FloatWritable();
		public void reduce(Text key, Iterable<FloatWritable> values,
				Context context) throws IOException, InterruptedException {			
			float sum = 0, count = 0;
			for (FloatWritable val : values) {
				sum += val.get();
				count += 1;
			}
			result.set(sum / count);
			context.write(key, result);
		}
	}

	public static class Mapper1 extends Mapper<Object, Text, Text, Text> {
		private String fileTag = "s1~";
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split("::");
			context.write(new Text(tokens[0]), new Text(fileTag + tokens[1]));
		}
	}

	public static class Mapper2 extends Mapper<Object, Text, Text, Text> {
		private String fileTag = "s2~";

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			//System.out.println(value.toString());
			String[] tokens = value.toString().split("\t");
			context.write(new Text(tokens[0]), new Text(fileTag + tokens[1]));
		}
	}

	public static class JoinReducer extends Reducer<Text, Text, Text, Text> {
		private String status1, status2 = null;
		public void reduce(Text key, Iterable<Text> values, Context output)
				throws IOException, InterruptedException {
			ArrayList<String> ary = new ArrayList<String>();
			for (Text val : values) {
				String splitVals[] = val.toString().split("~");
				if (splitVals[0].equals("s1")) {
					status1 = splitVals[1] != null ? splitVals[1].trim()
							: "status1";
				} else if (splitVals[0].equals("s2")) {
					status2 = splitVals[1] != null ? splitVals[1].trim()
							: "status2";
					ary.add(status2);
				}
			}
			for (String meanrating : ary)
				output.write(key, new Text(status1 + "\t" + meanrating));
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Customer Like Analysis Example");
		job.setJarByClass(CustomerLikes.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);
		job.setMapperClass(StatMapper.class);
		job.setCombinerClass(StatReducer.class);
		job.setReducerClass(StatReducer.class);
		FileInputFormat.addInputPath(job, new Path(
				"hdfs://localhost:9000/data/ratings.txt"));
		FileOutputFormat.setOutputPath(job, new Path(
				"hdfs://localhost:9000/tmp4/"));
		job.waitForCompletion(true);

		Job job2 = Job.getInstance(conf, "Customer Like Analysis Example2");
		job2.setJarByClass(CustomerLikes.class);
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		job2.setReducerClass(JoinReducer.class);
		MultipleInputs.addInputPath(job2, new Path(
				"hdfs://localhost:9000/data/items.txt"), TextInputFormat.class,
				Mapper1.class);
		MultipleInputs.addInputPath(job2, new Path(
				"hdfs://localhost:9000/tmp4/"),
				TextInputFormat.class, Mapper2.class);
		FileOutputFormat.setOutputPath(job2, new Path(
				"hdfs://localhost:9000/out2/"));
		System.exit(job2.waitForCompletion(true) ? 0 : 1);
	}
}
