
package com.juliette.smartedutv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.juliette.smartedutv.ui.theme.SmartEduTVTheme

data class Recipe(
    val name: String,
    val category: String,
    val description: String,
    val time: String,
    val image: Int,
    val ingredients: List<String>,
    val steps: List<String>
)

val recipes = listOf(
    Recipe(
        "Ensalada de pollo",
        "Comidas",
        "Una ensalada fresca y nutritiva.",
        "20 minutos",
        R.drawable.ensalada,
        listOf("Pechuga de pollo", "Lechuga", "Jitomate", "Aguacate", "Limon"),
        listOf(
            "Cocinar la pechuga de pollo.",
            "Lavar y cortar las verduras.",
            "Cortar el pollo en trozos.",
            "Mezclar los ingredientes y agregar limon."
        )
    ),
    Recipe(
        "Avena con frutas",
        "Desayunos",
        "Un desayuno sencillo y energetico.",
        "10 minutos",
        R.drawable.avena,
        listOf("Avena", "Leche", "Platano", "Fresas", "Canela"),
        listOf(
            "Colocar la avena en un recipiente.",
            "Agregar la leche y la canela.",
            "Cortar las frutas.",
            "Agregar las frutas y servir."
        )
    ),
    Recipe(
        "Tostadas de aguacate",
        "Desayunos",
        "Una opcion saludable para comenzar el dia.",
        "10 minutos",
        R.drawable.tostadas,
        listOf("Pan integral", "Aguacate", "Limon", "Tomate", "Sal"),
        listOf(
            "Tostar las rebanadas de pan.",
            "Machacar el aguacate.",
            "Agregar unas gotas de limon.",
            "Untar el aguacate sobre el pan."
        )
    ),
    Recipe(
        "Pescado al limon",
        "Cenas",
        "Una cena ligera y facil de preparar.",
        "30 minutos",
        R.drawable.pescado,
        listOf("Filete de pescado", "Limon", "Ajo", "Pimienta", "Aceite de oliva"),
        listOf(
            "Sazonar el pescado con limon y ajo.",
            "Calentar un poco de aceite.",
            "Cocinar el pescado hasta que este bien cocido.",
            "Servir con verduras."
        )
    ),
    Recipe(
        "Yogur con fresas",
        "Postres",
        "Un postre fresco y natural.",
        "5 minutos",
        R.drawable.yogur,
        listOf("Yogur natural", "Fresas", "Avena", "Miel"),
        listOf(
            "Lavar y cortar las fresas.",
            "Colocar el yogur en un recipiente.",
            "Agregar las fresas y la avena.",
            "Añadir un poco de miel."
        )
    ),
    Recipe(
        "Agua de pepino",
        "Bebidas",
        "Una bebida refrescante.",
        "10 minutos",
        R.drawable.pepino,
        listOf("Pepino", "Agua", "Limon", "Hielo"),
        listOf(
            "Lavar y cortar el pepino.",
            "Licuar con agua.",
            "Agregar jugo de limon.",
            "Servir con hielo."
        )
    )
)

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartEduTVTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SmartRecipesApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SmartRecipesApp() {

    var category by remember { mutableStateOf("Todas") }
    var searchText by remember { mutableStateOf("") }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    var favorites by remember { mutableStateOf(setOf<String>()) }

    val categories = listOf(
        "Todas",
        "Desayunos",
        "Comidas",
        "Cenas",
        "Postres",
        "Bebidas",
        "Favoritos"
    )

    val filteredRecipes = recipes.filter { recipe ->

        val matchesCategory = when (category) {
            "Todas" -> true
            "Favoritos" -> favorites.contains(recipe.name)
            else -> recipe.category == category
        }

        val matchesSearch =
            recipe.name.contains(searchText.trim(), ignoreCase = true) ||
                    recipe.description.contains(searchText.trim(), ignoreCase = true)

        matchesCategory && matchesSearch
    }

    if (selectedRecipe == null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF10251F))
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Text(
                text = "SMART RECIPES TV",
                color = Color(0xFF9BE3B1),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Come saludable, vive mejor",
                color = Color.White,
                fontSize = 25.sp
            )

            Text(
                text = "Descubre recetas deliciosas y saludables",
                color = Color(0xFFB9CFC3),
                fontSize = 17.sp
            )

            Text(
                text = "BUSCAR RECETAS",
                color = Color(0xFF9BE3B1),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF29483C))
                        .padding(16.dp)
                ) {

                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->

                            Box {
                                if (searchText.isEmpty()) {
                                    Text(
                                        text = "Escribe el nombre de una receta...",
                                        color = Color(0xFFB9CFC3),
                                        fontSize = 18.sp
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )
                }

                Button(
                    onClick = { searchText = "" }
                ) {
                    Text("Limpiar")
                }
            }

            Text(
                text = "CATEGORIAS",
                color = Color(0xFF9BE3B1),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                categories.forEach { item ->
                    Button(
                        onClick = { category = item }
                    ) {
                        Text(item)
                    }
                }
            }

            Text(
                text = when (category) {
                    "Favoritos" -> "MIS RECETAS FAVORITAS"
                    "Todas" -> "RECETAS DISPONIBLES"
                    else -> "RECETAS DE $category".uppercase()
                },
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            if (filteredRecipes.isEmpty()) {

                Text(
                    text = if (category == "Favoritos" && searchText.isEmpty()) {
                        "Todavia no tienes recetas favoritas."
                    } else {
                        "No se encontraron recetas."
                    },
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            filteredRecipes.forEach { recipe ->

                Button(
                    onClick = { selectedRecipe = recipe },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Image(
                            painter = painterResource(id = recipe.image),
                            contentDescription = recipe.name,
                            modifier = Modifier.size(
                                width = 130.dp,
                                height = 85.dp
                            ),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = recipe.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = recipe.description,
                                fontSize = 14.sp
                            )

                            Text(
                                text = "Tiempo: ${recipe.time}",
                                fontSize = 14.sp
                            )
                        }

                        if (favorites.contains(recipe.name)) {
                            Text(
                                text = "♥",
                                color = Color(0xFF9BE3B1),
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }

            Text(
                text = "Tus favoritas: ${favorites.size}",
                color = Color(0xFF9BE3B1),
                fontSize = 16.sp
            )

            Text(
                text = "SMART RECIPES TV | Recetas para todos",
                color = Color.LightGray,
                fontSize = 13.sp
            )
        }

    } else {

        val recipe = selectedRecipe!!

        RecipeDetails(
            recipe = recipe,
            isFavorite = favorites.contains(recipe.name),
            onBack = { selectedRecipe = null },
            onFavorite = {
                favorites = if (favorites.contains(recipe.name)) {
                    favorites - recipe.name
                } else {
                    favorites + recipe.name
                }
            }
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun RecipeDetails(
    recipe: Recipe,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onFavorite: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF10251F))
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        Button(onClick = onBack) {
            Text("Volver al recetario")
        }

        Image(
            painter = painterResource(id = recipe.image),
            contentDescription = recipe.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = recipe.name,
            color = Color(0xFF9BE3B1),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = recipe.description,
            color = Color.White,
            fontSize = 19.sp
        )

        Text(
            text = "Tiempo de preparacion: ${recipe.time}",
            color = Color(0xFFB9CFC3),
            fontSize = 17.sp
        )

        Text(
            text = "INGREDIENTES",
            color = Color(0xFF9BE3B1),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        recipe.ingredients.forEach {
            Text(
                text = "• $it",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Text(
            text = "PREPARACION",
            color = Color(0xFF9BE3B1),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        recipe.steps.forEachIndexed { index, step ->
            Text(
                text = "${index + 1}. $step",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Button(onClick = onFavorite) {
            Text(
                if (isFavorite) {
                    "Quitar de favoritos"
                } else {
                    "Agregar a favoritos"
                }
            )
        }
    }
}
