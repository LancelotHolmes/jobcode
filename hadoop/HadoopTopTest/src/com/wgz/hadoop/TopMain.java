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
 * �߼�����ȡ�û��м۸�Ϊ999.99�е�Top10�����������һ��Ϊ�û�id���ڶ���Ϊ�۸�Ϊ999.99�۸�ĸ���
 * �����ļ���ԭʼ�ı��ļ�����28�У����е�7��Ϊ�۸񣬵�����Ϊ�û�id,map����ȡ�۸�Ϊ999.99���û�id
 * �ű����з�ʽ���£�
 * ./hadoop-client/hadoop/bin/hadoop jar testcount.jar  com.wgz.hadoop.TopMain /outputpath 20150801
 * ��һ������Ϊ���·�����ڶ�����Ϊ�������ݵ�����
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

        // ����ͳ�Ƶ�����
        Job countJob = buildCountJob(countOutputPath, handleDate);

        // ����Top10��job
        Job topJob = buildTopJob(countOutputPath, topOutPath);

        // Top �����������ڼ���ͳ�Ƶ�����
        topJob.addDependingJob(countJob);

        JobControl jobControl = new JobControl("job chain");
        jobControl.addJob(countJob);
        jobControl.addJob(topJob);

        //���߳�����
        Thread t = new Thread(jobControl);
        t.start();
        long jobStartTime = System.currentTimeMillis();

        while (true) {

            if (jobControl.allFinished()) {//�����ҵ�ɹ���ɣ��ʹ�ӡ�ɹ���ҵ����Ϣ
                jobControl.stop();

                long currentTime = System.currentTimeMillis();

                System.out.println("job success , cost" + (currentTime - jobStartTime) + "ms");

                break;
            }

            if (jobControl.getFailedJobs().size() > 0) {//�����ҵʧ�ܣ��ʹ�ӡʧ����ҵ����Ϣ
                long currentTime = System.currentTimeMillis();

                System.out.println("job failed , cost" + (currentTime - jobStartTime) + "ms");
                System.out.println(jobControl.getFailedJobs());

                return 1;
            }

        }

        return 0;

    }

    /**
     * ��������hadoop����
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

        FileOutputFormat.setOutputPath(jobConf, outputPath); // �������·��

        jobConf.setMapperClass(CountMapper.class); // ����Mapper
        jobConf.setCombinerClass(CountCombiner.class); // ����Combiner���������ܣ�
        jobConf.setReducerClass(CountReducer.class); // ����Reducer

        jobConf.setMapOutputKeyClass(Text.class);
        jobConf.setMapOutputValueClass(IntWritable.class);

        jobConf.setOutputKeyClass(IntWritable.class);
        jobConf.setOutputValueClass(Text.class);

        Job job = new Job(jobConf);
        job.setJobName("count job1");
        return job;
    }

    /**
     * ����Top10��hadoop����
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

        FileOutputFormat.setOutputPath(jobConf, new Path(outputPath)); // �������·��

        jobConf.setMapperClass(TopMapper.class); // ����Mapper
        //        jobConf.setCombinerClass(CountReducer.class); // ����Combiner���������ܣ�
        jobConf.setReducerClass(TopReducer.class); // ����Reducer

        jobConf.setMapOutputKeyClass(IntWritable.class);
        jobConf.setMapOutputValueClass(Text.class);

        jobConf.setOutputKeyComparatorClass(DescSort.class);
        jobConf.setOutputKeyClass(Text.class);
        jobConf.setOutputValueClass(IntWritable.class);
        jobConf.setNumReduceTasks(1); // ��Ҫ

        Job job = new Job(jobConf);
        job.setJobName("top job2");
        return job;
    }

    private boolean buildInputPaths(JobConf jobConf, Configuration configuration, String handleDate) throws IOException {
        boolean status = false;
        //�����ض��������ݵ��ļ��б�
        String inputPath = "/hadoop/test/" + handleDate + "/" + "*";

        FileInputFormat.addInputPath(jobConf, new Path(inputPath));
        return status;
    }

}
