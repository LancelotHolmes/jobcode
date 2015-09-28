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
 * ��ԭʼ�ļ���ȡ��Ӧ��Ϣ
 * ԭʼ�ı��ļ�����28�У����е�7�м۸񣬵�����Ϊ�û�id,map����ȡ�۸�Ϊ999.99���û�id
 * @title CountMapper 
 * @description TODO 
 * @author neo
 * @date 2015��8��27��
 * @version 1.0
 */
public class CountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable arg0, Text value, OutputCollector<Text, IntWritable> output, Reporter arg3)
            throws IOException {
        // TODO Auto-generated method stub
        String[] values = value.toString().split("\t");
        if (28 != values.length) {
            // ���Ȳ��ԣ�20140808�գ����ݿ��б����
            return;
        }
        if ("null".equals(values[6])) {
            // ֵ�Ƿ�
            return;
        }

        Text key = new Text(values[3]);
        IntWritable inw = new IntWritable(1);
        if (values[6].equals("999.99")) {
            output.collect(key, inw);
        }
    }

}
