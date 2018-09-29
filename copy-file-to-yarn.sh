#!/bin/bash -e

FILE=$1

if [ -z "$FILE" ]; then
  echo "You must give FILE as first parameter eg. through-the-looking-glass.txt"
  exit 1
fi

POD_NAME=$(kubectl get pods | grep yarn-nm | awk '{print $1}')

kubectl cp $FILE "${POD_NAME}":/home

kubectl exec -it "${POD_NAME}" bash