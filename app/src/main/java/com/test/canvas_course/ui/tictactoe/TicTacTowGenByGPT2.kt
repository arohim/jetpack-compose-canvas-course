//package com.test.canvas_course.ui.tictactoe
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.MaterialTheme
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
//import androidx.compose.ui.graphics.nativeCanvas
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun TicTacToeGame() {
//    val context = LocalContext.current
//    var board by remember { mutableStateOf(Array(3) { Array(3) { ' ' } }) }
//    var currentPlayer by remember { mutableStateOf('X') }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colors.background)
//            .pointerInput(Unit) {
//                detectTapGestures { tapOffset ->
//                    val cellSize = size.width / 3
//                    val row = (tapOffset.y / cellSize).toInt()
//                    val col = (tapOffset.x / cellSize).toInt()
//                    if (row in 0..2 && col in 0..2 && board[row][col] == ' ') {
//                        board[row][col] = currentPlayer
//                        if (checkWin(board, currentPlayer)) {
//                            showDialog(context, "Player $currentPlayer wins!")
//                            resetGame()
//                        } else if (isBoardFull(board)) {
//                            showDialog(context, "It's a tie!")
//                            resetGame()
//                        } else {
//                            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
//                        }
//                    }
//                }
//            }
//    ) {
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            drawBoard()
//            drawPieces()
//        }
//    }
//}
//
//private fun DrawScope.drawBoard() {
//    val boardSize = size.width
//    val cellSize = boardSize / 3
//
//    for (i in 1..2) {
//        val startX = i * cellSize
//        drawLine(
//            color = Color.Black,
//            start = Offset(startX, 0f),
//            end = Offset(startX, boardSize),
//            strokeWidth = 4f
//        )
//
//        val startY = i * cellSize
//        drawLine(
//            color = Color.Black,
//            start = Offset(0f, startY),
//            end = Offset(boardSize, startY),
//            strokeWidth = 4f
//        )
//    }
//}
//
//private fun DrawScope.drawPieces() {
//    val boardSize = size.width
//    val cellSize = boardSize / 3
//
//    for (i in 0..2) {
//        for (j in 0..2) {
//            val centerX = j * cellSize + cellSize / 2
//            val centerY = i * cellSize + cellSize / 2
//
//            when (val piece = board[i][j]) {
//                'X' -> {
//                    drawLine(
//                        color = Color.Blue,
//                        start = Offset(centerX - 40f, centerY - 40f),
//                        end = Offset(centerX + 40f, centerY + 40f),
//                        strokeWidth = 8f
//                    )
//                    drawLine(
//                        color = Color.Blue,
//                        start = Offset(centerX + 40f, centerY - 40f),
//                        end = Offset(centerX - 40f, centerY + 40f),
//                        strokeWidth = 8f
//                    )
//                }
//                'O' -> {
//                    drawCircle(
//                        color = Color.Red,
//                        radius = 40f,
//                        center = Offset(centerX, centerY),
//                        style = Stroke(width = 8f)
//                    )
//                }
//            }
//        }
//    }
//}
//
//private fun checkWin(board: Array<Array<Char>>, player: Char): Boolean {
//    for (i in 0..2) {
//        if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
//            return true
//        }
//        if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
//            return true
//        }
//    }
//
//    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
//        return true
//    }
//    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
//        return true
//    }
//
//    return false
//}
//
//private fun isBoardFull(board: Array<Array<Char>>): Boolean {
//    for (row in board) {
//        for (cell in row) {
//            if (cell == ' ') {
//                return false
//            }
//        }
//    }
//    return true
//}
//
//private fun showDialog(context: Context, message: String) {
//    AlertDialog.Builder(context)
//        .setMessage(message)
//        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//        .show()
//}
//
//private fun resetGame() {
//    board = Array(3) { Array(3) { ' ' } }
//    currentPlayer = 'X'
//}
