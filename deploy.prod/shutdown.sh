target=$1
if [ "z$target" != "z" ]; then
ssh m2m@$target "export JAVA_HOME=/usr/local/jdk1.8.0_121 && sh /data/rest/tomcat/bin/shutdown.sh"
fi