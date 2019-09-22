package com.radix2.hr.graph

import java.util.*

class JourneyToMoon(private val graph: Graph) {
    private val marked = BooleanArray(graph.v)
    private val ids = LongArray(graph.v) { -1L }
    private var id = 0L

    var numberOfPairs = 0L
        private set

    init {
        var sum = 0

        for (v in 0 until graph.v) {
            if (!marked[v]) {
                val size = dfs(v)

                numberOfPairs += sum * size

                sum += size

                id++
            }
        }
    }

    private fun dfs(source: Int): Int {
        marked[source] = true
        ids[source] = id

        var count = 1

        for (w in graph.adj(source)) {
            if (!marked[w]) {
                count += dfs(w)
            }
        }

        return count
    }
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val (n, p) = scan.nextLine().split(" ").map { it.toInt() }

    val graph = Graph(n)

    for (i in 0 until p) {
        val (u, v) = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()
        graph.addEdge(u, v)
    }

    val journeyToMoon = JourneyToMoon(graph)

    val result = journeyToMoon.numberOfPairs

    println(result)
}
