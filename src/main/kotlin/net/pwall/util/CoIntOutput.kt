/*
 * @(#) CoIntOutput.java
 *
 * co-int-output  Non-blocking integer output functions
 * Copyright (c) 2022 Peter Wall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.pwall.util

import net.pwall.util.IntOutput.MIN_INTEGER_STRING
import net.pwall.util.IntOutput.MIN_LONG_STRING
import net.pwall.util.IntOutput.digits
import net.pwall.util.IntOutput.digitsHex
import net.pwall.util.IntOutput.digitsHexLC
import net.pwall.util.IntOutput.tensDigits

/**
 * Non-blocking functions used in the conversion of integer values to string representations.  The functions all output
 * digits left-to-right, avoiding the need to allocate a separate object to hold the string representation.
 *
 * Each of the functions takes a `suspend` function as its last parameter; this will be used to perform the actual
 * output.
 *
 * @author  Peter Wall
 */
object CoIntOutput {

    /**
     * Output an `Int` left-trimmed.
     */
    suspend fun coOutputInt(i: Int, outFunction: suspend (Char) -> Unit) {
        when {
            i >= 0 -> coOutputPositiveInt(i, outFunction)
            i == Int.MIN_VALUE -> coOutputString(MIN_INTEGER_STRING, outFunction)
            else -> {
                outFunction('-')
                coOutputPositiveInt(-i, outFunction)
            }
        }
    }

