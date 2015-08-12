/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */

package com.baidu.fengchao.wgz;

import org.apache.commons.lang3.StringUtils;

/**
 * 用于示例或测试的用户自定义类型
 * @title DemoCustomType
 * @description 用于示例或测试的用户自定义类型
 * @author Gongzheng Wang (wanggongzheng@baidu.com)
 * @date 2014年7月9日
 * @version 1.0
 */
public class DemoCustomType {

    private static final String DELIMER = ",";

    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 根据字符串解析出DemoCustomType
     * @param value 字符串格式为"name,sex"
     * @return DemoCustomType对象
     * @throws Exception 任何解析出错都会抛出异常
     */
    public static DemoCustomType parse(String value) throws Exception {
        String[] resStrs = StringUtils.split(value, DELIMER);
        if (resStrs.length != 2) {
            String errorMsg = "data of DemoCustomType is wrong, data= " + value;
            throw new Exception(errorMsg);
        }

        DemoCustomType custom = new DemoCustomType();
        custom.setName(resStrs[0]);
        custom.setSex(resStrs[1]);
        return custom;
    }

    @Override
    public String toString() {
        return name + DELIMER + sex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((sex == null) ? 0 : sex.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DemoCustomType other = (DemoCustomType) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (sex == null) {
            if (other.sex != null) {
                return false;
            }
        } else if (!sex.equals(other.sex)) {
            return false;
        }
        return true;
    }

}
