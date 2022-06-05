/*
 * @(#) CoOutput.java
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

/**
 * A non-blocking function to output a single character.
 */
typealias CoOutput = suspend (Char) -> Unit

/**
 * Output the nominated section of a [CharSequence].
 */
suspend fun CoOutput.output(cs: CharSequence, start: Int, end: Int) {
    for (i in start until end)
        this(cs[i])
}

/**
 * Output a [CharSequence].
 */
suspend fun CoOutput.output(cs: CharSequence) = output(cs, 0, cs.length)

/**
 * Output a character.
 */
suspend fun CoOutput.output(ch: Char) = this(ch)
