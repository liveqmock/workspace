#!/bin/bash
cd `dirname $0`/;
#应用目录
APP_HOME=$PWD
cd - > /dev/null

#应用名称，用于检测进程，必须保证服务器唯一
APP_NAME=baseWxTask

#虚拟机入口类
MAIN_CLASS=com.yazuo.TaskMain

#通过APP_NAME取得应用进程pid
pid=`ps aux|grep "Dapp_name=$APP_NAME"|grep -v grep|awk '{print $2}'`

#取得应用进程数量（0未启动、1起动，大于1说明程序重复起动）
pid_count=`echo $pid|wc -w`
