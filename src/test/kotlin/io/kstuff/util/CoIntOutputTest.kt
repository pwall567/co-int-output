/*
 * @(#) CoIntOutputTest.java
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

package io.kstuff.util

import kotlin.test.Test
import kotlinx.coroutines.runBlocking

import io.kstuff.test.shouldBe

import io.kstuff.util.CoIntOutput.coOutput1Digit
import io.kstuff.util.CoIntOutput.coOutput1DigitSafe
import io.kstuff.util.CoIntOutput.coOutput1Hex
import io.kstuff.util.CoIntOutput.coOutput1HexLC
import io.kstuff.util.CoIntOutput.coOutput2Digits
import io.kstuff.util.CoIntOutput.coOutput2DigitsSafe
import io.kstuff.util.CoIntOutput.coOutput2Hex
import io.kstuff.util.CoIntOutput.coOutput2HexLC
import io.kstuff.util.CoIntOutput.coOutput3Digits
import io.kstuff.util.CoIntOutput.coOutput3DigitsSafe
import io.kstuff.util.CoIntOutput.coOutput4Hex
import io.kstuff.util.CoIntOutput.coOutput4HexLC
import io.kstuff.util.CoIntOutput.coOutput8Hex
import io.kstuff.util.CoIntOutput.coOutput8HexLC
import io.kstuff.util.CoIntOutput.coOutputInt
import io.kstuff.util.CoIntOutput.coOutputIntGrouped
import io.kstuff.util.CoIntOutput.coOutputIntHex
import io.kstuff.util.CoIntOutput.coOutputIntHexLC
import io.kstuff.util.CoIntOutput.coOutputIntScaled
import io.kstuff.util.CoIntOutput.coOutputLong
import io.kstuff.util.CoIntOutput.coOutputLongGrouped
import io.kstuff.util.CoIntOutput.coOutputLongHex
import io.kstuff.util.CoIntOutput.coOutputLongHexLC
import io.kstuff.util.CoIntOutput.coOutputLongScaled
import io.kstuff.util.CoIntOutput.coOutputPositiveInt
import io.kstuff.util.CoIntOutput.coOutputPositiveIntGrouped
import io.kstuff.util.CoIntOutput.coOutputPositiveIntScaled
import io.kstuff.util.CoIntOutput.coOutputPositiveLong
import io.kstuff.util.CoIntOutput.coOutputPositiveLongGrouped
import io.kstuff.util.CoIntOutput.coOutputPositiveLongScaled
import io.kstuff.util.CoIntOutput.coOutputUnsignedInt
import io.kstuff.util.CoIntOutput.coOutputUnsignedLong
import io.kstuff.util.CoIntOutput.output1Digit
import io.kstuff.util.CoIntOutput.output1DigitSafe
import io.kstuff.util.CoIntOutput.output1Hex
import io.kstuff.util.CoIntOutput.output1HexLC
import io.kstuff.util.CoIntOutput.output2Digits
import io.kstuff.util.CoIntOutput.output2DigitsSafe
import io.kstuff.util.CoIntOutput.output2Hex
import io.kstuff.util.CoIntOutput.output2HexLC
import io.kstuff.util.CoIntOutput.output3Digits
import io.kstuff.util.CoIntOutput.output3DigitsSafe
import io.kstuff.util.CoIntOutput.output4Hex
import io.kstuff.util.CoIntOutput.output4HexLC
import io.kstuff.util.CoIntOutput.output8Hex
import io.kstuff.util.CoIntOutput.output8HexLC
import io.kstuff.util.CoIntOutput.outputInt
import io.kstuff.util.CoIntOutput.outputIntGrouped
import io.kstuff.util.CoIntOutput.outputIntHex
import io.kstuff.util.CoIntOutput.outputIntHexLC
import io.kstuff.util.CoIntOutput.outputIntScaled
import io.kstuff.util.CoIntOutput.outputLong
import io.kstuff.util.CoIntOutput.outputLongGrouped
import io.kstuff.util.CoIntOutput.outputLongHex
import io.kstuff.util.CoIntOutput.outputLongHexLC
import io.kstuff.util.CoIntOutput.outputLongScaled
import io.kstuff.util.CoIntOutput.outputPositiveInt
import io.kstuff.util.CoIntOutput.outputPositiveIntGrouped
import io.kstuff.util.CoIntOutput.outputPositiveIntScaled
import io.kstuff.util.CoIntOutput.outputPositiveLong
import io.kstuff.util.CoIntOutput.outputPositiveLongGrouped
import io.kstuff.util.CoIntOutput.outputPositiveLongScaled
import io.kstuff.util.CoIntOutput.outputUnsignedInt
import io.kstuff.util.CoIntOutput.outputUnsignedLong

class CoIntOutputTest {

    @Test fun `should convert int correctly using lambda`() = runBlocking {
        coOutputIntString(0) shouldBe "0"
        coOutputIntString(123456) shouldBe "123456"
        coOutputIntString(-22334455) shouldBe "-22334455"
        coOutputIntString(Int.MAX_VALUE) shouldBe "2147483647"
        coOutputIntString(Int.MIN_VALUE) shouldBe "-2147483648"
    }

    private suspend fun coOutputIntString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        val out: CoOutput = { charArray[i++] = it }
        coOutputInt(n, out)
        return String(charArray, 0, i)
    }

    @Test fun `should convert int correctly using extension function`() = runBlocking {
        outputIntString(0) shouldBe "0"
        outputIntString(123456) shouldBe "123456"
        outputIntString(-22334455) shouldBe "-22334455"
        outputIntString(Int.MAX_VALUE) shouldBe "2147483647"
        outputIntString(Int.MIN_VALUE) shouldBe "-2147483648"
    }

    private suspend fun outputIntString(n: Int) = CoCapture().apply { outputInt(n) }.toString()

    @Test fun `should convert positive int correctly using lambda`() = runBlocking {
        coOutputPositiveIntString(0) shouldBe "0"
        coOutputPositiveIntString(123456) shouldBe "123456"
        coOutputPositiveIntString(Int.MAX_VALUE) shouldBe "2147483647"
    }

    private suspend fun coOutputPositiveIntString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputPositiveInt(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert positive int correctly using extension function`() = runBlocking {
        outputPositiveIntString(0) shouldBe "0"
        outputPositiveIntString(123456) shouldBe "123456"
        outputPositiveIntString(Int.MAX_VALUE) shouldBe "2147483647"
    }

    private suspend fun outputPositiveIntString(n: Int) = CoCapture().apply { outputPositiveInt(n) }.toString()

    @Test fun `should convert unsigned int correctly using lambda`() = runBlocking {
        coOutputUnsignedIntString(0) shouldBe "0"
        coOutputUnsignedIntString(123456) shouldBe "123456"
        coOutputUnsignedIntString(2147483648.toInt()) shouldBe "2147483648"
        coOutputUnsignedIntString(3456789012.toInt()) shouldBe "3456789012"
        coOutputUnsignedIntString(0x89ABCDEF.toInt()) shouldBe "2309737967"
    }

    private suspend fun coOutputUnsignedIntString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputUnsignedInt(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert unsigned int correctly using extension function`() = runBlocking {
        outputUnsignedIntString(0) shouldBe "0"
        outputUnsignedIntString(123456) shouldBe "123456"
        outputUnsignedIntString(2147483648.toInt()) shouldBe "2147483648"
        outputUnsignedIntString(3456789012.toInt()) shouldBe "3456789012"
        outputUnsignedIntString(0x89ABCDEF.toInt()) shouldBe "2309737967"
    }

    private suspend fun outputUnsignedIntString(n: Int) = CoCapture().apply { outputUnsignedInt(n) }.toString()

    @Test fun `should convert int with scaling using lambda`() = runBlocking {
        coOutputIntScaledString(0, 0) shouldBe "0"
        coOutputIntScaledString(0, 1) shouldBe "0.0"
        coOutputIntScaledString(0, 2) shouldBe "0.00"
        coOutputIntScaledString(0, 3) shouldBe "0.000"
        coOutputIntScaledString(123456, 0) shouldBe "123456"
        coOutputIntScaledString(123456, 1) shouldBe "12345.6"
        coOutputIntScaledString(123456, 2) shouldBe "1234.56"
        coOutputIntScaledString(123456, 3) shouldBe "123.456"
        coOutputIntScaledString(123456, 6) shouldBe "0.123456"
        coOutputIntScaledString(123456, 7) shouldBe "0.0123456"
        coOutputIntScaledString(123456, 8) shouldBe "0.00123456"
        coOutputIntScaledString(-22334455, 0) shouldBe "-22334455"
        coOutputIntScaledString(-22334455, 1) shouldBe "-2233445.5"
        coOutputIntScaledString(-22334455, 2) shouldBe "-223344.55"
        coOutputIntScaledString(-22334455, 3) shouldBe "-22334.455"
        coOutputIntScaledString(Int.MAX_VALUE, 0) shouldBe "2147483647"
        coOutputIntScaledString(Int.MAX_VALUE, 1) shouldBe "214748364.7"
        coOutputIntScaledString(Int.MAX_VALUE, 2) shouldBe "21474836.47"
        coOutputIntScaledString(Int.MAX_VALUE, 3) shouldBe "2147483.647"
        coOutputIntScaledString(Int.MIN_VALUE, 0) shouldBe "-2147483648"
        coOutputIntScaledString(Int.MIN_VALUE, 1) shouldBe "-214748364.8"
        coOutputIntScaledString(Int.MIN_VALUE, 2) shouldBe "-21474836.48"
        coOutputIntScaledString(Int.MIN_VALUE, 3) shouldBe "-2147483.648"
        coOutputIntScaledString(Int.MIN_VALUE, 10) shouldBe "-0.2147483648"
        coOutputIntScaledString(Int.MIN_VALUE, 11) shouldBe "-0.02147483648"
        coOutputIntScaledString(Int.MIN_VALUE, 12) shouldBe "-0.002147483648"
    }

    private suspend fun coOutputIntScaledString(n: Int, scale: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntScaled(n, scale) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert int with scaling using extension function`() = runBlocking {
        outputIntScaledString(0, 0) shouldBe "0"
        outputIntScaledString(0, 1) shouldBe "0.0"
        outputIntScaledString(0, 2) shouldBe "0.00"
        outputIntScaledString(0, 3) shouldBe "0.000"
        outputIntScaledString(123456, 0) shouldBe "123456"
        outputIntScaledString(123456, 1) shouldBe "12345.6"
        outputIntScaledString(123456, 2) shouldBe "1234.56"
        outputIntScaledString(123456, 3) shouldBe "123.456"
        outputIntScaledString(123456, 6) shouldBe "0.123456"
        outputIntScaledString(123456, 7) shouldBe "0.0123456"
        outputIntScaledString(123456, 8) shouldBe "0.00123456"
        outputIntScaledString(-22334455, 0) shouldBe "-22334455"
        outputIntScaledString(-22334455, 1) shouldBe "-2233445.5"
        outputIntScaledString(-22334455, 2) shouldBe "-223344.55"
        outputIntScaledString(-22334455, 3) shouldBe "-22334.455"
        outputIntScaledString(Int.MAX_VALUE, 0) shouldBe "2147483647"
        outputIntScaledString(Int.MAX_VALUE, 1) shouldBe "214748364.7"
        outputIntScaledString(Int.MAX_VALUE, 2) shouldBe "21474836.47"
        outputIntScaledString(Int.MAX_VALUE, 3) shouldBe "2147483.647"
        outputIntScaledString(Int.MIN_VALUE, 0) shouldBe "-2147483648"
        outputIntScaledString(Int.MIN_VALUE, 1) shouldBe "-214748364.8"
        outputIntScaledString(Int.MIN_VALUE, 2) shouldBe "-21474836.48"
        outputIntScaledString(Int.MIN_VALUE, 3) shouldBe "-2147483.648"
        outputIntScaledString(Int.MIN_VALUE, 10) shouldBe "-0.2147483648"
        outputIntScaledString(Int.MIN_VALUE, 11) shouldBe "-0.02147483648"
        outputIntScaledString(Int.MIN_VALUE, 12) shouldBe "-0.002147483648"
    }

    private suspend fun outputIntScaledString(n: Int, scale: Int) =
            CoCapture().apply { outputIntScaled(n, scale) }.toString()

    @Test fun `should convert positive int with scaling using lambda`() = runBlocking {
        coOutputPositiveIntScaledString(0, 0) shouldBe "0"
        coOutputPositiveIntScaledString(0, 1) shouldBe "0.0"
        coOutputPositiveIntScaledString(0, 2) shouldBe "0.00"
        coOutputPositiveIntScaledString(123456, 0) shouldBe "123456"
        coOutputPositiveIntScaledString(123456, 1) shouldBe "12345.6"
        coOutputPositiveIntScaledString(123456, 2) shouldBe "1234.56"
    }

    private suspend fun coOutputPositiveIntScaledString(n: Int, scale: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputPositiveIntScaled(n, scale) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert positive int with scaling using extension function`() = runBlocking {
        outputPositiveIntScaledString(0, 0) shouldBe "0"
        outputPositiveIntScaledString(0, 1) shouldBe "0.0"
        outputPositiveIntScaledString(0, 2) shouldBe "0.00"
        outputPositiveIntScaledString(123456, 0) shouldBe "123456"
        outputPositiveIntScaledString(123456, 1) shouldBe "12345.6"
        outputPositiveIntScaledString(123456, 2) shouldBe "1234.56"
    }

    private suspend fun outputPositiveIntScaledString(n: Int, scale: Int) =
            CoCapture().apply { outputPositiveIntScaled(n, scale) }.toString()

    @Test fun `should convert long correctly using lambda`() = runBlocking {
        coOutputLongString(0) shouldBe "0"
        coOutputLongString(123456789012345678) shouldBe "123456789012345678"
        coOutputLongString(-2233445566778899) shouldBe "-2233445566778899"
        coOutputLongString(Int.MAX_VALUE.toLong()) shouldBe "2147483647"
        coOutputLongString(Int.MIN_VALUE.toLong()) shouldBe "-2147483648"
        coOutputLongString(Long.MAX_VALUE) shouldBe "9223372036854775807"
        coOutputLongString(Long.MIN_VALUE) shouldBe "-9223372036854775808"
    }

    private suspend fun coOutputLongString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLong(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long correctly using extension function`() = runBlocking {
        outputLongString(0) shouldBe "0"
        outputLongString(123456789012345678) shouldBe "123456789012345678"
        outputLongString(-2233445566778899) shouldBe "-2233445566778899"
        outputLongString(Int.MAX_VALUE.toLong()) shouldBe "2147483647"
        outputLongString(Int.MIN_VALUE.toLong()) shouldBe "-2147483648"
        outputLongString(Long.MAX_VALUE) shouldBe "9223372036854775807"
        outputLongString(Long.MIN_VALUE) shouldBe "-9223372036854775808"
    }

    private suspend fun outputLongString(n: Long) = CoCapture().apply { outputLong(n) }.toString()

    @Test fun `should convert positive long correctly using lambda`() = runBlocking {
        coOutputPositiveLongString(0) shouldBe "0"
        coOutputPositiveLongString(123456789012345678) shouldBe "123456789012345678"
        coOutputPositiveLongString(Int.MAX_VALUE.toLong()) shouldBe "2147483647"
        coOutputPositiveLongString(Long.MAX_VALUE) shouldBe "9223372036854775807"
    }

    private suspend fun coOutputPositiveLongString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputPositiveLong(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert positive long correctly using extension function`() = runBlocking {
        outputPositiveLongString(0) shouldBe "0"
        outputPositiveLongString(123456789012345678) shouldBe "123456789012345678"
        outputPositiveLongString(Int.MAX_VALUE.toLong()) shouldBe "2147483647"
        outputPositiveLongString(Long.MAX_VALUE) shouldBe "9223372036854775807"
    }

    private suspend fun outputPositiveLongString(n: Long) = CoCapture().apply { outputPositiveLong(n) }.toString()

    @Test fun `should convert unsigned long correctly using lambda`() = runBlocking {
        coOutputUnsignedLongString(0) shouldBe "0"
        coOutputUnsignedLongString(1234567890123456789) shouldBe "1234567890123456789"
        coOutputUnsignedLongString(maxValue() + 1) shouldBe "9223372036854775808"
        coOutputUnsignedLongString(nineteenDigits() * 10) shouldBe "12345678901234567890"
    }

    private suspend fun coOutputUnsignedLongString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputUnsignedLong(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert unsigned long correctly using extension function`() = runBlocking {
        outputUnsignedLongString(0) shouldBe "0"
        outputUnsignedLongString(1234567890123456789) shouldBe "1234567890123456789"
        outputUnsignedLongString(maxValue() + 1) shouldBe "9223372036854775808"
        outputUnsignedLongString(nineteenDigits() * 10) shouldBe "12345678901234567890"
    }

    private suspend fun outputUnsignedLongString(n: Long) = CoCapture().apply { outputUnsignedLong(n) }.toString()

    @Test fun `should convert long with scaling using lambda`() = runBlocking {
        coOutputLongScaledString(0, 0) shouldBe "0"
        coOutputLongScaledString(0, 1) shouldBe "0.0"
        coOutputLongScaledString(0, 2) shouldBe "0.00"
        coOutputLongScaledString(0, 3) shouldBe "0.000"
        coOutputLongScaledString(123456, 0) shouldBe "123456"
        coOutputLongScaledString(123456, 1) shouldBe "12345.6"
        coOutputLongScaledString(123456, 2) shouldBe "1234.56"
        coOutputLongScaledString(123456, 3) shouldBe "123.456"
        coOutputLongScaledString(123456, 6) shouldBe "0.123456"
        coOutputLongScaledString(123456, 7) shouldBe "0.0123456"
        coOutputLongScaledString(123456, 8) shouldBe "0.00123456"
        coOutputLongScaledString(-22334455, 0) shouldBe "-22334455"
        coOutputLongScaledString(-22334455, 1) shouldBe "-2233445.5"
        coOutputLongScaledString(-22334455, 2) shouldBe "-223344.55"
        coOutputLongScaledString(-22334455, 3) shouldBe "-22334.455"
        coOutputLongScaledString(123456789012345678, 0) shouldBe "123456789012345678"
        coOutputLongScaledString(123456789012345678, 1) shouldBe "12345678901234567.8"
        coOutputLongScaledString(123456789012345678, 2) shouldBe "1234567890123456.78"
        coOutputLongScaledString(123456789012345678, 3) shouldBe "123456789012345.678"
        coOutputLongScaledString(123456789012345678, 18) shouldBe "0.123456789012345678"
        coOutputLongScaledString(123456789012345678, 19) shouldBe "0.0123456789012345678"
        coOutputLongScaledString(123456789012345678, 20) shouldBe "0.00123456789012345678"
        coOutputLongScaledString(-2233445566778899, 0) shouldBe "-2233445566778899"
        coOutputLongScaledString(-2233445566778899, 1) shouldBe "-223344556677889.9"
        coOutputLongScaledString(-2233445566778899, 16) shouldBe "-0.2233445566778899"
        coOutputLongScaledString(-2233445566778899, 17) shouldBe "-0.02233445566778899"
        coOutputLongScaledString(Long.MAX_VALUE, 0) shouldBe "9223372036854775807"
        coOutputLongScaledString(Long.MAX_VALUE, 1) shouldBe "922337203685477580.7"
        coOutputLongScaledString(Long.MAX_VALUE, 2) shouldBe "92233720368547758.07"
        coOutputLongScaledString(Long.MAX_VALUE, 3) shouldBe "9223372036854775.807"
        coOutputLongScaledString(Long.MIN_VALUE, 0) shouldBe "-9223372036854775808"
        coOutputLongScaledString(Long.MIN_VALUE, 1) shouldBe "-922337203685477580.8"
        coOutputLongScaledString(Long.MIN_VALUE, 2) shouldBe "-92233720368547758.08"
        coOutputLongScaledString(Long.MIN_VALUE, 3) shouldBe "-9223372036854775.808"
        coOutputLongScaledString(Long.MIN_VALUE, 10) shouldBe "-922337203.6854775808"
        coOutputLongScaledString(Long.MIN_VALUE, 19) shouldBe "-0.9223372036854775808"
        coOutputLongScaledString(Long.MIN_VALUE, 20) shouldBe "-0.09223372036854775808"
        coOutputLongScaledString(Long.MIN_VALUE, 21) shouldBe "-0.009223372036854775808"
    }

    private suspend fun coOutputLongScaledString(n: Long, scale: Int): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongScaled(n, scale) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long with scaling using extension function`() = runBlocking {
        outputLongScaledString(0, 0) shouldBe "0"
        outputLongScaledString(0, 1) shouldBe "0.0"
        outputLongScaledString(0, 2) shouldBe "0.00"
        outputLongScaledString(0, 3) shouldBe "0.000"
        outputLongScaledString(123456, 0) shouldBe "123456"
        outputLongScaledString(123456, 1) shouldBe "12345.6"
        outputLongScaledString(123456, 2) shouldBe "1234.56"
        outputLongScaledString(123456, 3) shouldBe "123.456"
        outputLongScaledString(123456, 6) shouldBe "0.123456"
        outputLongScaledString(123456, 7) shouldBe "0.0123456"
        outputLongScaledString(123456, 8) shouldBe "0.00123456"
        outputLongScaledString(-22334455, 0) shouldBe "-22334455"
        outputLongScaledString(-22334455, 1) shouldBe "-2233445.5"
        outputLongScaledString(-22334455, 2) shouldBe "-223344.55"
        outputLongScaledString(-22334455, 3) shouldBe "-22334.455"
        outputLongScaledString(123456789012345678, 0) shouldBe "123456789012345678"
        outputLongScaledString(123456789012345678, 1) shouldBe "12345678901234567.8"
        outputLongScaledString(123456789012345678, 2) shouldBe "1234567890123456.78"
        outputLongScaledString(123456789012345678, 3) shouldBe "123456789012345.678"
        outputLongScaledString(123456789012345678, 18) shouldBe "0.123456789012345678"
        outputLongScaledString(123456789012345678, 19) shouldBe "0.0123456789012345678"
        outputLongScaledString(123456789012345678, 20) shouldBe "0.00123456789012345678"
        outputLongScaledString(-2233445566778899, 0) shouldBe "-2233445566778899"
        outputLongScaledString(-2233445566778899, 1) shouldBe "-223344556677889.9"
        outputLongScaledString(-2233445566778899, 16) shouldBe "-0.2233445566778899"
        outputLongScaledString(-2233445566778899, 17) shouldBe "-0.02233445566778899"
        outputLongScaledString(Long.MAX_VALUE, 0) shouldBe "9223372036854775807"
        outputLongScaledString(Long.MAX_VALUE, 1) shouldBe "922337203685477580.7"
        outputLongScaledString(Long.MAX_VALUE, 2) shouldBe "92233720368547758.07"
        outputLongScaledString(Long.MAX_VALUE, 3) shouldBe "9223372036854775.807"
        outputLongScaledString(Long.MIN_VALUE, 0) shouldBe "-9223372036854775808"
        outputLongScaledString(Long.MIN_VALUE, 1) shouldBe "-922337203685477580.8"
        outputLongScaledString(Long.MIN_VALUE, 2) shouldBe "-92233720368547758.08"
        outputLongScaledString(Long.MIN_VALUE, 3) shouldBe "-9223372036854775.808"
        outputLongScaledString(Long.MIN_VALUE, 10) shouldBe "-922337203.6854775808"
        outputLongScaledString(Long.MIN_VALUE, 19) shouldBe "-0.9223372036854775808"
        outputLongScaledString(Long.MIN_VALUE, 20) shouldBe "-0.09223372036854775808"
        outputLongScaledString(Long.MIN_VALUE, 21) shouldBe "-0.009223372036854775808"
    }

    private suspend fun outputLongScaledString(n: Long, scale: Int) =
            CoCapture().apply { outputLongScaled(n, scale) }.toString()

    @Test fun `should convert positive long with scaling using lambda`() = runBlocking {
        coOutputPositiveLongScaledString(0, 0) shouldBe "0"
        coOutputPositiveLongScaledString(0, 1) shouldBe "0.0"
        coOutputPositiveLongScaledString(0, 2) shouldBe "0.00"
        coOutputPositiveLongScaledString(123456789012345678, 0) shouldBe "123456789012345678"
        coOutputPositiveLongScaledString(123456789012345678, 1) shouldBe "12345678901234567.8"
        coOutputPositiveLongScaledString(123456789012345678, 2) shouldBe "1234567890123456.78"
    }

    private suspend fun coOutputPositiveLongScaledString(n: Long, scale: Int): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputPositiveLongScaled(n, scale) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert positive long with scaling using extension function`() = runBlocking {
        outputPositiveLongScaledString(0, 0) shouldBe "0"
        outputPositiveLongScaledString(0, 1) shouldBe "0.0"
        outputPositiveLongScaledString(0, 2) shouldBe "0.00"
        outputPositiveLongScaledString(123456789012345678, 0) shouldBe "123456789012345678"
        outputPositiveLongScaledString(123456789012345678, 1) shouldBe "12345678901234567.8"
        outputPositiveLongScaledString(123456789012345678, 2) shouldBe "1234567890123456.78"
    }

    private suspend fun outputPositiveLongScaledString(n: Long, scale: Int) =
            CoCapture().apply { outputPositiveLongScaled(n, scale) }.toString()

    @Test fun `should output 1 digit correctly using lambda`() = runBlocking {
        coOutput1DigitString(0) shouldBe "0"
        coOutput1DigitString(1) shouldBe "1"
        coOutput1DigitString(9) shouldBe "9"
    }

    private suspend fun coOutput1DigitString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput1Digit(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 1 digit correctly using extension function`() = runBlocking {
        output1DigitString(0) shouldBe "0"
        output1DigitString(1) shouldBe "1"
        output1DigitString(9) shouldBe "9"
    }

    private suspend fun output1DigitString(n: Int) = CoCapture().apply { output1Digit(n) }.toString()

    @Test fun `should output 1 digit safely using lambda`() = runBlocking {
        coOutput1DigitSafeString(20) shouldBe "0"
        coOutput1DigitSafeString(-61) shouldBe "1"
        coOutput1DigitSafeString(999999) shouldBe "9"
    }

    private suspend fun coOutput1DigitSafeString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput1DigitSafe(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 1 digit safely using extension function`() = runBlocking {
        output1DigitSafeString(20) shouldBe "0"
        output1DigitSafeString(-61) shouldBe "1"
        output1DigitSafeString(999999) shouldBe "9"
    }

    private suspend fun output1DigitSafeString(n: Int) = CoCapture().apply { output1DigitSafe(n) }.toString()

    @Test fun `should output 2 digits correctly using lambda`() = runBlocking {
        coOutput2DigitsString(0) shouldBe "00"
        coOutput2DigitsString(1) shouldBe "01"
        coOutput2DigitsString(21) shouldBe "21"
    }

    private suspend fun coOutput2DigitsString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput2Digits(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits correctly using extension function`() = runBlocking {
        output2DigitsString(0) shouldBe "00"
        output2DigitsString(1) shouldBe "01"
        output2DigitsString(21) shouldBe "21"
    }

    private suspend fun output2DigitsString(n: Int) = CoCapture().apply { output2Digits(n) }.toString()

    @Test fun `should output 2 digits safely using lambda`() = runBlocking {
        coOutput2DigitsSafeString(10000) shouldBe "00"
        coOutput2DigitsSafeString(-501) shouldBe "01"
        coOutput2DigitsSafeString(7654321) shouldBe "21"
    }

    private suspend fun coOutput2DigitsSafeString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput2DigitsSafe(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits safely using extension function`() = runBlocking {
        output2DigitsSafeString(10000) shouldBe "00"
        output2DigitsSafeString(-501) shouldBe "01"
        output2DigitsSafeString(7654321) shouldBe "21"
    }

    private suspend fun output2DigitsSafeString(n: Int) = CoCapture().apply { output2DigitsSafe(n) }.toString()

    @Test fun `should output 3 digits correctly using lambda`() = runBlocking {
        coOutput3DigitsString(0) shouldBe "000"
        coOutput3DigitsString(1) shouldBe "001"
        coOutput3DigitsString(21) shouldBe "021"
        coOutput3DigitsString(321) shouldBe "321"
    }

    private suspend fun coOutput3DigitsString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput3Digits(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 3 digits correctly using extension function`() = runBlocking {
        output3DigitsString(0) shouldBe "000"
        output3DigitsString(1) shouldBe "001"
        output3DigitsString(21) shouldBe "021"
        output3DigitsString(321) shouldBe "321"
    }

    private suspend fun output3DigitsString(n: Int) = CoCapture().apply { output3Digits(n) }.toString()

    @Test fun `should output 3 digits safely using lambda`() = runBlocking {
        coOutput3DigitsSafeString(2000) shouldBe "000"
        coOutput3DigitsSafeString(-15001) shouldBe "001"
        coOutput3DigitsSafeString(66021) shouldBe "021"
        coOutput3DigitsSafeString(987654321) shouldBe "321"
    }

    private suspend fun coOutput3DigitsSafeString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput3DigitsSafe(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 3 digits safely using extension function`() = runBlocking {
        output3DigitsSafeString(2000) shouldBe "000"
        output3DigitsSafeString(-15001) shouldBe "001"
        output3DigitsSafeString(66021) shouldBe "021"
        output3DigitsSafeString(987654321) shouldBe "321"
    }

    private suspend fun output3DigitsSafeString(n: Int) = CoCapture().apply { output3DigitsSafe(n) }.toString()

    @Test fun `should output integer with grouping using lambda`() = runBlocking {
        coOutputIntGroupedString(0) shouldBe "0"
        coOutputIntGroupedString(1) shouldBe "1"
        coOutputIntGroupedString(12) shouldBe "12"
        coOutputIntGroupedString(123) shouldBe "123"
        coOutputIntGroupedString(1234) shouldBe "1,234"
        coOutputIntGroupedString(12345) shouldBe "12,345"
        coOutputIntGroupedString(123456) shouldBe "123,456"
        coOutputIntGroupedString(1234567) shouldBe "1,234,567"
        coOutputIntGroupedString(12345678) shouldBe "12,345,678"
        coOutputIntGroupedString(123456789) shouldBe "123,456,789"
        coOutputIntGroupedString(1234567890) shouldBe "1,234,567,890"
        coOutputIntGroupedString(Int.MAX_VALUE, ' ') shouldBe "2 147 483 647"
        coOutputIntGroupedString(Int.MIN_VALUE, ' ') shouldBe "-2 147 483 648"
        coOutputIntGroupedString(-1) shouldBe "-1"
        coOutputIntGroupedString(-12) shouldBe "-12"
        coOutputIntGroupedString(-123) shouldBe "-123"
        coOutputIntGroupedString(-1234) shouldBe "-1,234"
        coOutputIntGroupedString(-12345) shouldBe "-12,345"
        coOutputIntGroupedString(-123456) shouldBe "-123,456"
        coOutputIntGroupedString(-1234567) shouldBe "-1,234,567"
        coOutputIntGroupedString(-12345678) shouldBe "-12,345,678"
        coOutputIntGroupedString(-123456789) shouldBe "-123,456,789"
        coOutputIntGroupedString(-1234567890) shouldBe "-1,234,567,890"
    }

    private suspend fun coOutputIntGroupedString(n: Int, groupingChar: Char = ','): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output integer with grouping using extension function`() = runBlocking {
        outputIntGroupedString(0) shouldBe "0"
        outputIntGroupedString(1) shouldBe "1"
        outputIntGroupedString(12) shouldBe "12"
        outputIntGroupedString(123) shouldBe "123"
        outputIntGroupedString(1234) shouldBe "1,234"
        outputIntGroupedString(12345) shouldBe "12,345"
        outputIntGroupedString(123456) shouldBe "123,456"
        outputIntGroupedString(1234567) shouldBe "1,234,567"
        outputIntGroupedString(12345678) shouldBe "12,345,678"
        outputIntGroupedString(123456789) shouldBe "123,456,789"
        outputIntGroupedString(1234567890) shouldBe "1,234,567,890"
        outputIntGroupedString(Int.MAX_VALUE, ' ') shouldBe "2 147 483 647"
        outputIntGroupedString(Int.MIN_VALUE, ' ') shouldBe "-2 147 483 648"
        outputIntGroupedString(-1) shouldBe "-1"
        outputIntGroupedString(-12) shouldBe "-12"
        outputIntGroupedString(-123) shouldBe "-123"
        outputIntGroupedString(-1234) shouldBe "-1,234"
        outputIntGroupedString(-12345) shouldBe "-12,345"
        outputIntGroupedString(-123456) shouldBe "-123,456"
        outputIntGroupedString(-1234567) shouldBe "-1,234,567"
        outputIntGroupedString(-12345678) shouldBe "-12,345,678"
        outputIntGroupedString(-123456789) shouldBe "-123,456,789"
        outputIntGroupedString(-1234567890) shouldBe "-1,234,567,890"
    }

    private suspend fun outputIntGroupedString(n: Int, groupingChar: Char = ',') =
            CoCapture().apply { outputIntGrouped(n, groupingChar) }.toString()

    @Test fun `should output positive integer with grouping using lambda`() = runBlocking {
        coOutputPositiveIntGroupedString(0) shouldBe "0"
        coOutputPositiveIntGroupedString(1) shouldBe "1"
        coOutputPositiveIntGroupedString(12) shouldBe "12"
        coOutputPositiveIntGroupedString(123) shouldBe "123"
        coOutputPositiveIntGroupedString(1234) shouldBe "1,234"
        coOutputPositiveIntGroupedString(12345) shouldBe "12,345"
        coOutputPositiveIntGroupedString(123456) shouldBe "123,456"
        coOutputPositiveIntGroupedString(1234567) shouldBe "1,234,567"
        coOutputPositiveIntGroupedString(12345678) shouldBe "12,345,678"
        coOutputPositiveIntGroupedString(123456789) shouldBe "123,456,789"
        coOutputPositiveIntGroupedString(1234567890) shouldBe "1,234,567,890"
        coOutputPositiveIntGroupedString(Int.MAX_VALUE, ' ') shouldBe "2 147 483 647"
    }

    private suspend fun coOutputPositiveIntGroupedString(n: Int, groupingChar: Char = ','): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputPositiveIntGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output positive integer with grouping using extension function`() = runBlocking {
        outputPositiveIntGroupedString(0) shouldBe "0"
        outputPositiveIntGroupedString(1) shouldBe "1"
        outputPositiveIntGroupedString(12) shouldBe "12"
        outputPositiveIntGroupedString(123) shouldBe "123"
        outputPositiveIntGroupedString(1234) shouldBe "1,234"
        outputPositiveIntGroupedString(12345) shouldBe "12,345"
        outputPositiveIntGroupedString(123456) shouldBe "123,456"
        outputPositiveIntGroupedString(1234567) shouldBe "1,234,567"
        outputPositiveIntGroupedString(12345678) shouldBe "12,345,678"
        outputPositiveIntGroupedString(123456789) shouldBe "123,456,789"
        outputPositiveIntGroupedString(1234567890) shouldBe "1,234,567,890"
        outputPositiveIntGroupedString(Int.MAX_VALUE, ' ') shouldBe "2 147 483 647"
    }

    private suspend fun outputPositiveIntGroupedString(n: Int, groupingChar: Char = ',') =
            CoCapture().apply { outputPositiveIntGrouped(n, groupingChar) }.toString()

    @Test fun `should output long with grouping using lambda`() = runBlocking {
        coOutputLongGroupedString(0) shouldBe "0"
        coOutputLongGroupedString(1) shouldBe "1"
        coOutputLongGroupedString(12) shouldBe "12"
        coOutputLongGroupedString(123) shouldBe "123"
        coOutputLongGroupedString(1234) shouldBe "1,234"
        coOutputLongGroupedString(12345) shouldBe "12,345"
        coOutputLongGroupedString(123456) shouldBe "123,456"
        coOutputLongGroupedString(1234567) shouldBe "1,234,567"
        coOutputLongGroupedString(12345678) shouldBe "12,345,678"
        coOutputLongGroupedString(123456789) shouldBe "123,456,789"
        coOutputLongGroupedString(1234567890) shouldBe "1,234,567,890"
        coOutputLongGroupedString(12345678901) shouldBe "12,345,678,901"
        coOutputLongGroupedString(123456789012) shouldBe "123,456,789,012"
        coOutputLongGroupedString(1234567890123) shouldBe "1,234,567,890,123"
        coOutputLongGroupedString(12345678901234) shouldBe "12,345,678,901,234"
        coOutputLongGroupedString(123456789012345) shouldBe "123,456,789,012,345"
        coOutputLongGroupedString(1234567890123456) shouldBe "1,234,567,890,123,456"
        coOutputLongGroupedString(12345678901234567) shouldBe "12,345,678,901,234,567"
        coOutputLongGroupedString(123456789012345678) shouldBe "123,456,789,012,345,678"
        coOutputLongGroupedString(1234567890123456789) shouldBe "1,234,567,890,123,456,789"
        coOutputLongGroupedString(Int.MAX_VALUE.toLong(), ' ') shouldBe "2 147 483 647"
        coOutputLongGroupedString(Int.MIN_VALUE.toLong(), ' ') shouldBe "-2 147 483 648"
        coOutputLongGroupedString(Long.MAX_VALUE, '_') shouldBe "9_223_372_036_854_775_807"
        coOutputLongGroupedString(Long.MIN_VALUE) shouldBe "-9,223,372,036,854,775,808"
        coOutputLongGroupedString(-1) shouldBe "-1"
        coOutputLongGroupedString(-12) shouldBe "-12"
        coOutputLongGroupedString(-123) shouldBe "-123"
        coOutputLongGroupedString(-1234) shouldBe "-1,234"
        coOutputLongGroupedString(-12345) shouldBe "-12,345"
        coOutputLongGroupedString(-123456) shouldBe "-123,456"
        coOutputLongGroupedString(-1234567) shouldBe "-1,234,567"
        coOutputLongGroupedString(-12345678) shouldBe "-12,345,678"
        coOutputLongGroupedString(-123456789) shouldBe "-123,456,789"
        coOutputLongGroupedString(-1234567890) shouldBe "-1,234,567,890"
        coOutputLongGroupedString(-12345678901) shouldBe "-12,345,678,901"
        coOutputLongGroupedString(-123456789012) shouldBe "-123,456,789,012"
        coOutputLongGroupedString(-1234567890123) shouldBe "-1,234,567,890,123"
        coOutputLongGroupedString(-12345678901234) shouldBe "-12,345,678,901,234"
        coOutputLongGroupedString(-123456789012345) shouldBe "-123,456,789,012,345"
        coOutputLongGroupedString(-1234567890123456) shouldBe "-1,234,567,890,123,456"
        coOutputLongGroupedString(-12345678901234567) shouldBe "-12,345,678,901,234,567"
        coOutputLongGroupedString(-123456789012345678) shouldBe "-123,456,789,012,345,678"
        coOutputLongGroupedString(-1234567890123456789) shouldBe "-1,234,567,890,123,456,789"
    }

    private suspend fun coOutputLongGroupedString(n: Long, groupingChar: Char = ','): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output long with grouping using extension function`() = runBlocking {
        outputLongGroupedString(0) shouldBe "0"
        outputLongGroupedString(1) shouldBe "1"
        outputLongGroupedString(12) shouldBe "12"
        outputLongGroupedString(123) shouldBe "123"
        outputLongGroupedString(1234) shouldBe "1,234"
        outputLongGroupedString(12345) shouldBe "12,345"
        outputLongGroupedString(123456) shouldBe "123,456"
        outputLongGroupedString(1234567) shouldBe "1,234,567"
        outputLongGroupedString(12345678) shouldBe "12,345,678"
        outputLongGroupedString(123456789) shouldBe "123,456,789"
        outputLongGroupedString(1234567890) shouldBe "1,234,567,890"
        outputLongGroupedString(12345678901) shouldBe "12,345,678,901"
        outputLongGroupedString(123456789012) shouldBe "123,456,789,012"
        outputLongGroupedString(1234567890123) shouldBe "1,234,567,890,123"
        outputLongGroupedString(12345678901234) shouldBe "12,345,678,901,234"
        outputLongGroupedString(123456789012345) shouldBe "123,456,789,012,345"
        outputLongGroupedString(1234567890123456) shouldBe "1,234,567,890,123,456"
        outputLongGroupedString(12345678901234567) shouldBe "12,345,678,901,234,567"
        outputLongGroupedString(123456789012345678) shouldBe "123,456,789,012,345,678"
        outputLongGroupedString(1234567890123456789) shouldBe "1,234,567,890,123,456,789"
        outputLongGroupedString(Int.MAX_VALUE.toLong(), ' ') shouldBe "2 147 483 647"
        outputLongGroupedString(Int.MIN_VALUE.toLong(), ' ') shouldBe "-2 147 483 648"
        outputLongGroupedString(Long.MAX_VALUE, '_') shouldBe "9_223_372_036_854_775_807"
        outputLongGroupedString(Long.MIN_VALUE) shouldBe "-9,223,372,036,854,775,808"
        outputLongGroupedString(-1) shouldBe "-1"
        outputLongGroupedString(-12) shouldBe "-12"
        outputLongGroupedString(-123) shouldBe "-123"
        outputLongGroupedString(-1234) shouldBe "-1,234"
        outputLongGroupedString(-12345) shouldBe "-12,345"
        outputLongGroupedString(-123456) shouldBe "-123,456"
        outputLongGroupedString(-1234567) shouldBe "-1,234,567"
        outputLongGroupedString(-12345678) shouldBe "-12,345,678"
        outputLongGroupedString(-123456789) shouldBe "-123,456,789"
        outputLongGroupedString(-1234567890) shouldBe "-1,234,567,890"
        outputLongGroupedString(-12345678901) shouldBe "-12,345,678,901"
        outputLongGroupedString(-123456789012) shouldBe "-123,456,789,012"
        outputLongGroupedString(-1234567890123) shouldBe "-1,234,567,890,123"
        outputLongGroupedString(-12345678901234) shouldBe "-12,345,678,901,234"
        outputLongGroupedString(-123456789012345) shouldBe "-123,456,789,012,345"
        outputLongGroupedString(-1234567890123456) shouldBe "-1,234,567,890,123,456"
        outputLongGroupedString(-12345678901234567) shouldBe "-12,345,678,901,234,567"
        outputLongGroupedString(-123456789012345678) shouldBe "-123,456,789,012,345,678"
        outputLongGroupedString(-1234567890123456789) shouldBe "-1,234,567,890,123,456,789"
    }

    private suspend fun outputLongGroupedString(n: Long, groupingChar: Char = ',') =
            CoCapture().apply { outputLongGrouped(n, groupingChar) }.toString()

    @Test fun `should output positive long with grouping using lambda`() = runBlocking {
        coOutputPositiveLongGroupedString(0) shouldBe "0"
        coOutputPositiveLongGroupedString(1) shouldBe "1"
        coOutputPositiveLongGroupedString(12) shouldBe "12"
        coOutputPositiveLongGroupedString(123) shouldBe "123"
        coOutputPositiveLongGroupedString(1234) shouldBe "1,234"
        coOutputPositiveLongGroupedString(12345) shouldBe "12,345"
        coOutputPositiveLongGroupedString(123456) shouldBe "123,456"
        coOutputPositiveLongGroupedString(1234567) shouldBe "1,234,567"
        coOutputPositiveLongGroupedString(12345678) shouldBe "12,345,678"
        coOutputPositiveLongGroupedString(123456789) shouldBe "123,456,789"
        coOutputPositiveLongGroupedString(1234567890) shouldBe "1,234,567,890"
        coOutputPositiveLongGroupedString(12345678901) shouldBe "12,345,678,901"
        coOutputPositiveLongGroupedString(123456789012) shouldBe "123,456,789,012"
        coOutputPositiveLongGroupedString(1234567890123) shouldBe "1,234,567,890,123"
        coOutputPositiveLongGroupedString(12345678901234) shouldBe "12,345,678,901,234"
        coOutputPositiveLongGroupedString(123456789012345) shouldBe "123,456,789,012,345"
        coOutputPositiveLongGroupedString(1234567890123456) shouldBe "1,234,567,890,123,456"
        coOutputPositiveLongGroupedString(12345678901234567) shouldBe "12,345,678,901,234,567"
        coOutputPositiveLongGroupedString(123456789012345678) shouldBe "123,456,789,012,345,678"
        coOutputPositiveLongGroupedString(1234567890123456789) shouldBe "1,234,567,890,123,456,789"
        coOutputPositiveLongGroupedString(Int.MAX_VALUE.toLong(), ' ') shouldBe "2 147 483 647"
        coOutputPositiveLongGroupedString(Long.MAX_VALUE, '_') shouldBe "9_223_372_036_854_775_807"
    }

    private suspend fun coOutputPositiveLongGroupedString(n: Long, groupingChar: Char = ','): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputPositiveLongGrouped(n, groupingChar) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output positive long with grouping using extension function`() = runBlocking {
        outputPositiveLongGroupedString(0) shouldBe "0"
        outputPositiveLongGroupedString(1) shouldBe "1"
        outputPositiveLongGroupedString(12) shouldBe "12"
        outputPositiveLongGroupedString(123) shouldBe "123"
        outputPositiveLongGroupedString(1234) shouldBe "1,234"
        outputPositiveLongGroupedString(12345) shouldBe "12,345"
        outputPositiveLongGroupedString(123456) shouldBe "123,456"
        outputPositiveLongGroupedString(1234567) shouldBe "1,234,567"
        outputPositiveLongGroupedString(12345678) shouldBe "12,345,678"
        outputPositiveLongGroupedString(123456789) shouldBe "123,456,789"
        outputPositiveLongGroupedString(1234567890) shouldBe "1,234,567,890"
        outputPositiveLongGroupedString(12345678901) shouldBe "12,345,678,901"
        outputPositiveLongGroupedString(123456789012) shouldBe "123,456,789,012"
        outputPositiveLongGroupedString(1234567890123) shouldBe "1,234,567,890,123"
        outputPositiveLongGroupedString(12345678901234) shouldBe "12,345,678,901,234"
        outputPositiveLongGroupedString(123456789012345) shouldBe "123,456,789,012,345"
        outputPositiveLongGroupedString(1234567890123456) shouldBe "1,234,567,890,123,456"
        outputPositiveLongGroupedString(12345678901234567) shouldBe "12,345,678,901,234,567"
        outputPositiveLongGroupedString(123456789012345678) shouldBe "123,456,789,012,345,678"
        outputPositiveLongGroupedString(1234567890123456789) shouldBe "1,234,567,890,123,456,789"
        outputPositiveLongGroupedString(Int.MAX_VALUE.toLong(), ' ') shouldBe "2 147 483 647"
        outputPositiveLongGroupedString(Long.MAX_VALUE, '_') shouldBe "9_223_372_036_854_775_807"
    }

    private suspend fun outputPositiveLongGroupedString(n: Long, groupingChar: Char = ',') =
            CoCapture().apply { outputPositiveLongGrouped(n, groupingChar) }.toString()

    @Test fun `should convert int to hex correctly using lambda`() = runBlocking {
        coOutputIntHexString(0) shouldBe "0"
        coOutputIntHexString(1) shouldBe "1"
        coOutputIntHexString(0x23) shouldBe "23"
        coOutputIntHexString(0x456) shouldBe "456"
        coOutputIntHexString(0xA789) shouldBe "A789"
        coOutputIntHexString(0x8A1B1) shouldBe "8A1B1"
        coOutputIntHexString(0xFEEABC) shouldBe "FEEABC"
        coOutputIntHexString(0xDEADFEED.toInt()) shouldBe "DEADFEED"
    }

    private suspend fun coOutputIntHexString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntHex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert int to hex correctly using extension function`() = runBlocking {
        outputIntHexString(0) shouldBe "0"
        outputIntHexString(1) shouldBe "1"
        outputIntHexString(0x23) shouldBe "23"
        outputIntHexString(0x456) shouldBe "456"
        outputIntHexString(0xA789) shouldBe "A789"
        outputIntHexString(0x8A1B1) shouldBe "8A1B1"
        outputIntHexString(0xFEEABC) shouldBe "FEEABC"
        outputIntHexString(0xDEADFEED.toInt()) shouldBe "DEADFEED"
    }

    private suspend fun outputIntHexString(n: Int) = CoCapture().apply { outputIntHex(n) }.toString()

    @Test fun `should convert int to hex correctly in lower case using lambda`() = runBlocking {
        coOutputIntHexLCString(0) shouldBe "0"
        coOutputIntHexLCString(1) shouldBe "1"
        coOutputIntHexLCString(0x23) shouldBe "23"
        coOutputIntHexLCString(0x456) shouldBe "456"
        coOutputIntHexLCString(0xA789) shouldBe "a789"
        coOutputIntHexLCString(0x8A1B1) shouldBe "8a1b1"
        coOutputIntHexLCString(0xFEEABC) shouldBe "feeabc"
        coOutputIntHexLCString(0xDEADFEED.toInt()) shouldBe "deadfeed"
    }

    private suspend fun coOutputIntHexLCString(n: Int): String {
        val charArray = CharArray(16)
        var i = 0
        coOutputIntHexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert int to hex correctly in lower case using extension function`() = runBlocking {
        outputIntHexLCString(0) shouldBe "0"
        outputIntHexLCString(1) shouldBe "1"
        outputIntHexLCString(0x23) shouldBe "23"
        outputIntHexLCString(0x456) shouldBe "456"
        outputIntHexLCString(0xA789) shouldBe "a789"
        outputIntHexLCString(0x8A1B1) shouldBe "8a1b1"
        outputIntHexLCString(0xFEEABC) shouldBe "feeabc"
        outputIntHexLCString(0xDEADFEED.toInt()) shouldBe "deadfeed"
    }

    private suspend fun outputIntHexLCString(n: Int) = CoCapture().apply { outputIntHexLC(n) }.toString()

    @Test fun `should convert long to hex correctly using lambda`() = runBlocking {
        coOutputLongHexString(0) shouldBe "0"
        coOutputLongHexString(1) shouldBe "1"
        coOutputLongHexString(0x23) shouldBe "23"
        coOutputLongHexString(0x456) shouldBe "456"
        coOutputLongHexString(0xA789) shouldBe "A789"
        coOutputLongHexString(0x8A1B1) shouldBe "8A1B1"
        coOutputLongHexString(0xFEEABC) shouldBe "FEEABC"
        coOutputLongHexString(0xDEADFEED) shouldBe "DEADFEED"
        coOutputLongHexString(0x123DEADFEED) shouldBe "123DEADFEED"
        coOutputLongHexString(Long.MIN_VALUE) shouldBe "8000000000000000"
    }

    private suspend fun coOutputLongHexString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongHex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long to hex correctly using extension function`() = runBlocking {
        outputLongHexString(0) shouldBe "0"
        outputLongHexString(1) shouldBe "1"
        outputLongHexString(0x23) shouldBe "23"
        outputLongHexString(0x456) shouldBe "456"
        outputLongHexString(0xA789) shouldBe "A789"
        outputLongHexString(0x8A1B1) shouldBe "8A1B1"
        outputLongHexString(0xFEEABC) shouldBe "FEEABC"
        outputLongHexString(0xDEADFEED) shouldBe "DEADFEED"
        outputLongHexString(0x123DEADFEED) shouldBe "123DEADFEED"
        outputLongHexString(Long.MIN_VALUE) shouldBe "8000000000000000"
    }

    private suspend fun outputLongHexString(n: Long) = CoCapture().apply { outputLongHex(n) }.toString()

    @Test fun `should convert long to hex correctly in lower case using lambda`() = runBlocking {
        coOutputLongHexLCString(0) shouldBe "0"
        coOutputLongHexLCString(1) shouldBe "1"
        coOutputLongHexLCString(0x23) shouldBe "23"
        coOutputLongHexLCString(0x456) shouldBe "456"
        coOutputLongHexLCString(0xA789) shouldBe "a789"
        coOutputLongHexLCString(0x8A1B1) shouldBe "8a1b1"
        coOutputLongHexLCString(0xFEEABC) shouldBe "feeabc"
        coOutputLongHexLCString(0xDEADFEED) shouldBe "deadfeed"
        coOutputLongHexLCString(0x123DEADFEED) shouldBe "123deadfeed"
        coOutputLongHexLCString(Long.MIN_VALUE) shouldBe "8000000000000000"
    }

    private suspend fun coOutputLongHexLCString(n: Long): String {
        val charArray = CharArray(32)
        var i = 0
        coOutputLongHexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should convert long to hex correctly in lower case using extension function`() = runBlocking {
        outputLongHexLCString(0) shouldBe "0"
        outputLongHexLCString(1) shouldBe "1"
        outputLongHexLCString(0x23) shouldBe "23"
        outputLongHexLCString(0x456) shouldBe "456"
        outputLongHexLCString(0xA789) shouldBe "a789"
        outputLongHexLCString(0x8A1B1) shouldBe "8a1b1"
        outputLongHexLCString(0xFEEABC) shouldBe "feeabc"
        outputLongHexLCString(0xDEADFEED) shouldBe "deadfeed"
        outputLongHexLCString(0x123DEADFEED) shouldBe "123deadfeed"
        outputLongHexLCString(Long.MIN_VALUE) shouldBe "8000000000000000"
    }

    private suspend fun outputLongHexLCString(n: Long) = CoCapture().apply { outputLongHexLC(n) }.toString()

    @Test fun `should output 8 digits hex correctly using lambda`() = runBlocking {
        coOutput8HexString(0) shouldBe "00000000"
        coOutput8HexString(1) shouldBe "00000001"
        coOutput8HexString(0xABCD) shouldBe "0000ABCD"
        coOutput8HexString(0x9ABCD) shouldBe "0009ABCD"
        coOutput8HexString(0x89ABCD) shouldBe "0089ABCD"
        coOutput8HexString(0xE89ABCD) shouldBe "0E89ABCD"
        coOutput8HexString(0x7E89ABCD) shouldBe "7E89ABCD"
    }

    private suspend fun coOutput8HexString(n: Int): String {
        val charArray = CharArray(8)
        var i = 0
        coOutput8Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 8 digits hex correctly using extension function`() = runBlocking {
        output8HexString(0) shouldBe "00000000"
        output8HexString(1) shouldBe "00000001"
        output8HexString(0xABCD) shouldBe "0000ABCD"
        output8HexString(0x9ABCD) shouldBe "0009ABCD"
        output8HexString(0x89ABCD) shouldBe "0089ABCD"
        output8HexString(0xE89ABCD) shouldBe "0E89ABCD"
        output8HexString(0x7E89ABCD) shouldBe "7E89ABCD"
    }

    private suspend fun output8HexString(n: Int) = CoCapture().apply { output8Hex(n) }.toString()

    @Test fun `should output 8 digits hex correctly in lower case using lambda`() = runBlocking {
        coOutput8HexLCString(0) shouldBe "00000000"
        coOutput8HexLCString(1) shouldBe "00000001"
        coOutput8HexLCString(0xABCD) shouldBe "0000abcd"
        coOutput8HexLCString(0x9ABCD) shouldBe "0009abcd"
        coOutput8HexLCString(0x89ABCD) shouldBe "0089abcd"
        coOutput8HexLCString(0xE89ABCD) shouldBe "0e89abcd"
        coOutput8HexLCString(0xFE89ABCD.toInt()) shouldBe "fe89abcd"
    }

    private suspend fun coOutput8HexLCString(n: Int): String {
        val charArray = CharArray(8)
        var i = 0
        coOutput8HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 8 digits hex correctly in lower case using extension function`() = runBlocking {
        output8HexLCString(0) shouldBe "00000000"
        output8HexLCString(1) shouldBe "00000001"
        output8HexLCString(0xABCD) shouldBe "0000abcd"
        output8HexLCString(0x9ABCD) shouldBe "0009abcd"
        output8HexLCString(0x89ABCD) shouldBe "0089abcd"
        output8HexLCString(0xE89ABCD) shouldBe "0e89abcd"
        output8HexLCString(0xFE89ABCD.toInt()) shouldBe "fe89abcd"
    }

    private suspend fun output8HexLCString(n: Int) = CoCapture().apply { output8HexLC(n) }.toString()

    @Test fun `should output 4 digits hex correctly using lambda`() = runBlocking {
        coOutput4HexString(0) shouldBe "0000"
        coOutput4HexString(1) shouldBe "0001"
        coOutput4HexString(0xABCD) shouldBe "ABCD"
    }

    private suspend fun coOutput4HexString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput4Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 4 digits hex correctly using extension function`() = runBlocking {
        output4HexString(0) shouldBe "0000"
        output4HexString(1) shouldBe "0001"
        output4HexString(0xABCD) shouldBe "ABCD"
    }

    private suspend fun output4HexString(n: Int) = CoCapture().apply { output4Hex(n) }.toString()

    @Test fun `should output 4 digits hex correctly in lower case using lambda`() = runBlocking {
        coOutput4HexLCString(0) shouldBe "0000"
        coOutput4HexLCString(1) shouldBe "0001"
        coOutput4HexLCString(0xABCD) shouldBe "abcd"
    }

    private suspend fun coOutput4HexLCString(n: Int): String {
        val charArray = CharArray(4)
        var i = 0
        coOutput4HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 4 digits hex correctly in lower case`() = runBlocking {
        output4HexLCString(0) shouldBe "0000"
        output4HexLCString(1) shouldBe "0001"
        output4HexLCString(0xABCD) shouldBe "abcd"
    }

    private suspend fun output4HexLCString(n: Int) = CoCapture().apply { output4HexLC(n) }.toString()

    @Test fun `should output 2 digits hex correctly using lambda`() = runBlocking {
        coOutput2HexString(0) shouldBe "00"
        coOutput2HexString(1) shouldBe "01"
        coOutput2HexString(0xAB) shouldBe "AB"
    }

    private suspend fun coOutput2HexString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput2Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits hex correctly using extension function`() = runBlocking {
        output2HexString(0) shouldBe "00"
        output2HexString(1) shouldBe "01"
        output2HexString(0xAB) shouldBe "AB"
    }

    private suspend fun output2HexString(n: Int) = CoCapture().apply { output2Hex(n) }.toString()

    @Test fun `should output 2 digits hex correctly in lower case using lambda`() = runBlocking {
        coOutput2HexLCString(0) shouldBe "00"
        coOutput2HexLCString(1) shouldBe "01"
        coOutput2HexLCString(0xAB) shouldBe "ab"
    }

    private suspend fun coOutput2HexLCString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput2HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 2 digits hex correctly in lower case using extension function`() = runBlocking {
        output2HexLCString(0) shouldBe "00"
        output2HexLCString(1) shouldBe "01"
        output2HexLCString(0xAB) shouldBe "ab"
    }

    private suspend fun output2HexLCString(n: Int) = CoCapture().apply { output2HexLC(n) }.toString()

    @Test fun `should output 1 digit hex correctly using lambda`() = runBlocking {
        coOutput1HexString(0) shouldBe "0"
        coOutput1HexString(1) shouldBe "1"
        coOutput1HexString(0xA) shouldBe "A"
    }

    private suspend fun coOutput1HexString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput1Hex(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 1 digit hex correctly using extension function`() = runBlocking {
        output1HexString(0) shouldBe "0"
        output1HexString(1) shouldBe "1"
        output1HexString(0xA) shouldBe "A"
    }

    private suspend fun output1HexString(n: Int) = CoCapture().apply { output1Hex(n) }.toString()

    @Test fun `should output 1 digit hex correctly in lower case using lambda`() = runBlocking {
        coOutput1HexLCString(0) shouldBe "0"
        coOutput1HexLCString(1) shouldBe "1"
        coOutput1HexLCString(0xA) shouldBe "a"
    }

    private suspend fun coOutput1HexLCString(n: Int): String {
        val charArray = CharArray(2)
        var i = 0
        coOutput1HexLC(n) { charArray[i++] = it }
        return String(charArray, 0, i)
    }

    @Test fun `should output 1 digit hex correctly in lower case using extension function`() = runBlocking {
        output1HexLCString(0) shouldBe "0"
        output1HexLCString(1) shouldBe "1"
        output1HexLCString(0xA) shouldBe "a"
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
