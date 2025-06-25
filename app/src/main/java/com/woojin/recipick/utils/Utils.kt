package com.woojin.recipick.utils

import com.woojin.recipick.presentation.home.add_recipe.title_and_ingredients.Fraction

object Utils {

    /** 분수 덧셈 계산 */
    fun addFractions(f1: Fraction, f2: Fraction): Fraction {
        if (f1.denominator == 0 || f2.denominator == 0) {
            // 0으로 나누는 경우 0 반환
            return Fraction(0, 1)
        }
        // 통분 하여 더하기: (a/b) + (c/d) = (ad + bc) / bd
        val newNumerator = f1.numerator * f2.denominator + f2.numerator * f1.denominator
        val newDenominator = f1.denominator * f2.denominator
        return simplifyFraction(Fraction(newNumerator, newDenominator))
    }

    /** 통분된 분수 약분 */
    fun simplifyFraction(fraction: Fraction): Fraction {
        if (fraction.numerator == 0) return Fraction(0, 1) // 분자가 0이면 0 반환
        val commonDivisor = gcd(fraction.numerator.let { kotlin.math.abs(it) }, fraction.denominator.let { kotlin.math.abs(it) })
        var num = fraction.numerator / commonDivisor
        var den = fraction.denominator / commonDivisor

        // 분모가 음수면 분자, 분모 모두 부호 변경
        if (den < 0) {
            num *= -1
            den *= -1
        }
        return Fraction(num, den)
    }

    // 최대 공약수(GCD) 계산 함수
    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }
}