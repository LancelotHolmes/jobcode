package com.wgz.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * ���ļ���ȡ�����������û���999.99�۸����Ϊkey,ͭ�Ľ�������������
 * @title TopMapper
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class TopMapper extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, Text> {

    IntWritable iw = new IntWritable();
    private Text t = new Text();

    @Override
    public void map(LongWritable arg0, Text value, OutputCollector<IntWritable, Text> output, Reporter arg3)
            throws IOException {
        // TODO Auto-generated method stub
        String[] values = value.toString().split("\t");
        int iValue = Integer.parseInt(values[0]);
        IntWritable key = new IntWritable(iValue);
        Text v = new Text(values[1]);
        output.collect(key, v);
    }

}
