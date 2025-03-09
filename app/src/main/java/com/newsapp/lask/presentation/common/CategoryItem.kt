package com.newsapp.lask.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newsapp.lask.R

@Composable
fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onCategorySelect:(String)->Unit
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) colorResource(R.color.category_background)
                else colorResource(R.color.input_background)
            )
            .width(120.dp)
            .height(40.dp)
            .clickable {
                onSelect()
                onCategorySelect(category)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = category,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = if (isSelected) colorResource(R.color.input_background) else colorResource(R.color.text_title)
        )
    }
}
