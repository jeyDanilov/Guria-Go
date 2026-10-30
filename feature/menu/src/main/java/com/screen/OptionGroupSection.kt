package com.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.model.Option
import com.model.OptionGroup
import com.model.SelectionType

@Composable
fun OptionGroupSection(
    group: OptionGroup,
    selectedIds: Set<String>,
    onOptionClick: (Option) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(group.title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        when (group.type) {
            SelectionType.SINGLE -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                group.options.forEach { option ->
                    FilterChip(
                        selected = option.id in selectedIds,
                        onClick = { onOptionClick(option) },
                        label = { Text(option.name) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Wine,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            SelectionType.MULTIPLE -> group.options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable
                        { onOptionClick(option) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = option.id in selectedIds, onCheckedChange = null)
                    Text(option.name, modifier = Modifier.padding(start = 8.dp))
                    if (option.extraPriceCents > 0) {
                        Text(
                            "+%.2f €".format(option.extraPriceCents / 100.0),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 120)
@Composable
fun SingleChoicePreview() {
    val group = OptionGroup(
        id = "spice",
        title = "Picante",
        type = SelectionType.SINGLE,
        options = listOf(
            Option("leve", "No pica"),
            Option("caliente", "Picante")
        ),
        required = true
    )
    var selected by remember { mutableStateOf(emptySet<String>()) }
    MaterialTheme {
        OptionGroupSection(
            group = group,
            selectedIds = selected,
            onOptionClick = { selected = setOf(it.id) },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 120)
@Composable
fun MeatChoicePreview() {
    val group = OptionGroup(
        id = "carne ",
        title = "Carne",
        type = SelectionType.SINGLE,
        options = listOf(
            Option("pollo", "Pollo"),
            Option("cerda", "Cerda")
        ),
        required = true
    )
    var selected by remember { mutableStateOf(emptySet<String>()) }
    MaterialTheme {
        OptionGroupSection(
            group = group,
            selectedIds = selected,
            onOptionClick = { selected = setOf(it.id) },
            modifier = Modifier.padding(16.dp)
        )
    }
}
