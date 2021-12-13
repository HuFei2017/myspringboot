#!/bin/bash
SERVER_NAME='Demo-1.0.0'
JAR_NAME=$SERVER_NAME'.jar'
# 获取应用的端口号
SERVER_PORT=`sed '/^server.port=/!d;s/.*=//' ../config/application.properties`
SERVER_PORT=`echo $SERVER_PORT | grep -Eo "[0-9]*"`
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
  echo "ERROR: The $SERVER_NAME already started!"
  echo "PID: $PIDS"
  exit 1
fi
if [ -n "$SERVER_PORT" ]; then
  SERVER_PORT_COUNT=`netstat -nltp | grep $SERVER_PORT | wc -l`
  if [ $SERVER_PORT_COUNT -gt 0 ]; then
    echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
    exit 1
  fi
fi
LOGS_DIR='../logs'
if [ ! -d $LOGS_DIR ]; then
  mkdir $LOGS_DIR
  if [ $SERVER_PORT_COUNT -gt 0 ]; then
    echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
    exit 1
  fi
fi
CONFIG_FILES="-Dspring.config.location=../config/ "
STATIC_DIR="-Dspring.resources.static-location=file:../static/ "
echo -e "Starting the $SERVER_NAME ..."
nohup java -jar $CONFIG_FILES $STATIC_DIR ../lib/$JAR_NAME >/dev/null 2>&1 &
COUNT=0
TIMEOUT=0
while [ $COUNT -lt 1 ]; do
  if [ -n "$SERVER_PORT" ]; then
    COUNT=`netstat -nltp | grep $SERVER_PORT | wc -l`
  else
    COUNT=`ps -ef | grep java | grep $JAR_NAME |awk '{print $2}' | wc -l`
  fi
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