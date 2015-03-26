#!/bin/bash
#初始化参数，取得应用pid
source `dirname $0`/set_env.sh

if [ $pid_count -eq 0 ]
    then echo "$APP_NAME is stoped .."; exit 1;
fi

if [ $pid_count -eq 1 ]
    then echo "$APP_NAME is running .."; exit 0;
fi

if [ $pid_count -gt 1 ]
    then echo "$APP_NAME having more than 1 running instance .."; exit $pid_count;
fi
