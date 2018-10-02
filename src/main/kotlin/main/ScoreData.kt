package main

import java.io.*
import java.util.*
import org.apache.hadoop.io.IOUtils
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.io.IOException
import java.io.File
import java.util.HashMap

class ScoreData @Throws(IOException::class) constructor(file: File) {

    private val studentScores = HashMap<Long, Array<Int>>()

    fun getStudentScores(studentId: Long): Array<Int> {
        val scores = studentScores[studentId]
        if (scores != null) {
            return scores
        }
        return arrayOf()
    }

    init {
        var `in`: BufferedReader? = null
        try {
            `in` = BufferedReader(InputStreamReader(FileInputStream(file)))
            var line = `in`.readLine()
            while (line != null) {
                val words = line.split(",")
                val studentId = words.get(0).toLong()
                val score1 = words.get(1).toInt()
                val score2 = words.get(2).toInt()
                val score3 = words.get(3).toInt()
                studentScores.put(studentId, arrayOf(score1, score2, score3))
                line = `in`.readLine()
            }
        } finally {
            IOUtils.closeStream(`in`)
        }
    }

}