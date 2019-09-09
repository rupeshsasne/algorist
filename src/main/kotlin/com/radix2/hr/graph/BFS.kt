package com.radix2.hr.graph

import java.util.*

class Graph(val v: Int) {
    private val bag = Array<MutableSet<Int>>(v) { mutableSetOf() }

    var e: Int = 0
        private set

    fun addEdge(v: Int, w: Int) {
        bag[v].add(w)
        bag[w].add(v)

        e++
    }

    fun adj(v: Int): Iterable<Int> = bag[v]
}

fun bfs(graph: Graph, source: Int): Sequence<Int> {
    val marked = BooleanArray(graph.v)
    val distTo = Array(graph.v) { 0 }

    val queue: Queue<Int> = LinkedList<Int>()
    queue.offer(source)
    marked[source] = true

    while (queue.isNotEmpty()) {
        val node = queue.poll()

        for (w in graph.adj(node)) {
            if (!marked[w]) {
                queue.offer(w)
                marked[w] = true
                distTo[w] = distTo[node] + 6
            }
        }
    }

    return distTo.filterIndexed { index, _ -> index != source}
        .asSequence()
        .map { if (it == 0) -1 else it }
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val q = scan.nextLine().trim().toInt()

    for (qItr in 1..q) {
        val nm = scan.nextLine().split(" ")

        val n = nm[0].trim().toInt()

        val graph = Graph(n)

        val m = nm[1].trim().toInt()

        for (i in 0 until m) {
            val (v, w) = scan.nextLine()
                .split(" ")
                .map{ it.trim().toInt() - 1 }
                .toTypedArray()

            graph.addEdge(v, w)
        }

        val s = scan.nextLine().trim().toInt()

        val result = bfs(graph, s - 1)

        println(result.joinToString(" "))
    }
}
