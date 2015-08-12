/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * WordDictParserImpl单测
 * @title WordDictParserImplTest
 * @description WordDictParserImpl单测
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月11日
 * @version 1.0
 */
public class WordDictParserImplTest {

    // 预配置的测试用资源文件，位于src/test/resource下面
    private static final String SCHEMA_FILE_NAME_BASE_TYPE = "word_dict_schema_base_type.xml";
    private static final String DATA_FILE_NAME_BASE_TYPE = "word_dict_data_base_type.txt";
    private static final String SCHEMA_FILE_NAME_CUSTOM_TYPE = "word_dict_schema_custom_type.xml";
    private static final String DATA_FILE_NAME_CUSTOM_TYPE = "word_dict_data_custom_type.txt";
    private static final String SCHEMA_FILE_NAME_ERROR = "word_dict_schema_error.xml";
    private static final String DATA_FILE_NAME_ERROR = "word_dict_data_error.txt";
    private static final String SCHEMA_FILE_NAME_PROCESSOR = "word_dict_schema_processor.xml";
    private static final String DATA_FILE_NAME_PROCESSOR = "word_dict_data_processor.txt";

    @Test
    public void testParseToList_BaseType() {
        String schemaFilePath = this.getClass().getClassLoader().getResource(SCHEMA_FILE_NAME_BASE_TYPE).getPath();
        String dataFilePath = this.getClass().getClassLoader().getResource(DATA_FILE_NAME_BASE_TYPE).getPath();
        WordDictParser parser = new WordDictParserImpl();
        List<List<Object>> result = parser.parseToList(schemaFilePath, dataFilePath);

        assertNotNull(result);

        // 以下的校验值和word_dict_data_base_type.txt中第一行数据直接相关
        int expectSize = 2;
        assertEquals(expectSize, result.size());

        int expectInt = 13;
        assertEquals(expectInt, result.get(0).get(0));

        String expectStr = "test";
        assertEquals(expectStr, result.get(0).get(1));

        BigInteger expectUint64 = new BigInteger("987654");
        assertEquals(expectUint64, result.get(0).get(2));

        List<Integer> expectArray = new ArrayList<Integer>();
        expectArray.add(3);
        expectArray.add(2);
        expectArray.add(1);
        assertEquals(expectArray, result.get(0).get(3));

        float expectFloat = 3.1f;
        assertEquals(expectFloat, result.get(0).get(4));

        long expectUint32 = 12345L;
        assertEquals(expectUint32, result.get(0).get(5));
    }

    @Test
    public void testParseToList_CustomType() {
        String schemaFilePath = this.getClass().getClassLoader().getResource(SCHEMA_FILE_NAME_CUSTOM_TYPE).getPath();
        String dataFilePath = this.getClass().getClassLoader().getResource(DATA_FILE_NAME_CUSTOM_TYPE).getPath();
        WordDictParser parser = new WordDictParserImpl();
        List<List<Object>> result = parser.parseToList(schemaFilePath, dataFilePath);

        assertNotNull(result);

        // 以下的校验值和word_dict_data_custom_type.txt中第一行数据直接相关
        int expectSize = 2;
        assertEquals(expectSize, result.size());

        DemoCustomType expectCustom = new DemoCustomType();
        expectCustom.setName("lili");
        expectCustom.setSex("female");
        assertEquals(expectCustom, result.get(0).get(1));
    }

    @Test
    public void testParseToList_Fail() {
        String schemaFilePath = this.getClass().getClassLoader().getResource(SCHEMA_FILE_NAME_ERROR).getPath();
        String dataFilePath = this.getClass().getClassLoader().getResource(DATA_FILE_NAME_ERROR).getPath();
        WordDictParser parser = new WordDictParserImpl();
        List<List<Object>> result = parser.parseToList(schemaFilePath, dataFilePath);

        assertNull(result);
    }

    @Test
    public void testParse() {
        // 以ErrorCntProcessor示范和测试自定义processor的使用
        String schemaFilePath = this.getClass().getClassLoader().getResource(SCHEMA_FILE_NAME_PROCESSOR).getPath();
        String dataFilePath = this.getClass().getClassLoader().getResource(DATA_FILE_NAME_PROCESSOR).getPath();
        WordDictParser parser = new WordDictParserImpl();

        ErrorCntProcessor errorCntProcessor = new ErrorCntProcessor();
        parser.parse(schemaFilePath, dataFilePath, errorCntProcessor);

        int expectTotalCnt = 4;
        int expectErrotCnt = 2;
        assertEquals(expectTotalCnt, errorCntProcessor.getTotalLineCnt());
        assertEquals(expectErrotCnt, errorCntProcessor.getErrorLineCnt());

    }

}
