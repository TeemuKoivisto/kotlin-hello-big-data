#!/bin/bash -e

LIMITS=$1
HELM_HADOOP_NAME=$(helm list | grep hadoop | awk '{print $1}')

if [ -z "$LIMITS" ]; then
  echo "You must give LIMITS as first parameter that is the limits you want to use: high, low or double"
  exit 1
fi

if [ "$LIMITS" = "high" ]; then
    helm upgrade $HELM_HADOOP_NAME \
       --set yarn.nodeManager.resources.limits.memory=8Gi \
       --set yarn.nodeManager.resources.limits.cpu=4 \
       --set yarn.nodeManager.replicas=1 \
       stable/hadoop
fi

if [ "$LIMITS" = "low" ]; then
    helm upgrade $HELM_HADOOP_NAME \
       --set yarn.nodeManager.resources.limits.memory=4Gi \
       --set yarn.nodeManager.resources.limits.cpu=1 \
       --set yarn.nodeManager.replicas=1 \
       stable/hadoop
fi

if [ "$LIMITS" = "double" ]; then
    helm upgrade $HELM_HADOOP_NAME \
       --set yarn.nodeManager.resources.limits.memory=4Gi \
       --set yarn.nodeManager.resources.limits.cpu=2 \
       --set yarn.nodeManager.replicas=2 \
       stable/hadoop
fi

echo "NOTE: for some reason the yarn-nm pods won't update automatically, delete them to restart"