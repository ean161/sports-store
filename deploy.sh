#!/bin/bash
SSH_USER="root"
SSH_HOST="160.30.44.158"
SSH_PORT=22

REMOTE_DIR="/www/wwwroot/sports-store.ean.vn"
REMOTE_FILE="$REMOTE_DIR/sports-store-0.0.1-SNAPSHOT.jar"
LOCAL_FILE="./target/sports-store-0.0.1-SNAPSHOT.jar"

ssh -p $SSH_PORT $SSH_USER@$SSH_HOST "pkill -f 'java -jar sports-store-0.0.1-SNAPSHOT.jar' || true"
ssh -p $SSH_PORT $SSH_USER@$SSH_HOST "rm -f $REMOTE_FILE"
scp -P $SSH_PORT $LOCAL_FILE $SSH_USER@$SSH_HOST:$REMOTE_DIR/
ssh -p $SSH_PORT $SSH_USER@$SSH_HOST "cd $REMOTE_DIR && java -jar sports-store-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &"