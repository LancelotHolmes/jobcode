from pyspark import SparkContext, SparkConf
from pyspark.sql import SQLContext, Row

srcPath="/app/ecom/fc-star/wanggongzheng/wordtxt"
resPath="/app/ecom/fc-star/wanggongzheng/wordres"
appName="word count test"

#init sqlContext
conf = SparkConf().setAppName(appName)   
sc = SparkContext(conf=conf)
sqlContext = SQLContext(sc)

#create rdd from hadoop txt file
textRdd = sc.textFile(srcPath)
#map text word line to (word,1)
wordSplit = textRdd.flatMap(lambda line: line.split()).map(lambda word: (word, 1))
#reducebyKey to get word count
wordCounts = wordSplit.reduceByKey(lambda a, b: a+b)

#create row based rdd
rowRdd = wordCounts.map(lambda x: Row(word=x[0],wc=x[1]))
wordFrames = sqlContext.inferSchema(rowRdd)
wordFrames.registerTempTable("tword")
top10Frames = sqlContext.sql("select word,wc FROM tword order by wc desc limit 10")
print top10Frames.collect()
