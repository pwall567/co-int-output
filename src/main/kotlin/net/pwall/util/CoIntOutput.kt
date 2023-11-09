/*
 * @(#) CoIntOutput.java
 *
 * co-int-output  Non-blocking integer output functions
 * Copyright (c) 2022, 2023 Peter Wall
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

import net.pwall.util.IntOutput.MIN_INTEGER_DIGITS
import net.pwall.util.IntOutput.MIN_LONG_DIGITS
import net.pwall.util.IntOutput.digits
import net.pwall.util.IntOutput.digitsHex
import net.pwall.util.IntOutput.digitsHexLC
import net.pwall.util.IntOutput.tensDigits

/**
 * Non-blocking functions used in the conversion of integer values to string representations.  The functions all output
 * digits left-to-right, avoiding the need to allocate a separate object to hold the string representation.
 *
 * Each of the functions comes in two forms &ndash; one that takes a `suspend` function as its last parameter, and an
 * extension function on the `CoOutput` `typealias`.
 *
 * @author  Peter Wall
 */
object CoIntOutput {

    /**
     * Output an `Int` left-trimmed.
     */
    suspend fun coOutputInt(i: Int, out: CoOutput) = out.outputInt(i)

    /**
     * Output an `Int` left-trimmed.
     */
    suspend fun CoOutput.outputInt(i: Int) {
        if (i < 0) {
            output('-')
            if (i == Int.MIN_VALUE)
                output(MIN_INTEGER_DIGITS)
            else
                outputPositiveInt(-i)
        }
        else
            outputPositiveInt(i)
    }

    /**
     * Output a positive `Int` left-trimmed.
     */
    suspend fun coOutputPositiveInt(i: Int, out: CoOutput) = out.outputPositiveInt(i)

    /**
     * Output a positive `Int` left-trimmed.
     */
    suspend fun CoOutput.outputPositiveInt(i: Int) {
        when {
            i >= 100 -> {
                val n = i / 100
                outputPositiveInt(n)
                output2Digits(i - n * 100)
            }
            i >= 10 -> output2Digits(i)
            else -> output(digits[i])
        }
    }

    /**
     * Output an unsigned `Int` left-trimmed.
     */
    suspend fun coOutputUnsignedInt(i: Int, out: CoOutput) = out.outputUnsignedInt(i)

    /**
     * Output an unsigned `Int` left-trimmed.
     */
    suspend fun CoOutput.outputUnsignedInt(i: Int) {
        if (i >= 0)
            outputPositiveInt(i)
        else {
            val n = (i ushr 1) / 50
            outputPositiveInt(n)
            output2Digits(i - n * 100)
        }
    }

    /**
     * Output an `Int` left-trimmed, using a scale parameter to indicate the number of decimal places.
     *
     * Negative scale values (indicating that decimal point is to the right of the last digit) are ignored.  It is left
     * to the user to decide whether to output additional zeros following the number, or to add an exponent suffix.
     */
    suspend fun coOutputIntScaled(i: Int, scale: Int, separator: Char = '.', out: CoOutput) =
            out.outputIntScaled(i, scale, separator)

    /**
     * Output an `Int` left-trimmed, using a scale parameter to indicate the number of decimal places.
     *
     * Negative scale values (indicating that decimal point is to the right of the last digit) are ignored.  It is left
     * to the user to decide whether to output additional zeros following the number, or to add an exponent suffix.
     */
    suspend fun CoOutput.outputIntScaled(i: Int, scale: Int, separator: Char = '.') {
        if (i < 0) {
            output('-')
            if (i == Int.MIN_VALUE)
                outputStringScaled(MIN_INTEGER_DIGITS, scale, separator)
            else
                outputPositiveIntScaled(-i, scale, separator)
        }
        else
            outputPositiveIntScaled(i, scale, separator)
    }

    /**
     * Output a positive `Int` left-trimmed, using a scale parameter to indicate the number of decimal places.
     *
     * Negative scale values (indicating that decimal point is to the right of the last digit) are ignored.  It is left
     * to the user to decide whether to output additional zeros following the number, or to add an exponent suffix.
     */
    suspend fun coOutputPositiveIntScaled(i: Int, scale: Int, separator: Char = '.', out: CoOutput) =
            out.outputPositiveIntScaled(i, scale, separator)

