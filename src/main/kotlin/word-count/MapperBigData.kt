package `word-count`

import extensions.*
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper
import java.util.regex.Pattern


/**
 * Represents the Mapper class used for this example.
 * The generic types correspond to <InputKey, InputValue, OutputKey, OutputValue>
 */
class MapperBigData : Mapper<LongWritable, Text, Text, IntWritable>() {
    private val one = 1.toIntWritable()

    override fun map(key: LongWritable, value: Text, context: Context) {
        // Splits each sentence in a list of words.
//        val p = Pattern.compile("the\\W+[a-zA-Z]+")
//        val m = p.matcher(value.toString())
//        var lastMatchPos = 0
//        while (m.find()) {
//            val word = m.group(0).split(Pattern.compile("the\\W+"))[1] // First value is empty -> "", second the actual word
//            context.write(word.trim().toLowerCase().toText(), one)
//        }
        val words = value.split(Pattern.compile("\\W+"))
        words.map(String::toLowerCase)
                .forEach { context.write(it.toText(), one) }
    }
}