package main

import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.WritableUtils
import java.io.IOException
import java.io.DataOutput
import java.io.DataInput
import org.apache.hadoop.io.Writable
import org.apache.hadoop.io.WritableComparable

class StudentIdKey : WritableComparable<StudentIdKey> {

    internal var studentId : Long
    internal var type : String

    constructor() {
        this.studentId = 0
        this.type = "undefined"
    }

    constructor(id : Long, t : String) {
        this.studentId = id
        this.type = t
    }

    /**
     * Compares only the student-ids, this will group the same student-ids together
     * so that reducers can then merge the scores and students together
     */
    override fun compareTo(other: StudentIdKey): Int {
        return this.studentId.compareTo(other.studentId)
    }

    override fun readFields(`in`: DataInput?) {
//        this.studentId.readFields(`in`)
        this.studentId = WritableUtils.readVLong(`in`)
        this.type = WritableUtils.readString(`in`)
    }

    override fun write(out: DataOutput?) {
//        this.studentId.write(out)
        WritableUtils.writeVLong(out, this.studentId.toLong())
        WritableUtils.writeString(out, this.type.toString())
    }

    override fun toString() : String {
        return this.studentId.toString() + " " + this.type
    }
}