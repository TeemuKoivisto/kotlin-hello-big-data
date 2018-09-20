//@file:JvmName("Main")

package main

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.util.ToolRunner
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val result = ToolRunner.run(Configuration(), DriverBigData(), args)
    exitProcess(result)
}