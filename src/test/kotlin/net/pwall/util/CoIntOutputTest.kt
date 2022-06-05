/*
 * @(#) CoIntOutputTest.java
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

import kotlin.test.Test
import kotlin.test.expect
import kotlinx.coroutines.runBlocking

import net.pwall.util.CoIntOutput.coOutput1Hex
import net.pwall.util.CoIntOutput.coOutput1HexLC
import net.pwall.util.CoIntOutput.coOutput2Digits
import net.pwall.util.CoIntOutput.coOutput2Hex
import net.pwall.util.CoIntOutput.coOutput2HexLC
import net.pwall.util.CoIntOutput.coOutput3Digits
import net.pwall.util.CoIntOutput.coOutput4Hex
import net.pwall.util.CoIntOutput.coOutput4HexLC
import net.pwall.util.CoIntOutput.coOutput8Hex
import net.pwall.util.CoIntOutput.coOutput8HexLC
import net.pwall.util.CoIntOutput.coOutputInt
import net.pwall.util.CoIntOutput.coOutputIntGrouped
import net.pwall.util.CoIntOutput.coOutputIntHex
import net.pwall.util.CoIntOutput.coOutputIntHexLC
import net.pwall.util.CoIntOutput.coOutputLong
import net.pwall.util.CoIntOutput.coOutputLongGrouped
import net.pwall.util.CoIntOutput.coOutputLongHex
import net.pwall.util.CoIntOutput.coOutputLongHexLC
import net.pwall.util.CoIntOutput.coOutputPositiveInt
import net.pwall.util.CoIntOutput.coOutputPositiveIntGrouped
import net.pwall.util.CoIntOutput.coOutputPositiveLong
import net.pwall.util.CoIntOutput.coOutputPositiveLongGrouped
import net.pwall.util.CoIntOutput.coOutputUnsignedInt
import net.pwall.util.CoIntOutput.coOutputUnsignedLong
import net.pwall.util.CoIntOutput.output1Hex
import net.pwall.util.CoIntOutput.output1HexLC
import net.pwall.util.CoIntOutput.output2Digits
import net.pwall.util.CoIntOutput.output2Hex
import net.pwall.util.CoIntOutput.output2HexLC
import net.pwall.util.CoIntOutput.output3Digits
import net.pwall.util.CoIntOutput.output4Hex
import net.pwall.util.CoIntOutput.output4HexLC
import net.pwall.util.CoIntOutput.output8Hex
import net.pwall.util.CoIntOutput.output8HexLC
import net.pwall.util.CoIntOutput.outputInt
import net.pwall.util.CoIntOutput.outputIntGrouped
import net.pwall.util.CoIntOutput.outputIntHex
import net.pwall.util.CoIntOutput.outputIntHexLC
import net.pwall.util.CoIntOutput.outputLong
import net.pwall.util.CoIntOutput.outputLongGrouped
import net.pwall.util.CoIntOutput.outputLongHex
import net.pwall.util.CoIntOutput.outputLongHexLC
import net.pwall.util.CoIntOutput.outputPositiveInt
import net.pwall.util.CoIntOutput.outputPositiveIntGrouped
import net.pwall.util.CoIntOutput.outputPositiveLong
import net.pwall.util.CoIntOutput.outputPositiveLongGrouped
import net.pwall.util.CoIntOutput.outputUnsignedInt
import net.pwall.util.CoIntOutput.outputUnsignedLong

class CoIntOutputTest {

    @Test fun `should convert int correctly using lambda`() = runBlocking {
        expect("0") { coOutputIntString(0) }
        expect("123456") { coOutputIntString(123456) }
        expect("-22334455") { coOutputIntString(-22334455) }
        expect("2147483647") { coOutputIntString(Int.MAX_VALUE) }
        expect("-2147483648") { coOutputIntString(Int.MIN_VALUE) }
    }

    private suspend fun coOutputIntString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        val out: CoOutput = { charArray[i++] = it }
        coOutputInt(n, out)
        return String(charArray, 0, i)
    }

    @Test fun `should convert int correctly using extension function`() = runBlocking {
        expect("0") { outputIntString(0) }
        expect("123456") { outputIntString(123456) }
        expect("-22334455") { outputIntString(-22334455) }
        expect("2147483647") { outputIntString(Int.MAX_VALUE) }
        expect("-2147483648") { outputIntString(Int.MIN_VALUE) }
    }

    private suspend fun outputIntString(n: Int) = CoCapture().apply { outputInt(n) }.toString()

    @Test fun `should convert positive int correctly using lambda`() = runBlocking {
        expect("0") { coOutputPositiveIntString(0) }
        expect("123456") { coOutputPositiveIntString(123456) }
        expect("2147483647") { coOutputPositiveIntString(Int.MAX_VALUE) }
    }

    private suspend fun coOutputPositiveIntString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputPositiveInt(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert positive int correctly using extension function`() = runBlocking {
        expect("0") { outputPositiveIntString(0) }
        expect("123456") { outputPositiveIntString(123456) }
        expect("2147483647") { outputPositiveIntString(Int.MAX_VALUE) }
    }

    private suspend fun outputPositiveIntString(n: Int) = CoCapture().apply { outputPositiveInt(n) }.toString()

    @Test fun `should convert unsigned int correctly using lambda`() = runBlocking {
        expect("0") { coOutputUnsignedIntString(0) }
        expect("123456") { coOutputUnsignedIntString(123456) }
        expect("2147483648") { coOutputUnsignedIntString(2147483648.toInt()) }
        expect("3456789012") { coOutputUnsignedIntString(3456789012.toInt()) }
        expect("2309737967") { coOutputUnsignedIntString(0x89ABCDEF.toInt()) }
    }

    private suspend fun coOutputUnsignedIntString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputUnsignedInt(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert unsigned int correctly using extension function`() = runBlocking {
        expect("0") { outputUnsignedIntString(0) }
        expect("123456") { outputUnsignedIntString(123456) }
        expect("2147483648") { outputUnsignedIntString(2147483648.toInt()) }
        expect("3456789012") { outputUnsignedIntString(3456789012.toInt()) }
        expect("2309737967") { outputUnsignedIntString(0x89ABCDEF.toInt()) }
    }

    private suspend fun outputUnsignedIntString(n: Int) = CoCapture().apply { outputUnsignedInt(n) }.toString()

    @Test fun `should convert long correctly using lambda`() = runBlocking {
        expect("0") { coOutputLongString(0) }
        expect("123456789012345678") { coOutputLongString(123456789012345678) }
        expect("-2233445566778899") { coOutputLongString(-2233445566778899) }
        expect("2147483647") { coOutputLongString(Int.MAX_VALUE.toLong()) }
        expect("-2147483648") { coOutputLongString(Int.MIN_VALUE.toLong()) }
        expect("9223372036854775807") { coOutputLongString(Long.MAX_VALUE) }
        expect("-9223372036854775808") { coOutputLongString(Long.MIN_VALUE) }
    }

    private suspend fun coOutputLongString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLong(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long correctly using extension function`() = runBlocking {
        expect("0") { outputLongString(0) }
        expect("123456789012345678") { outputLongString(123456789012345678) }
        expect("-2233445566778899") { outputLongString(-2233445566778899) }
        expect("2147483647") { outputLongString(Int.MAX_VALUE.toLong()) }
        expect("-2147483648") { outputLongString(Int.MIN_VALUE.toLong()) }
        expect("9223372036854775807") { outputLongString(Long.MAX_VALUE) }
        expect("-9223372036854775808") { outputLongString(Long.MIN_VALUE) }
    }

    private suspend fun outputLongString(n: Long) = CoCapture().apply { outputLong(n) }.toString()

    @Test fun `should convert positive long correctly using lambda`() = runBlocking {
        expect("0") { coOutputPositiveLongString(0) }
        expect("123456789012345678") { coOutputPositiveLongString(123456789012345678) }
        expect("2147483647") { coOutputPositiveLongString(Int.MAX_VALUE.toLong()) }
        expect("9223372036854775807") { coOutputPositiveLongString(Long.MAX_VALUE) }
    }

    private suspend fun coOutputPositiveLongString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputPositiveLong(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert positive long correctly using extension function`() = runBlocking {
        expect("0") { outputPositiveLongString(0) }
        expect("123456789012345678") { outputPositiveLongString(123456789012345678) }
        expect("2147483647") { outputPositiveLongString(Int.MAX_VALUE.toLong()) }
        expect("9223372036854775807") { outputPositiveLongString(Long.MAX_VALUE) }
    }

    private suspend fun outputPositiveLongString(n: Long) = CoCapture().apply { outputPositiveLong(n) }.toString()

    @Test fun `should convert unsigned long correctly using lambda`() = runBlocking {
        expect("0") { coOutputUnsignedLongString(0) }
        expect("1234567890123456789") { coOutputUnsignedLongString(1234567890123456789) }
        expect("9223372036854775808") { coOutputUnsignedLongString(maxValue() + 1) }
        expect("12345678901234567890") { coOutputUnsignedLongString(nineteenDigits() * 10) }
    }

    private suspend fun coOutputUnsignedLongString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputUnsignedLong(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert unsigned long correctly using extension function`() = runBlocking {
        expect("0") { outputUnsignedLongString(0) }
        expect("1234567890123456789") { outputUnsignedLongString(1234567890123456789) }
        expect("9223372036854775808") { outputUnsignedLongString(maxValue() + 1) }
        expect("12345678901234567890") { outputUnsignedLongString(nineteenDigits() * 10) }
    }

    private suspend fun outputUnsignedLongString(n: Long) = CoCapture().apply { outputUnsignedLong(n) }.toString()

    @Test fun `should output 2 digits correctly using lambda`() = runBlocking {
        expect("00") { coOutput2DigitsString(0) }
        expect("01") { coOutput2DigitsString(1) }
        expect("21") { coOutput2DigitsString(21) }
    }

    private suspend fun coOutput2DigitsString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput2Digits(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits correctly using extension function`() = runBlocking {
        expect("00") { output2DigitsString(0) }
        expect("01") { output2DigitsString(1) }
        expect("21") { output2DigitsString(21) }
    }

    private suspend fun output2DigitsString(n: Int) = CoCapture().apply { output2Digits(n) }.toString()

    @Test fun `should output 3 digits correctly using lambda`() = runBlocking {
        expect("000") { coOutput3DigitsString(0) }
        expect("001") { coOutput3DigitsString(1) }
        expect("021") { coOutput3DigitsString(21) }
        expect("321") { coOutput3DigitsString(321) }
    }

    private suspend fun coOutput3DigitsString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput3Digits(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 3 digits correctly using extension function`() = runBlocking {
        expect("000") { output3DigitsString(0) }
        expect("001") { output3DigitsString(1) }
        expect("021") { output3DigitsString(21) }
        expect("321") { output3DigitsString(321) }
    }

    private suspend fun output3DigitsString(n: Int) = CoCapture().apply { output3Digits(n) }.toString()

    @Test fun `should output integer with grouping using lambda`() = runBlocking {
        expect("0") { coOutputIntGroupedString(0) }
        expect("1") { coOutputIntGroupedString(1) }
        expect("12") { coOutputIntGroupedString(12) }
        expect("123") { coOutputIntGroupedString(123) }
        expect("1,234") { coOutputIntGroupedString(1234) }
        expect("12,345") { coOutputIntGroupedString(12345) }
        expect("123,456") { coOutputIntGroupedString(123456) }
        expect("1,234,567") { coOutputIntGroupedString(1234567) }
        expect("12,345,678") { coOutputIntGroupedString(12345678) }
        expect("123,456,789") { coOutputIntGroupedString(123456789) }
        expect("1,234,567,890") { coOutputIntGroupedString(1234567890) }
        expect("2 147 483 647") { coOutputIntGroupedString(Int.MAX_VALUE, ' ') }
        expect("-2 147 483 648") { coOutputIntGroupedString(Int.MIN_VALUE, ' ') }
        expect("-1") { coOutputIntGroupedString(-1) }
        expect("-12") { coOutputIntGroupedString(-12) }
        expect("-123") { coOutputIntGroupedString(-123) }
        expect("-1,234") { coOutputIntGroupedString(-1234) }
        expect("-12,345") { coOutputIntGroupedString(-12345) }
        expect("-123,456") { coOutputIntGroupedString(-123456) }
        expect("-1,234,567") { coOutputIntGroupedString(-1234567) }
        expect("-12,345,678") { coOutputIntGroupedString(-12345678) }
        expect("-123,456,789") { coOutputIntGroupedString(-123456789) }
        expect("-1,234,567,890") { coOutputIntGroupedString(-1234567890) }
    }

    private suspend fun coOutputIntGroupedString(n: Int, groupingChar: Char = ','): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output integer with grouping using extension function`() = runBlocking {
        expect("0") { outputIntGroupedString(0) }
        expect("1") { outputIntGroupedString(1) }
        expect("12") { outputIntGroupedString(12) }
        expect("123") { outputIntGroupedString(123) }
        expect("1,234") { outputIntGroupedString(1234) }
        expect("12,345") { outputIntGroupedString(12345) }
        expect("123,456") { outputIntGroupedString(123456) }
        expect("1,234,567") { outputIntGroupedString(1234567) }
        expect("12,345,678") { outputIntGroupedString(12345678) }
        expect("123,456,789") { outputIntGroupedString(123456789) }
        expect("1,234,567,890") { outputIntGroupedString(1234567890) }
        expect("2 147 483 647") { outputIntGroupedString(Int.MAX_VALUE, ' ') }
        expect("-2 147 483 648") { outputIntGroupedString(Int.MIN_VALUE, ' ') }
        expect("-1") { outputIntGroupedString(-1) }
        expect("-12") { outputIntGroupedString(-12) }
        expect("-123") { outputIntGroupedString(-123) }
        expect("-1,234") { outputIntGroupedString(-1234) }
        expect("-12,345") { outputIntGroupedString(-12345) }
        expect("-123,456") { outputIntGroupedString(-123456) }
        expect("-1,234,567") { outputIntGroupedString(-1234567) }
        expect("-12,345,678") { outputIntGroupedString(-12345678) }
        expect("-123,456,789") { outputIntGroupedString(-123456789) }
        expect("-1,234,567,890") { outputIntGroupedString(-1234567890) }
    }

    private suspend fun outputIntGroupedString(n: Int, groupingChar: Char = ',') =
            CoCapture().apply { outputIntGrouped(n, groupingChar) }.toString()

    @Test fun `should output positive integer with grouping using lambda`() = runBlocking {
        expect("0") { coOutputPositiveIntGroupedString(0) }
        expect("1") { coOutputPositiveIntGroupedString(1) }
        expect("12") { coOutputPositiveIntGroupedString(12) }
        expect("123") { coOutputPositiveIntGroupedString(123) }
        expect("1,234") { coOutputPositiveIntGroupedString(1234) }
        expect("12,345") { coOutputPositiveIntGroupedString(12345) }
        expect("123,456") { coOutputPositiveIntGroupedString(123456) }
        expect("1,234,567") { coOutputPositiveIntGroupedString(1234567) }
        expect("12,345,678") { coOutputPositiveIntGroupedString(12345678) }
        expect("123,456,789") { coOutputPositiveIntGroupedString(123456789) }
        expect("1,234,567,890") { coOutputPositiveIntGroupedString(1234567890) }
        expect("2 147 483 647") { coOutputPositiveIntGroupedString(Int.MAX_VALUE, ' ') }
    }

    private suspend fun coOutputPositiveIntGroupedString(n: Int, groupingChar: Char = ','): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputPositiveIntGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output positive integer with grouping using extension function`() = runBlocking {
        expect("0") { outputPositiveIntGroupedString(0) }
        expect("1") { outputPositiveIntGroupedString(1) }
        expect("12") { outputPositiveIntGroupedString(12) }
        expect("123") { outputPositiveIntGroupedString(123) }
        expect("1,234") { outputPositiveIntGroupedString(1234) }
        expect("12,345") { outputPositiveIntGroupedString(12345) }
        expect("123,456") { outputPositiveIntGroupedString(123456) }
        expect("1,234,567") { outputPositiveIntGroupedString(1234567) }
        expect("12,345,678") { outputPositiveIntGroupedString(12345678) }
        expect("123,456,789") { outputPositiveIntGroupedString(123456789) }
        expect("1,234,567,890") { outputPositiveIntGroupedString(1234567890) }
        expect("2 147 483 647") { outputPositiveIntGroupedString(Int.MAX_VALUE, ' ') }
    }

    private suspend fun outputPositiveIntGroupedString(n: Int, groupingChar: Char = ',') =
        CoCapture().apply { outputPositiveIntGrouped(n, groupingChar) }.toString()

    @Test fun `should output long with grouping using lambda`() = runBlocking {
        expect("0") { coOutputLongGroupedString(0) }
        expect("1") { coOutputLongGroupedString(1) }
        expect("12") { coOutputLongGroupedString(12) }
        expect("123") { coOutputLongGroupedString(123) }
        expect("1,234") { coOutputLongGroupedString(1234) }
        expect("12,345") { coOutputLongGroupedString(12345) }
        expect("123,456") { coOutputLongGroupedString(123456) }
        expect("1,234,567") { coOutputLongGroupedString(1234567) }
        expect("12,345,678") { coOutputLongGroupedString(12345678) }
        expect("123,456,789") { coOutputLongGroupedString(123456789) }
        expect("1,234,567,890") { coOutputLongGroupedString(1234567890) }
        expect("12,345,678,901") { coOutputLongGroupedString(12345678901) }
        expect("123,456,789,012") { coOutputLongGroupedString(123456789012) }
        expect("1,234,567,890,123") { coOutputLongGroupedString(1234567890123) }
        expect("12,345,678,901,234") { coOutputLongGroupedString(12345678901234) }
        expect("123,456,789,012,345") { coOutputLongGroupedString(123456789012345) }
        expect("1,234,567,890,123,456") { coOutputLongGroupedString(1234567890123456) }
        expect("12,345,678,901,234,567") { coOutputLongGroupedString(12345678901234567) }
        expect("123,456,789,012,345,678") { coOutputLongGroupedString(123456789012345678) }
        expect("1,234,567,890,123,456,789") { coOutputLongGroupedString(1234567890123456789) }
        expect("2 147 483 647") { coOutputLongGroupedString(Int.MAX_VALUE.toLong(), ' ') }
        expect("-2 147 483 648") { coOutputLongGroupedString(Int.MIN_VALUE.toLong(), ' ') }
        expect("9_223_372_036_854_775_807") { coOutputLongGroupedString(Long.MAX_VALUE, '_') }
        expect("-9,223,372,036,854,775,808") { coOutputLongGroupedString(Long.MIN_VALUE) }
        expect("-1") { coOutputLongGroupedString(-1) }
        expect("-12") { coOutputLongGroupedString(-12) }
        expect("-123") { coOutputLongGroupedString(-123) }
        expect("-1,234") { coOutputLongGroupedString(-1234) }
        expect("-12,345") { coOutputLongGroupedString(-12345) }
        expect("-123,456") { coOutputLongGroupedString(-123456) }
        expect("-1,234,567") { coOutputLongGroupedString(-1234567) }
        expect("-12,345,678") { coOutputLongGroupedString(-12345678) }
        expect("-123,456,789") { coOutputLongGroupedString(-123456789) }
        expect("-1,234,567,890") { coOutputLongGroupedString(-1234567890) }
        expect("-12,345,678,901") { coOutputLongGroupedString(-12345678901) }
        expect("-123,456,789,012") { coOutputLongGroupedString(-123456789012) }
        expect("-1,234,567,890,123") { coOutputLongGroupedString(-1234567890123) }
        expect("-12,345,678,901,234") { coOutputLongGroupedString(-12345678901234) }
        expect("-123,456,789,012,345") { coOutputLongGroupedString(-123456789012345) }
        expect("-1,234,567,890,123,456") { coOutputLongGroupedString(-1234567890123456) }
        expect("-12,345,678,901,234,567") { coOutputLongGroupedString(-12345678901234567) }
        expect("-123,456,789,012,345,678") { coOutputLongGroupedString(-123456789012345678) }
        expect("-1,234,567,890,123,456,789") { coOutputLongGroupedString(-1234567890123456789) }
    }

    private suspend fun coOutputLongGroupedString(n: Long, groupingChar: Char = ','): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output long with grouping using extension function`() = runBlocking {
        expect("0") { outputLongGroupedString(0) }
        expect("1") { outputLongGroupedString(1) }
        expect("12") { outputLongGroupedString(12) }
        expect("123") { outputLongGroupedString(123) }
        expect("1,234") { outputLongGroupedString(1234) }
        expect("12,345") { outputLongGroupedString(12345) }
        expect("123,456") { outputLongGroupedString(123456) }
        expect("1,234,567") { outputLongGroupedString(1234567) }
        expect("12,345,678") { outputLongGroupedString(12345678) }
        expect("123,456,789") { outputLongGroupedString(123456789) }
        expect("1,234,567,890") { outputLongGroupedString(1234567890) }
        expect("12,345,678,901") { outputLongGroupedString(12345678901) }
        expect("123,456,789,012") { outputLongGroupedString(123456789012) }
        expect("1,234,567,890,123") { outputLongGroupedString(1234567890123) }
        expect("12,345,678,901,234") { outputLongGroupedString(12345678901234) }
        expect("123,456,789,012,345") { outputLongGroupedString(123456789012345) }
        expect("1,234,567,890,123,456") { outputLongGroupedString(1234567890123456) }
        expect("12,345,678,901,234,567") { outputLongGroupedString(12345678901234567) }
        expect("123,456,789,012,345,678") { outputLongGroupedString(123456789012345678) }
        expect("1,234,567,890,123,456,789") { outputLongGroupedString(1234567890123456789) }
        expect("2 147 483 647") { outputLongGroupedString(Int.MAX_VALUE.toLong(), ' ') }
        expect("-2 147 483 648") { outputLongGroupedString(Int.MIN_VALUE.toLong(), ' ') }
        expect("9_223_372_036_854_775_807") { outputLongGroupedString(Long.MAX_VALUE, '_') }
        expect("-9,223,372,036,854,775,808") { outputLongGroupedString(Long.MIN_VALUE) }
        expect("-1") { outputLongGroupedString(-1) }
        expect("-12") { outputLongGroupedString(-12) }
        expect("-123") { outputLongGroupedString(-123) }
        expect("-1,234") { outputLongGroupedString(-1234) }
        expect("-12,345") { outputLongGroupedString(-12345) }
        expect("-123,456") { outputLongGroupedString(-123456) }
        expect("-1,234,567") { outputLongGroupedString(-1234567) }
        expect("-12,345,678") { outputLongGroupedString(-12345678) }
        expect("-123,456,789") { outputLongGroupedString(-123456789) }
        expect("-1,234,567,890") { outputLongGroupedString(-1234567890) }
        expect("-12,345,678,901") { outputLongGroupedString(-12345678901) }
        expect("-123,456,789,012") { outputLongGroupedString(-123456789012) }
        expect("-1,234,567,890,123") { outputLongGroupedString(-1234567890123) }
        expect("-12,345,678,901,234") { outputLongGroupedString(-12345678901234) }
        expect("-123,456,789,012,345") { outputLongGroupedString(-123456789012345) }
        expect("-1,234,567,890,123,456") { outputLongGroupedString(-1234567890123456) }
        expect("-12,345,678,901,234,567") { outputLongGroupedString(-12345678901234567) }
        expect("-123,456,789,012,345,678") { outputLongGroupedString(-123456789012345678) }
        expect("-1,234,567,890,123,456,789") { outputLongGroupedString(-1234567890123456789) }
    }

    private suspend fun outputLongGroupedString(n: Long, groupingChar: Char = ',') =
        CoCapture().apply { outputLongGrouped(n, groupingChar) }.toString()

    @Test fun `should output positive long with grouping using lambda`() = runBlocking {
        expect("0") { coOutputPositiveLongGroupedString(0) }
        expect("1") { coOutputPositiveLongGroupedString(1) }
        expect("12") { coOutputPositiveLongGroupedString(12) }
        expect("123") { coOutputPositiveLongGroupedString(123) }
        expect("1,234") { coOutputPositiveLongGroupedString(1234) }
        expect("12,345") { coOutputPositiveLongGroupedString(12345) }
        expect("123,456") { coOutputPositiveLongGroupedString(123456) }
        expect("1,234,567") { coOutputPositiveLongGroupedString(1234567) }
        expect("12,345,678") { coOutputPositiveLongGroupedString(12345678) }
        expect("123,456,789") { coOutputPositiveLongGroupedString(123456789) }
        expect("1,234,567,890") { coOutputPositiveLongGroupedString(1234567890) }
        expect("12,345,678,901") { coOutputPositiveLongGroupedString(12345678901) }
        expect("123,456,789,012") { coOutputPositiveLongGroupedString(123456789012) }
        expect("1,234,567,890,123") { coOutputPositiveLongGroupedString(1234567890123) }
        expect("12,345,678,901,234") { coOutputPositiveLongGroupedString(12345678901234) }
        expect("123,456,789,012,345") { coOutputPositiveLongGroupedString(123456789012345) }
        expect("1,234,567,890,123,456") { coOutputPositiveLongGroupedString(1234567890123456) }
        expect("12,345,678,901,234,567") { coOutputPositiveLongGroupedString(12345678901234567) }
        expect("123,456,789,012,345,678") { coOutputPositiveLongGroupedString(123456789012345678) }
        expect("1,234,567,890,123,456,789") { coOutputPositiveLongGroupedString(1234567890123456789) }
        expect("2 147 483 647") { coOutputPositiveLongGroupedString(Int.MAX_VALUE.toLong(), ' ') }
        expect("9_223_372_036_854_775_807") { coOutputPositiveLongGroupedString(Long.MAX_VALUE, '_') }
    }

    private suspend fun coOutputPositiveLongGroupedString(n: Long, groupingChar: Char = ','): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputPositiveLongGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output positive long with grouping using extension function`() = runBlocking {
        expect("0") { outputPositiveLongGroupedString(0) }
        expect("1") { outputPositiveLongGroupedString(1) }
        expect("12") { outputPositiveLongGroupedString(12) }
        expect("123") { outputPositiveLongGroupedString(123) }
        expect("1,234") { outputPositiveLongGroupedString(1234) }
        expect("12,345") { outputPositiveLongGroupedString(12345) }
        expect("123,456") { outputPositiveLongGroupedString(123456) }
        expect("1,234,567") { outputPositiveLongGroupedString(1234567) }
        expect("12,345,678") { outputPositiveLongGroupedString(12345678) }
        expect("123,456,789") { outputPositiveLongGroupedString(123456789) }
        expect("1,234,567,890") { outputPositiveLongGroupedString(1234567890) }
        expect("12,345,678,901") { outputPositiveLongGroupedString(12345678901) }
        expect("123,456,789,012") { outputPositiveLongGroupedString(123456789012) }
        expect("1,234,567,890,123") { outputPositiveLongGroupedString(1234567890123) }
        expect("12,345,678,901,234") { outputPositiveLongGroupedString(12345678901234) }
        expect("123,456,789,012,345") { outputPositiveLongGroupedString(123456789012345) }
        expect("1,234,567,890,123,456") { outputPositiveLongGroupedString(1234567890123456) }
        expect("12,345,678,901,234,567") { outputPositiveLongGroupedString(12345678901234567) }
        expect("123,456,789,012,345,678") { outputPositiveLongGroupedString(123456789012345678) }
        expect("1,234,567,890,123,456,789") { outputPositiveLongGroupedString(1234567890123456789) }
        expect("2 147 483 647") { outputPositiveLongGroupedString(Int.MAX_VALUE.toLong(), ' ') }
        expect("9_223_372_036_854_775_807") { outputPositiveLongGroupedString(Long.MAX_VALUE, '_') }
    }

    private suspend fun outputPositiveLongGroupedString(n: Long, groupingChar: Char = ',') =
        CoCapture().apply { outputPositiveLongGrouped(n, groupingChar) }.toString()

    @Test fun `should convert int to hex correctly using lambda`() = runBlocking {
        expect("0") { coOutputIntHexString(0) }
        expect("1") { coOutputIntHexString(1) }
        expect("23") { coOutputIntHexString(0x23) }
        expect("456") { coOutputIntHexString(0x456) }
        expect("A789") { coOutputIntHexString(0xA789) }
        expect("8A1B1") { coOutputIntHexString(0x8A1B1) }
        expect("FEEABC") { coOutputIntHexString(0xFEEABC) }
        expect("DEADFEED") { coOutputIntHexString(0xDEADFEED.toInt()) }
    }

    private suspend fun coOutputIntHexString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntHex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert int to hex correctly using extension function`() = runBlocking {
        expect("0") { outputIntHexString(0) }
        expect("1") { outputIntHexString(1) }
        expect("23") { outputIntHexString(0x23) }
        expect("456") { outputIntHexString(0x456) }
        expect("A789") { outputIntHexString(0xA789) }
        expect("8A1B1") { outputIntHexString(0x8A1B1) }
        expect("FEEABC") { outputIntHexString(0xFEEABC) }
        expect("DEADFEED") { outputIntHexString(0xDEADFEED.toInt()) }
    }

    private suspend fun outputIntHexString(n: Int) = CoCapture().apply { outputIntHex(n) }.toString()

    @Test fun `should convert int to hex correctly in lower case using lambda`() = runBlocking {
        expect("0") { coOutputIntHexLCString(0) }
        expect("1") { coOutputIntHexLCString(1) }
        expect("23") { coOutputIntHexLCString(0x23) }
        expect("456") { coOutputIntHexLCString(0x456) }
        expect("a789") { coOutputIntHexLCString(0xA789) }
        expect("8a1b1") { coOutputIntHexLCString(0x8A1B1) }
        expect("feeabc") { coOutputIntHexLCString(0xFEEABC) }
        expect("deadfeed") { coOutputIntHexLCString(0xDEADFEED.toInt()) }
    }

    private suspend fun coOutputIntHexLCString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntHexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert int to hex correctly in lower case using extension function`() = runBlocking {
        expect("0") { outputIntHexLCString(0) }
        expect("1") { outputIntHexLCString(1) }
        expect("23") { outputIntHexLCString(0x23) }
        expect("456") { outputIntHexLCString(0x456) }
        expect("a789") { outputIntHexLCString(0xA789) }
        expect("8a1b1") { outputIntHexLCString(0x8A1B1) }
        expect("feeabc") { outputIntHexLCString(0xFEEABC) }
        expect("deadfeed") { outputIntHexLCString(0xDEADFEED.toInt()) }
    }

    private suspend fun outputIntHexLCString(n: Int) = CoCapture().apply { outputIntHexLC(n) }.toString()

    @Test fun `should convert long to hex correctly using lambda`() = runBlocking {
        expect("0") { coOutputLongHexString(0) }
        expect("1") { coOutputLongHexString(1) }
        expect("23") { coOutputLongHexString(0x23) }
        expect("456") { coOutputLongHexString(0x456) }
        expect("A789") { coOutputLongHexString(0xA789) }
        expect("8A1B1") { coOutputLongHexString(0x8A1B1) }
        expect("FEEABC") { coOutputLongHexString(0xFEEABC) }
        expect("DEADFEED") { coOutputLongHexString(0xDEADFEED) }
        expect("123DEADFEED") { coOutputLongHexString(0x123DEADFEED) }
        expect("8000000000000000") { coOutputLongHexString(Long.MIN_VALUE) }
    }

    private suspend fun coOutputLongHexString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongHex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long to hex correctly using extension function`() = runBlocking {
        expect("0") { outputLongHexString(0) }
        expect("1") { outputLongHexString(1) }
        expect("23") { outputLongHexString(0x23) }
        expect("456") { outputLongHexString(0x456) }
        expect("A789") { outputLongHexString(0xA789) }
        expect("8A1B1") { outputLongHexString(0x8A1B1) }
        expect("FEEABC") { outputLongHexString(0xFEEABC) }
        expect("DEADFEED") { outputLongHexString(0xDEADFEED) }
        expect("123DEADFEED") { outputLongHexString(0x123DEADFEED) }
        expect("8000000000000000") { outputLongHexString(Long.MIN_VALUE) }
    }

    private suspend fun outputLongHexString(n: Long) = CoCapture().apply { outputLongHex(n) }.toString()

    @Test fun `should convert long to hex correctly in lower case using lambda`() = runBlocking {
        expect("0") { coOutputLongHexLCString(0) }
        expect("1") { coOutputLongHexLCString(1) }
        expect("23") { coOutputLongHexLCString(0x23) }
        expect("456") { coOutputLongHexLCString(0x456) }
        expect("a789") { coOutputLongHexLCString(0xA789) }
        expect("8a1b1") { coOutputLongHexLCString(0x8A1B1) }
        expect("feeabc") { coOutputLongHexLCString(0xFEEABC) }
        expect("deadfeed") { coOutputLongHexLCString(0xDEADFEED) }
        expect("123deadfeed") { coOutputLongHexLCString(0x123DEADFEED) }
        expect("8000000000000000") { coOutputLongHexLCString(Long.MIN_VALUE) }
    }

    private suspend fun coOutputLongHexLCString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongHexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long to hex correctly in lower case using extension function`() = runBlocking {
        expect("0") { outputLongHexLCString(0) }
        expect("1") { outputLongHexLCString(1) }
        expect("23") { outputLongHexLCString(0x23) }
        expect("456") { outputLongHexLCString(0x456) }
        expect("a789") { outputLongHexLCString(0xA789) }
        expect("8a1b1") { outputLongHexLCString(0x8A1B1) }
        expect("feeabc") { outputLongHexLCString(0xFEEABC) }
        expect("deadfeed") { outputLongHexLCString(0xDEADFEED) }
        expect("123deadfeed") { outputLongHexLCString(0x123DEADFEED) }
        expect("8000000000000000") { outputLongHexLCString(Long.MIN_VALUE) }
    }

    private suspend fun outputLongHexLCString(n: Long) = CoCapture().apply { outputLongHexLC(n) }.toString()

    @Test fun `should output 8 digits hex correctly using lambda`() = runBlocking {
        expect("00000000") { coOutput8HexString(0) }
        expect("00000001") { coOutput8HexString(1) }
        expect("0000ABCD") { coOutput8HexString(0xABCD) }
        expect("0009ABCD") { coOutput8HexString(0x9ABCD) }
        expect("0089ABCD") { coOutput8HexString(0x89ABCD) }
        expect("0E89ABCD") { coOutput8HexString(0xE89ABCD) }
        expect("7E89ABCD") { coOutput8HexString(0x7E89ABCD) }
    }

    private suspend fun coOutput8HexString(n: Int): String {
        val charArray = CharArray(8)
        var i = 0
        coOutput8Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 8 digits hex correctly using extension function`() = runBlocking {
        expect("00000000") { output8HexString(0) }
        expect("00000001") { output8HexString(1) }
        expect("0000ABCD") { output8HexString(0xABCD) }
        expect("0009ABCD") { output8HexString(0x9ABCD) }
        expect("0089ABCD") { output8HexString(0x89ABCD) }
        expect("0E89ABCD") { output8HexString(0xE89ABCD) }
        expect("7E89ABCD") { output8HexString(0x7E89ABCD) }
    }

    private suspend fun output8HexString(n: Int) = CoCapture().apply { output8Hex(n) }.toString()

    @Test fun `should output 8 digits hex correctly in lower case using lambda`() = runBlocking {
        expect("00000000") { coOutput8HexLCString(0) }
        expect("00000001") { coOutput8HexLCString(1) }
        expect("0000abcd") { coOutput8HexLCString(0xABCD) }
        expect("0009abcd") { coOutput8HexLCString(0x9ABCD) }
        expect("0089abcd") { coOutput8HexLCString(0x89ABCD) }
        expect("0e89abcd") { coOutput8HexLCString(0xE89ABCD) }
        expect("fe89abcd") { coOutput8HexLCString(0xFE89ABCD.toInt()) }
    }

    private suspend fun coOutput8HexLCString(n: Int): String {
        val charArray = CharArray(8)
        var i = 0
        coOutput8HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 8 digits hex correctly in lower case using extension function`() = runBlocking {
        expect("00000000") { output8HexLCString(0) }
        expect("00000001") { output8HexLCString(1) }
        expect("0000abcd") { output8HexLCString(0xABCD) }
        expect("0009abcd") { output8HexLCString(0x9ABCD) }
        expect("0089abcd") { output8HexLCString(0x89ABCD) }
        expect("0e89abcd") { output8HexLCString(0xE89ABCD) }
        expect("fe89abcd") { output8HexLCString(0xFE89ABCD.toInt()) }
    }

    private suspend fun output8HexLCString(n: Int) = CoCapture().apply { output8HexLC(n) }.toString()

    @Test fun `should output 4 digits hex correctly using lambda`() = runBlocking {
        expect("0000") { coOutput4HexString(0) }
        expect("0001") { coOutput4HexString(1) }
        expect("ABCD") { coOutput4HexString(0xABCD) }
    }

    private suspend fun coOutput4HexString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput4Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 4 digits hex correctly using extension function`() = runBlocking {
        expect("0000") { output4HexString(0) }
        expect("0001") { output4HexString(1) }
        expect("ABCD") { output4HexString(0xABCD) }
    }

    private suspend fun output4HexString(n: Int) = CoCapture().apply { output4Hex(n) }.toString()

    @Test fun `should output 4 digits hex correctly in lower case using lambda`() = runBlocking {
        expect("0000") { coOutput4HexLCString(0) }
        expect("0001") { coOutput4HexLCString(1) }
        expect("abcd") { coOutput4HexLCString(0xABCD) }
    }

    private suspend fun coOutput4HexLCString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput4HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 4 digits hex correctly in lower case`() = runBlocking {
        expect("0000") { output4HexLCString(0) }
        expect("0001") { output4HexLCString(1) }
        expect("abcd") { output4HexLCString(0xABCD) }
    }

    private suspend fun output4HexLCString(n: Int) = CoCapture().apply { output4HexLC(n) }.toString()

    @Test fun `should output 2 digits hex correctly using lambda`() = runBlocking {
        expect("00") { coOutput2HexString(0) }
        expect("01") { coOutput2HexString(1) }
        expect("AB") { coOutput2HexString(0xAB) }
    }

    private suspend fun coOutput2HexString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput2Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits hex correctly using extension function`() = runBlocking {
        expect("00") { output2HexString(0) }
        expect("01") { output2HexString(1) }
        expect("AB") { output2HexString(0xAB) }
    }

    private suspend fun output2HexString(n: Int) = CoCapture().apply { output2Hex(n) }.toString()

    @Test fun `should output 2 digits hex correctly in lower case using lambda`() = runBlocking {
        expect("00") { coOutput2HexLCString(0) }
        expect("01") { coOutput2HexLCString(1) }
        expect("ab") { coOutput2HexLCString(0xAB) }
    }

    private suspend fun coOutput2HexLCString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput2HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits hex correctly in lower case using extension function`() = runBlocking {
        expect("00") { output2HexLCString(0) }
        expect("01") { output2HexLCString(1) }
        expect("ab") { output2HexLCString(0xAB) }
    }

    private suspend fun output2HexLCString(n: Int) = CoCapture().apply { output2HexLC(n) }.toString()

    @Test fun `should output 1 digit hex correctly using lambda`() = runBlocking {
        expect("0") { coOutput1HexString(0) }
        expect("1") { coOutput1HexString(1) }
        expect("A") { coOutput1HexString(0xA) }
    }

    private suspend fun coOutput1HexString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput1Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 1 digit hex correctly using extension function`() = runBlocking {
        expect("0") { output1HexString(0) }
        expect("1") { output1HexString(1) }
        expect("A") { output1HexString(0xA) }
    }

    private suspend fun output1HexString(n: Int) = CoCapture().apply { output1Hex(n) }.toString()

    @Test fun `should output 1 digit hex correctly in lower case using lambda`() = runBlocking {
        expect("0") { coOutput1HexLCString(0) }
        expect("1") { coOutput1HexLCString(1) }
        expect("a") { coOutput1HexLCString(0xA) }
    }

    private suspend fun coOutput1HexLCString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput1HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 1 digit hex correctly in lower case using extension function`() = runBlocking {
        expect("0") { output1HexLCString(0) }
        expect("1") { output1HexLCString(1) }
        expect("a") { output1HexLCString(0xA) }
    }

    private suspend fun output1HexLCString(n: Int) = CoCapture().apply { output1HexLC(n) }.toString()

    companion object {
        fun maxValue() = Long.MAX_VALUE // this and the following are contrivances to avoid compiler warnings
        fun nineteenDigits() = 1234567890123456789
    }

    class CoCapture(size: Int = 256) : CoOutput {

        private val array = CharArray(size)
        private var index = 0

        override suspend fun invoke(ch: Char) {
            array[index++] = ch
        }

        override fun toString() = String(array, 0, index)

    }

}
