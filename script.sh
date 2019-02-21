#!/usr/bin/env sh

echo "sleep 20"
sleep 20
set -e

echo "create database if not exists base_activiti;\
      GRANT ALL ON base_activiti.* TO 'user' IDENTIFIED BY 'user';\
" | mysql -u root -h mydb

echo "sleep 10"
sleep 10
java -jar springbootactiviti-1.0.jar