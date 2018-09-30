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
class StudentScoreReducer : Reducer<StudentIdKey, StudentScoreWritable, LongWritable, Text>() {

    override fun reduce(key: StudentIdKey, values: Iterable<StudentScoreWritable>, context: Context) {
        val iter = values.iterator()
        val first = iter.next()
        val merged = StudentScoreWritable(first.name, first.year, first.score1, first.score2, first.score3)
        if (iter.hasNext()) {
            merged.merge(iter.next())
            context.write(LongWritable(key.studentId), merged.toString().toText())
        }
    }
}