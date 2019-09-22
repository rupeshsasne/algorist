package com.radix2.hr.graph

import java.util.*
import kotlin.collections.*

fun roadsAndLibraries(cLib: Long, cRoad: Long, graph: Graph): Long {
    if (cLib <= cRoad)
        return cLib * graph.v.toLong()

    val roadsAndLibraries = RoadsAndLibraries(graph)

    return roadsAndLibraries.libraries * cLib + roadsAndLibraries.roads * cRoad
}

class RoadsAndLibraries(private val graph: Graph) {
    private val marked = BooleanArray(graph.v)
    private val ids = LongArray(graph.v) { -1L }
    private var id = 0L

    var roads: Long = 0
        private set

    val libraries: Long
        get() = id

    init {
        for (v in 0 until graph.v) {
            if (!marked[v]) {
                roads += (dfs(v) - 1) // i.e. (v - 1) edges of spanning tree
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

    val q = scan.nextLine().trim().toInt()

    for (qItr in 1..q) {
        val (n, m, cLib, cRoad) = scan.nextLine()
            .split(" ")
            .map { it.trim().toLong() }
            .toTypedArray()

        val graph = Graph(n.toInt())

        for (i in 0 until m) {
            val (v, w) = scan.nextLine()
                .split(" ")
                .map{ it.trim().toInt() - 1 }
                .toTypedArray()

            graph.addEdge(v, w)
        }

        val result = roadsAndLibraries(cLib, cRoad, graph)

        println(result)
    }
}
