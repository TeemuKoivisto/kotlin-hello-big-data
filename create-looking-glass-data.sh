#!/bin/bash -xe

POD_NAME=$(kubectl get pods | grep yarn-nm | awk '{print $1}')

kubectl cp through-the-looking-glass.txt "${POD_NAME}":/home

kubectl exec -it "${POD_NAME}" bash <<EOF
cd /home
mkdir input
mv through-the-looking-glass.txt ./input
hadoop fs -rm -r /input # Empty the input directory first
hadoop fs -put input / # Put the data into the HDFS drive to path /input
echo "Created looking glass data"
EOF
