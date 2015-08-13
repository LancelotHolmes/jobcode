// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 词表解析的工具类，是词表解析工具的主类

#ifndef  __DICT_PARSER_H_
#define  __DICT_PARSER_H_
#include <string>
#include <map>
#include "base_type_parser.h"

namespace word_parser {

// 列描述定义
struct ColumnType {
	// 列序号。从1开始
	int col_num;
	// 列数据的类型
	std::string type;
	// 是否为数组类型
	bool is_array;
};

// 词表解析类
class DictParser {
public:
	DictParser(){
	}

	~DictParser() {
	}

	// 词表的初始化
	// schema_file 词表描述文件，具体格式可参考data/schemal.xml
	int init(const std::string& schema_file);

	// 添加自定义类型对应解析对象；默认类型默认使用DefalutTypeParser，可以在init后覆盖
	void add_type_parser(std::string& type, BaseTypeParser* type_parser);

	// 根据schema定义解析特定数据文件
	int parse(const std::string& data_file);

	// 返回成功即系的行数
	int get_success_cnt() {
		return _success_cnt;
	}

	// 读入解析的总行数
	int get_total_cnt() {
		return _total_cnt;
	}

private:
	// 载入列类型解析文件
	int load_shema_file(const std::string& schema_file);

	// 初始化基本类型对应的解析类
	void init_type_parser();

	// 成功解析行数
	int _success_cnt;
	// 解析总行数
	int _total_cnt;

	// 特定类型对应的解析器
	std::map<std::string, BaseTypeParser*> _type_parser_map;
	// 特定列对应的类型，从1开始
	std::map<int, ColumnType*> _column_type_map;
};
// end  DictParser
}// end namespace wgz

#endif  //__DICT_PARSER_H_
