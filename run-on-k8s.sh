#!/bin/bash -e

BUILD=$1

if [ "$BUILD" = "build" ]; then
  ./gradlew fatJar
fi

POD_NAME=$(kubectl get pods | grep yarn-nm | awk '{print $1}')

kubectl cp build/libs/kotlin-hello-big-data-all-1.0-SNAPSHOT.jar "${POD_NAME}":/home

TIMESTAMP=$(date +"%H_%M_%S__%d_%m_%Y")

kubectl exec -it "${POD_NAME}" bash <<EOF
cd /home
hadoop jar kotlin-hello-big-data-all-1.0-SNAPSHOT.jar 1 /input /$TIMESTAMP
echo "Results:"
echo ""
hadoop fs -cat /$TIMESTAMP/part-r-00000
EOF
