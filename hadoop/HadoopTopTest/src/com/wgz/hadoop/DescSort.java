package com.wgz.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparator;

/**
 * IntWritable�Ľ���������
 * @title DescSort
 * @description TODO 
 * @author neo
 * @version 1.0
 */
public class DescSort extends WritableComparator {

    public DescSort() {
        super(IntWritable.class, true);//ע���������
    }

    @Override
    public int compare(byte[] arg0, int arg1, int arg2, byte[] arg3, int arg4, int arg5) {
        return -super.compare(arg0, arg1, arg2, arg3, arg4, arg5);//ע��ʹ�ø�������ɽ���
    }

    @Override
    public int compare(Object a, Object b) {

        return -super.compare(a, b);//ע��ʹ�ø�������ɽ���
    }

}
