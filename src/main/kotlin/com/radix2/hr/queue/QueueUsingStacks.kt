package com.radix2.hr.queue

import java.util.*

class Queue {
    val stack1 = Stack<Int>()
    val stack2 = Stack<Int>()

    fun enqueue(num: Int) = stack1.push(num)

    fun dequeue(): Int {
        if (stack2.isEmpty()) {
            while (stack1.isNotEmpty()) {
                stack2.push(stack1.pop())
            }
        }

        return stack2.pop()
    }

    fun peek(): Int {
        if (stack2.isEmpty()) {
            while (stack1.isNotEmpty()) {
                stack2.push(stack1.pop())
            }
        }

        return stack2.peek()
    }
}

fun main() {
    val scanner = Scanner(System.`in`)

    var t = scanner.nextInt()

    val queue = Queue()

    while (t-- > 0) {

        when (scanner.nextInt()) {
            1 -> queue.enqueue(scanner.nextInt())

            2 -> queue.dequeue()

            3 -> println(queue.peek())
        }
    }
}
