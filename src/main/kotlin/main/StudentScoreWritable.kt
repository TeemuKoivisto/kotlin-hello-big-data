package main

import org.apache.hadoop.io.Writable
import org.apache.hadoop.io.WritableUtils
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException

class StudentScoreWritable : Writable {

    internal var name = ""
    internal var year = 0
    internal var score1 = 0
    internal var score2 = 0
    internal var score3 = 0

    constructor() {}

    constructor(n: String, y: Int) {
        this.name = n
        this.year = y
    }

    constructor(s1: Int, s2: Int, s3: Int) {
        this.score1 = s1
        this.score2 = s2
        this.score3 = s3
    }

    fun isStudent() : Boolean {
        return this.year != 0
    }

    @Throws(IOException::class)
    override fun readFields(`in`: DataInput) {
        name = WritableUtils.readString(`in`);
        year = `in`.readInt()
        score1 = `in`.readInt()
        score2 = `in`.readInt()
        score3 = `in`.readInt()
    }

    @Throws(IOException::class)
    override fun write(out: DataOutput) {
        WritableUtils.writeString(out, name)
        out.writeInt(year)
        out.writeInt(score1)
        out.writeInt(score2)
        out.writeInt(score3)
    }

    override fun toString(): String {
        return this.name + "\t" + this.year + "\t" + this.score1.toString() + "\t" + this.score2 + "\t" + this.score3
    }
}