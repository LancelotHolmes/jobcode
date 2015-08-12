/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 词表列描述的schema管理类
 * @title SchemaManager
 * @description 词表列描述的schema管理类 
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月8日
 * @version 1.0
 */
public class SchemaManager {

    // schema的xml节点名称
    private static final String COLUMN_NAME = "column";
    private static final String COLUMN_INDEX = "index";
    private static final String COLUMN_TYPE = "type";

    private String schemaFilePath;

    // 列对应的类型字符串，索引从1开始
    private Map<Integer, String> columnTypes = new HashMap<Integer, String>();

    /**
     * 词表列描述的schema文件路径
     * @param schemaFilePath 词表schema文件路径
     * @throws DocumentException schema解析错误
     */
    public SchemaManager(String schemaFilePath) throws DocumentException {
        this.schemaFilePath = schemaFilePath;
        loadSchema();
    }

    private void loadSchema() throws DocumentException {
        File file = new File(schemaFilePath);
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        Element root = doc.getRootElement();

        Iterator columnIterator = root.elementIterator(COLUMN_NAME);

        while (columnIterator.hasNext()) {
            Element column = (Element) columnIterator.next();
            String indexStr = column.attributeValue(COLUMN_INDEX);
            String typeStr = column.attributeValue(COLUMN_TYPE);
            Integer index = new Integer(indexStr);
            columnTypes.put(index, typeStr);
        }
    }

    /**
     * 获取词表列的类型
     * @param index 词表列号，从1开始
     * @return 类型字符串
     */
    public String getColumnType(int index) {
        return columnTypes.get(index);
    }

    /**
     * 返回schema定义的列数
     * @return schema定义的列数
     */
    public int getColunmSize() {
        return columnTypes.size();
    }

}
