package com.example.testscannerapp

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent.*
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testscannerapp.ui.theme.TestScannerAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestScannerAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .onKeyEvent { keyEvent ->
                                println("on Key Event $keyEvent")
                                println("keyEvent.key.nativeKeyCode : ${keyEvent.nativeKeyEvent.characters}")
                                if (keyEvent.nativeKeyEvent.action == ACTION_UP
//                    && keyEvent.nativeKeyEvent?.keyCode == KEYCODE_ENTER
                                ) {
                                    val pressedKey = keyEvent.nativeKeyEvent.unicodeChar.toChar()
                                    println("presssed key : ${keyEvent.nativeKeyEvent.unicodeChar.toChar()}, $pressedKey")
//                                barcode.append(pressedKey)
//                                showToast(context, barcode.toString())
//                                barcode.delete(0, barcode.length)
//
//                                keyboardController?.hide()
                                }
                                if (keyEvent.key != Key.Enter) return@onKeyEvent false
//                if (keyEvent.key != Key.Enter) {
//                    println("Entered ${keyEvent.key}")
//                }
                                true
                            },
                    ) {
                        Greeting("Android")
                        Spacer(modifier = Modifier.height(8.dp))
                        ScannerTest()
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalComposeUiApi::class)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScannerTest() {
    var scanValue by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val barcode = StringBuffer()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

//    LaunchedEffect("") {
//        println("launched effect")
//        delay(100)
////        inputService?.showSoftwareKeyboard()
////        focusRequester.requestFocus()
////        keyboardController?.hide()
//    }

    TextField(
        value = scanValue,
        onValueChange = { newText ->
            if (newText.text != "") {
                println("value changed ${newText}")
//                showToast(context, newText.text)
            }
        },
        modifier = Modifier
            .focusable(false)
//            .focusRequester(focusRequester)
            .onFocusChanged {
                println("on Focus Changed")
                keyboardController?.hide()
//                    if (focus.value != it.isFocused) {
//                        focus.value = it.isFocused
//                        if (!it.isFocused) {
//                        }
//                    }
            }
            .onKeyEvent { keyEvent ->
                println("on Key Event $keyEvent")
                println("keyEvent.key.nativeKeyCode : ${keyEvent.nativeKeyEvent.characters}")
                if (keyEvent.nativeKeyEvent.action == ACTION_UP
//                    && keyEvent.nativeKeyEvent?.keyCode == KEYCODE_ENTER
                ) {
                    val pressedKey = keyEvent.nativeKeyEvent.unicodeChar.toChar()
                    println("presssed key : ${keyEvent.nativeKeyEvent.unicodeChar.toChar()}, $pressedKey")
                    barcode.append(pressedKey)
                    showToast(context, barcode.toString())
                    barcode.delete(0, barcode.length)

                    keyboardController?.hide()
                }
                if (keyEvent.key != Key.Enter) return@onKeyEvent false
//                if (keyEvent.key != Key.Enter) {
//                    println("Entered ${keyEvent.key}")
//                }
                true
            },
        label = { Text(text = "Your Label") },
        placeholder = { Text(text = "Your Placeholder/Hint") },
    )
//    val listScans by remember {
//        mutable
//    }
}

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestScannerAppTheme {
        Greeting("Android")
    }
}