 * 逻辑：会计用户中价格为999.99列的个数，输出第一列为用户id，第二列为个数
 * 输入文件：原始文本文件共有28列，其中第7列价格，第四列为用户id,map会提取价格为999.99的用户id
 * 脚本运行方式如下：
 * ./hadoop-client/hadoop/bin/hadoop jar testcount.jar  com.wgz.hadoop.CountJobMain /outputpath 20150801
 * 第一个参数为输出路径，第二参数为输入数据的日期