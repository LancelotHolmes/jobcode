package com.wgz.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * �߼�������û��м۸�Ϊ999.99�еĸ����������һ��Ϊ�û�id���ڶ���Ϊ����
 * �����ļ���ԭʼ�ı��ļ�����28�У����е�7�м۸񣬵�����Ϊ�û�id,map����ȡ�۸�Ϊ999.99���û�id
 * �ű����з�ʽ���£�
 * ./hadoop-client/hadoop/bin/hadoop jar testcount.jar  com.wgz.hadoop.CountJobMain /outputpath 20150801
 * ��һ������Ϊ���·�����ڶ�����Ϊ�������ݵ�����
 * @title CountJob
 * @description TODO 
 * @author neo
 * @date 2015��8��27��
 * @version 1.0
 */
public class CountJobMain extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new CountJobMain(), args);
        System.out.println("exit code : " + exitCode);
        System.exit(exitCode);
    }

    @Override
    public int run(String[] args) throws Exception {
        String output = args[0];
        String handleDate = args[1];
        System.out.println("output : " + output);
        System.out.println("handleDate : " + handleDate);

        Path outputPath = new Path(output);

        JobConf jobConf = new JobConf(this.getConf(), this.getClass());
        jobConf.setJobName("Ecom fcstar max bid count job");
        Configuration configuration = this.getConf();

        boolean db58WordinfoStatus = this.buildInputPaths(jobConf, configuration, handleDate);
        if (!db58WordinfoStatus) {
            throw new IOException("wordinfo path is empty");
        }
        FileOutputFormat.setOutputPath(jobConf, outputPath); // �������·��

        jobConf.setMapperClass(CountMapper.class); // ����Mapper
        jobConf.setCombinerClass(CountReducer.class); // ����Combiner���������ܣ�
        jobConf.setReducerClass(CountReducer.class); // ����Reducer

        jobConf.setMapOutputKeyClass(Text.class);
        jobConf.setMapOutputValueClass(IntWritable.class);

        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(IntWritable.class);

        jobConf.setNumReduceTasks(1); // ��Ҫ

        JobClient.runJob(jobConf);

        return 0;
    }

    private boolean buildInputPaths(JobConf jobConf, Configuration configuration, String handleDate) throws IOException {
        boolean status = false;
        //�����ض��������ݵ��ļ��б�
        String inputPath = "/hadoop/test/" + handleDate + "/" + "*";

        FileInputFormat.addInputPath(jobConf, new Path(inputPath));
        return status;
    }
}
