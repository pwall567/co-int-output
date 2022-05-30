package net.pwall.util

import net.pwall.util.IntOutput.MIN_INTEGER_STRING
import net.pwall.util.IntOutput.MIN_LONG_STRING
import net.pwall.util.IntOutput.digits
import net.pwall.util.IntOutput.tensDigits

object CoIntOutput {

    suspend fun coOutputInt(i: Int, outFunction: suspend (Int) -> Unit) { // TODO try switch to Char
        if (i < 0) {
            if (i == Int.MIN_VALUE)
                coOutputString(MIN_INTEGER_STRING, outFunction)
            else {
                outFunction('-'.code)
                coOutputPositiveInt(-i, outFunction)
            }
        }
        else
            coOutputPositiveInt(i, outFunction)
    }

    suspend fun coOutputPositiveInt(i: Int, outFunction: suspend (Int) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveInt(n, outFunction)
                coOutput2Digits(i - n * 100, outFunction)
            }
            i >= 10 -> coOutput2Digits(i, outFunction)
            else -> outFunction(digits[i].code)
        }
    }

    suspend fun coOutputUnsignedInt(i: Int, outFunction: suspend (Int) -> Unit) {
        if (i >= 0)
            coOutputPositiveInt(i, outFunction)
        else {
            val n = (i ushr 1) / 50
            coOutputPositiveInt(n, outFunction)
            coOutput2Digits(i - n * 100, outFunction)
        }
    }

    suspend fun coOutputLong(n: Long, outFunction: suspend (Int) -> Unit) {
        if (n < 0) {
            if (n == Long.MIN_VALUE)
                coOutputString(MIN_LONG_STRING, outFunction)
            else {
                outFunction('-'.code)
                coOutputPositiveLong(-n, outFunction)
            }
        }
        else
            coOutputPositiveLong(n, outFunction)
    }

    suspend fun coOutputPositiveLong(n: Long, outFunction: suspend (Int) -> Unit) {
        when {
            n >= 100 -> {
                val m = n / 100
                coOutputPositiveLong(m, outFunction)
                coOutput2Digits((n - m * 100).toInt(), outFunction)
            }
            n >= 10 -> coOutput2Digits(n.toInt(), outFunction)
            else -> outFunction(digits[n.toInt()].code)
        }
    }

    suspend fun coOutputUnsignedLong(n: Long, outFunction: suspend (Int) -> Unit) {
        if (n >= 0)
            coOutputPositiveLong(n, outFunction)
        else {
            val m = (n ushr 1) / 50
            coOutputPositiveLong(m, outFunction)
            coOutput2Digits((n - m * 100).toInt(), outFunction)
        }
    }

    suspend fun coOutput2Digits(i: Int, outFunction: suspend (Int) -> Unit) {
        outFunction(tensDigits[i].code)
        outFunction(digits[i].code)
    }

    suspend fun coOutput3Digits(i: Int, outFunction: suspend (Int) -> Unit) {
        val n = i / 100
        outFunction(digits[n].code)
        coOutput2Digits(i - n * 100, outFunction)
    }

    suspend fun coOutputIntGrouped(i: Int, groupingChar: Char = ',', outFunction: suspend (Int) -> Unit) {
        if (i < 0) {
            if (i == Int.MIN_VALUE) {
                coOutputString(MIN_INTEGER_STRING, 0, 2, outFunction)
                outFunction(groupingChar.code)
                coOutputString(MIN_INTEGER_STRING, 2, 5, outFunction)
                outFunction(groupingChar.code)
                coOutputString(MIN_INTEGER_STRING, 5, 8, outFunction)
                outFunction(groupingChar.code)
                coOutputString(MIN_INTEGER_STRING, 8, 11, outFunction)
            }
            else {
                outFunction('-'.code)
                coOutputPositiveIntGrouped(-i, groupingChar, outFunction)
            }
        }
        else
            coOutputPositiveIntGrouped(i, groupingChar, outFunction)
    }

    suspend fun coOutputPositiveIntGrouped(i: Int, groupingChar: Char = ',', outFunction: suspend (Int) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveIntGrouped1(n, groupingChar, outFunction)
                coOutput2Digits(i - n * 100, outFunction)
            }
            i >= 10 -> coOutput2Digits(i, outFunction)
            else -> outFunction(digits[i].code)
        }
    }

    private suspend fun coOutputPositiveIntGrouped1(i: Int, groupingChar: Char, outFunction: suspend (Int) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveIntGrouped2(n, groupingChar, outFunction)
                val m = i - n * 100
                outFunction(tensDigits[m].code)
                outFunction(groupingChar.code)
                outFunction(digits[m].code)
            }
            i >= 10 -> {
                outFunction(tensDigits[i].code)
                outFunction(groupingChar.code)
                outFunction(digits[i].code)
            }
            else -> outFunction(digits[i].code)
        }
    }

    private suspend fun coOutputPositiveIntGrouped2(i: Int, groupingChar: Char, outFunction: suspend (Int) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveIntGrouped(n, groupingChar, outFunction)
                outFunction(groupingChar.code)
                coOutput2Digits(i - n * 100, outFunction)
            }
            i >= 10 -> coOutput2Digits(i, outFunction)
            else -> outFunction(digits[i].code)
        }
    }

    suspend fun coOutputString(s: String, start: Int, end: Int, outFunction: suspend (Int) -> Unit) {
        for (i in start until end)
            outFunction(s[i].code)
    }

    suspend fun coOutputString(s: String, outFunction: suspend (Int) -> Unit) {
        coOutputString(s, 0, s.length, outFunction)
    }

}
