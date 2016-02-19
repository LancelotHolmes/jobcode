from pyspark import SparkContext, SparkConf
import string  
import json 


conf = SparkConf().setAppName("test_uv_dis")
sc = SparkContext(conf=conf)


servicefile = sc.textFile("/path/spark/servicepvdis/*")
servicefile.cache()
print servicefile.count()

def getOpt(line):
    obj = json.loads(line)
    return (obj["date"],obj["optid"])

def getOptUser(line):
    obj = json.loads(line)
    return (obj["date"],obj["userid"])
optids = servicefile.map(getOpt)
print optids.distinct().countByKey()
print optids.map(lambda x: x[1]).distinct().count()

optUserids = servicefile.map(getOptUser)
uniqOptUserids = optUserids.distinct()
uniqOptUserids.cache()

print uniqOptUserids.countByKey()
distinctOptUserids = uniqOptUserids.map(lambda x: x[1]).distinct()
print distinctOptUserids.count()


userfile = sc.textFile("/path/spark/userpvdis/*")
def getUser(line):
    obj = json.loads(line)
    return (obj["date"],obj["userid"])

userids = userfile.map(getUser)
print "date user count: " 
uniqUserids =  userids.distinct()
uniqUserids.cache()
print uniqUserids.countByKey()
print "all distinct user count" 
distinctUserids = userids.map(lambda x: x[1]).distinct()
print distinctUserids.count()


joinUserids = distinctUserids.union(distinctOptUserids)
print "all uniq user count,including user and opt" 
print joinUserids.distinct().count()

intersectUserids = distinctUserids.intersection(distinctOptUserids)
print "all intersect user count "
print intersectUserids.count()

intersectUserids.saveAsTextFile("/path/spark/optintersectUser")
distinctOptUserids.saveAsTextFile("/path/spark/uniqOptUser")
distinctUserids.saveAsTextFile("/path/spark/uniqCommonUser")
 




