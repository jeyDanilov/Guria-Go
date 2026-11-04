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
            description = "Tradicionales empanadillas georgianas elaboradas a mano, rellenas de una jugosa mezcla de carne de ternera y cerdo, cebolla y especias aromáticas. Se cocinan hasta conseguir una masa tierna y un interior lleno de caldo sabroso. Se disfrutan calientes, espolvoreadas con pimienta negra.",
            pricing = Pricing.PerUnit(unitPriceCents = 250, minQuantity = 5, maxQuantity = 10),
            image = R.drawable.khinkali,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 2,
            name = "Adjarian khachapuri",
            description = "Uno de los platos más emblemáticos de Georgia. Pan recién horneado con forma de barco, relleno de una generosa mezcla de quesos georgianos fundidos y coronado con huevo y mantequilla. Su masa ligeramente crujiente por fuera y esponjosa por dentro combina perfectamente con el relleno cremoso y salado.",
            pricing = Pricing.Fixed(1600),
            image = R.drawable.adjarulikhachapuri,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 3,
            name = "Lobiani",
            description = "Pan tradicional georgiano preparado con una masa suave y esponjosa, rellena de judías cocinadas lentamente y condimentadas con especias y hierbas aromáticas. Su interior cremoso y lleno de sabor contrasta con la superficie dorada y ligeramente crujiente. Una receta casera muy popular en Georgia.",
            pricing = Pricing.Fixed(900),
            image = R.drawable.lobiani,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 4,
            name = "Wrap",
            description = "Tortilla de trigo suave y ligeramente tostada, rellena de una combinación de carne a elegir, ingredientes frescos y una deliciosa salsa.  Una opción sabrosa y práctica, perfecta para disfrutar de una comida completa.",
            pricing = Pricing.Fixed(1245),
            prepMinMinutes = 20,
            prepMaxMinutes = 25,
            image = R.drawable.wrap,
            categories = listOf("Platos Calientes"),
            optionGroups = listOf(
                OptionGroup(
                    id = "meat",
                    title = "Carne",
                    type = SelectionType.SINGLE,
                    options = listOf(
                        Option("pollo", "Pollo"),
                        Option("cerda", "Cerdo")
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
            description = "Pan tradicional de la región de Imereti, elaborado en forma redonda y relleno de una generosa mezcla de quesos georgianos. Se hornea hasta conseguir una superficie dorada y un interior suave, fundente y ligeramente salado. Cada porción ofrece el equilibrio perfecto entre una masa tierna y un relleno cremoso de queso.",
            pricing = Pricing.Fixed(1200),
            image = R.drawable.imerulikhachapuri,
            categories = listOf("Platos Calientes")
        ),
        Dish(
            id = 6,
            name = "Badrijani",
            description = "Deliciosos rollitos de berenjena asada, rellenos de una cremosa pasta de nueces trituradas, ajo y especias tradicionales georgianas. Se sirven fríos para conservar toda su frescura y sabor. Su textura suave y su combinación de notas tostadas, ligeramente ácidas y aromáticas los convierten en uno de los entrantes más característicos de la gastronomía georgiana.",
            pricing = Pricing.Fixed(1200),
            image = R.drawable.badrijani,
            categories = listOf("Platos Fríos")
        ),
        Dish(
            id = 7,
            name = "Ensalada",
            description = "Ensalada fresca y colorida preparada con tomates maduros, pepino crujiente, cebolla y una selección de hierbas aromáticas. Una combinación sencilla de ingredientes naturales que aporta frescura y equilibrio a cualquier comida. Ideal para acompañar platos calientes o disfrutar como entrante ligero.",
            pricing = Pricing.Fixed(900),
            image = R.drawable.ensaladageorgian,
            categories = listOf("Platos Fríos", "Ensaladas")
        ),
    )

    fun byId(id: Int): Dish = all.first { it.id == id }

}