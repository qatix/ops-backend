#!/bin/bash
cd /home/webuser/java/opsbackend
git checkout master && git pull
mvn clean package -Dmaven.test.skip=true
mkdir -p /home/pubsrv/javaprojs/opsbackend
cp /home/webuser/java/opsbackend/admin/target/admin.jar /home/pubsrv/javaprojs/opsbackend/admin.jar
cp /home/webuser/java/opsbackend/bin/server.sh /home/pubsrv/javaprojs/opsbackend/
echo "deploy done,use bin/server.sh to start server"