    suspend fun CoOutput.outputPositiveIntScaled(i: Int, scale: Int, separator: Char = '.') {
        when {
            scale > 2 -> {
                val n = i / 100
                outputPositiveIntScaled(n, scale - 2, separator)
                output2Digits(i - n * 100)
            }
            scale == 2 -> {
                val n = i / 100
                outputPositiveInt(n)
                output(separator)
                output2Digits(i - n * 100)
            }
            scale == 1 -> {
                val n = i / 10
                outputPositiveInt(n)
                output(separator)
                output(digits[i - n * 10])
            }
            i >= 100 -> {
                val n = i / 100
                outputPositiveInt(n)
                output2Digits(i - n * 100)
            }
            i >= 10 -> output2Digits(i)
            else -> output(digits[i])
        }
    }

    private suspend fun CoOutput.outputStringScaled(string: String, scale: Int, separator: Char) {
        if (scale <= 0)
            output(string)
        else {
            val length = string.length
            if (scale >= length) {
                output('0')
                output(separator)
                repeat(scale - length) { output('0') }
                output(string)
            }
            else {
                val insertionPoint = length - scale
                for (i in 0 until length) {
                    if (i == insertionPoint)
                        output(separator)
                    output(string[i])
                }
            }
        }
    }

    /**
     * Output a `Long` left-trimmed.
     */
    suspend fun coOutputLong(n: Long, out: CoOutput) = out.outputLong(n)

    /**
     * Output a `Long` left-trimmed.
     */
    suspend fun CoOutput.outputLong(n: Long) {
        if (n < 0) {
            output('-')
            if (n == Long.MIN_VALUE)
                output(MIN_LONG_DIGITS)
            else
                outputPositiveLong(-n)
        }
        else
            outputPositiveLong(n)
    }

    /**
     * Output a positive `Long` left-trimmed.
     */
    suspend fun coOutputPositiveLong(n: Long, out: CoOutput) = out.outputPositiveLong(n)

    /**
     * Output a positive `Long` left-trimmed.
     */
    suspend fun CoOutput.outputPositiveLong(n: Long) {
        when {
            n >= 100 -> {
                val m = n / 100
                outputPositiveLong(m)
                output2Digits((n - m * 100).toInt())
            }
            n >= 10 -> output2Digits(n.toInt())
            else -> output(digits[n.toInt()])
        }
    }

    /**
     * Output an unsigned `Long` left-trimmed.
     */
    suspend fun coOutputUnsignedLong(n: Long, out: CoOutput) = out.outputUnsignedLong(n)

    /**
     * Output an unsigned `Long` left-trimmed.
     */
    suspend fun CoOutput.outputUnsignedLong(n: Long) {
        if (n >= 0)
            outputPositiveLong(n)
        else {
            val m = (n ushr 1) / 50
            outputPositiveLong(m)
            output2Digits((n - m * 100).toInt())
        }
    }

    suspend fun coOutputLongScaled(n: Long, scale: Int, separator: Char = '.', out: CoOutput) =
        out.outputLongScaled(n, scale, separator)

    suspend fun CoOutput.outputLongScaled(n: Long, scale: Int, separator: Char = '.') {
        if (n < 0) {
            output('-')
            if (n == Long.MIN_VALUE)
                outputStringScaled(MIN_LONG_DIGITS, scale, separator)
            else
                outputPositiveLongScaled(-n, scale, separator)
        }
        else
            outputPositiveLongScaled(n, scale, separator)
    }

    suspend fun coOutputPositiveLongScaled(n: Long, scale: Int, separator: Char = '.', out: CoOutput) =
        out.outputPositiveLongScaled(n, scale, separator)

    suspend fun CoOutput.outputPositiveLongScaled(n: Long, scale: Int, separator: Char = '.') {
        when {
            scale > 2 -> {
                val m = n / 100
                outputPositiveLongScaled(m, scale - 2, separator)
                output2Digits((n - m * 100).toInt())
            }
            scale == 2 -> {
                val m = n / 100
                outputPositiveLong(m)
                output(separator)
                output2Digits((n - m * 100).toInt())
            }
            scale == 1 -> {
                val m = n / 10
                outputPositiveLong(m)
                output(separator)
                output(digits[(n - m * 10).toInt()])
            }
            n >= 100 -> {
                val m = n / 100
                outputPositiveLong(m)
                output2Digits((n - m * 100).toInt())
            }
            n >= 10 -> output2Digits(n.toInt())
            else -> output(digits[n.toInt()])
        }
    }

