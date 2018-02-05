ssh m2m@jetty-t-1 "mkdir -p /data/rest"
rsync -lurv apache-tomcat-8.0.26 m2m@jetty-t-1:/data/rest
ssh m2m@jetty-t-1 "cd /data/rest; ln -nsf apache-tomcat-8.0.26 tomcat"
