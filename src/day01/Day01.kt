package day01

import println
import readInput
import java.util.LinkedList

fun main() {
    /**
     * TODO: Before running the solution
     *  1. Create a package names `day01`
     *  2. Create Day01.txt for real input
     *  3. Create Day01_part01_test.txt, Day01_part02_test.txt
     *   for part1 and part2 test inputs
     *   Note: All files should be in the `day01` package
     */

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstChar = line.first { it.isDigit() }.digitToInt()
            val lastChar = line.last { it.isDigit() }.digitToInt()
            "$firstChar$lastChar".toInt()
        }
    }

    val stringToDigitMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (firstDigit, lastDigit) = getFirstAndLastDigit(stringToDigitMap, line)
            "$firstDigit$lastDigit".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("day01/Day01_part01_test")
    check(part1(testInputPart1) == 142)

    val testInputPart2 = readInput("day01/Day01_part02_test")
    check(part2(testInputPart2) == 281)

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}

fun getFirstAndLastDigit(words: Map<String, Int>, string: String): Pair<Int, Int> {
    val foundDigits = LinkedList<Int?>()
    string.forEachIndexed { idx, letter ->
        if (letter.isDigit()) foundDigits.add(letter.digitToInt())
        else words.keys.findPossibleWord(at = idx, startFrom = letter, `in` = string).also { possibleWord ->
            possibleWord?.let { w -> foundDigits.add(words[w]) }
        }
    }
    val filtered = foundDigits.filterNotNull()
    return filtered.first() to filtered.last()
}

fun Set<String>.findPossibleWord(at: Int, startFrom: Char, `in`: String): String? {
    val hasPossibleWordsInMap = any { it.startsWith(startFrom) }
    return if (hasPossibleWordsInMap) {
        find { key ->
            `in`.substring(at).startsWith(key)
        }
    } else {
        null
    }
}