    /**
     * Output an `Int` as two decimal digits.
     */
    suspend fun coOutput2Digits(i: Int, out: CoOutput) = out.output2Digits(i)

    /**
     * Output an `Int` as two decimal digits.
     */
    suspend fun CoOutput.output2Digits(i: Int) {
        output(tensDigits[i])
        output(digits[i])
    }

    /**
     * Output an `Int` as three decimal digits.
     */
    suspend fun coOutput3Digits(i: Int, out: CoOutput) = out.output3Digits(i)

    /**
     * Output an `Int` as three decimal digits.
     */
    suspend fun CoOutput.output3Digits(i: Int) {
        val n = i / 100
        output(digits[n])
        output2Digits(i - n * 100)
    }

    /**
     * Output an `Int` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputIntGrouped(i: Int, groupingChar: Char = ',', out: CoOutput) =
            out.outputIntGrouped(i, groupingChar)

    /**
     * Output an `Int` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun CoOutput.outputIntGrouped(i: Int, groupingChar: Char = ',') {
        if (i < 0) {
            output('-')
            if (i == Int.MIN_VALUE) {
                output(MIN_INTEGER_DIGITS[0])
                output(groupingChar)
                output(MIN_INTEGER_DIGITS, 1, 4)
                output(groupingChar)
                output(MIN_INTEGER_DIGITS, 4, 7)
                output(groupingChar)
                output(MIN_INTEGER_DIGITS, 7, 10)
            }
            else
                outputPositiveIntGrouped(-i, groupingChar)
        }
        else
            outputPositiveIntGrouped(i, groupingChar)
    }

    /**
     * Output a positive `Int` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputPositiveIntGrouped(i: Int, groupingChar: Char = ',', out: CoOutput) =
            out.outputPositiveIntGrouped(i, groupingChar)

    /**
     * Output a positive `Int` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun CoOutput.outputPositiveIntGrouped(i: Int, groupingChar: Char = ',') {
        when {
            i >= 100 -> {
                val n = i / 100
                outputPositiveIntGrouped1(n, groupingChar)
                output2Digits(i - n * 100)
            }
            i >= 10 -> output2Digits(i)
            else -> output(digits[i])
        }
    }

    private suspend fun CoOutput.outputPositiveIntGrouped1(i: Int, groupingChar: Char) {
        when {
            i >= 100 -> {
                val n = i / 100
                outputPositiveIntGrouped2(n, groupingChar)
                val m = i - n * 100
                output(tensDigits[m])
                output(groupingChar)
                output(digits[m])
            }
            i >= 10 -> {
                output(tensDigits[i])
                output(groupingChar)
                output(digits[i])
            }
            else -> output(digits[i])
        }
    }

    private suspend fun CoOutput.outputPositiveIntGrouped2(i: Int, groupingChar: Char) {
        when {
            i >= 100 -> {
                val n = i / 100
                outputPositiveIntGrouped(n, groupingChar)
                output(groupingChar)
                output2Digits(i - n * 100)
            }
            i >= 10 -> output2Digits(i)
            else -> output(digits[i])
        }
    }

    /**
     * Output a `Long` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputLongGrouped(n: Long, groupingChar: Char = ',', out: CoOutput) =
            out.outputLongGrouped(n, groupingChar)

    /**
     * Output a `Long` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun CoOutput.outputLongGrouped(n: Long, groupingChar: Char = ',') {
        if (n < 0) {
            output('-')
            if (n == Long.MIN_VALUE) {
                output(MIN_LONG_DIGITS[0])
                output(groupingChar)
                output(MIN_LONG_DIGITS, 1, 4)
                output(groupingChar)
                output(MIN_LONG_DIGITS, 4, 7)
                output(groupingChar)
                output(MIN_LONG_DIGITS, 7, 10)
                output(groupingChar)
                output(MIN_LONG_DIGITS, 10, 13)
                output(groupingChar)
                output(MIN_LONG_DIGITS, 13, 16)
                output(groupingChar)
                output(MIN_LONG_DIGITS, 16, 19)
            }
            else
                outputPositiveLongGrouped(-n, groupingChar)
        }
        else
            outputPositiveLongGrouped(n, groupingChar)
    }

    /**
     * Output a positive `Long` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun coOutputPositiveLongGrouped(n: Long, groupingChar: Char = ',', out: CoOutput) =
            out.outputPositiveLongGrouped(n, groupingChar)

    /**
     * Output a positive `Long` left-trimmed with digits grouped in 3s, separated by a specified grouping character.
     */
    suspend fun CoOutput.outputPositiveLongGrouped(n: Long, groupingChar: Char = ',') {
        when {
            n >= 100 -> {
                val m = n / 100
                outputPositiveLongGrouped1(m, groupingChar)
                output2Digits((n - m * 100).toInt())
            }
            n >= 10 -> output2Digits(n.toInt())
            else -> output(digits[n.toInt()])
        }
    }

