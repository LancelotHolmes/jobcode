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
 * 计数的combiner，和reducer的输出有差异
 * @title CountReducer
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class CountCombiner extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text arg0, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter arg3)
            throws IOException {
        // TODO Auto-generated method stub
        int sum = 0;

        while (values.hasNext()) {
            sum += values.next().get();
        }
        output.collect(arg0, new IntWritable(sum));

        System.out.print("CountReducer");
    }

}
