package net.pwall.util

import kotlinx.coroutines.runBlocking
import net.pwall.util.CoIntOutput.outputInt
import kotlin.test.Test
import kotlin.test.expect

class CoOutputFlushableTest {

    @Test fun `should flush output`() = runBlocking {
        val flushable = CoFlushableCapture()
        flushable.outputInt(123)
        expect("123") { flushable.toString() }
        flushable.flush()
        expect("123\uFFFF") { flushable.toString() }
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
