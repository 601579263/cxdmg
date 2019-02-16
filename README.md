# cxdmg
这是一个阿里云的demo;

一.json在线格式化地址,把token格式化一下带着访问：
http://www.bejson.com/

二.在线访问地址登录：
  
  http://www.cxdmg.top/cxdmg-web ,
  
 三.在线访问地址订单系统：
  http://47.105.71.96:8082/ ,
  
 四.在线访问地址登录首页：
  http://www.cxdmg.top/cxdmg-web/index ;
  
 五.获取access_token:
  http://www.cxdmg.top/cxdmg-web/oauth/token?username=zhangsan&password=1&client_id=clientId4&client_secret=1&grant_type=password 
  
 六.在线访问资源服务器地址带token访问：
  http://47.105.71.96:8081/user/getUserInfo?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ6aGFuZ3NhbiIsInNjb3BlIjpbImFsbCJdLCJuYW1lIjoi5byg5LiJIiwiZXhwIjoxNTUwMzE0MjQ2LCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiZ2V0VXNlckxpc3QifSx7ImF1dGhvcml0eSI6ImdldFJvbGVMaXN0In0seyJhdXRob3JpdHkiOiJnZXRQZXJtaXNzaW9uTGlzdCJ9LHsiYXV0aG9yaXR5Ijoic2V0VXBQZXJtaXNzaW9uIn0seyJhdXRob3JpdHkiOiJzZXRVcFJvbGUifSx7ImF1dGhvcml0eSI6Im9yZGVyRGV0YWlsIn0seyJhdXRob3JpdHkiOiJnZXRVc2VyTGlzdCJ9LHsiYXV0aG9yaXR5IjoiZ2V0Um9sZUxpc3QifSx7ImF1dGhvcml0eSI6ImdldFVzZXJMaXN0In0seyJhdXRob3JpdHkiOiJnZXRQZXJtaXNzaW9uTGlzdCJ9XSwianRpIjoiMDk4ZTNkNWMtNzYyMS00NmMyLWJjNDYtNmVjM2ZhYTQ5ZmNhIiwiY2xpZW50X2lkIjoiY2xpZW50SWQ0IiwidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.jNYsvQHW7R51miNxQ70bSGmAL0N3TXJHTx3ZhqCodbqQaYwt0Ua2IaUZKwGpRT6JRhtNBXB1vvMyP78lZNjKPatJwAdO-I-sAEHjQ_iuqquqAvOSfget5GR9_GieYbP4B7T3CividCmKhUh-E0sQMAKkzxNtsM49_DJmbkd0naldoil_kq7gPhhnr7J-RKDqSMrfCy767H5v-PJl6LBDqdDDFYGC_aHstk6wlZ_kjZ2RxqLdCuNtHJZ1dPnUaeB-tCpje7GophWbqEpEq_1zbBnLK9NQXly-WJqgb3NucOx-SE3oT-sKphoD7XAiKaj2GYEsq5vXuz15KAnUvtHI2g

1.联合qq登陆;

2.发送腾讯云短信;

3.开发工具eclipse;

4.数据库mysql;

5.jdk1.8;

6.服务器阿里云centos7.2;

7.发送qq邮件,163邮件;

8.nginx反向代理,负载均衡

9.springSecurity安全框架

10.OAuth2.0开放平台

11.jwt token

12.sso单点登陆


1.1 版本
  qq登陆是js qq的sdk是2012年的,如果你用qq浏览器登陆的话,可能第一次不显示,需要qq登陆后在刷新界面才显示,建议使用谷歌浏览器;
  本次版本新增springSecurity安全框架;
  
1.2 版本
  本次新增jwt+oauth+sso+springSecurity的整合
