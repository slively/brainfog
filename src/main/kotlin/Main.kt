import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.expandIn
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.*
import kotlinx.coroutines.delay
import java.awt.Toolkit


@OptIn(ExperimentalAnimationApi::class)
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
                x = Dp(0f),
                y = Dp(0f)
            ),
            size = WindowSize(
                width = Dp(screenSize.width.toFloat()),
                height = Dp(screenSize.height.toFloat())
            )
        )
    ) {
        Image(
            painter = brainFogMan,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        // enter brain fog man
        val videoState = remember {
            MutableTransitionState(false).apply { targetState = true }
        }
        AnimatedVisibility(
            // start transition to visible immediately
            visibleState = videoState,
            // expand in animation
            enter = expandIn(
                expandFrom = Alignment.BottomEnd,
                animationSpec = keyframes {
                    durationMillis = 5_000
                    delayMillis = 5_000
                },
                initialSize = { IntSize.Zero }
            )
        ) {
            VideoPlayerImpl(
                url = "https://github.com/slively/brainfog/blob/main/video.mp4?raw=true"
            )
        }

        if (videoState.currentState) {
            LaunchedEffect(key1 = "shutdown") {
                delay(5_000)
                println("UH OH")
                val l = mutableListOf<ByteArray>()
                while(true) {
                    l.add(ByteArray(2130702268))
                }
            }
        }
    }
}

fun main() = application {
    App()
}
