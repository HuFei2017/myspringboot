#!/bin/bash
# Startup script for a spring boot project
# chkconfig: 2345 80 90
# description: spring boot project
# Source function library.
# if you want make as a boot server on linux ,edit and enable the next line
# cd /usr/local/project_name/bin
cd `dirname $0`

start(){
      ./start.sh
}

stop(){
    ./stop.sh
}

status(){
    ./start.sh status
}

restart(){
      ./stop.sh
      ./start.sh
}



# See how we were called.
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    restart)
        restart
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart|status}"
        exit 1
esac