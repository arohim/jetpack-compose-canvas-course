package com.test.canvas_course.ui.tictactoe
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.unit.dp
//
//// Game Constants
//private const val BOARD_SIZE = 3
//private const val CELL_SIZE = 100
//private const val LINE_WIDTH = 8
//
//// Game States
//private val board = mutableStateListOf<List<Char>>()
//private val currentPlayer = mutableStateOf('X')
//private val winner = mutableStateOf<Char?>(null)
//private val gameEnded = mutableStateOf(false)
//
//@Composable
//fun TicTacToeGenByGPT() {
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
////            .clickable(enabled = !gameEnded, onClick = { pos ->
////                handleMove(pos)
////            })
//    ) {
//        drawGameBoard()
//        drawPlayerMoves()
//        drawWinnerLine()
//    }
//}
//
//@Composable
//private fun handleMove(position: Offset) {
//    if (winner.value != null || gameEnded.value) return
//
//    val row = (position.y / CELL_SIZE).toInt()
//    val col = (position.x / CELL_SIZE).toInt()
//
//    if (board[row][col] == ' ') {
//        board[row][col] = currentPlayer.value
//        checkGameStatus(row, col)
//        currentPlayer.value = if (currentPlayer.value == 'X') 'O' else 'X'
//    }
//}
//
//@Composable
//private fun checkGameStatus(row: Int, col: Int) {
//    // Check rows
//    if (board[row].all { it == currentPlayer.value }) {
//        winner.value = currentPlayer.value
//        gameEnded.value = true
//        return
//    }
//
//    // Check columns
//    if (board.all { it[col] == currentPlayer.value }) {
//        winner.value = currentPlayer.value
//        gameEnded.value = true
//        return
//    }
//
//    // Check diagonals
//    if (row == col && board.all { it[row] == currentPlayer.value }) {
//        winner.value = currentPlayer.value
//        gameEnded.value = true
//        return
//    }
//
//    if (row + col == BOARD_SIZE - 1 && board.all { it[BOARD_SIZE - 1 - col] == currentPlayer.value }) {
//        winner.value = currentPlayer.value
//        gameEnded.value = true
//        return
//    }
//
//    // Check for a draw
//    if (board.flatten().none { it == ' ' }) {
//        gameEnded.value = true
//        return
//    }
//}
//
//@Composable
//fun DrawScope.drawGameBoard() {
//    val width = CELL_SIZE * BOARD_SIZE
//
//    // Draw lines
//    repeat(BOARD_SIZE - 1) { i ->
//        val offset = (i + 1) * CELL_SIZE.toFloat()
//        drawLine(
//            color = Color.Black,
//            start = Offset(offset, 0f),
//            end = Offset(offset, size.height),
//            strokeWidth = LINE_WIDTH.toFloat()
//        )
//
//        drawLine(
//            color = Color.Black,
//            start = Offset(0f, offset),
//            end = Offset(size.width, offset),
//            strokeWidth = LINE_WIDTH.toFloat()
//        )
//    }
//
//    // Draw cells
//    for (row in 0 until BOARD_SIZE) {
//        for (col in 0 until BOARD_SIZE) {
//            val cellContent = board[row][col]
//            val cellX = col * CELL_SIZE.toFloat()
//            val cellY = row * CELL_SIZE.toFloat()
//
//            drawPlayerSymbol(cellContent, cellX, cellY, CELL_SIZE)
//        }
//    }
//}
//
//@Composable
//private fun DrawScope.drawPlayerSymbol(symbol: Char, cellX: Float, cellY: Float, cellSize: Int) {
//    val offsetX = cellSize / 4f
//    val offsetY = cellSize / 4f
//    val symbolSize = cellSize / 2f
//
//    when (symbol) {
//        'X' -> {
//            drawLine(
//                color = Color.Black,
//                start = Offset(cellX + offsetX, cellY + offsetY),
//                end = Offset(cellX + offsetX + symbolSize, cellY + offsetY + symbolSize),
//                strokeWidth = LINE_WIDTH.toFloat()
//            )
//            drawLine(
//                color = Color.Black,
//                start = Offset(cellX + offsetX, cellY + offsetY + symbolSize),
//                end = Offset(cellX + offsetX + symbolSize, cellY + offsetY),
//                strokeWidth = LINE_WIDTH.toFloat()
//            )
//        }
//
//        'O' -> {
//            drawCircle(
//                color = Color.Transparent,
//                center = Offset(cellX + cellSize / 2f, cellY + cellSize / 2f),
//                radius = cellSize / 4f,
//                style = androidx.compose.ui.graphics.drawscope.Fill,
//                strokeWidth = LINE_WIDTH.toFloat(),
//                alpha = 1f
//            )
//        }
//    }
//}
//
//@Composable
//private fun DrawScope.drawWinnerLine() {
//    if (winner.value == null) return
//
//    val lineColor = MaterialTheme.colors.primary
//    val startX = CELL_SIZE / 2f
//    val startY = CELL_SIZE / 2f
//    val endX = size.width - CELL_SIZE / 2f
//    val endY = size.height - CELL_SIZE / 2f
//
//    drawLine(
//        color = lineColor,
//        start = Offset(startX, startY),
//        end = Offset(endX, endY),
//        strokeWidth = LINE_WIDTH.toFloat()
//    )
//}
