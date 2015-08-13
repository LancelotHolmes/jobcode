// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 词表解析工具类实现

#include <string>
#include <iostream>
#include <fstream>
#include <exception>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp> 
#include <boost/foreach.hpp>
#include <boost/algorithm/string.hpp> 
#include "default_type_parser.h"
#include "dict_parser.h"

namespace word_parser {

int DictParser::load_shema_file(const std::string& schema_file) {
	try {
		boost::property_tree::ptree pt;
		boost::property_tree::read_xml(schema_file, pt);
		BOOST_FOREACH(boost::property_tree::ptree::value_type & v,
				pt.get_child("dict_meta.columns"))
		{
			// 依次解析各列定义
			if (v.first == "column") {
				ColumnType* column = new ColumnType();
				column->type = v.second.get < std::string > ("type");
				column->col_num = v.second.get<int>("col_num");
				column->is_array = v.second.get<bool>("is_array");
				_column_type_map.insert(
						std::map<int, ColumnType*>::value_type(column->col_num,
								column));
			}
		}
		return 0;
	} catch (std::exception & e) {
		std::cout << e.what() << std::endl;
		return -1;
	}
}

int DictParser::init(const std::string& schema_file) {
	_success_cnt = 0;
	_total_cnt = 0;
	_type_parser_map.clear();
	_column_type_map.clear();

	int result = load_shema_file(schema_file);
	if (result != 0) {
		return result;
	}

	// 默认类型对应的解析器初始化
	init_type_parser();
	return 0;
}

void DictParser::init_type_parser() {
	std::string base_types[5] = { "int", "float", "char *", "uint32_t",
			"uint64_t" };
	BaseTypeParser* type_parser = new DefaultTypeParser();
	for (int i = 0; i < 5; i++) {
		add_type_parser(base_types[i], type_parser);
	}
}

void DictParser::add_type_parser(std::string& type,
		BaseTypeParser* type_parser) {
	_type_parser_map.insert(
			std::map<std::string, BaseTypeParser*>::value_type(type,
					type_parser));
}

int DictParser::parse(const std::string& data_file) {
	try {
		std::ifstream in_file(data_file.c_str());
		std::string line;
		int line_num = 0;
		// 读入数据行
		while (std::getline(in_file, line) && line.length() > 0) {
			line_num++;
			bool is_success = true;
			std::vector<std::string> column_strs;
			boost::split(column_strs, line, boost::is_any_of("\t"));

			// 依次找到各列的解析器并解析
			for (size_t i = 0; i < column_strs.size(); i++) {
				// 获取该列的解析器
				std::map<int, ColumnType*>::iterator iter;
				int column_num = i + 1;
				iter = _column_type_map.find(column_num);
				if (iter == _column_type_map.end()) {
					std::cerr << " line " << line_num
							<< " parse failed!, Reason is " << column_num
							<< " not find in schema !!" << std::endl;
					is_success = false;
					break;
				}
				ColumnType* column_type = iter->second;
				std::map<std::string, BaseTypeParser*>::iterator parser_iter;
				parser_iter = _type_parser_map.find(column_type->type);
				if (parser_iter == _type_parser_map.end()) {
					std::cerr << " line " << line_num
							<< " parse failed!, Reason is " << column_type->type
							<< " has no type parser " << std::endl;
					is_success = false;
					break;
				}

				// 有类对应解析器解析该列
				BaseTypeParser* type_parser = parser_iter->second;
				int result_code = 1;
				if (column_type->is_array) {
					result_code = type_parser->parse_array(column_strs[i],
							column_type->type);
				} else {
					result_code = type_parser->parse(column_strs[i],
							column_type->type);
				}

				if (result_code != 0) {
					std::cerr << " line " << line_num
							<< " parse failed!, Reason is " << column_strs[i]
							<< " parsed by " << column_type->type << " failed!"
							<< " result_code= " << result_code << std::endl;
					is_success = false;
					break;
				}
			}
			// 如果该行所有列解析成功，计数加1
			if (is_success) {
				_success_cnt++;
			}
		}
		_total_cnt = line_num;
		in_file.close();
		return 0;
	} catch (std::exception &e) {
		std::cerr << e.what() << std::endl;
		return -1;
	}
}
} // end word parser
