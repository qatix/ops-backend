#!/bin/bash
#sync to other servers,need to set none-password login
rsync -avzPog --delete --exclude 'runtime' /home/pubsrv/javaprojs/opsbackend/ webuser@remote-server:/home/pubsrv/javaprojs/opsbackend/

#login remote server and restart server
ssh webuser@remote-server /bin/sh -s - < /home/pubsrv/javaprojs/opsbackend/server.sh restart

#TODO support multi-servers