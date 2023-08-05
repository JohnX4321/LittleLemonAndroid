package com.example.littlelemon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.models.MenuItem

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(item: MenuItem) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row {
            Column(modifier = Modifier.width(300.dp)) {
                Text(text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                    modifier = Modifier.padding(5.dp),
                    color = Color.Black
                )
                Text(text = item.desc,
                color = colorResource(id = R.color.littlelemon_grey),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 1.em,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(5.dp))
                Text(text = "$${item.price}",
                color = colorResource(id = R.color.littlelemon_grey),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(5.dp))
            }
            AsyncImage(model = item.image, contentDescription = "Images",
                placeholder = painterResource(id = R.drawable.image_placeholder),
                error = painterResource(id = R.drawable.image_placeholder),
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .align(CenterVertically))
            //Glide is unable to load large imaze and causes invalid requirements exception. Hence using Coil
            /*GlideImage(model = ContextCompat.getDrawable(LocalContext.current,R.drawable.logo),contentDescription = "Dish Image", contentScale = ContentScale.Crop,
                modifier = Modifier.width(20.dp)
                    .height(20.dp)
                .padding(start = 8.dp,end=8.dp)
                .align(CenterVertically))*/
            /*{
                it.error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
                    .load("https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg")
            }*/
        }
    }
}