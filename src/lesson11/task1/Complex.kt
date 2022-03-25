@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

/**
 * Фабричный метод для создания комплексного числа из строки вида x+yi
 */
fun Complex(s: String): Complex {
    val sign = if ("-" in s) "-"
    else "+"
    val parts = s.split(sign, "i")
    val re = parts[0].toDouble()
    val im = if (sign == "-") -parts[1].toDouble() else parts[1].toDouble()
    return (Complex(re, im))
}

/**
 * Класс "комплексное число".
 *
 * Общая сложность задания -- лёгкая, общая ценность в баллах -- 8.
 * Объект класса -- комплексное число вида x+yi.
 * Про принципы работы с комплексными числами см. статью Википедии "Комплексное число".
 *
 * Аргументы конструктора -- вещественная и мнимая часть числа.
 */
class Complex(val re: Double, val im: Double) {

    /**
     * Конструктор из вещественного числа
     */
    constructor(x: Double) : this(x, 0.0)

    /**
     * Сложение.
     */
    operator fun plus(other: Complex): Complex = Complex(this.re + other.re, this.im + other.im)

    /**
     * Смена знака (у обеих частей числа)
     */
    operator fun unaryMinus(): Complex = Complex(-this.re, -this.im)

    /**
     * Вычитание
     */
    operator fun minus(other: Complex): Complex = Complex(this.re - other.re, this.im - other.im) // все как с fun plus, ток "+" замен на "-"

    /**
     * Умножение
     */
    operator fun times(other: Complex): Complex =
        // (a1 + b1 * i) * (a2 + b2 * i) = (a1 * a2 - b1 * b2) + (b1 * a2 + a1 * b2) * i
        Complex(
            (this.re * other.re - this.im * other.im),
            (this.im * other.re + this.re * other.im)
        )

    /**
     * Деление
     */
    operator fun div(other: Complex): Complex {
        // ((a1 * a2 + b1 * b2) / (a2 * a2 + b2 * b2)) + ((b1 * a2 - a1 * b2) / (a2 * a2 + b2 * b2)) * i
        val a1 = this.re
        val b1 = this.im
        val a2 = other.re
        val b2 = other.im
        val re = ((a1 * a2 + b1 * b2) / (a2 * a2 + b2 * b2))
        val im = ((b1 * a2 - a1 * b2) / (a2 * a2 + b2 * b2))
        return Complex(re, im)
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean =
        if (other !is Complex) false
        else ((this.re == other.re) && (this.im == other.im))

    /**
     * Преобразование в строку
     */
    override fun toString(): String = "$re+${im}i"
}
