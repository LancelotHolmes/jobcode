/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.util.List;

/**
 * 统计解析出错的行数和总数据行数，默认行数在int范围内
 * @title PrintLineProcessor
 * @description 用于示例或测试的用户自定义类型
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月9日
 * @version 1.0
 */
public class ErrorCntProcessor implements Processor {

    // 解析出错行数
    private int errorLineCnt = 0;
    private int totalLineCnt = 0;

    public int getErrorLineCnt() {
        return errorLineCnt;
    }

    public int getTotalLineCnt() {
        return totalLineCnt;
    }

    @Override
    public void process(List<Object> objectsOfLine) {
        if (objectsOfLine == null) {
            errorLineCnt++;
        }
        totalLineCnt++;
    }

    @Override
    public String toString() {
        return "ErrorCntProcessor [errorLineCnt=" + errorLineCnt + ", totalLineCnt=" + totalLineCnt + "]";
    }

}
