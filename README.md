# Hadoop WordCount with Kotlin

Okey so this is directly copied from here https://github.com/gmariotti/intro-big-data/tree/master/01_hello-big-data. Big thanks to Guido Pio Mariotti who has much more material and explanations in his blog https://thegmariottiblog.blogspot.com/2017/02/hello-blog-world-learning-big-data-with.html.

## How to run this

Well... to be honest building this sucks balls and nothing has worked without problems.

To express this poetically; Gradle is a blight upon humanity and Hadoop an alien technology not meant to be used by humans.

But here it goes:

1) Install IntelliJ IDEA & Java.
2) Clone this repo.
3) You can build this from IDEA or using command line: `gradlew fatJar` which will build the JAR with Hadoop & Kotlin as dependencies. Building the regular jar won't include Kotlin which is why this 	inconvenience.
4) Yey now you have your JAR-file. Now you only need to run it with Hadoop. Internet is full of Hadoop installation guides so go ahead and pick one. Good luck because you're gonna need it. I'm using Hadoop 2.8.0 to compile this since it was used in one tutorial and the Hadoop cluster I'm using has 2.7.3 and they are (I think) compatible. If you want to use Kubernetes to run your Hadoop cluster [here's my instructions](https://gist.github.com/TeemuKoivisto/5632fabee4915dc63055e8e544247f60). It's not that bad, I guess. You're just going need to do some copying if you want to upload data to your volumes. Which you'd do either way with local HDFS so it's kinda the same! Kubernetes has its own problems although following that guide it will probably take you 20-30 minutes to install Hadoop compared to the 2-3 hours I used to install it on Windows.
5) Now you have your Hadoop installed. Create some test data for your program and then run it:
```bash
mkdir input
echo Hello World Bye World > input/file01
echo Hello Hadoop Goodbye Hadoop > input/file02
/usr/local/hadoop/bin/hadoop fs -put input / # Put the data into the HDFS drive
/usr/local/hadoop/bin/hadoop jar kotlin-hello-big-data-all-1.0-SNAPSHOT.jar 1 /input /output
```
That's the command I used with my Kubernetes cluster. First you of course have to copy the JAR and then exec into the container.

6) If all went well running
```bash
/usr/local/hadoop/bin/hadoop fs -cat /output/part-r-00000
```
should yield:
```
bye     1
goodbye 1
hadoop  2
hello   2
world   2
```
7) Have fun! Kotlin I feel is much better language than Java but keep in mind that most of the material in internet is probably in Java so you're going to either rewrite it or use IDEA's built-in capabilities to transform it into Kotlin.

## Helper scripts

* `./create-test-data.sh` will generate `/input` folder to the HDFS drive inside Kubernetes
* `./run-on-k8s.sh <?build>` will copy the built JAR-file and execute it inside the Hadoop-cluster. You can provide an optional build parameter: `./run-on-k8s.sh build` that will also build the JAR-file.