    /**
     * Output a positive `Int` left-trimmed.
     */
    suspend fun coOutputPositiveInt(i: Int, outFunction: suspend (Char) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveInt(n, outFunction)
                coOutput2Digits(i - n * 100, outFunction)
            }
            i >= 10 -> coOutput2Digits(i, outFunction)
            else -> outFunction(digits[i])
        }
    }

    /**
     * Output an unsigned `Int` left-trimmed.
     */
    suspend fun coOutputUnsignedInt(i: Int, outFunction: suspend (Char) -> Unit) {
        if (i >= 0)
            coOutputPositiveInt(i, outFunction)
        else {
            val n = (i ushr 1) / 50
            coOutputPositiveInt(n, outFunction)
            coOutput2Digits(i - n * 100, outFunction)
        }
    }

    /**
     * Output a `Long` left-trimmed.
     */
    suspend fun coOutputLong(n: Long, outFunction: suspend (Char) -> Unit) {
        when {
            n >= 0 -> coOutputPositiveLong(n, outFunction)
            n == Long.MIN_VALUE -> coOutputString(MIN_LONG_STRING, outFunction)
            else -> {
                outFunction('-')
                coOutputPositiveLong(-n, outFunction)
            }
        }
    }

    /**
     * Output a positive `Long` left-trimmed.
     */
    suspend fun coOutputPositiveLong(n: Long, outFunction: suspend (Char) -> Unit) {
        when {
            n >= 100 -> {
                val m = n / 100
                coOutputPositiveLong(m, outFunction)
                coOutput2Digits((n - m * 100).toInt(), outFunction)
            }
            n >= 10 -> coOutput2Digits(n.toInt(), outFunction)
            else -> outFunction(digits[n.toInt()])
        }
    }

    /**
     * Output an unsigned `Long` left-trimmed.
     */
    suspend fun coOutputUnsignedLong(n: Long, outFunction: suspend (Char) -> Unit) {
        if (n >= 0)
            coOutputPositiveLong(n, outFunction)
        else {
            val m = (n ushr 1) / 50
            coOutputPositiveLong(m, outFunction)
            coOutput2Digits((n - m * 100).toInt(), outFunction)
        }
    }

    /**
     * Output an `Int` as two decimal digits.
     */
    suspend fun coOutput2Digits(i: Int, outFunction: suspend (Char) -> Unit) {
        outFunction(tensDigits[i])
        outFunction(digits[i])
    }

    /**
     * Output an `Int` as three decimal digits.
     */
    suspend fun coOutput3Digits(i: Int, outFunction: suspend (Char) -> Unit) {
        val n = i / 100
        outFunction(digits[n])
        coOutput2Digits(i - n * 100, outFunction)
    }

    /**
     * Output an `Int` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputIntGrouped(i: Int, groupingChar: Char = ',', outFunction: suspend (Char) -> Unit) {
        when {
            i >= 0 -> coOutputPositiveIntGrouped(i, groupingChar, outFunction)
            i == Int.MIN_VALUE -> {
                coOutputString(MIN_INTEGER_STRING, 0, 2, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_INTEGER_STRING, 2, 5, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_INTEGER_STRING, 5, 8, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_INTEGER_STRING, 8, 11, outFunction)
            }
            else -> {
                outFunction('-')
                coOutputPositiveIntGrouped(-i, groupingChar, outFunction)
            }
        }
    }

    /**
     * Output a positive `Int` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputPositiveIntGrouped(i: Int, groupingChar: Char = ',', outFunction: suspend (Char) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveIntGrouped1(n, groupingChar, outFunction)
                coOutput2Digits(i - n * 100, outFunction)
            }
            i >= 10 -> coOutput2Digits(i, outFunction)
            else -> outFunction(digits[i])
        }
    }

    private suspend fun coOutputPositiveIntGrouped1(i: Int, groupingChar: Char, outFunction: suspend (Char) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveIntGrouped2(n, groupingChar, outFunction)
                val m = i - n * 100
                outFunction(tensDigits[m])
                outFunction(groupingChar)
                outFunction(digits[m])
            }
            i >= 10 -> {
                outFunction(tensDigits[i])
                outFunction(groupingChar)
                outFunction(digits[i])
            }
            else -> outFunction(digits[i])
        }
    }

    private suspend fun coOutputPositiveIntGrouped2(i: Int, groupingChar: Char, outFunction: suspend (Char) -> Unit) {
        when {
            i >= 100 -> {
                val n = i / 100
                coOutputPositiveIntGrouped(n, groupingChar, outFunction)
                outFunction(groupingChar)
                coOutput2Digits(i - n * 100, outFunction)
            }
            i >= 10 -> coOutput2Digits(i, outFunction)
            else -> outFunction(digits[i])
        }
    }

    /**
     * Output a `Long` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputLongGrouped(n: Long, groupingChar: Char = ',', outFunction: suspend (Char) -> Unit) {
        when {
            n >= 0 -> coOutputPositiveLongGrouped(n, groupingChar, outFunction)
            n == Long.MIN_VALUE -> {
                coOutputString(MIN_LONG_STRING, 0, 2, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_LONG_STRING, 2, 5, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_LONG_STRING, 5, 8, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_LONG_STRING, 8, 11, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_LONG_STRING, 11, 14, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_LONG_STRING, 14, 17, outFunction)
                outFunction(groupingChar)
                coOutputString(MIN_LONG_STRING, 17, 20, outFunction)
            }
            else -> {
                outFunction('-')
                coOutputPositiveLongGrouped(-n, groupingChar, outFunction)
            }
        }
    }

    /**
     * Output a positive `Long` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputPositiveLongGrouped(n: Long, groupingChar: Char = ',', outFunction: suspend (Char) -> Unit) {
        when {
            n >= 100 -> {
                val m = n / 100
                coOutputPositiveLongGrouped1(m, groupingChar, outFunction)
                coOutput2Digits((n - m * 100).toInt(), outFunction)
            }
            n >= 10 -> coOutput2Digits(n.toInt(), outFunction)
            else -> outFunction(digits[n.toInt()])
        }
    }

    private suspend fun coOutputPositiveLongGrouped1(n: Long, groupingChar: Char, outFunction: suspend (Char) -> Unit) {
        when {
            n >= 100 -> {
                val m = n / 100
                coOutputPositiveLongGrouped2(m, groupingChar, outFunction)
                val k = (n - m * 100).toInt()
                outFunction(tensDigits[k])
                outFunction(groupingChar)
                outFunction(digits[k])
            }
            n >= 10 -> {
                outFunction(tensDigits[n.toInt()])
                outFunction(groupingChar)
                outFunction(digits[n.toInt()])
            }
            else -> outFunction(digits[n.toInt()])
        }
    }

    private suspend fun coOutputPositiveLongGrouped2(n: Long, groupingChar: Char, outFunction: suspend (Char) -> Unit) {
        when {
            n >= 100 -> {
                val m = n / 100
                coOutputPositiveLongGrouped(m, groupingChar, outFunction)
                outFunction(groupingChar)
                coOutput2Digits((n - m * 100).toInt(), outFunction)
            }
            n >= 10 -> coOutput2Digits(n.toInt(), outFunction)
            else -> outFunction(digits[n.toInt()])
        }
    }

    /**
     * Output an `Int` left-trimmed in hexadecimal.
     */
    suspend fun coOutputIntHex(i: Int, outFunction: suspend (Char) -> Unit) {
        if ((i and 0xFFFF.inv()) != 0) {
            coOutput16BitsHex(i ushr 16, outFunction)
            coOutput4Hex(i, outFunction)
        }
        else
            coOutput16BitsHex(i, outFunction)
    }

    private suspend fun coOutput8BitsHex(i: Int, outFunction: suspend (Char) -> Unit) {
        if ((i and 0xF.inv()) != 0)
            outFunction(digitsHex[i ushr 4])
        outFunction(digitsHex[i and 0xF])
    }

    private suspend fun coOutput16BitsHex(i: Int, outFunction: suspend (Char) -> Unit) {
        if ((i and 0xFF.inv()) != 0) {
            coOutput8BitsHex(i ushr 8, outFunction)
            coOutput2Hex(i, outFunction)
        }
        else
            coOutput8BitsHex(i, outFunction)
    }

    /**
     * Output an `Int` left-trimmed in hexadecimal, using lower-case for the alphabetic characters.
     */
    suspend fun coOutputIntHexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        if ((i and 0xFFFF.inv()) != 0) {
            coOutput16BitsHexLC(i ushr 16, outFunction)
            coOutput4HexLC(i, outFunction)
        }
        else
            coOutput16BitsHexLC(i, outFunction)
    }

    private suspend fun coOutput8BitsHexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        if ((i and 0xF.inv()) != 0)
            outFunction(digitsHexLC[i ushr 4])
        outFunction(digitsHexLC[i and 0xF])
    }

    private suspend fun coOutput16BitsHexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        if ((i and 0xFF.inv()) != 0) {
            coOutput8BitsHexLC(i ushr 8, outFunction)
            coOutput2HexLC(i, outFunction)
        }
        else
            coOutput8BitsHexLC(i, outFunction)
    }

    /**
     * Output a `Long` left-trimmed in hexadecimal.
     */
    suspend fun coOutputLongHex(n: Long, outFunction: suspend (Char) -> Unit) {
        val hi = (n ushr 32).toInt()
        val lo = n.toInt()
        if (hi != 0) {
            coOutputIntHex(hi, outFunction)
            coOutput8Hex(lo, outFunction)
        }
        else
            coOutputIntHex(lo, outFunction)
    }

    /**
     * Output a `Long` left-trimmed in hexadecimal, using lower-case for the alphabetic characters.
     */
    suspend fun coOutputLongHexLC(n: Long, outFunction: suspend (Char) -> Unit) {
        val hi = (n ushr 32).toInt()
        val lo = n.toInt()
        if (hi != 0) {
            coOutputIntHexLC(hi, outFunction)
            coOutput8HexLC(lo, outFunction)
        }
        else
            coOutputIntHexLC(lo, outFunction)
    }

    /**
     * Output an `Int` as eight hexadecimal digits.
     */
    suspend fun coOutput8Hex(i: Int, outFunction: suspend (Char) -> Unit) {
        coOutput4Hex(i ushr 16, outFunction)
        coOutput4Hex(i, outFunction)
    }

    /**
     * Output an `Int` as eight hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput8HexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        coOutput4HexLC(i ushr 16, outFunction)
        coOutput4HexLC(i, outFunction)
    }

    /**
     * Output an `Int` as four hexadecimal digits.
     */
    suspend fun coOutput4Hex(i: Int, outFunction: suspend (Char) -> Unit) {
        coOutput2Hex(i ushr 8, outFunction)
        coOutput2Hex(i, outFunction)
    }

    /**
     * Output an `Int` as four hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput4HexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        coOutput2HexLC(i ushr 8, outFunction)
        coOutput2HexLC(i, outFunction)
    }

    /**
     * Output an `Int` as two hexadecimal digits.
     */
    suspend fun coOutput2Hex(i: Int, outFunction: suspend (Char) -> Unit) {
        outFunction(digitsHex[(i shr 4) and 0xF])
        outFunction(digitsHex[i and 0xF])
    }

    /**
     * Output an `Int` as two hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput2HexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        outFunction(digitsHexLC[(i shr 4) and 0xF])
        outFunction(digitsHexLC[i and 0xF])
    }

    /**
     * Output an `Int` as a single hexadecimal digit.
     */
    suspend fun coOutput1Hex(i: Int, outFunction: suspend (Char) -> Unit) {
        outFunction(digitsHex[i and 0xF])
    }

    /**
     * Output an `Int` as a single hexadecimal digit, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput1HexLC(i: Int, outFunction: suspend (Char) -> Unit) {
        outFunction(digitsHexLC[i and 0xF])
    }

    private suspend fun coOutputString(s: String, start: Int, end: Int, outFunction: suspend (Char) -> Unit) {
        for (i in start until end)
            outFunction(s[i])
    }

    private suspend fun coOutputString(s: String, outFunction: suspend (Char) -> Unit) {
        coOutputString(s, 0, s.length, outFunction)
    }

}
