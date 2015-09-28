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
 * 统计计数的reducer
 * @title CountReducer
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class CountReducer extends MapReduceBase implements Reducer<Text, IntWritable, IntWritable, Text> {

    @Override
    public void reduce(Text arg0, Iterator<IntWritable> values, OutputCollector<IntWritable, Text> output, Reporter arg3)
            throws IOException {
        // TODO Auto-generated method stub
        int sum = 0;

        while (values.hasNext()) {
            sum += values.next().get();
        }
        output.collect(new IntWritable(sum), arg0);

        System.out.print("CountReducer");
    }

}