    private suspend fun CoOutput.outputPositiveLongGrouped1(n: Long, groupingChar: Char) {
        when {
            n >= 100 -> {
                val m = n / 100
                outputPositiveLongGrouped2(m, groupingChar)
                val k = (n - m * 100).toInt()
                output(tensDigits[k])
                output(groupingChar)
                output(digits[k])
            }
            n >= 10 -> {
                output(tensDigits[n.toInt()])
                output(groupingChar)
                output(digits[n.toInt()])
            }
            else -> output(digits[n.toInt()])
        }
    }

    private suspend fun CoOutput.outputPositiveLongGrouped2(n: Long, groupingChar: Char) {
        when {
            n >= 100 -> {
                val m = n / 100
                outputPositiveLongGrouped(m, groupingChar)
                output(groupingChar)
                output2Digits((n - m * 100).toInt())
            }
            n >= 10 -> output2Digits(n.toInt())
            else -> output(digits[n.toInt()])
        }
    }

    /**
     * Output an `Int` left-trimmed in hexadecimal.
     */
    suspend fun coOutputIntHex(i: Int, out: CoOutput) = out.outputIntHex(i)

    /**
     * Output an `Int` left-trimmed in hexadecimal.
     */
    suspend fun CoOutput.outputIntHex(i: Int) {
        if ((i and 0xFFFF.inv()) != 0) {
            output16BitsHex(i ushr 16)
            output4Hex(i)
        }
        else
            output16BitsHex(i)
    }

    private suspend fun CoOutput.output8BitsHex(i: Int) {
        if ((i and 0xF.inv()) != 0)
            output(digitsHex[i ushr 4])
        output(digitsHex[i and 0xF])
    }

    private suspend fun CoOutput.output16BitsHex(i: Int) {
        if ((i and 0xFF.inv()) != 0) {
            output8BitsHex(i ushr 8)
            output2Hex(i)
        }
        else
            output8BitsHex(i)
    }

    /**
     * Output an `Int` left-trimmed in hexadecimal, using lower-case for the alphabetic characters.
     */
    suspend fun coOutputIntHexLC(i: Int, out: CoOutput) = out.outputIntHexLC(i)

    /**
     * Output an `Int` left-trimmed in hexadecimal, using lower-case for the alphabetic characters.
     */
    suspend fun CoOutput.outputIntHexLC(i: Int) {
        if ((i and 0xFFFF.inv()) != 0) {
            output16BitsHexLC(i ushr 16)
            output4HexLC(i)
        }
        else
            output16BitsHexLC(i)
    }

    private suspend fun CoOutput.output8BitsHexLC(i: Int) {
        if ((i and 0xF.inv()) != 0)
            output(digitsHexLC[i ushr 4])
        output(digitsHexLC[i and 0xF])
    }

    private suspend fun CoOutput.output16BitsHexLC(i: Int) {
        if ((i and 0xFF.inv()) != 0) {
            output8BitsHexLC(i ushr 8)
            output2HexLC(i)
        }
        else
            output8BitsHexLC(i)
    }

