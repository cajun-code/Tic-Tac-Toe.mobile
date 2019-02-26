package com.lasley.adam.codingtest.Objects


/**
 * Mutable class of pair
 */
object Tuple {

    operator fun <A> invoke(_1: A): Tuple1<A> = Tuple1(_1)

    operator fun <A, B> invoke(_1: A, _2: B): Tuple2<A, B> = Tuple2(_1, _2)

    operator fun <A, B, C> invoke(_1: A, _2: B, _3: C): Tuple3<A, B, C> = Tuple3(_1, _2, _3)

    operator fun <A, B, C, D> invoke(_1: A, _2: B, _3: C, _4: D): Tuple4<A, B, C, D> = Tuple4(_1, _2, _3, _4)

    operator fun <A, B, C, D, E> invoke(_1: A, _2: B, _3: C, _4: D, _5: E): Tuple5<A, B, C, D, E> =
        Tuple5(_1, _2, _3, _4, _5)

}

data class Tuple1<A>(var _1: A)
data class Tuple2<A, B>(var _1: A, var _2: B)
data class Tuple3<A, B, C>(var _1: A, var _2: B, var _3: C)
data class Tuple4<A, B, C, D>(var _1: A, var _2: B, var _3: C, var _4: D)
data class Tuple5<A, B, C, D, E>(var _1: A, var _2: B, var _3: C, var _4: D, var _5: E)