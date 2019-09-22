package com.radix2.hr.graph

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
