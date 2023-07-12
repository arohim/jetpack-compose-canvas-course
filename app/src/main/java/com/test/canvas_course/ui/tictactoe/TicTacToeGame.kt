package com.test.canvas_course.ui.tictactoe

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeGame(

) {
    var gameState by remember {
        mutableStateOf(Array(3) { Array(3) { 'E' } })
    }
    var currentPlayer by remember { mutableStateOf('X') }
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Yellow)
                .pointerInput(Unit) {
                    detectTapGestures { clickedOffset ->
                        val result = handleClicked(clickedOffset, gameState, currentPlayer)
                        gameState = result.first
                        if (checkWin(gameState, currentPlayer)) {
                            showDialog(context, "Player $currentPlayer wins!")
                            gameState = Array(3) { Array(3) { 'E' } }
                            currentPlayer = 'X'
                        }
                        currentPlayer = result.second
                    }
                }
        ) {
            drawBoard()
            drawPlayerMoves(gameState)
        }
    }
}

fun PointerInputScope.handleClicked(
    it: Offset,
    gameState: Array<Array<Char>>,
    currentPlayer: Char
): Pair<Array<Array<Char>>, Char> {
    var newGameState = gameState
    when {
        it.x < size.width / 3f && it.y < size.height / 3f -> {
            // Top left
            if (gameState[0][0] == 'E') {
                newGameState = updateGameState(gameState, 0, 0, currentPlayer)
//                scope.animateFloatToOne(animations[0][0])
            }
        }

        it.x in (size.width / 3f)..(2 * size.width / 3f) &&
                it.y < size.height / 3f -> {
            // Top middle
            if (gameState[1][0] == 'E') {
                newGameState = updateGameState(gameState, 1, 0, currentPlayer)
//                scope.animateFloatToOne(animations[1][0])
            }
        }

        it.x > 2 * size.width / 3f && it.y < size.height / 3f -> {
            // Top right
            if (gameState[2][0] == 'E') {
                newGameState = updateGameState(gameState, 2, 0, currentPlayer)
//                scope.animateFloatToOne(animations[2][0])
            }
        }

        it.x < size.width / 3f && it.y in (size.height / 3f)..(2 * size.height / 3f) -> {
            // Middle left
            if (gameState[0][1] == 'E') {
                newGameState = updateGameState(gameState, 0, 1, currentPlayer)
//                scope.animateFloatToOne(animations[0][1])
            }
        }

        it.x in (size.width / 3f)..(2 * size.width / 3f) &&
                it.y in (size.height / 3f)..(2 * size.height / 3f) -> {
            // Middle middle
            if (gameState[1][1] == 'E') {
                newGameState = updateGameState(gameState, 1, 1, currentPlayer)
//                scope.animateFloatToOne(animations[1][1])
            }
        }

        it.x > 2 * size.width / 3f && it.y in (size.height / 3f)..(2 * size.height / 3f) -> {
            // Middle right
            if (gameState[2][1] == 'E') {
                newGameState = updateGameState(gameState, 2, 1, currentPlayer)
//                scope.animateFloatToOne(animations[2][1])
            }
        }

        it.x < size.width / 3f && it.y > 2 * size.height / 3f -> {
            // Bottom left
            if (gameState[0][2] == 'E') {
                newGameState = updateGameState(gameState, 0, 2, currentPlayer)
//                scope.animateFloatToOne(animations[0][2])
            }
        }

        it.x in (size.width / 3f)..(2 * size.width / 3f) && it.y > 2 * size.height / 3f -> {
            // Bottom middle
            if (gameState[1][2] == 'E') {
                newGameState = updateGameState(gameState, 1, 2, currentPlayer)
//                scope.animateFloatToOne(animations[1][2])
            }
        }

        it.x > 2 * size.width / 3f && it.y > 2 * size.height / 3f -> {
            // Bottom right
            if (gameState[2][2] == 'E') {
                newGameState = updateGameState(gameState, 2, 2, currentPlayer)
//                scope.animateFloatToOne(animations[2][2])
            }
        }
    }
    return Pair(newGameState, if (currentPlayer == 'X') 'O' else 'X')
}

private fun updateGameState(
    gameState: Array<Array<Char>>,
    i: Int,
    j: Int,
    symbol: Char
): Array<Array<Char>> {
    val arrayCopy = gameState.copyOf()
    arrayCopy[i][j] = symbol
    return arrayCopy
}

private fun DrawScope.drawPlayerMoves(gameState: Array<Array<Char>>) {
    val width = size.width
    val height = size.height

    gameState.forEachIndexed { i, row ->
        row.forEachIndexed { j, symbol ->

            when (val piece = gameState[i][j]) {
                'X' -> {
                    //  draw X
                    val path1 = Path().apply {
                        moveTo(
                            i * size.width / 3f + size.width / 6f - 50f,
                            j * size.height / 3f + size.height / 6f - 50f
                        )
                        lineTo(
                            i * size.width / 3f + size.width / 6f + 50f,
                            j * size.height / 3f + size.height / 6f + 50f
                        )
                    }
                    val path2 = Path().apply {
                        moveTo(
                            i * size.width / 3f + size.width / 6f - 50f,
                            j * size.height / 3f + size.height / 6f + 50f
                        )
                        lineTo(
                            i * size.width / 3f + size.width / 6f + 50f,
                            j * size.height / 3f + size.height / 6f - 50f,
                        )
                    }

                    drawPath(
                        path1,
                        color = Color.Black,
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    drawPath(
                        path2,
                        color = Color.Black,
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }

                'O' -> {
                    // draw O
                    val x = i * size.width / 3f + size.width / 6f - 50f
                    val y = j * size.height / 3f + size.height / 6f - 50f
                    drawArc(
                        color = Color.Black,
                        topLeft = Offset(x, y),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = true,
                        size = Size(100f, 100f),
                        style = Stroke(
                            width = 5.dp.toPx()
                        )
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawBoard() {
    val width = size.width
    val cellSize = width / 3f
    for (i in 1..2) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, cellSize * i),
            end = Offset(width, cellSize * i),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = Offset(cellSize * i, 0f),
            end = Offset(cellSize * i, width),
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun checkWin(board: Array<Array<Char>>, player: Char): Boolean {
    for (i in 0..2) {
        if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
            return true
        }
        if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
            return true
        }
    }

    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
        return true
    }
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
        return true
    }

    return false
}

private fun showDialog(context: Context, message: String) {
    AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        .show()
}