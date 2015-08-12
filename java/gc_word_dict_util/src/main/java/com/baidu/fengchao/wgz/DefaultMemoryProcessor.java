/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载词表到内存中，供后续使用。词表内存形式为List<List<Object>>
 * @title DefaultMemoryProcessor
 * @description 加载词表到内存的processor 
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月8日
 * @version 1.0
 */
public class DefaultMemoryProcessor implements Processor {

    private List<List<Object>> wordDicts;

    /**
     * 返回解析词表文件后的词表对象列表
     * @return 词表对象列表
     */
    public List<List<Object>> getWordDicts() {
        return wordDicts;
    }

    public DefaultMemoryProcessor() {
        wordDicts = new ArrayList<List<Object>>();
    }

    /**
     * 将解析后的词表数据追加到内存列表中
     */
    @Override
    public void process(List<Object> objectsOfLine) {
        if (objectsOfLine != null) {
            wordDicts.add(objectsOfLine);
        }
    }

}
