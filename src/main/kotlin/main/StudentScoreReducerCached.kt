package main

import extensions.toText
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import java.io.File

/**
 * Joins key with Score and Student values into single row:
 * (20096468734, ["score: 90,80,40", "student:Testi9,1995"])
 * -> "20096468734,Testi9,1995,90,80,40"
 */
class StudentScoreReducerCached : Reducer<StudentIdKey, StudentScoreWritable, LongWritable, Text>() {

    private var scoreData : ScoreData? = null

    /**
     * Merges the students and scores together
     */
    override fun reduce(key: StudentIdKey, values: Iterable<StudentScoreWritable>, context: Context) {
        val iter = values.iterator()
        val student = iter.next()
        val score = scoreData?.getStudentScores(key.studentId)
        if (score != null) {
            student.addScores(score)
            context.write(LongWritable(key.studentId), student.toString().toText())
        }
    }

    override fun setup(context: Context?) {
        super.setup(context)
        val cacheFile = context?.cacheFiles?.get(0)
        this.scoreData = ScoreData(File(cacheFile))
    }
}