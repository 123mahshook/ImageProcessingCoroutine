package com.mahshook.imageprocessingcoroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahshook.imageprocessingcoroutine.ui.theme.ImageProcessingCoroutineTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL
import java.util.logging.Filter

class MainActivity : ComponentActivity() {
    val imageUrl =
        "https://previews.123rf.com/images/yamayka/yamayka1606/yamayka160600170/57527311-cartoon-wild-animals-for-kids-little-cute-elephant-lets-water-fountain-from-the-trunk-he-smiles.jpg"
    var isLoading = false;
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContent {*/
        /*    ImageProcessingCoroutineTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {*/
        /* Greeting("Android")*/
        coroutineScope.launch {
            isLoading = true;
            setContent {
                ImageProcessingCoroutineTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        progress(isLoading)
                    }
                }
            }
            val originalDeferred = coroutineScope.async(Dispatchers.IO) { getBitmap() }
            val originalBitmap = originalDeferred.await()
            isLoading = false;
            setContent {
                progress(isLoading)
                BitmapImage(originalBitmap)
            }
        }
        /* }
     }*/
        /*}*/
    }

    private fun getBitmap() = URL(imageUrl).openStream().use { BitmapFactory.decodeStream(it) }

    /*private fun applyFilter(originalBitmap: Bitmap)= Filter.apply()*/

    @Composable
    fun BitmapImage(bitmap: Bitmap) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier=Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "some useful description",
                )

            }
        }

    }

    @Composable
    fun progress(isLoading: Boolean) {
        if (isLoading) {
            Card(

                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier=Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    CircularProgressIndicator(progress = 0.5f)

                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageProcessingCoroutineTheme {
        Greeting("Android")
    }
}