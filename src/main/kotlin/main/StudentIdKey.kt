package main

import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.WritableUtils
import java.io.IOException
import java.io.DataOutput
import java.io.DataInput
import org.apache.hadoop.io.Writable
import org.apache.hadoop.io.WritableComparable


class StudentIdKey : WritableComparable<StudentIdKey> {

    internal var studentId : LongWritable
    internal var type : String

    constructor() {
        this.studentId = LongWritable(0)
        this.type = "undefined"
    }

    constructor(id : LongWritable, t : String) {
        this.studentId = id
        this.type = t
    }

    override fun compareTo(other: StudentIdKey): Int {
        val idCompare = this.studentId.compareTo(other.studentId)
        if (idCompare == 0) {
            if (this.type == "student") {
                return 1
            }
            return -1
        }
        return idCompare
    }

    override fun readFields(`in`: DataInput?) {
        this.studentId.readFields(`in`)
        this.type = WritableUtils.readString(`in`);
    }

    override fun write(out: DataOutput?) {
        this.studentId.write(out)
        WritableUtils.writeString(out, this.type)
    }

    override fun toString() : String {
        return this.studentId.toString() + " " + this.type
    }
}