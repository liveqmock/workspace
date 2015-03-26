#!/bin/bash
#初始化参数，取得应用pid
source `dirname $0`/set_env.sh
#验证是否已经启动
if [ $pid_count -eq 0 ]
    then echo "$APP_NAME is not running, can't stop"; exit 0;
fi

#关闭
kill -15 $pid

#检测进程是否关闭，默认等待10秒
for i in $(seq 10); do
    sleep 1
    source `dirname $0`/set_env.sh
    if [ $pid_count -eq 0 ]
        then echo "stopping $APP_NAME success ($i)"; exit 0;
    fi
done

echo "stopping $APP_NAME failed, please check the thread of $APP_NAME"
exit 1



