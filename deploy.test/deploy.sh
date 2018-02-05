#!/usr/bin/env bash
BASE_DIR=$(cd -P -- "$(dirname -- "$0")" && cd ..; pwd -P)
cd $BASE_DIR
    git pull
    mvn -Dmaven.test.skip=true -Ptest clean package
    if [ -d $BASE_DIR/deploy.test/tomcat/webapps/rest ]; then
        rm -rf $BASE_DIR/deploy.test/tomcat/webapps/rest
    fi
    if [ -d $BASE_DIR/deploy.test/tomcat/webapps/rest.war ]; then
        rm -rf $BASE_DIR/deploy.test/tomcat/webapps/rest.war
    fi
    ls target/rest.war
    \cp target/rest.war $BASE_DIR/deploy.test/tomcat/webapps/rest.war
cd -
