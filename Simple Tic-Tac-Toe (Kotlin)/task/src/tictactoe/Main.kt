package tictactoe

const val SIZE = 3
const val EMPTY = ' '

enum class Player(val symbol: Char) {
    ONE('X'),
    TWO('O'),
}

val grid = List(SIZE) { MutableList(SIZE) { EMPTY } }

fun main() {
    printGrid()
    var player = Player.ONE
    while (true) {
        try {
            val (row, column) = readln().split(" ").map { it.toInt() - 1 }
            if (row !in grid.indices || column !in grid.first().indices) { println("Coordinates should be from 1 to ${grid.size}!"); continue }
            if (grid[row][column] != EMPTY) { println("This cell is occupied! Choose another one!"); continue }
            grid[row][column] = player.symbol
            printGrid()
            if (wins(player)) { println("${player.symbol} wins"); return }
            if (!grid.any { it.contains(EMPTY) }) { println("Draw"); return }
            player = if (player == Player.ONE) Player.TWO else Player.ONE
        } catch (e: NumberFormatException) {
            println("You should enter numbers!")
        }
    }
}

fun printGrid() {
    println("-".repeat(grid.size * 2 + 3))
    grid.map { println("| ${it.joinToString(" ")} |") }
    println("-".repeat(grid.size * 2 + 3))
}

fun wins(player: Player): Boolean {
    val win = player.symbol.toString().repeat(grid.size)
    var leftDiagonal = ""
    var rightDiagonal = ""
    grid.forEachIndexed { index, row ->
        if (row.joinToString("") == win || grid.joinToString("") { it[index].toString() } == win) return true
        leftDiagonal += grid[index][index]
        rightDiagonal += grid[index][grid.size - 1 - index]
    }
    return leftDiagonal == win || rightDiagonal == win
}
