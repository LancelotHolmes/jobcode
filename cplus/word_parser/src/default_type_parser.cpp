// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 默认类型解析实现

#include <vector>
#include <string>
#include <iostream> 
#include "default_type_parser.h"
#include <boost/algorithm/string.hpp>
#include <boost/lexical_cast.hpp>  
#include "default_type_parser.h"

namespace word_parser {

// 默认类型解析成功后，默认行为是输出到标准输出，如需覆盖，可自定义覆盖
int DefaultTypeParser::parse(std::string &data, std::string &type) {
	try {
		if (type == "int") {
			int value = boost::lexical_cast<int>(data);
			std::cout << value << std::endl;
		} else if (type == "float") {
			float value = boost::lexical_cast<float>(data);
			std::cout << value << std::endl;
		} else if (type == "char *") {
			const char * c_str = data.c_str();
			std::cout << c_str << std::endl;
		} else if (type == "uint32_t") {
			uint32_t value = boost::lexical_cast < uint32_t > (data);
			std::cout << value << std::endl;
			;
		} else if (type == "uint64_t") {
			uint64_t value = boost::lexical_cast < uint64_t > (data);
			std::cout << value << std::endl;
		}
		return 0;
	} catch (std::exception &e) {
		std::cerr << data << " parsed by type " << type
				<< " failed!, Reason is " << e.what() << std::endl;
		return -1;
	}
}

// 默认类型的数组解析成功后，直接输出
int DefaultTypeParser::parse_array(std::string &data, std::string &type) {
	try {
		// 解析数组
		std::vector<std::string> array_num_str;
		boost::split(array_num_str, data, boost::is_any_of(":"));
		if (array_num_str.size() != 2) {
			std::cerr << data << " array format is wrong!" << std::endl;
			return -1;
		}

		std::string print_str = "array ";
		int array_num = boost::lexical_cast<int>(array_num_str[0]);
		print_str = print_str + boost::lexical_cast < std::string
				> (array_num) + ":";
		std::vector<std::string> data_strs;
		boost::split(data_strs, array_num_str[1], boost::is_any_of(","));

		// 校验数组的大小是否正确
		if (array_num != data_strs.size()) {
			std::cerr << data << " array numbers format is wrong!" << std::endl;
			return -1;
		}

		// 依次解析数组成员并输出
		for (size_t i = 0; i < data_strs.size(); i++) {
			if (type == "int") {
				int value = boost::lexical_cast<int>(data_strs[i]);
				print_str = print_str + boost::lexical_cast < std::string
						> (value) + ",";
			} else if (type == "float") {
				float value = boost::lexical_cast<float>(data_strs[i]);
				print_str = print_str + boost::lexical_cast < std::string
						> (value) + ",";
			} else if (type == "char *") {
				const char * c_str = data_strs[i].c_str();
				std::string value(c_str);
				print_str = print_str + value + ",";
			} else if (type == "uint32_t") {
				uint32_t value = boost::lexical_cast < uint32_t
						> (data_strs[i]);
				print_str = print_str + boost::lexical_cast < std::string
						> (value) + ",";
			} else if (type == "uint64_t") {
				uint64_t value = boost::lexical_cast < uint64_t
						> (data_strs[i]);
				print_str = print_str + boost::lexical_cast < std::string
						> (value) + ",";
			}

		}
		std::cout << print_str << std::endl;
		return 0;
	} catch (std::exception &e) {
		std::cerr << data << " array parsed by type " << type
				<< " failed!, Reason is " << e.what() << std::endl;
		return -1;
	}
}

} // end namespace word_parser
