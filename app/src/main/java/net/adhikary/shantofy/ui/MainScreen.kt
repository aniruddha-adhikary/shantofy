package net.adhikary.shantofy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import net.adhikary.shantofy.R

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val backgroundColor = Color(0xFF3D1240)
    val imagePadding = 16.dp
    val buttonPadding = 8.dp
    val textPadding = 16.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(imagePadding)
        ) {
            // Image at the top
            Image(
                painter = painterResource(id = R.drawable.ic_shantofy_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(width = 233.dp, height = 240.dp)
            )

            // Scrollable Text in the middle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.welcome_message),
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Button and Credits at the bottom
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(bottom = buttonPadding)
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/aniruddha-adhikary/shantofy"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(0.5f)  // Take up 50% of the width
                ) {
                    Text(
                        text = "GitHub",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(buttonPadding))

                Text(
                    text = stringResource(R.string.credits_message),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 72.dp)
                )
            }
        }
    }
}
