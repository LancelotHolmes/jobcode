/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URL;

import org.dom4j.DocumentException;
import org.junit.Test;

/**
 * SchemaManager单测
 * @title SchemaManagerTest
 * @description  SchemaManager单测
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月11日
 * @version 1.0
 */
public class SchemaManagerTest {

    // 预配置的测试用资源文件，位于src/test/resource下面
    private static final String SCHEMA_FILE_NAME = "word_dict_schema.xml";
    private static final String SCHEMA_FILE_ERROR = "word_dict_schema_error.xml";
    private int schemaSize = 6;

    @Test
    public void testGetColumnType() {
        try {
            String path = this.getClass().getClassLoader().getResource(SCHEMA_FILE_NAME).getPath();
            SchemaManager manager = new SchemaManager(path);

            int expectSize = schemaSize;
            assertEquals(expectSize, manager.getColunmSize());

            String expectType = "int";
            int index = 1;
            assertEquals(expectType, manager.getColumnType(index));

            expectType = "int[]";
            index = 4;
            assertEquals(expectType, manager.getColumnType(index));
        } catch (DocumentException e) {
            fail("schema load failed, DocumentException message: " + e.getMessage());
        }
    }

    @Test(expected = DocumentException.class)
    public void testSchemaManager_LoadException() throws DocumentException {
        URL resource = this.getClass().getClassLoader().getResource(SCHEMA_FILE_ERROR);
        assertNotNull(resource);
        String path = resource.getPath();
        SchemaManager manager = new SchemaManager(path);
    }

    @Test
    public void testGetColunmSize() {
        try {
            String path = this.getClass().getClassLoader().getResource(SCHEMA_FILE_NAME).getPath();
            SchemaManager manager = new SchemaManager(path);
            int expectSize = schemaSize;
            assertEquals(expectSize, manager.getColunmSize());
        } catch (DocumentException e) {
            fail("schema load failed, DocumentException message: " + e.getMessage());
        }
    }

}
