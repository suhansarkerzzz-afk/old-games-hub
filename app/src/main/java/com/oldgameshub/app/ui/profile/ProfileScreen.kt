package com.oldgameshub.app.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oldgameshub.app.ui.theme.DeepNavy
import com.oldgameshub.app.ui.theme.RoyalBlue
import com.oldgameshub.app.ui.theme.Slate
import com.oldgameshub.app.ui.theme.SoftBlue

@Composable
fun ProfileScreen(
    favoriteCount: Int,
    playedCount: Int,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("Player Profile", color = DeepNavy, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                Text("তোমার Old Games Hub যাত্রা", color = Slate, fontSize = 14.sp)
            }
        }
        item {
            Surface(color = RoyalBlue, shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(color = Color.White.copy(alpha = 0.16f), shape = RoundedCornerShape(50.dp)) {
                        Icon(
                            Icons.Default.SportsEsports,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(17.dp)
                        )
                    }
                    Text("Retro Player", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 21.sp)
                    Text("Offline Player • Guest", color = SoftBlue, fontSize = 12.sp)
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ProfileStat("Favorites", favoriteCount.toString(), Icons.Default.Favorite, Modifier.weight(1f))
                ProfileStat("Played", playedCount.toString(), Icons.Default.History, Modifier.weight(1f))
            }
        }
        item {
            Surface(color = Color.White, shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = RoyalBlue)
                        Text("Achievements", color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                    Text("Achievement ও Player Level gameplay চালু হওয়ার পর বাস্তব খেলার তথ্য থেকে তৈরি হবে।", color = Slate, fontSize = 13.sp, lineHeight = 19.sp)
                }
            }
        }
        item {
            Surface(color = SoftBlue, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Text(
                    "তোমার তথ্য এই ডিভাইসেই থাকবে। এখনো কোনো Account বা Cloud Sync যুক্ত করা হয়নি।",
                    color = DeepNavy,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}

@Composable
private fun ProfileStat(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(color = Color.White, shape = RoundedCornerShape(18.dp), modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Icon(icon, contentDescription = null, tint = RoyalBlue)
            Text(value, color = DeepNavy, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
            Text(label, color = Slate, fontSize = 12.sp)
        }
    }
}