    /**
     * Output a `Long` left-trimmed in hexadecimal.
     */
    suspend fun coOutputLongHex(n: Long, out: CoOutput) = out.outputLongHex(n)

    /**
     * Output a `Long` left-trimmed in hexadecimal.
     */
    suspend fun CoOutput.outputLongHex(n: Long) {
        val hi = (n ushr 32).toInt()
        val lo = n.toInt()
        if (hi != 0) {
            outputIntHex(hi)
            output8Hex(lo)
        }
        else
            outputIntHex(lo)
    }

    /**
     * Output a `Long` left-trimmed in hexadecimal, using lower-case for the alphabetic characters.
     */
    suspend fun coOutputLongHexLC(n: Long, out: CoOutput) = out.outputLongHexLC(n)

    /**
     * Output a `Long` left-trimmed in hexadecimal, using lower-case for the alphabetic characters.
     */
    suspend fun CoOutput.outputLongHexLC(n: Long) {
        val hi = (n ushr 32).toInt()
        val lo = n.toInt()
        if (hi != 0) {
            outputIntHexLC(hi)
            output8HexLC(lo)
        }
        else
            outputIntHexLC(lo)
    }

    /**
     * Output an `Int` as eight hexadecimal digits.
     */
    suspend fun coOutput8Hex(i: Int, out: CoOutput) = out.output8Hex(i)

    /**
     * Output an `Int` as eight hexadecimal digits.
     */
    suspend fun CoOutput.output8Hex(i: Int) {
        output4Hex(i ushr 16)
        output4Hex(i)
    }

    /**
     * Output an `Int` as eight hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput8HexLC(i: Int, out: CoOutput) = out.output8HexLC(i)

    /**
     * Output an `Int` as eight hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun CoOutput.output8HexLC(i: Int) {
        output4HexLC(i ushr 16)
        output4HexLC(i)
    }

    /**
     * Output an `Int` as four hexadecimal digits.
     */
    suspend fun coOutput4Hex(i: Int, out: CoOutput) = out.output4Hex(i)

    /**
     * Output an `Int` as four hexadecimal digits.
     */
    suspend fun CoOutput.output4Hex(i: Int) {
        output2Hex(i ushr 8)
        output2Hex(i)
    }

    /**
     * Output an `Int` as four hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput4HexLC(i: Int, out: CoOutput) = out.output4HexLC(i)

    /**
     * Output an `Int` as four hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun CoOutput.output4HexLC(i: Int) {
        output2HexLC(i ushr 8)
        output2HexLC(i)
    }

    /**
     * Output an `Int` as two hexadecimal digits.
     */
    suspend fun coOutput2Hex(i: Int, out: CoOutput) = out.output2Hex(i)

    /**
     * Output an `Int` as two hexadecimal digits.
     */
    suspend fun CoOutput.output2Hex(i: Int) {
        output(digitsHex[(i shr 4) and 0xF])
        output(digitsHex[i and 0xF])
    }

    /**
     * Output an `Int` as two hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput2HexLC(i: Int, out: CoOutput) = out.output2HexLC(i)

    /**
     * Output an `Int` as two hexadecimal digits, using lower-case for the alphabetic characters.
     */
    suspend fun CoOutput.output2HexLC(i: Int) {
        output(digitsHexLC[(i shr 4) and 0xF])
        output(digitsHexLC[i and 0xF])
    }

    /**
     * Output an `Int` as a single hexadecimal digit.
     */
    suspend fun coOutput1Hex(i: Int, out: CoOutput) = out.output1Hex(i)

    /**
     * Output an `Int` as a single hexadecimal digit.
     */
    suspend fun CoOutput.output1Hex(i: Int) {
        output(digitsHex[i and 0xF])
    }

    /**
     * Output an `Int` as a single hexadecimal digit, using lower-case for the alphabetic characters.
     */
    suspend fun coOutput1HexLC(i: Int, out: CoOutput) = out.output1HexLC(i)

    /**
     * Output an `Int` as a single hexadecimal digit, using lower-case for the alphabetic characters.
     */
    suspend fun CoOutput.output1HexLC(i: Int) {
        output(digitsHexLC[i and 0xF])
    }

}
