package mapreduce;

import java.io.IOException;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WebAnalysis {

	public static class WebMapper extends
			Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split("\t");
			
			URL aurl = new URL(tokens[1]);
			//System.out.println(aurl.getHost());
			context.write(new Text(tokens[2]), one);
		}
	}

	public static class WebReducer extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "web analysis example");
		job.setJarByClass(WebAnalysis.class);
		job.setMapperClass(WebMapper.class);
		job.setCombinerClass(WebReducer.class);
		job.setReducerClass(WebReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//FileInputFormat.addInputPath(job, new Path(
		//		"hdfs://localhost:9000/data/web.txt"));
		//FileOutputFormat.setOutputPath(job, new Path(
		//		"hdfs://localhost:9000/out214/"));
		 FileInputFormat.addInputPath(job, new Path(args[0]));
		 FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}