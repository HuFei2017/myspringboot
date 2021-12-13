#!/bin/bash
SERVER_NAME='Demo-1.0.0'
JAR_NAME=$SERVER_NAME'.jar'
PIDS=`ps -ef | grep java | grep $JAR_NAME |awk '{print $2}'`
if [ -z "$PIDS" ]; then
  echo "ERROR: The $SERVER_NAME does not started!"
  exit 1
fi
echo -e "Stopping the $SERVER_NAME ..."
for PID in $PIDS ; do
  COUNT=0
  TIMEOUT=1
  while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    kill -9 $PID >/dev/null 2>&1
    sleep 1
    COUNT=1
    PID_EXIST=`ps -ef | grep java | grep $PID`
    if [ -n "$PID_EXIST" ]; then
      COUNT=0
    else
      break
    fi
    if [ $TIMEOUT -ge 10 ]; then
      echo ""
      echo "ERROR: The $SERVER_NAME pid $PID stop failed, please check manually!"
      exit 1
    fi
    ((TIMEOUT++))
  done
done
echo ""
echo "All pids killed finished!"
echo "PID: $PIDS"