from pyspark import SparkContext, SparkConf

srcPath="/app/ecom/fc-star/wanggongzheng/wordtxt"
resPath="/app/ecom/fc-star/wanggongzheng/wordres"
appName="word count test"

#init sparkContext
conf = SparkConf().setAppName(appName)   
sc = SparkContext(conf=conf)

#create rdd from hadoop txt file
textRdd = sc.textFile(srcPath)
#map text word line to (word,1)
wordSplit = textRdd.flatMap(lambda line: line.split()).map(lambda word: (word, 1))
#reducebyKey to get word count
wordCounts = wordSplit.reduceByKey(lambda a, b: a+b)
print wordCounts.collect()
#save to hdfs
wordCounts.saveAsTextFile(resPath)
