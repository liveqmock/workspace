#!/bin/bash
sleep 1
#初始化参数
source /etc/profile
source `dirname $0`/set_env.sh

#验证是否已经启动
if [ $pid_count -ne 0 ]
    then echo "$APP_NAME is running, can't start again"; exit 1;
fi

#设置内存最大值
JAVA_OPTS="-server -Xmx512m -XX:MaxPermSize=128m"
#开启jmx
#JAVA_OPTS="-Dcom.sun.management.jmxremote \
#-Dcom.sun.management.jmxremote.port=60001 \
#-Dcom.sun.management.jmxremote.ssl=false \
#-Dcom.sun.management.jmxremote.authenticate=false \
#$JAVA_OPTS"

#app_name加入起动参数，用于检测进程
JAVA_OPTS="-Dapp_name=$APP_NAME \
$JAVA_OPTS"

#拼凑完整的classpath参数，包括classes及lib目录下所有的jar
CLASSPATH=$APP_HOME/classes
for i in "$APP_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

export CLASSPATH

#起动java进程
cmd=""
echo "starting java $JAVA_OPTS $MAIN_CLASS"
if [ "$1" = "-front" ]
    then java $JAVA_OPTS $MAIN_CLASS
    else nohup java $JAVA_OPTS $MAIN_CLASS > /dev/null &
fi

echo  "starting success"

sleep 1
