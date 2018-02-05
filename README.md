### maven
mvn idea:clean -Dmaven.test.skip=true -DsocksProxyHost=127.0.0.1 -DsocksProxyPort=7777
mvn idea:idea -Dmaven.test.skip=true -DsocksProxyHost=127.0.0.1 -DsocksProxyPort=7777
mvn package -Dmaven.test.skip=true -DsocksProxyHost=127.0.0.1 -DsocksProxyPort=7777

#在lb或lb-t上需要建立socket5动态代理接口,用来做邮件转发
ssh -p 22 -N -f -g -D 0.0.0.0:5555 m2m@127.0.0.1

#在lb或lb-t上需要建立HTTP和HTTPS代理服务
systemctl start squid

#由于本项目中有脚本，故需要设置下windows下git的全局变量，以适应脚本换行符问题
git config --global core.autocrlf true

#MySQL死锁例子
https://dev.mysql.com/doc/refman/5.7/en/innodb-deadlock-example.html

#MySQL优化官方文档
https://dev.mysql.com/doc/refman/5.7/en/optimization.html
https://dev.mysql.com/doc/refman/5.7/en/optimize-overview.html
##
https://dev.mysql.com/doc/refman/5.7/en/data-size.html
## Optimizing for BLOB Types
https://dev.mysql.com/doc/refman/5.7/en/optimize-blob.html

