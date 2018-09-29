package main

import extensions.toText
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer

/**
 * Joins key with Score and Student values into single row:
 * (20096468734, ["score: 90,80,40", "student:Testi9,1995"])
 * -> "20096468734,Testi9,1995,90,80,40"
 */
class StudentScoreReducer : Reducer<LongWritable, StudentScoreWritable, LongWritable, Text>() {

    override fun reduce(key: LongWritable, values: Iterable<StudentScoreWritable>, context: Context) {
//        if (values.count() == 2) {
//            val student = if (values.first().isStudent()) values.first() else values.last()
//            val score = if (!values.first().isStudent()) values.first() else values.last()
//            val text = student.name + student.year + score.score1 + score.score2 + score.score3
//            context.write(key, text.toText())
//        }
        for (ssw in values.toList()) {
            context.write(key, ssw.toString().toText())
        }
    }
}
