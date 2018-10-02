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

    /**
     * Merges the students and scores together
     */
    override fun reduce(key: StudentIdKey, values: Iterable<StudentScoreWritable>, context: Context) {
        val iter = values.iterator()
        // The first value is either student or score
        val first = iter.next()
        // Create new instance since the iter.next() only returns a pointer so the reference will be gone
        // the next time we will use iter.next(). It's pretty dumb but couldn't figure out a better way
        val merged = StudentScoreWritable(first.name, first.year, first.score1, first.score2, first.score3)
        if (iter.hasNext()) {
            merged.merge(iter.next())
            // Write the merged StudentScore to specific student-id
            context.write(LongWritable(key.studentId), merged.toString().toText())
        }
    }
}