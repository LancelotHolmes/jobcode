// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 自定义类型解析类

#ifndef  __INCLUDE/CUSTOM_TYPE_PARSER_H_
#define  __INCLUDE/CUSTOM_TYPE_PARSER_H_

#include <string>
#include "base_type_parser.h"

namespace word_parser {

// 自定义类型的定义
struct MyCustomType {
	int age;
	std::string name;
	std::string sex;
};

// 自定义类型的解析类
class CustomTypeParser: public BaseTypeParser {
public:
	CustomTypeParser() {
	}

	~CustomTypeParser() {
	}

	virtual int parse(std::string &data, std::string &type);
	virtual int parse_array(std::string &data, std::string &type);
private:
	std::string type_to_string(MyCustomType& my_type);
	int CustomTypeParser::string_to_type(std::string& data,
			MyCustomType& my_type);

};
// end CustomTypeParser
}// end namespace wgz

#endif  //__INCLUDE/CUSTOM_TYPE_PARSER_H_
