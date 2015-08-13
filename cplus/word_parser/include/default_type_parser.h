// Copyright 2015 Baidu Inc. All Rights Reserved.
// Author: wang gongzheng (wanggongzheng@baidu.com)
//
// 默认类型的解析类，包括int，char *,uint_32,uint_64，数组类型的解析

#ifndef  __DEFAULT_TYPE_PARSER_H_
#define  __DEFAULT_TYPE_PARSER_H_

#include "base_type_parser.h"

namespace word_parser {

// 基础类型解析类
class DefaultTypeParser: public BaseTypeParser {
public:
	virtual int parse(std::string &data, std::string &type);
	virtual int parse_array(std::string &data, std::string &type);

}; // end DefaultTypeParser
}// end namespace wgz

#endif  //__DEFAULT_TYPE_PARSER_H_

