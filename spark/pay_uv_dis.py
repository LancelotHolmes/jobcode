from pyspark import SparkContext, SparkConf
import string  

conf = SparkConf().setAppName("pay_uv_dis")
sc = SparkContext(conf=conf)
avpayfile = sc.textFile("/path/spark/userpay/avgpay.txt")
def getAvgPay(line):
    arr = line.split(",")
    userid = arr[0].strip()
    pay = int(arr[1].strip())
    return (userid,pay)
avgpay = avpayfile.map(getAvgPay)

file = sc.textFile("/path/spark/uniqCommonUser/*")
cuserids = file.map(lambda x : (x,1))

joinpayforuser = cuserids.leftOuterJoin(avgpay)
rawpayforuser = joinpayforuser.map(lambda x:x[1][1])
def fillZero(line):
    line_str = str(line)
    if( line_str == "None"):
        return 0
    return line
payforuser  = rawpayforuser.map(fillZero)

print payforuser.count()
c_result = payforuser.histogram([0,50,500,1000,2000,5000,10000,20000,10000000])
print c_result



optfile = sc.textFile("/path/spark/uniqOptUser/*")
ouserids = optfile.map(lambda x : (x,1))
joinOptPayUser = ouserids.leftOuterJoin(avgpay)
rawoptpay =joinOptPayUser.map(lambda x:x[1][1])
payforopt = rawoptpay.map(fillZero)
print  payforopt.count()
o_result = payforopt.histogram([0,50,500,1000,2000,5000,10000,20000,10000000])
print o_result



ifile = sc.textFile("/app/ecom/fc-star/wanggongzheng/spark/optintersectUser/*")
iuserids = ifile.map(lambda x : (x,1))
joinIPayUser = iuserids.leftOuterJoin(avgpay)
rawIpay =joinIPayUser.map(lambda x:x[1][1])
payforI = rawIpay.map(fillZero)
print  payforI.count()
i_result = payforI.histogram([0,50,500,1000,2000,5000,10000,20000,10000000])
print i_result
