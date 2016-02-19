from pyspark import SparkContext, SparkConf
from pyspark.sql import SQLContext, Row
import string  
import json  

conf = SparkConf().setAppName("test_wgz")
sc = SparkContext(conf=conf)
sqlContext = SQLContext(sc)

#file = sc.textFile("/path/20151116/0900")
file = sc.textFile("/path/2015111[0-6]/*/")
fil1 = file.filter(lambda line:line.find("phoenix_dataCenter_") !=-1)
fil1.cache()

def filter_service(line):
   jsonobj = json.loads(line)
   return jsonobj["msg"]["param"]["optid"] != jsonobj["msg"]["param"]["userid"]

fil_service = fil1.filter(filter_service)
fil_service.cache()

def filter_user(line):
   jsonobj = json.loads(line)
   return jsonobj["msg"]["param"]["optid"] == jsonobj["msg"]["param"]["userid"]
fil_user = fil1.filter(filter_user)
fil_user.cache()

fil1.unpersist()

def getServiceRow(line):
    line = json.loads(line)
    date1 = line["logtime"].split(" ")[0]
    userid1 = line["msg"]["userid"]
    optid1 = line["msg"]["optid"]
    ulevelid1 = line["msg"]["param"]["ulevelid"]
    source1 = line["msg"]["param"]["source"]
    target1 = line["msg"]["param"]["target"]
    return Row(date=date1,userid=userid1,optid=optid1,ulevelid=ulevelid1, source=source1, target=target1,pv=int(1))


service = fil_service.map(getServiceRow)
schemaService =  sqlContext.inferSchema(service)
schemaService.registerTempTable("service")
schemaService.cache()
fil_service.unpersist()

groupPvFrame = sqlContext.sql("select date,AVG(pvs) as avgpvs,SUM(pvs) as sumpvs,COUNT(pvs) as cnt  from (select date,userid,optid,sum(pv) as pvs from service group by date,userid,optid)t group by date")
sumPvs =  sqlContext.sql(" select SUM(pv) from service")
serviceDetailPvFrame = sqlContext.sql("select date,userid,optid,sum(pv) as pvs from service group by date,userid,optid")
serviceJsonRdd = serviceDetailPvFrame.toJSON()
serviceJsonRdd.saveAsTextFile("/path/spark/servicepvdis")

print sumPvs.collect()
print groupPvFrame.collect()

def getUserRow(line):
    line = json.loads(line)
    date1 = line["logtime"].split(" ")[0]
    userid1 = line["msg"]["userid"]
    ulevelid1 = line["msg"]["param"]["ulevelid"]
    source1 = line["msg"]["param"]["source"]
    target1 = line["msg"]["param"]["target"]
    return Row(date=date1,userid=userid1,ulevelid=ulevelid1, source=source1, target=target1,pv=int(1))

user= fil_user.map(getUserRow)
schemaUser = sqlContext.inferSchema(user)
schemaUser.registerTempTable("user")
schemaUser.cache()
fil_user.unpersist()


userPvFrame = sqlContext.sql("select date,AVG(pvs) as avgpvs,SUM(pvs) as sumpvs,COUNT(pvs) as cnt  from (select date,userid,sum(pv) as pvs from user group by date,userid)t group by date")
userSumPvs =  sqlContext.sql(" select SUM(pv) from user")
userDetailPvFrame = sqlContext.sql("select date,userid,sum(pv) as pvs from user group by date,userid")
jsonRdd = userDetailPvFrame.toJSON()
jsonRdd.saveAsTextFile("/path/spark/userpvdis")

print userSumPvs.collect()
print userPvFrame.collect()



