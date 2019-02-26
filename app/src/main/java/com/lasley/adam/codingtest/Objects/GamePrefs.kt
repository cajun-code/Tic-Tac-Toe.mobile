package com.lasley.adam.codingtest.Objects

import com.chibatching.kotpref.KotprefModel

class GamePrefs : KotprefModel() {
    var playerX by booleanPref(true)
    var boardSize by intPref(0)
    var winSize by intPref(0)
    var showTimer by booleanPref(false)

    var PlayerCount by intPref(1)
    var AICount by intPref(1)
}