#import 
import MySQLdb
import sys
import datetime
import string

# global conf , database parameters for connection 
host = "#"
port =1
user="#"
passwd="#"
database="#"



def queryDb(sql):
    global host, port, user, passwd, database;
    try:
        conn =  MySQLdb.connect(host, user, passwd,database,  port);
        cur = conn.cursor(MySQLdb.cursors.DictCursor);
        cur.execute("SET NAMES 'gbk'");
        cur.execute(sql);
        rows = cur.fetchall();
        cur.close();
        conn.close();
        return rows;
    except MySQLdb.Error,e:
        print("Mysql Error %d: %s" %(e.args[0], e.args[1]))

srcFile = sys.argv[1];
payFile = sys.argv[2];

#test
#print srcFile 
#print toFile
#sql = "select username,password from useracct limit 10"
#result = queryDb(sql);
#print result

#read useracct
res_inf = {}
for line in open(srcFile):
    userid = line.strip(" ");
    userid = line.strip("\n");
    #read useracct
    sql = "select username,ustatid from useracct where userid=" + userid;
    q_res = queryDb(sql);
    if(q_res == None):
        print "error in query useracct of" + userid;
        exit();
    username = q_res[0]["username"].strip(" ");
    userstat = q_res[0]["ustatid"]
    user_inf = {};
    user_inf["username"] = username;
    user_inf["ustatid"] = userstat;

    # read userinfo
    sql = "select company,website,efftime,exptime from userinfo where userid=" + userid;
    q_res = queryDb(sql);
    if(q_res == None):
        print "error in query userinfo of" + userid;
        exit();
    user_inf["website"] = q_res[0]["website"];
    user_inf["company"] = q_res[0]["company"];
    user_inf["efftime"] = q_res[0]["efftime"];

    now_time =  datetime.datetime.now(); 
    exp_time = q_res[0]["exptime"];


    effdays = (now_time - user_inf["efftime"]).days;

    exp_days =0;
    if(exp_time != None):
        exp_days = (now_time - exp_time).days;

    user_inf["effdays"] = effdays;
    # check user valid
    if(user_inf["ustatid"] == 3 and exp_days <= 7):
        user_inf["valid"] = 0;
    elif(user_inf["ustatid"] == 2):
        user_inf["valid"] = 0;
    else:
        user_inf["valid"] = 1;

    res_inf[userid] = user_inf;

# load pay 
for pline in open(payFile):
    pline = pline.strip(" ");
    pline = pline.strip("\n");
    user_pay = pline.split(" ");
    p_userid = user_pay[0];
    pay = string.atoi(user_pay[1]);
    res_inf[p_userid]["pay"] = pay;



# uniq by company  sort by
sort_res = sorted(res_inf.iteritems(), key= lambda x:x[1]["company"])

last_company = sort_res[0][1]["company"];
company_num =1;

for row in sort_res:
    if(row[1]["company"]  != last_company):
        company_num = company_num+1;
        last_company = row[1]["company"];
    print "%s %s %s %s %s %s %s %s %s" %(row[1]['username'],row[0],\
            row[1]['ustatid'],row[1]['valid'],row[1]['effdays'],row[1]['company'],\
            row[1]['website'],row[1]['pay'], company_num)


#for key in res_inf:
#    print "%s %s %s %s %s %s %s" %( res_inf[key]['username'], key, \
#            res_inf[key]['ustatid'],res_inf[key]['valid'],res_inf[key]['effdays'],res_inf[key]['company'],\
#            res_inf[key]['website'])





