package day02

import println
import readInput

const val MAX_RED_COUNT = 12
const val MAX_GREEN_COUNT = 13
const val MAX_BLUE_COUNT = 14

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
        val gameSubsetMap = input.associate { it.toGameSubsetsPair() }
        val sumOfPossibleGamesID = gameSubsetMap.keys.sumOf { key ->
            val value = gameSubsetMap[key]
            val isEligible = value.isEligible()
            if (isEligible) {
                key
            } else {
                0
            }
        }
        return sumOfPossibleGamesID
    }

    fun part2(input: List<String>): Int {
        val gameSubsetMap = input.associate { it.toGameSubsetsPair() }
        val sumOfPowerOfSets = gameSubsetMap.entries.sumOf { entry ->
            val subsets = entry.value
            val redNeeded = subsets.maxOf { it.redCubes }
            val greenNeeded = subsets.maxOf { it.greenCubes }
            val blueNeeded = subsets.maxOf { it.blueCubes }
            redNeeded * greenNeeded * blueNeeded
        }
        return sumOfPowerOfSets
    }

    // test if implementation meets criteria from the description, like:
    val testInputPart1 = readInput("day02/Day02_part01_test")
    check(part1(testInputPart1) == 8)

    val testInputPart2 = readInput("day02/Day02_part02_test")
    check(part2(testInputPart2) == 2286)

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}

fun List<Subset>?.isEligible(): Boolean {
    return this?.all {
        it.redCubes <= MAX_RED_COUNT && it.greenCubes <= MAX_GREEN_COUNT && it.blueCubes <= MAX_BLUE_COUNT
    } ?: false
}

fun String.toGameSubsetsPair(): Pair<Int, List<Subset>> {
    val (gameStr, infoStr) = split(":")
    val gameId = gameStr.split(" ").last().toInt()
    val subsets = infoStr.split(";")

    val subsetClassList = subsets.map { rawString ->
        // 4 red, 1 green ----> rawString
        val cubes = rawString.trim().split(", ")
        // [4 red, 1 green]
        var subset = Subset()
        cubes.map { cubeStr ->
            val (color, count) = getCubeCountAndColor(cubeStr)
            subset = when (color) {
                CubeColor.RED -> {
                    subset.copy(redCubes = count)
                }

                CubeColor.GREEN -> {
                    subset.copy(greenCubes = count)
                }

                CubeColor.BLUE -> {
                    subset.copy(blueCubes = count)
                }
            }
        }
        subset
    }

    return gameId to subsetClassList
}

fun getCubeCountAndColor(cubeString: String): Pair<CubeColor, Int> {
    val (countStr, colorStr) = cubeString.split(" ")
    val cubeColor = when (colorStr) {
        "red" -> CubeColor.RED
        "green" -> CubeColor.GREEN
        "blue" -> CubeColor.BLUE
        else -> error("Unknown color")
    }
    return cubeColor to countStr.toInt()
}

enum class CubeColor {
    RED, GREEN, BLUE
}

data class Subset(
    val redCubes: Int = 0,
    val greenCubes: Int = 0,
    val blueCubes: Int = 0
)