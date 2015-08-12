/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

/**
 * 词表解析的具体实现逻辑
 * @title WordDictParserImpl
 * @description 词表解析的具体实现逻辑
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月8日
 * @version 1.0
 */
public class WordDictParserImpl implements WordDictParser {

    // 词表列分隔符
    private static final String COLUMN_DELIMITER = "\t";
    // 词表数组类型的列字符串的大小和元素分割符
    private static final String ARRAY_NUM_DELIMITER = ":";
    // 词表数组类型的列字符串的元素间分割符
    private static final String ARRAY_ELEMENT_DELIMITER = ",";

    // 用户自定义类型的parse方法，要求要静态，且参数依次为数据字符串
    private static final String CUSTOM_PARSE_METHOD = "parse";

    // 非用户自定义的内置类型
    private static final String INT_TYPE = "int";
    private static final String FLOAT_TYPE = "float";
    private static final String STRING_TYPE = "char *";
    private static final String UINT32_TYPE = "uint32_t";
    private static final String UINT64_TYPE = "uint64_t";
    private static final String ARRAY_TYPE = "[]";

    // 内置类型集合，新增内置类型时，需要在构造函数新增
    private Set<String> baseTypes;

    private static Logger log = Logger.getLogger(WordDictParserImpl.class);

    public WordDictParserImpl() {
        this.baseTypes = new HashSet<String>();
        baseTypes.add(INT_TYPE);
        baseTypes.add(FLOAT_TYPE);
        baseTypes.add(STRING_TYPE);
        baseTypes.add(UINT32_TYPE);
        baseTypes.add(UINT64_TYPE);
    }

    @Override
    public List<List<Object>> parseToList(String schemaFilePath, String dataFilePath) {
        DefaultMemoryProcessor processor = new DefaultMemoryProcessor();
        boolean isSuccess = parse(schemaFilePath, dataFilePath, processor);
        if (!isSuccess) {
            return null;
        }
        return processor.getWordDicts();
    }

    @Override
    public boolean parse(String schemaFilePath, String dataFilePath, Processor processor) {

        Scanner scanner = null;
        try {
            SchemaManager schemaManager = new SchemaManager(schemaFilePath);

            File dataFile = new File(dataFilePath);
            scanner = new Scanner(dataFile);
            // 依次读取和处理词表每行数据
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<Object> results = parseLine(line, schemaManager);
                // 解析出错，results为null
                processor.process(results);
            }
            scanner.close();
        } catch (DocumentException e) {
            String errorMsg = "schema load error!";
            log.warn(errorMsg, e);
            return false;
        } catch (FileNotFoundException e) {
            String errorMsg = "data file not found!";
            log.warn(errorMsg, e);
            return false;
        } finally {
            if (scanner != null) {
                try {
                    scanner.close();
                } catch (Throwable ignore) {
                    // scanner文件流关闭错误，无需更多处理，忽略
                }
            }
        }
        return true;

    }

    /**
     * 解析词表一行数据
     * @param line 一行字符
     * @param schemaManager schema管理
     * @return 对象列表，如果词表一行解析出错则返回null
     */
    private List<Object> parseLine(String line, SchemaManager schemaManager) {
        try {
            List<Object> results = new ArrayList<Object>();

            String[] columnStrs = StringUtils.split(line, COLUMN_DELIMITER);
            // 校验数据列数量
            if (columnStrs.length != schemaManager.getColunmSize()) {
                log.warn("data column length is wrong, expect " + schemaManager.getColunmSize() + " but is "
                        + columnStrs.length);
                return null;
            }

            // 解析列字符串，转为相应类型对象
            for (int i = 0; i < columnStrs.length; i++) {
                String type = schemaManager.getColumnType(i + 1);
                String columnData = columnStrs[i];
                Object obj = null;
                if (isBaseType(type)) {
                    obj = parseBaseType(columnData, type);
                } else if (type.endsWith(ARRAY_TYPE)) {
                    obj = parseArray(columnData, type);
                } else {
                    obj = parseCustomType(columnData, type);
                }
                results.add(obj);
            }

            return results;
        } catch (Exception e) {
            // 捕获词表行数据解析中出现所有的异常，以忽略行解析错误，继续解析后续行，具体异常信息抛出时已具体化。
            log.warn(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 解析类型为数组的列
     * @param arrayStr 列字符串
     * @param type 具体数组类型
     * @return 解析成功，返回List
     * @throws Exception 格式等错误到导致解析失败，抛出异常
     */
    private List<Object> parseArray(String arrayStr, String type) throws Exception {
        String arrayType = type.substring(0, type.length() - 2);
        if (!isBaseType(arrayType)) {
            throw new Exception("array type is not a built-in type, type is " + arrayType);
        }

        String[] numStrs = StringUtils.split(arrayStr, ARRAY_NUM_DELIMITER);
        if (numStrs.length != 2) {
            throw new Exception("array column length is wrong, data is" + arrayStr);
        }
        int num = Integer.parseInt(numStrs[0]);

        String[] eleStrs = StringUtils.split(numStrs[1], ARRAY_ELEMENT_DELIMITER);
        if (eleStrs.length != num) {
            throw new Exception("array element number is wrong, data is" + arrayStr);
        }

        List<Object> results = new ArrayList<Object>();
        for (String eleStr : eleStrs) {
            Object obj = parseBaseType(eleStr, arrayType);
            results.add(obj);
        }

        return results;
    }

    private Object parseBaseType(String columnData, String type) {
        Object obj = null;
        if (type.equals(INT_TYPE)) {
            obj = new Integer(columnData);
        } else if (type.equals(FLOAT_TYPE)) {
            obj = new Float(columnData);
        } else if (type.equals(STRING_TYPE)) {
            obj = columnData;
        } else if (type.equals(UINT32_TYPE)) {
            obj = new Long(columnData);
        } else if (type.equals(UINT64_TYPE)) {
            // uint64,由于java没有对应基础类型，转为BigInteger
            obj = new BigInteger(columnData);
        }
        return obj;
    }

    /**
     * 解析用户自定义类型，需要用户自定类型中实现parse的静态函数，解析字符串为自定义类型的对象
     * @param columnData 列字符串
     * @param type 用户自定义类型
     * @return 解析成功后的用户自定义类型的对象
     * @throws Exception 任何导致自定义类型列解析失败，即抛出异常
     */
    private Object parseCustomType(String columnData, String type) throws Exception {
        try {
            Class typeClass = Class.forName(type);
            Method parseMethod = typeClass.getMethod(CUSTOM_PARSE_METHOD, new Class[] { String.class });
            Object obj = parseMethod.invoke(typeClass, new Object[] { columnData });
            return obj;
        } catch (ClassNotFoundException e) {
            String errorMsg = "custom type not found, type is " + type;
            throw new Exception(errorMsg);
        } catch (NoSuchMethodException e) {
            String errorMsg = "custom type has no parse method, type is " + type;
            throw new Exception(errorMsg);
        }
    }

    private boolean isBaseType(String type) {
        return baseTypes.contains(type);
    }

}
