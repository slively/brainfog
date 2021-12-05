import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.expandIn
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.*
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit


@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenRect = Rectangle(Toolkit.getDefaultToolkit().screenSize)
    val capture = Robot().createScreenCapture(screenRect).asPainter()

    Window(
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
        // replace background with screenshot
        Image(painter = capture, contentDescription = null)

//        val videoUrl =
//        println(videoUrl)
        VideoPlayerImpl(
            url = videoUrl,
            width = 640,
            height = 480
        )
//        // enter brain fog man
//        val brainFogManState = remember {
//            MutableTransitionState(false).apply { targetState = true }
//        }
//        AnimatedVisibility(
//            // start transition to visible immediately
//            visibleState = brainFogManState,
//            // expand in animation
//            enter = expandIn(
//                expandFrom = Alignment.BottomEnd,
//                animationSpec = keyframes {
//                    durationMillis = 5_000
//                },
//                initialSize = { IntSize.Zero }
//            )
//        ) {
//            Image(
//                painter = painterResource("img.png"),
//                contentDescription = null,
//                contentScale = ContentScale.FillWidth,
//                modifier = Modifier.fillMaxWidth()
////                    )
//            )
//        }
//
//        if (brainFogManState.currentState) {
//            VideoPlayerImpl(
//                url = "./video.mp4",
//                width = 640,
//                height = 480
//            )
//        }
    }
}

fun main() = application {
    App()
}
