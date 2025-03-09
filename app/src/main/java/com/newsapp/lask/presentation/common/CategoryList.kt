package com.newsapp.lask.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newsapp.lask.R


@Composable
fun CategoryList(
    onCategoryClick: (String) -> Unit,
) {
    val categories = listOf(
        R.string.sport_category,
        R.string.business_category,
        R.string.politic_category,
        R.string.technology_category,
        R.string.culture_category
    )
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    LazyRow(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        item {
            Spacer(modifier = Modifier.width(25.dp))

        }
        categories.forEach { category ->
            item {
                CategoryItem(
                    category = stringResource(category),
                    isSelected = category == selectedCategory,
                    onSelect = { selectedCategory = category },
                    onCategorySelect = { onCategoryClick(it) }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview
@Composable
fun CatList() {
    CategoryList { }
}