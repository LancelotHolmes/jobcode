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
 * 逻辑：会计用户中价格为999.99列的个数，输出第一列为用户id，第二列为个数
 * 输入文件：原始文本文件共有28列，其中第7列价格，第四列为用户id,map会提取价格为999.99的用户id
 * 脚本运行方式如下：
 * ./hadoop-client/hadoop/bin/hadoop jar testcount.jar  com.wgz.hadoop.CountJobMain /outputpath 20150801
 * 第一个参数为输出路径，第二参数为输入数据的日期
 * @title CountJob
 * @description TODO 
 * @author neo
 * @date 2015年8月27日
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
        FileOutputFormat.setOutputPath(jobConf, outputPath); // 设置输出路径

        jobConf.setMapperClass(CountMapper.class); // 设置Mapper
        jobConf.setCombinerClass(CountReducer.class); // 设置Combiner（提升性能）
        jobConf.setReducerClass(CountReducer.class); // 设置Reducer

        jobConf.setMapOutputKeyClass(Text.class);
        jobConf.setMapOutputValueClass(IntWritable.class);

        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(IntWritable.class);

        jobConf.setNumReduceTasks(1); // 重要

        JobClient.runJob(jobConf);

        return 0;
    }

    private boolean buildInputPaths(JobConf jobConf, Configuration configuration, String handleDate) throws IOException {
        boolean status = false;
        //输入特定日期数据的文件列表
        String inputPath = "/hadoop/test/" + handleDate + "/" + "*";

        FileInputFormat.addInputPath(jobConf, new Path(inputPath));
        return status;
    }
}
