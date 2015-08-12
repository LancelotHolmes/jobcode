/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.util.List;

/**
 * 使用词表工具类的Demo
 * @title Main
 * @description 使用词表工具类的Demo
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月9日
 * @version 1.0
 */
public class Main {

    // 示例词表文件资源，位于源码src/java/resources目录下
    private static final String SCHEMA_FILE_NAME_BASE_TYPE = "word_dict_schema_base_type.xml";
    private static final String DATA_FILE_NAME_BASE_TYPE = "word_dict_data_base_type.txt";

    public static void main(String[] args) {
        // 获取示例资源文件绝对路径
        String schemaFilePath = Main.class.getClassLoader().getResource(SCHEMA_FILE_NAME_BASE_TYPE).getPath();
        String dataFilePath = Main.class.getClassLoader().getResource(DATA_FILE_NAME_BASE_TYPE).getPath();
        // 1、解析返回词表对象列表的使用方式，适合数据文件大小有限情形
        WordDictParser parser = new WordDictParserImpl();
        List<List<Object>> result = parser.parseToList(schemaFilePath, dataFilePath);

        System.out.println("词表行数:" + result.size());

        // 2、用自定义的继承Proccessor的处理器，在每行数据解析后，逐行自定义处理的使用方式，以ErrorCntProcessor示例。适合流式处理场景
        ErrorCntProcessor errotCntProcessor = new ErrorCntProcessor();
        parser.parse(schemaFilePath, dataFilePath, errotCntProcessor);

        System.out.println("解析出错行数:" + errotCntProcessor.getErrorLineCnt());
    }

}
