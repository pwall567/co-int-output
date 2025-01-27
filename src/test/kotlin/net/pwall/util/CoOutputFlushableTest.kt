/*
 * @(#) CoOutputFlushableTest.java
 *
 * co-int-output  Non-blocking integer output functions
 * Copyright (c) 2023 Peter Wall
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

import kotlinx.coroutines.runBlocking
import kotlin.test.Test

import io.kstuff.test.shouldBe

import net.pwall.util.CoIntOutput.outputInt

class CoOutputFlushableTest {

    @Test fun `should flush output`() = runBlocking {
        val flushable = CoFlushableCapture()
        flushable.outputInt(123)
        flushable.toString() shouldBe "123"
        flushable.flush()
        flushable.toString() shouldBe "123\uFFFF"
    }

    class CoFlushableCapture(size: Int = 256) : CoOutputFlushable() {

        private val array = CharArray(size)
        private var index = 0

        override suspend fun invoke(ch: Char) {
            array[index++] = ch
        }

        override suspend fun flush() {
            array[index++] = '\uFFFF'
        }

        override fun toString() = String(array, 0, index)

    }

}
