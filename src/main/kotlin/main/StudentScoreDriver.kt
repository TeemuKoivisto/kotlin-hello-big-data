package main

import extensions.*
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat
import org.apache.hadoop.util.Tool

/**
 * Hello World for Big Data with Hadoop
 */

class StudentScoreDriver : Configured(), Tool {
    override fun run(args: Array<out String>): Int {
        // parsing of the input parameters
        if (args.size != 4) {
            error("Expected <#reducers> <score.txt_path> <student.txt_path> <output_path>, but received ${args.size} elements.")
        }
        val numReducers = args[0].toInt()
        val scorePath = Path(args[1])
        val studentPath = Path(args[2])
        val outputPath = Path(args[3])

        // Defines a new job
        with(Job.getInstance(this.conf)) {
            jobName = "Big Data - Hello World"

            addMultipleInputPath<TextInputFormat, ScoreMapper>(scorePath)
            mapOutput<LongWritable, StudentScoreWritable>()
            addMultipleInputPath<TextInputFormat, StudentMapper>(studentPath)
            mapOutput<LongWritable, StudentScoreWritable>()

            // Specifies the class of the Driver for this job
            setJarByClass<StudentScoreDriver>()

            // Specifies the job's format for input and output
            setInputFormatClass<TextInputFormat>()
//            setOutputFormatClass<TextOutputFormat<Text, IntWritable>>()
//            setOutputFormatClass<TextOutputFormat<LongWritable, Text>>()

            // Specifies the reducer class and its key:value output
            setReducerClass<StudentScoreReducer>(numReducers)
//            reducerOutput<Text, IntWritable>()
            setOutputFormatClass<TextOutputFormat<LongWritable, Text>>()

            addOutputPath(outputPath)

            return if (waitForCompletion(true)) 0 else 1
        }
    }
}