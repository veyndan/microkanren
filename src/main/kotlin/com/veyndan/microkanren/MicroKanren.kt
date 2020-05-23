package com.veyndan.microkanren

// TODO Check if substitution is cyclical

@Suppress("unused")
sealed class Types<V, C> {

    data class Variable<V, C>(val value: V) : Types<V, C>()

    data class Constant<V, C>(val value: C) : Types<V, C>()
}

object MicroKanren {

    fun <V, C> walk(substitutions: Map<Types.Variable<V, C>, Types<V, C>>, variable: Types.Variable<V, C>): C? {
        val substitution = substitutions[variable]
        return when (substitution) {
            null -> null
            is Types.Variable -> walk(substitutions, substitution)
            is Types.Constant -> substitution.value
        }
    }

    fun <V, C> unify(
            substitutions: Map<Types.Variable<V, C>, Types<V, C>>,
            u: Types<V, C>,
            v: Types<V, C>
    ): Map<Types.Variable<V, C>, Types<V, C>>? {
        return when {
            u is Types.Variable && v is Types.Variable -> {
                when {
                    walk(substitutions, u) == walk(substitutions, v) -> substitutions
                    else -> null
                }
            }
            u is Types.Variable && v is Types.Constant -> {
                val substitution = walk(substitutions, u)
                when {
                    substitution == v.value -> substitutions
                    substitution == null -> substitutions + (u to v)
                    else -> null
                }
            }
            u is Types.Constant && v is Types.Variable -> {
                val substitution = walk(substitutions, v)
                when {
                    substitution == u.value -> substitutions
                    substitution == null -> substitutions + (v to u)
                    else -> null
                }
            }
            u is Types.Constant && v is Types.Constant -> {
                null
            }
            else -> throw IllegalStateException()
        }
    }
}
