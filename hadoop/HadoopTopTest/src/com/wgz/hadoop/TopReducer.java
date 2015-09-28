package com.wgz.hadoop;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * Êä³öTop10
 * @title TopReducer
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class TopReducer extends MapReduceBase implements Reducer<IntWritable, Text, Text, IntWritable> {

    int max = 10;
    int index = 0;

    @Override
    public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<Text, IntWritable> output, Reporter arg3)
            throws IOException {
        // TODO Auto-generated method stub
        while (values.hasNext()) {
            Text v = values.next();
            if (index < max) {
                output.collect(v, key);
                index = index + 1;
            }
        }

    }

}
