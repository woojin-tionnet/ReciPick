package com.woojin.recipick.presentation.add_recipe.title_and_ingredients

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme
import com.woojin.recipick.utils.Utils

@Composable
fun AddRecipeTitleAndIngredients(
    navController: NavHostController,
    selectedIngredientName: String, //재료 추가 버튼으로 가져오는 재료의 이름
    clearSelectedIngredient: () -> Unit, //추가된 재료 이름 설정 후 기존 데이터 삭제
    addIngredientsClick: () -> Unit, //재료 추가 버튼 클릭
    onComplete: (Pair<String, List<String>>) -> Unit //다음 버튼 클릭
) {
    val context = LocalContext.current
    var title by rememberSaveable { mutableStateOf("") } //레시피 제목

    var ingredientName by remember { mutableStateOf("") } //재료 이름
    var selectedUnit by remember { mutableStateOf("g") } //선택된 단위
    var quantityTotal by remember { mutableStateOf(Fraction(0,1)) } //선택된 재료 양

    // String 목록을 저장하고 복원하기 위한 Saver 정의
    val listSaver = listSaver<MutableList<String>, String>(
        save = { it.toList() }, // 저장할 때 List<String>으로 변환
        restore = { it.toMutableStateList() } // 복원할 때 MutableList<String>으로 변환 (toMutableStateList() 사용)
    )
    val addedIngredients =
        rememberSaveable(saver = listSaver) { mutableStateListOf<String>() } //추가된 재료
    LaunchedEffect(selectedIngredientName) {
        if (selectedIngredientName.isNotBlank()) {
            ingredientName = selectedIngredientName
            clearSelectedIngredient()
        }
    }

    val unitOptions = listOf("개", "g", "스푼", "컵")
    val unitQuantities: List<Fraction> = when (selectedUnit) {
        "개" -> listOf(Fraction(1,2), Fraction(1,1), Fraction(2,1))
        "g" -> listOf(Fraction(50,1), Fraction(100,1), Fraction(600,1))
        "스푼" -> listOf(Fraction(1,3), Fraction(1,2), Fraction(1,1))
        "컵" -> listOf(Fraction(1,3), Fraction(1,2), Fraction(1,1))
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.recipe_add_text),
                true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 제목 입력
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.recipe_title_text)) },
                singleLine = true,
                modifier = Modifier.fillMaxSize()
            )

            HorizontalDivider()

            // 재료 이름
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = ingredientName,
                    onValueChange = { ingredientName = it },
                    label = { Text(stringResource(R.string.recipe_ingredient_text)) },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { addIngredientsClick() },
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                ) {
                    Text(text = stringResource(R.string.add_recipe))
                }
            }

            // 단위 선택
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stringResource(R.string.unit))
                unitOptions.forEach { unit ->
                    FilterChip(
                        selected = selectedUnit == unit,
                        onClick = {
                            selectedUnit = unit
                            quantityTotal = Fraction(0,1)
                        },
                        label = { Text(unit) }
                    )
                }
            }

            // 양 선택 버튼들
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                unitQuantities.forEach { q ->
                    Button(
                        onClick = {
                            if (ingredientName.isNotBlank()) {
                                quantityTotal = Utils.addFractions(quantityTotal, q)
                            }
                        }
                    ) {
                        Text(q.toString())
                    }
                }
            }

            // 현재 선택된 양 표시
            if (quantityTotal.numerator > 0) {
                Text(
                    text = "현재: ${quantityTotal.toMixedFractionString()}$selectedUnit",
                    fontWeight = FontWeight.Bold
                )
            }

            // 추가 버튼
            Button(
                onClick = {
                    if (ingredientName.isNotBlank() && quantityTotal.numerator > 0) {
                        val displayQuantity = quantityTotal.toString()
                        val item = "$ingredientName ${displayQuantity}${selectedUnit}"
                        addedIngredients.add(item)
                        ingredientName = ""
                        quantityTotal = Fraction(0, 1)
                    } else {
                        Toast.makeText(context, R.string.put_ingredient_and_quantity, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add_button))
            }

            HorizontalDivider()

            // 추가된 재료 리스트
            Text(stringResource(R.string.added_ingredient))
            addedIngredients.forEach {
                Text("• $it")
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 완료 버튼
            Button(
                onClick = {
                    if (title.isNotBlank() && addedIngredients.isNotEmpty()) {
                        onComplete(Pair(title, addedIngredients))
                    } else {
                        Toast.makeText(context, R.string.please_input_all, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeTitleAndIngredientsPreview() {
    RecipickTheme {
        AddRecipeTitleAndIngredients(
            navController = rememberNavController(),
            selectedIngredientName = "",
            clearSelectedIngredient = { },
            addIngredientsClick = { },
            onComplete = { }
        )
    }
}
