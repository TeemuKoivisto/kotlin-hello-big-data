#!/bin/bash -e

POD_NAME=$(kubectl get pods | grep yarn-nm | awk '{print $1}')

kubectl exec -it "${POD_NAME}" bash <<EOF
cd /home
mkdir input
echo Hello World Bye World > input/file01
echo Hello Hadoop Goodbye Hadoop > input/file02

hadoop fs -put input / # Put the data into the HDFS drive to path /input
echo "Created test data"
EOF
