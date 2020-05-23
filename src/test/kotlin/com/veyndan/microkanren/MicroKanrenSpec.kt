package com.veyndan.microkanren

import com.veyndan.microkanren.MicroKanren.unify
import com.veyndan.microkanren.MicroKanren.walk
import com.veyndan.microkanren.Types.Constant
import com.veyndan.microkanren.Types.Variable
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertNull

object MicroKanrenSpec : Spek({
    val a = Variable<String, Int>("a")
    val b = Variable<String, Int>("b")
    val c = Variable<String, Int>("c")

    val ten = Constant<String, Int>(10)
    val eleven = Constant<String, Int>(11)

    describe("microKanren") {
        on("walk") {
            it("should return value of dependent variable") {
                val substitutions: Map<Variable<String, Int>, Types<String, Int>> = mapOf(
                        a to b,
                        b to ten
                )

                assertEquals(10, walk(substitutions, a))
                assertEquals(10, walk(substitutions, b))
            }
            it("should return null if no substitutions") {
                assertNull(walk(emptyMap(), a))
            }
        }
        on("unify") {
            it("should return substitutions with result of binding a variable to a constant") {
                assertEquals(
                        mapOf(a to ten, b to eleven),
                        unify(mapOf(a to ten), b, eleven)
                )
            }
            it("should return substitutions with result of binding a constant to a variable") {
                assertEquals(
                        mapOf(a to ten, b to eleven),
                        unify(mapOf(a to ten), eleven, b)
                )
            }
            it("should return initial substitutions when both terms walk to same value") {
                assertEquals(
                        mapOf(a to b, b to c, c to ten),
                        unify(mapOf(a to b, b to c, c to ten), a, c)
                )
            }
        }
    }
    describe("cons") {
        on("head and tail of list known") {
            it("should return singleton list containing head if tail empty") {

            }
            it("should return list of head and tail") {
                val head = Variable<String, Int>("head")
                val tail = Variable<String, Int>("tail")
                val out = Variable<String, Int>("out")

                val substitutions: Map<Variable<String, Int>, Constant<String, List<Constant<String, Int>>>> = mapOf(
                        head to Constant(listOf(Constant(1))),
                        tail to Constant((2..10).map { Constant<String, Int>(it) })
                )

                val expectedSubstituions: Map<Variable<String, Int>, Constant<String, List<Constant<String, Int>>>> = mapOf(
                        head to Constant(listOf(Constant(1))),
                        tail to Constant((2..10).map { Constant<String, Int>(it) }),
                        out to Constant((1..10).map { Constant<String, Int>(it) })
                )

                val actualSubstitutions: Map<Variable<String, Int>, Constant<String, List<Constant<String, Int>>>> = mapOf(

                )

                assertEquals(
                        expectedSubstituions,
                        actualSubstitutions
                )
            }
        }
    }
    describe("append") {
        on("first and second values known") {
            it("should return empty if first and second are empty") {

            }
            it("should return first and second appended") {

            }
        }
    }
})
