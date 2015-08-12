/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import java.util.List;

/**
 * 当词表的一行数据解析出来后，将会调用具体传入的<code>Processor</code>的process方法进行处理,
 * 这个可以提供较好可扩展性，用户可以添加自定义的业务逻辑。
 * @title Processor
 * @description 通用行解析后处理接口定义
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月8日
 * @version 1.0
 */
public interface Processor {

    /**
     * 根据具体业务需求，处理已经解析成功的一行词表数据
     * @param objectsOfLine 一行词表数据解析后的对象列表，索引对应着列,如果为null,表示该行解析出错
     */
    public void process(List<Object> objectsOfLine);
}
