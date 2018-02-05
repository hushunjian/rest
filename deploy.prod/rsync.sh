target=$1
if [ "z$target" != "z" ]; then
ssh m2m@$target "mkdir -p /data/rest"
rsync -lurv apache-tomcat-8.0.26 m2m@$target:/data/rest
ssh m2m@$target "cd /data/rest; ln -nsf apache-tomcat-8.0.26 tomcat"
fi
