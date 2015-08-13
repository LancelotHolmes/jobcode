// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 类型解析器的基类

#ifndef  __BASE_TYPE_PARSER_H_
#define  __BASE_TYPE_PARSER_H_

#include <string>

namespace word_parser {

class BaseTypeParser {
public:
	// 类型解析方法，由具体类型实现
	virtual int parse(std::string &data, std::string &type)=0;

	// 类型数组字符串解析方法，由具体类型实现
	virtual int parse_array(std::string &data, std::string &type)=0;

};

} // end namespace wgz

#endif  // __BASE_TYPE_PARSER_H_
