package com.example.littlelemon.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.models.HomeState
import com.example.littlelemon.models.MenuItem
import com.example.littlelemon.ui.MenuItemCard
import com.example.littlelemon.ui.events.HomeEvent
import com.example.littlelemon.utils.Profile
import com.example.littlelemon.utils.Resource
import com.example.littlelemon.vm.HomeViewModel

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by remember(viewModel) {
        viewModel.state
    }.collectAsState(initial = HomeState())
    Column {
        HomeTop() {navController.navigate(Profile.route)}
        HeroSection(state.searchPhrase) {
            viewModel.onEvent(HomeEvent.onSearchPhraseChanged(it))
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Column {
                CategoryPillSection(state.categories.sortedDescending(), state.categorySelected) {
                    viewModel.onEvent(HomeEvent.onFilter(it))
                }
                when (state.menu) {
                    is Resource.Error -> {
                        Text(text = "${state.menu.msg}",
                        color = colorResource(id = R.color.littlelemon_grey),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentHeight())
                    }
                    is Resource.Loading -> CircularProgressIndicator()
                    is Resource.Success -> state.menu.data?.let { MenuItemList(it) }
                }
            }
        }
    }
}


@Composable
fun HomeTop(onClickIcon: () -> Unit = {}) {
    Box {
       Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
       modifier = Modifier
           .fillMaxWidth()
           .height(70.dp)
           .padding(bottom = 10.dp, top = 10.dp))
        Image(painter = painterResource(id = R.drawable.profile), contentDescription = "Profile",
        modifier = Modifier
            .height(50.dp)
            .padding(end = 20.dp)
            .align(Alignment.CenterEnd)
            .clickable { onClickIcon() })
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HeroSection(searchPhraseValue: String, onValueChanged: (String)->Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.littlelemon_green))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.restaurant_name),
            color = colorResource(id = R.color.littlelemon_yellow),
            fontSize = 40.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .offset(y = (-8).dp)) {
                Column {
                    Text(text = stringResource(id = R.string.city),
                    color = colorResource(id = R.color.littlelemon_light_grey),
                    fontSize = 40.sp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .offset(y = (-24).dp))
                    Text(text = stringResource(id = R.string.short_description),
                    color = colorResource(id = R.color.littlelemon_light_grey),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                    lineHeight = 1.em,
                    modifier = Modifier
                        .width(260.dp)
                        .padding(start = 8.dp, end = 4.dp, bottom = 8.dp)
                        .clip(RoundedCornerShape(16.dp)))
                    OutlinedTextField(
                        value = searchPhraseValue,
                        onValueChange = { onValueChanged(it) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
                        placeholder = {
                            Text(text = stringResource(id = R.string.home_text_field_label), fontSize = 14.sp, textAlign = TextAlign.Center)
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "")
                        },
                        colors = TextFieldDefaults.textFieldColors(containerColor = colorResource(id = R.color.littlelemon_light_grey)),
                        shape = RoundedCornerShape(8.dp),
                        textStyle = TextStyle(fontSize = 14.sp),
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .offset(y = (-8).dp)
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryPillSection(
    categories: List<String> = emptyList(),
    categorySelected: String = "",
    onPillClicked: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(id = R.string.home_pill_text),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            categories.forEach {
                Pill(it, it == categorySelected) {
                    onPillClicked(it)
                }
            }
        }
    }

}

@Composable
fun MenuItemList(
    menu: List<MenuItem> = emptyList()
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(menu) { item ->
            MenuItemCard(item)
        }
    }
}

@Composable
fun Pill(
    text: String = "",
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val backgroundColor = if (selected) {
        colorResource(id = R.color.littlelemon_yellow)
    } else {
        colorResource(id = R.color.littlelemon_light_grey)
    }
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(backgroundColor)
            .padding(6.dp)

    ) {
        Text(text = text.replaceFirstChar { it.uppercaseChar() }, fontSize = 14.sp, color = Color.Black)
    }
}