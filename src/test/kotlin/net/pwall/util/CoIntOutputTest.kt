package net.pwall.util

import kotlin.test.Test
import kotlin.test.expect
import kotlinx.coroutines.runBlocking
import net.pwall.util.CoIntOutput.coOutput2Digits
import net.pwall.util.CoIntOutput.coOutput3Digits
import net.pwall.util.CoIntOutput.coOutputInt
import net.pwall.util.CoIntOutput.coOutputIntGrouped
import net.pwall.util.CoIntOutput.coOutputLong
import net.pwall.util.CoIntOutput.coOutputUnsignedInt
import net.pwall.util.CoIntOutput.coOutputUnsignedLong

class CoIntOutputTest {

    @Test fun `should convert int correctly`() = runBlocking {
        expect("0") { coOutputIntString(0) }
        expect("123456") { coOutputIntString(123456) }
        expect("-22334455") { coOutputIntString(-22334455) }
        expect("2147483647") { coOutputIntString(Int.MAX_VALUE) }
        expect("-2147483648") { coOutputIntString(Int.MIN_VALUE) }
    }

    private suspend fun coOutputIntString(n: Int): String { // TODO convert others to this pattern?
        val charArray = CharArray(40)
        var i = 0
        coOutputInt(n) { charArray[i++] = it.toChar() }
        return String(charArray, 0, i)
    }

    @Test fun `should convert unsigned int correctly`() = runBlocking {
        val charArray = CharArray(40)
        var i = 0
        coOutputUnsignedInt(0) { charArray[i++] = it.toChar() }
        expect("0") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedInt(123456) { charArray[i++] = it.toChar() }
        expect("123456") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedInt(2147483648.toInt()) { charArray[i++] = it.toChar() }
        expect("2147483648") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedInt(3456789012.toInt()) { charArray[i++] = it.toChar() }
        expect("3456789012") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedInt(0x89ABCDEF.toInt()) { charArray[i++] = it.toChar() }
        expect("2309737967") { String(charArray, 0, i) }
    }

    @Test fun `should convert long correctly`() = runBlocking {
        val charArray = CharArray(40)
        var i = 0
        coOutputLong(0) { charArray[i++] = it.toChar() }
        expect("0") { String(charArray, 0, i) }
        i = 0
        coOutputLong(123456789012345678) { charArray[i++] = it.toChar() }
        expect("123456789012345678") { String(charArray, 0, i) }
        i = 0
        coOutputLong(-2233445566778899) { charArray[i++] = it.toChar() }
        expect("-2233445566778899") { String(charArray, 0, i) }
        i = 0
        coOutputLong(Int.MAX_VALUE.toLong()) { charArray[i++] = it.toChar() }
        expect("2147483647") { String(charArray, 0, i) }
        i = 0
        coOutputLong(Int.MIN_VALUE.toLong()) { charArray[i++] = it.toChar() }
        expect("-2147483648") { String(charArray, 0, i) }
        i = 0
        coOutputLong(Long.MAX_VALUE) { charArray[i++] = it.toChar() }
        expect("9223372036854775807") { String(charArray, 0, i) }
        i = 0
        coOutputLong(Long.MIN_VALUE) { charArray[i++] = it.toChar() }
        expect("-9223372036854775808") { String(charArray, 0, i) }
    }

    @Test fun `should convert unsigned long correctly`() = runBlocking {
        val charArray = CharArray(40)
        var i = 0
        coOutputUnsignedLong(0) { charArray[i++] = it.toChar() }
        expect("0") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedLong(1234567890123456789) { charArray[i++] = it.toChar() }
        expect("1234567890123456789") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedLong(maxValue() + 1) { charArray[i++] = it.toChar() }
        expect("9223372036854775808") { String(charArray, 0, i) }
        i = 0
        coOutputUnsignedLong(nineteenDigits() * 10) { charArray[i++] = it.toChar() }
        expect("12345678901234567890") { String(charArray, 0, i) }
    }

    @Test fun `should output 2 digits correctly`() = runBlocking {
        val charArray = CharArray(40)
        var i = 0
        coOutput2Digits(0) { charArray[i++] = it.toChar() }
        expect("00") { String(charArray, 0, i) }
        i = 0
        coOutput2Digits(1) { charArray[i++] = it.toChar() }
        expect("01") { String(charArray, 0, i) }
        i = 0
        coOutput2Digits(21) { charArray[i++] = it.toChar() }
        expect("21") { String(charArray, 0, i) }
    }

    @Test fun `should output 3 digits correctly`() = runBlocking {
        val charArray = CharArray(40)
        var i = 0
        coOutput3Digits(0) { charArray[i++] = it.toChar() }
        expect("000") { String(charArray, 0, i) }
        i = 0
        coOutput3Digits(1) { charArray[i++] = it.toChar() }
        expect("001") { String(charArray, 0, i) }
        i = 0
        coOutput3Digits(21) { charArray[i++] = it.toChar() }
        expect("021") { String(charArray, 0, i) }
        i = 0
        coOutput3Digits(321) { charArray[i++] = it.toChar() }
        expect("321") { String(charArray, 0, i) }
    }

    @Test fun `should output integer using grouping`() = runBlocking {
        val charArray = CharArray(40)
        var i = 0
        coOutputIntGrouped(0) { charArray[i++] = it.toChar() }
        expect("0") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(1) { charArray[i++] = it.toChar() }
        expect("1") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(12) { charArray[i++] = it.toChar() }
        expect("12") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(123) { charArray[i++] = it.toChar() }
        expect("123") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(1234) { charArray[i++] = it.toChar() }
        expect("1,234") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(12345) { charArray[i++] = it.toChar() }
        expect("12,345") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(123456) { charArray[i++] = it.toChar() }
        expect("123,456") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(1234567) { charArray[i++] = it.toChar() }
        expect("1,234,567") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(12345678) { charArray[i++] = it.toChar() }
        expect("12,345,678") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(123456789) { charArray[i++] = it.toChar() }
        expect("123,456,789") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(1234567890) { charArray[i++] = it.toChar() }
        expect("1,234,567,890") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(Int.MAX_VALUE, ' ') { charArray[i++] = it.toChar() }
        expect("2 147 483 647") { String(charArray, 0, i) }
        i = 0
        coOutputIntGrouped(Int.MIN_VALUE, ' ') { charArray[i++] = it.toChar() }
        expect("-2 147 483 648") { String(charArray, 0, i) }
    }

    companion object {

        fun maxValue() = Long.MAX_VALUE // this and the following are contrivances to avoid compiler warnings

        fun nineteenDigits() = 1234567890123456789

    }

}
