package com.wgz.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.jobcontrol.Job;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * 逻辑：获取用户中价格为999.99列的Top10个数，输出第一列为用户id，第二列为价格为999.99价格的个数
 * 输入文件：原始文本文件共有28列，其中第7列为价格，第四列为用户id,map会提取价格为999.99的用户id
 * 脚本运行方式如下：
 * ./hadoop-client/hadoop/bin/hadoop jar testcount.jar  com.wgz.hadoop.TopMain /outputpath 20150801
 * 第一个参数为输出路径，第二参数为输入数据的日期
 * @title TopMain
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class TopMain extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new TopMain(), args);
        System.out.println("exit code : " + exitCode);
        System.exit(exitCode);
    }

    @Override
    public int run(String[] args) throws Exception {
        String output = args[0];
        String handleDate = args[1];
        System.out.println("output : " + output);
        System.out.println("handleDate : " + handleDate);

        String countOutPath = "/app/ecom/fc-star/wanggongzheng/countu";
        String topOutPath = output;
        Path countOutputPath = new Path(countOutPath);
        Path topOutpath = new Path(topOutPath);

        // 计数统计的任务
        Job countJob = buildCountJob(countOutputPath, handleDate);

        // 计算Top10的job
        Job topJob = buildTopJob(countOutputPath, topOutPath);

        // Top 的任务依赖于计数统计的任务
        topJob.addDependingJob(countJob);

        JobControl jobControl = new JobControl("job chain");
        jobControl.addJob(countJob);
        jobControl.addJob(topJob);

        //在线程启动
        Thread t = new Thread(jobControl);
        t.start();
        long jobStartTime = System.currentTimeMillis();

        while (true) {

            if (jobControl.allFinished()) {//如果作业成功完成，就打印成功作业的信息
                jobControl.stop();

                long currentTime = System.currentTimeMillis();

                System.out.println("job success , cost" + (currentTime - jobStartTime) + "ms");

                break;
            }

            if (jobControl.getFailedJobs().size() > 0) {//如果作业失败，就打印失败作业的信息
                long currentTime = System.currentTimeMillis();

                System.out.println("job failed , cost" + (currentTime - jobStartTime) + "ms");
                System.out.println(jobControl.getFailedJobs());

                return 1;
            }

        }

        return 0;

    }

    /**
     * 构建计数hadoop任务
     * @param outputPath
     * @param handleDate
     * @return
     * @throws Exception
     */
    private Job buildCountJob(Path outputPath, String handleDate) throws Exception {
        JobConf jobConf = new JobConf(this.getConf(), this.getClass());
        jobConf.setJobName("Ecom fcstar max bid count job");
        Configuration configuration = this.getConf();

        boolean db58WordinfoStatus = this.buildInputPaths(jobConf, configuration, handleDate);
        if (!db58WordinfoStatus) {
            throw new IOException("wordinfo path is empty");
        }

        FileOutputFormat.setOutputPath(jobConf, outputPath); // 设置输出路径

        jobConf.setMapperClass(CountMapper.class); // 设置Mapper
        jobConf.setCombinerClass(CountCombiner.class); // 设置Combiner（提升性能）
        jobConf.setReducerClass(CountReducer.class); // 设置Reducer

        jobConf.setMapOutputKeyClass(Text.class);
        jobConf.setMapOutputValueClass(IntWritable.class);

        jobConf.setOutputKeyClass(IntWritable.class);
        jobConf.setOutputValueClass(Text.class);

        Job job = new Job(jobConf);
        job.setJobName("count job1");
        return job;
    }

    /**
     * 构建Top10的hadoop任务
     * @param iputPath
     * @param outputPath
     * @return
     * @throws Exception
     */
    private Job buildTopJob(Path iputPath, String outputPath) throws Exception {
        JobConf jobConf = new JobConf(this.getConf(), this.getClass());
        jobConf.setJobName("Ecom fcstar max bid top job");
        Configuration configuration = this.getConf();

        Path topPath = new Path(iputPath + "/part*");
        FileInputFormat.addInputPath(jobConf, topPath);

        FileOutputFormat.setOutputPath(jobConf, new Path(outputPath)); // 设置输出路径

        jobConf.setMapperClass(TopMapper.class); // 设置Mapper
        //        jobConf.setCombinerClass(CountReducer.class); // 设置Combiner（提升性能）
        jobConf.setReducerClass(TopReducer.class); // 设置Reducer

        jobConf.setMapOutputKeyClass(IntWritable.class);
        jobConf.setMapOutputValueClass(Text.class);

        jobConf.setOutputKeyComparatorClass(DescSort.class);
        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(IntWritable.class);
        jobConf.setNumReduceTasks(1); // 重要

        Job job = new Job(jobConf);
        job.setJobName("top job2");
        return job;
    }

    private boolean buildInputPaths(JobConf jobConf, Configuration configuration, String handleDate) throws IOException {
        boolean status = false;
        //输入特定日期数据的文件列表
        String inputPath = "/hadoop/test/" + handleDate + "/" + "*";

        FileInputFormat.addInputPath(jobConf, new Path(inputPath));
        return status;
    }

}
