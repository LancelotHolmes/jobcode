// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 自定义类型解析，注意数据类型分隔符有，和：，所以其序列化不能和其有冲突

#include <string>
#include <vector>
#include <boost/algorithm/string.hpp>
#include <boost/lexical_cast.hpp>
#include "custom_type_parser.h"

namespace word_parser {

int CustomTypeParser::parse(std::string &data, std::string &type) {
	MyCustomType my_type;
	int result_code = string_to_type(data, my_type);
	if (result_code != 0) {
		std::cerr << data << " format is wrong ! type = " << type << std::endl;
		return -1;
	}
	std::string custom_str = type_to_string(my_type);
	std::cout << " custom type " << custom_str << std::endl;
	return 0;
}

int CustomTypeParser::parse_array(std::string &data, std::string &type) {

}

// 将自定义类型转化的string，成员之间用；分隔
std::string CustomTypeParser::type_to_string(MyCustomType& my_type) {
	std::string result = "";
	std::string age_str = boost::lexical_cast < std::string > (my_type.age);
	result += age_str;
	result += ";";
	result += my_type.name;
	result += ";";
	result += my_type.sex;
	return result;
}

// 将字符串转化为自定义类型MyCustomType
int CustomTypeParser::string_to_type(std::string& data, MyCustomType& my_type) {
	// 根据;分隔成员字符串
	std::vector<std::string> value_strs;
	boost::split(value_strs, data, boost::is_any_of(";"));
	if (value_strs.size() != 3) {
		std::cerr << "custom type data format is wrong !!" << std::endl;
		return -1;
	}

	// 解析为自定义类型
	try {
		my_type.age = boost::lexical_cast<int>(value_strs[0]);
		my_type.name = value_strs[1];
		my_type.sex = value_strs[2];
		return 0;
	} catch (std::exception &e) {
		std::cerr << data << " custom_type parsed  " << " failed!, Reason is "
				<< e.what() << std::endl;
		return -1;
	}
}
}
