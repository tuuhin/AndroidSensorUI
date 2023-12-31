package com.eva.sensorui.presentation.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eva.sensorui.R

@Composable
fun SensorCard(
    @DrawableRes image: Int,
    title: String,
    range: Float,
    vendor: String,
    onTap: () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    OutlinedCard(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onTap, role = Role.Button),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    colorFilter = ColorFilter
                        .tint(MaterialTheme.colorScheme.surfaceTint),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
            ) {
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor
                )
                Divider(
                    modifier = Modifier
                        .padding(vertical = 2.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        append("Range: ")
                        append("$range")
                    },
                    style = textStyle,
                    color = textColor
                )
                Text(
                    text = buildAnnotatedString {
                        append("Vendor: ")
                        append(vendor)
                    }, style = textStyle,
                    color = textColor
                )
            }
        }
    }
}

@Preview
@Composable
fun SensorCardPreview() {
    SensorCard(
        image = R.drawable.ic_sensor_brightness,
        title = "Brightness",
        onTap = { },
        vendor = "Something",
        range = 0f
    )
}