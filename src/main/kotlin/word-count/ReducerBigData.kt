package `word-count`

import extensions.*
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import java.util.HashMap

/**
 * Represents the Reducer class used for this example.
 * The generic types correspond to <InputKey, InputValue, OutputKey, OutputValue>
 */
class ReducerBigData : Reducer<Text, IntWritable, Text, IntWritable>() {

    private val countMap = HashMap<Text, Int>()

    override fun reduce(key: Text, values: Iterable<IntWritable>, context: Context) {
        // For each word returns the number of times it appeared in the input text.
//        countMap.put(key, values.sumBy(IntWritable::get).toIntWritable())
        context.write(key, values.sumBy(IntWritable::get).toIntWritable())
//        val oldVal = countMap.get(key)
//        if (oldVal != null) {
//            countMap.put(key, oldVal + 1)
//        } else {
//            countMap.put(key, 1)
//        }
    }

//    override fun cleanup(context: Context?) {
//
//        val sorted = countMap.toSortedMap()
//        var count = 0
//
//        for (word in sorted.keys.toList()) {
//            if (count == 10) break
//            val value = countMap.getValue(word)
//            context?.write(word, value.toIntWritable())
//            count += 1
//        }
//    }
}