package com.screen

import com.example.menu.R
import com.model.Option
import com.model.OptionGroup
import com.model.Pricing
import com.model.SelectionType

object FakeDishes {
    val category = listOf("Hot Dishes", "Cold Dishes", "Salads", "Desserts")
    val all = listOf(
        Dish(
            id = 1,
            name = "Khinkali",
            description = "Georgian dumplings with beef and pork",
            pricing = Pricing.PerUnit(unitPriceCents = 250, minQuantity = 5, maxQuantity = 10),
            image = R.drawable.khinkali,
            categories = listOf("Hot Dishes")
        ),
        Dish(
            id = 2,
            name = "Adjarian khachapuri",
            description = "Cheese bread with egg",
            pricing = Pricing.Fixed(1600),
            image = R.drawable.adjarulikhachapuri,
            categories = listOf("Hot Dishes")
        ),
        Dish(
            id = 3,
            name = "Lobiani",
            description = "Bread filled with beans",
            pricing = Pricing.Fixed(900),
            image = R.drawable.lobiani,
            categories = listOf("Hot Dishes")
        ),
        Dish(
            id = 4,
            name = "Wrap",
            description = "Our best-seller",
            pricing = Pricing.Fixed(1245),
            image = R.drawable.wrap,
            categories = listOf("Hot Dishes"),
            optionGroups = listOf(
                OptionGroup(
                    id = "meat",
                    title = "Meat",
                    type = SelectionType.SINGLE,
                    options = listOf(
                        Option("chicken", "Chicken"),
                        Option("pork", "Pork")
                    ),
                    required = true
                ),
                OptionGroup(
                    id = "spice",
                    title = "Spiciness",
                    type = SelectionType.SINGLE,
                    options = listOf(
                        Option("mild", "Not spicy"),
                        Option("hot", "Spicy")
                    ),
                    required = true
                )
            )
        ),
        Dish(
            id = 5,
            name = "Imeruli khachapuri",
            description = "Our best-seller",
            pricing = Pricing.Fixed(1200),
            image = R.drawable.imerulikhachapuri,
            categories = listOf("Hot Dishes")
        ),
        Dish(
            id = 6,
            name = "Badrijani",
            description = "Our best-seller",
            pricing = Pricing.Fixed(1200),
            image = R.drawable.badrijani,
            categories = listOf("Cold Dishes")
        ),
        Dish(
            id = 7,
            name = "Ensalada",
            description = "Our best-seller",
            pricing = Pricing.Fixed(900),
            image = R.drawable.ensaladageorgian,
            categories = listOf("Cold Dishes", "Salads")
        ),
    )

    fun byId(id: Int): Dish = all.first { it.id == id }

}