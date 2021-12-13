#!/bin/bash
SERVER_NAME='Demo-1.0.0'
JAR_NAME=$SERVER_NAME'.jar'
# 获取应用的端口号
PIDS=`ps -ef | grep java | grep $JAR_NAME |awk '{print $2}'`
if [ "$1" = "status" ]; then
  if [ -n "$PIDS" ]; then
    echo "The $SERVER_NAME is running...!"
    echo "PID: $PIDS"
    exit 0
  else
    echo "The $SERVER_NAME is stopped"
    exit 0
  fi
fi
if [ -n "$PIDS" ]; then
  for PID in $PIDS ; do
    COUNT=0
    while [ $COUNT -lt 1 ]; do
      kill -9 $PID >/dev/null 2>&1
      sleep 1
      COUNT=1
      PID_EXIST=`ps -ef | grep java | grep $PID`
      if [ -n "$PID_EXIST" ]; then
        COUNT=0
      else
        break
      fi
    done
  done
fi
echo -e "Starting the $SERVER_NAME ..."
nohup java -jar ../lib/$JAR_NAME >/dev/null 2>&1 &
COUNT=0
TIMEOUT=0
while [ $COUNT -lt 1 ]; do
  COUNT=`ps -ef | grep java | grep $JAR_NAME |awk '{print $2}' | wc -l`
  if [ $COUNT -gt 0 ]; then
    break
  fi
  echo -e ".\c"
  sleep 1
  if [ $TIMEOUT -gt 60 ]; then
    echo ""
    echo "ERROR: The $SERVER_NAME start failed!"
    exit 1
  fi
  ((TIMEOUT++))
done
echo ""
echo "OK!"
PIDS=`ps -ef | grep java | grep $JAR_NAME |awk '{print $2}'`
echo "PID: $PIDS"