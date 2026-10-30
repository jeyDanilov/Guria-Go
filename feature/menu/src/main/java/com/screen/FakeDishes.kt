package com.screen

import com.example.menu.R
import com.model.Option
import com.model.OptionGroup
import com.model.Pricing
import com.model.SelectionType

object FakeDishes {
    val category = listOf("Platos Calientes", "Platos Fríos", "Ensaladas", "Postres")
    val all = listOf(
        Dish(
            id = 1,
            name = "Khinkali",
            description = "Empanadillas tradicionales georgianas rellenas de ternera y cerdo especiados.",
            pricing = Pricing.PerUnit(unitPriceCents = 250, minQuantity = 5, maxQuantity = 10),
            image = R.drawable.khinkali,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 2,
            name = "Adjarian khachapuri",
            description = "Pan georgiano en forma de barco relleno de queso fundido y huevo fresco.",
            pricing = Pricing.Fixed(1600),
            image = R.drawable.adjarulikhachapuri,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 3,
            name = "Lobiani",
            description = "Pan tradicional georgiano relleno de judías blancas condimentadas.",
            pricing = Pricing.Fixed(900),
            image = R.drawable.lobiani,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 4,
            name = "Wrap",
            description = "Tortilla rellena con la carne de tu elección y el nivel de picante que prefieras.",
            pricing = Pricing.Fixed(1245),
            image = R.drawable.wrap,
            categories = listOf("Platos Calientes"),
            optionGroups = listOf(
                OptionGroup(
                    id = "meat",
                    title = "Carne",
                    type = SelectionType.SINGLE,
                    options = listOf(
                        Option("pollo", "Pollo"),
                        Option("cerda", "Cerda")
                    ),
                    required = true
                ),
                OptionGroup(
                    id = "spice",
                    title = "Picante",
                    type = SelectionType.SINGLE,
                    options = listOf(
                        Option("suave", "No pica"),
                        Option("caliente", "Picante")
                    ),
                    required = true
                )
            )
        ),
        Dish(
            id = 5,
            name = "Imeruli khachapuri",
            description = "Pan redondo georgiano relleno de una mezcla de quesos fundidos.",
            pricing = Pricing.Fixed(1200),
            image = R.drawable.imerulikhachapuri,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 6,
            name = "Badrijani",
            description = "Rollitos de berenjena rellenos de crema de nueces y especias georgianas.",
            pricing = Pricing.Fixed(1200),
            image = R.drawable.badrijani,
            categories = listOf("Platos Fríos")
        ),
        Dish(
            id = 7,
            name = "Ensalada",
            description = "Ensalada fresca de tomate, pepino, cebolla y hierbas aromáticas.",
            pricing = Pricing.Fixed(900),
            image = R.drawable.ensaladageorgian,
            categories = listOf("Platos Fríos", "Ensaladas")
        ),
    )

    fun byId(id: Int): Dish = all.first { it.id == id }

}