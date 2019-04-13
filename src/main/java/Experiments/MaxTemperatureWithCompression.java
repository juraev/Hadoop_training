package Experiments;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;


import java.io.IOException;
import java.util.Iterator;

/**
 * Created by user on 9/25/18.
 */
public class MaxTemperatureWithCompression {
    static class MaxTemperatureMapper
            implements Mapper<LongWritable, Text, Text, IntWritable> {
        private static final int MISSING = 9999;

        public void map(LongWritable longWritable, Text value, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter) throws IOException {
            String line = value.toString();
            String year = line.substring(15, 19);
            int airTemperature;
            if (line.charAt(87) == '+') { // parseInt doesn't like leading plus signs
                airTemperature = Integer.parseInt(line.substring(88, 92));
            } else {
                airTemperature = Integer.parseInt(line.substring(87, 92));
            }
            String quality = line.substring(92, 93);
            if (airTemperature != MISSING && quality.matches("[01459]")) {
                outputCollector.collect(new Text(year), new IntWritable(airTemperature));
            }
        }

        public void close() throws IOException {

        }

        public void configure(JobConf jobConf) {

        }
    }

    static class MaxTemperatureReducer
            implements Reducer<Text, IntWritable, Text, IntWritable> {

        public void reduce(Text text, Iterator<IntWritable> iterator, OutputCollector<Text, IntWritable> outputCollector, Reporter reporter) throws IOException {
            int maxValue = Integer.MIN_VALUE;


            while (iterator.hasNext()){
                IntWritable i = iterator.next();
                maxValue = Math.max(maxValue, i.get());
            }
            outputCollector.collect(text, new IntWritable(maxValue));
        }

        public void close() throws IOException {

        }

        public void configure(JobConf jobConf) {

        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println(args[0] + " " + args[1] + " " + args[2]);
            System.err.println("Usage: MaxTemperatureWithCompression <input path> " + "<output path>");
            System.exit(-1);
        }
        JobConf conf = new JobConf(MaxTemperatureWithCompression.class);
        conf.setJobName("Max temperature with output compression");
        FileInputFormat.addInputPath(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setBoolean("mapred.output.compress", true);
        conf.setClass("mapred.output.compression.codec", GzipCodec.class,
                CompressionCodec.class);
        conf.setMapperClass(MaxTemperatureMapper.class);
        conf.setCombinerClass(MaxTemperatureReducer.class);
        conf.setReducerClass(MaxTemperatureReducer.class);
        JobClient.runJob(conf);
    }
}
