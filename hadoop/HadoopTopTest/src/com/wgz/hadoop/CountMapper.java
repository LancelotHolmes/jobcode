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
 * 从原始文件数据提取出价格为999.99的用户id
 * * 输入文件：原始文本文件共有28列，其中第7列为价格，第四列为用户id,map会提取价格为999.99的用户id
 * @title CountMapper
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class CountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable arg0, Text value, OutputCollector<Text, IntWritable> output, Reporter arg3)
            throws IOException {
        // TODO Auto-generated method stub
        String[] values = value.toString().split("\t");
        // 确认列数量是否正确
        if (28 != values.length) {
            // 长度不对（20140808日，数据库有变更）
            return;
        }
        if ("null".equals(values[6])) {
            // 值非法
            return;
        }

        Text key = new Text(values[3]);
        IntWritable inw = new IntWritable(1);
        if (values[6].equals("999.99")) {
            output.collect(key, inw);
        }
    }

}
