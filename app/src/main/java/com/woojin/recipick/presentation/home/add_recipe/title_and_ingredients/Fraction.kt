package com.woojin.recipick.presentation.home.add_recipe.title_and_ingredients

import com.woojin.recipick.utils.Utils

/** 분수 표현을 위한 data class */
data class Fraction(val numerator: Int, val denominator: Int) {

    override fun toString(): String {
        if (denominator == 0) return "Error" // 분모가 0인 경우
        if (numerator == 0) return "0"
        val simplified = Utils.simplifyFraction(this) // 약분 먼저 수행
        return if (simplified.denominator == 1) "${simplified.numerator}" else "${simplified.numerator}/${simplified.denominator}"
    }

    // 대분수 형태로 변환하는 함수
    fun toMixedFractionString(): String {
        if (denominator == 0) return "Error"
        if (numerator == 0) return "0"

        val simplified = Utils.simplifyFraction(this) // 약분된 분수를 기준으로 작업
        val wholePart = simplified.numerator / simplified.denominator
        val newNumerator = simplified.numerator % simplified.denominator

        return when {
            newNumerator == 0 -> "$wholePart" // 정수로 나누어 떨어지는 경우 (예: 6/3 -> 2)
            wholePart == 0 -> "${simplified.numerator}/${simplified.denominator}" // 진 분수인 경우 (예: 1/2)
            else -> "$wholePart + ${newNumerator}/${simplified.denominator}" // 대 분수인 경우 (예: 1 + 5/6)
        }
    }
}
