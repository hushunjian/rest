BASE_DIR=$(cd -P -- "$(dirname -- "$0")"; pwd -P)
cd $BASE_DIR
    sh shutdown.sh jetty-1
    sh shutdown.sh jetty-2
    sh configure.sh
    sh deploy.sh
    sh rsync.sh    jetty-1
    sh rsync.sh    jetty-2
    sh startup.sh  jetty-1
    sh startup.sh  jetty-2
cd -
