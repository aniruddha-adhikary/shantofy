package net.adhikary.thamen.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.adhikary.thamen.R

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val backgroundColor = Color(0xFF1E1E1E)  // Dark background
    val accentColor = Color(0xFF6200EE)      // Primary purple accent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // App Title
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .padding(top = 16.dp),
            )

            // Status Card
            val statusHelper = StatusHelper(context)
            val status = remember { mutableStateOf(statusHelper.getAppStatus()) }
            
            // Update status every second
            LaunchedEffect(Unit) {
                while(true) {
                    status.value = statusHelper.getAppStatus()
                    delay(1000)
                }
            }
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp)),
                color = accentColor.copy(alpha = 0.08f)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.status_title),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    StatusItem(
                        title = stringResource(R.string.notification_permission_status),
                        isEnabled = status.value.isNotificationPermissionGranted
                    )
                    
                    StatusItem(
                        title = stringResource(R.string.notification_listener_status),
                        isEnabled = status.value.isNotificationListenerEnabled
                    )
                    
                    StatusItem(
                        title = stringResource(R.string.service_status),
                        isEnabled = status.value.isServiceRunning
                    )
                    
                    if (!status.value.isNotificationPermissionGranted || 
                        !status.value.isNotificationListenerEnabled) {
                        Text(
                            text = stringResource(R.string.status_help_message),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // GitHub Button
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/aniruddha-adhikary/shantofy"))
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "View on GitHub",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Credits
            Text(
                text = stringResource(R.string.credits_message),
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
fun StatusItem(title: String, isEnabled: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = if (isEnabled) "✅" else "❌",
            fontSize = 16.sp
        )
    }
}
