// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 词表解析的示范使用

#include <iostream>
#include <string>
#include "dict_parser.h"
#include "custom_type_parser.h"
#include "default_type_parser.h"

using namespace word_parser;

int main() {
	// test DefaultTypeParser()
	/*
	 DefaultTypeParser* parser = new DefaultTypeParser();
	 std::string value = "323";
	 std::string type = "int";
	 parser->parse(value,type);
	 */

	// test DictParser
	// simple test without array
	// string schema_file = "/home/users/wanggongzheng/c_learn/gcoder/word_parser/data/schemal1.xml";
	// string data_file = "/home/users/wanggongzheng/c_learn/gcoder/word_parser/data/data1.txt";
	// test with array
	// std::string schema_file = "/home/users/wanggongzheng/c_learn/gcoder/word_parser/data/schemal2.xml";
	// std::string data_file = "/home/users/wanggongzheng/c_learn/gcoder/word_parser/data/data2.txt";
	// test with custom type
	std::string schema_file =
			"data/schemal3.xml";
	std::string data_file =
			"data/data3.txt";
	// 自定义类型对应的解析对象
	BaseTypeParser* custom_type_parser = new CustomTypeParser();
	std::string custom_key = "custom_type";

	DictParser* dict_parser = new DictParser();
	dict_parser->init(schema_file);
	// 添加自定义类型的解析对象
	dict_parser->add_type_parser(custom_key, custom_type_parser);
	dict_parser->parse(data_file);

	std::cout << dict_parser->get_success_cnt()
			<< " lines are parsed successfully in "
			<< dict_parser->get_total_cnt() << " lines " << std::endl;
}
