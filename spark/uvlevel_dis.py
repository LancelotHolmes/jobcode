from pyspark import SparkContext, SparkConf
import string  
import json  

conf = SparkConf().setAppName("test_wgz")
sc = SparkContext(conf=conf)
#file = sc.textFile("/path/20151116/0900")
file = sc.textFile("/path/2015111[0-6]/*/")
fil1 = file.filter(lambda line:line.find("phoenix_dataCenter_") !=-1)

def getUlevelid(line):
   jsonobj = json.loads(line)
   admin="admin"
   if jsonobj["msg"]["param"]["optid"] == jsonobj["msg"]["param"]["userid"]:
       admin="user"
   date = jsonobj["logtime"].split(" ")[0]
   return (admin +date +","+jsonobj["msg"]["param"]["ulevelid"],1)

fil2 = fil1.map(getUlevelid)
adminCnt = fil2.filter(lambda d:d[0].find("admin") != -1).countByKey()
userCnt = fil2.filter(lambda d:d[0].find("user") != -1).countByKey()
totalAdminCnt = {}
for key in  adminCnt.keys():
    ulevelid = key.split(",")[1]
    if not(totalAdminCnt.has_key(ulevelid)):
        totalAdminCnt[ulevelid] =0
    totalAdminCnt[ulevelid] =totalAdminCnt[ulevelid] +adminCnt[key]


totalUserCnt = {}
for key in  userCnt.keys():
    ulevelid = key.split(",")[1]
    if not(totalUserCnt.has_key(ulevelid)):
        totalUserCnt[ulevelid] =0
    totalUserCnt[ulevelid] =totalUserCnt[ulevelid] +userCnt[key]
result ={}
result["totalUserCnt"] = totalUserCnt
result["totalAdminCnt"] = totalAdminCnt
result["adminCnt"] = adminCnt
result["userCnt"] = userCnt
print  str(result)

list_res = [str(result)]
output = sc.parallelize(list_res)
output.saveAsTextFile("/path/spark/ulevel_dis")




