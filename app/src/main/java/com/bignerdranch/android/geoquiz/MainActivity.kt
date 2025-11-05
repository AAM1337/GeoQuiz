package com.bignerdranch.android.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bignerdranch.android.geoquiz.ui.theme.GeoQuizTheme
import com.bignerdranch.android.geoquiz.ui.theme.Question

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(innerPadding: PaddingValues) {
    val questions = listOf(
        Question("Canberra is the capital of Australia.", true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
        Question("The source of the Nile River is in Egypt.", false),
        Question("The Amazon River is the longest river in the Americas.", true),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
    )
    var currentIndex by remember { mutableIntStateOf(0) }
    var correctAnswers by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
        if (currentIndex < questions.size) {
            QuestionCard(
                question = questions[currentIndex],
                onAnswer = { userAnswer ->
                    if (!answered) {
                        if (userAnswer == questions[currentIndex].answer) {
                            correctAnswers++
                        }
                        answered = true
                    }
                },
                buttonsEnabled = !answered
            )
            if (answered) {
                Button(
                    onClick = {
                        if (currentIndex < questions.lastIndex) {
                            currentIndex++
                            answered = false
                        }
                    },
                    enabled = currentIndex < questions.lastIndex
                ) {
                    Text("Next")
                }
            }
        }
        if (currentIndex == questions.lastIndex && answered) {
            Text("Вы набрали $correctAnswers из ${questions.size}")
        }
    }
}
@Composable
fun QuestionCard(
    question: Question,
    onAnswer: (Boolean) -> Unit,
    buttonsEnabled: Boolean
) {
    Column {
        Text(text = question.text)
        if (buttonsEnabled) {
            Row {
                Button(onClick = { onAnswer(true) }) {
                    Text("True")
                }
                Button(onClick = { onAnswer(false) }) {
                    Text("False")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeoQuizTheme {
        Greeting(
            innerPadding = PaddingValues()
        )
    }
}