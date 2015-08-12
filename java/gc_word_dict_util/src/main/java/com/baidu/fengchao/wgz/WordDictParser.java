/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.util.List;

/**
 * @title WordDictParser
 * @description 词表解析器的接口定义
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月8日
 * @version 1.0
 */
public interface WordDictParser {

    /**
     * 解析词表数据文件
     * @param schemaFilePath 词表的列描述的schema文件路径
     * @param dataFilePath  词表的数据文件路径
     * @param processor 词表解析后的处理器
     * @return 如果成功开始解析，则返回true，如schema或data文件错误导致解析未开始，则返回false。具体解析出错，会忽略。
     */
    public boolean parse(String schemaFilePath, String dataFilePath, Processor processor);

    /**
     * 解析词表数据文件后，返回词表对象列表
     * @param schemaFilePath 词表的列描述的schema文件路径
     * @param dataFilePath 词表的数据文件路径
     * @return 词表对象列表，每个元素对应着词表一行数据，索引顺序和列序一致,如果解析完全出错，返回null
     */
    public List<List<Object>> parseToList(String schemaFilePath, String dataFilePath);

}
