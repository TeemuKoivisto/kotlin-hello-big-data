package main

import extensions.*
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper


/**
 * Represents the Mapper class used for this example.
 * The generic types correspond to <InputKey, InputValue, OutputKey, OutputValue>
 */
class ScoreMapper : Mapper<Any, Text, StudentIdKey, StudentScoreWritable>() {

    override fun map(key: Any, value: Text, context: Context) {
        // Splits the words in the row by comma
        val words = value.toString().split(",")
        val studentId = words.get(0).toLong()
        val score1 = words.get(1).toInt()
        val score2 = words.get(2).toInt()
        val score3 = words.get(3).toInt()
        context.write(StudentIdKey(studentId, "score"), StudentScoreWritable(score1, score2, score3))
    }
}