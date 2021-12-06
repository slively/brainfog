import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skija.Bitmap
import org.jetbrains.skija.Codec
import org.jetbrains.skija.Data


@Composable
fun GifAnimation(resourceFilePath: String, modifier: Modifier = Modifier) {
    val codec = remember {
        val classLoader = Thread.currentThread().contextClassLoader!!
        val inputStream = requireNotNull(classLoader.getResourceAsStream(resourceFilePath)) {
            "Resource $resourceFilePath not found"
        }
        Codec.makeFromData(Data.makeFromBytes(inputStream.readAllBytes()))
    }

    val transition = rememberInfiniteTransition()
    val frameIndex by transition.animateValue(
        initialValue = 0,
        targetValue = codec.frameCount - 1,
        Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 0
                for ((index, frame) in codec.framesInfo.withIndex()) {
                    index at durationMillis
                    durationMillis += frame.duration
                }
            }
        )
    )

    val bitmap = remember { Bitmap().apply { allocPixels(codec.imageInfo) } }
    Canvas(modifier) {
        codec.readPixels(bitmap, frameIndex)
        drawImage(bitmap.asImageBitmap())
    }
}