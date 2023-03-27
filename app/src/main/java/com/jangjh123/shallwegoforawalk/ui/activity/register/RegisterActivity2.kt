package com.jangjh123.shallwegoforawalk.ui.activity.register

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.shallwegoforawalk.R
import com.jangjh123.shallwegoforawalk.data.model.FurType
import com.jangjh123.shallwegoforawalk.ui.activity.register.ui.theme.*
import kotlinx.coroutines.launch


//@AndroidEntryPoint
class RegisterActivity2 : ComponentActivity() {
    //    private val viewModel: RegisterViewModel by viewModels()
    private val nameTextFieldState = mutableStateOf("")
    private val isBoyState = mutableStateOf(true)
    private val ageTextFieldState = mutableStateOf("")
    private val furTypeState = mutableStateOf<FurType?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShallWeGoForAWalkTheme {
                RegisterActivityContent(
                    nameTextFieldState = nameTextFieldState,
                    isBoyState = isBoyState,
                    ageTextFieldState = ageTextFieldState,
                    furTypeState = furTypeState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RegisterActivityContent(
    nameTextFieldState: MutableState<String>,
    isBoyState: MutableState<Boolean>,
    ageTextFieldState: MutableState<String>,
    furTypeState: MutableState<FurType?>
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = bottomSheetState,
        scrimColor = Color(0x88000000),
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .height(480.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .width(48.dp)
                            .height(4.dp)
                            .background(color = Color(0xFFD1D1D1))
                            .align(CenterHorizontally)
                    )

                    Text(
                        modifier = Modifier.padding(top = 30.dp),
                        text = stringResource(id = R.string.activity_register_choice_fur_type),
                        style = Typography.h1,
                        color = Gray1,
                        fontSize = 17.sp
                    )

                    val isFurTypeDescriptionExpanded = remember { mutableStateOf(false) }
                    val furTypeDescriptionOriginHeightState = remember { mutableStateOf(160) }
                    val furTypeDescriptionHeightState = animateDpAsState(targetValue = if (!isFurTypeDescriptionExpanded.value) 30.dp else furTypeDescriptionOriginHeightState.value.dp)

                    Column(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .height(furTypeDescriptionHeightState.value)
                            .background(color = SubColor)
                            .clickable { isFurTypeDescriptionExpanded.value = !isFurTypeDescriptionExpanded.value }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            verticalAlignment = CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .size(20.dp),
                                painter = painterResource(id = R.drawable.icon_exclamation),
                                contentDescription = "furTypeDescriptionIcon"
                            )

                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = stringResource(id = R.string.activity_register_choice_fur_type_help),
                                style = Typography.h1,
                                fontWeight = Normal,
                                fontSize = 13.sp,
                                color = Gray2
                            )
                        }

                        Text(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 4.dp
                            ),
                            text = "${context.getString(R.string.activity_register_long_fur_example)}\n${context.getString(R.string.activity_register_silky_fur_example)}\n${context.getString(R.string.activity_register_short_fur_example)}\n${context.getString(R.string.activity_register_strong_fur_example)}\n${context.getString(R.string.activity_register_curly_fur_example)}",
                            fontSize = 12.sp,
                            style = Typography.h1,
                            color = Gray3
                        )
                    }

                    Row(
                        modifier = Modifier.clickableWithoutRipple {
                            furTypeState.value = FurType.Long
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        },
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(shape = CircleShape)
                                .background(color = MainColor)
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "장모종 - 더블코트",
                            style = Typography.h1,
                            fontWeight = Medium,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.clickableWithoutRipple {
                            furTypeState.value = FurType.Silky
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        },
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(shape = CircleShape)
                                .background(color = MainColor)
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "견모종 - 실크코트",
                            style = Typography.h1,
                            fontWeight = Medium,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.clickableWithoutRipple {
                            furTypeState.value = FurType.Short
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        },
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(shape = CircleShape)
                                .background(color = MainColor)
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "단모종",
                            style = Typography.h1,
                            fontWeight = Medium,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.clickableWithoutRipple {
                            furTypeState.value = FurType.Strong
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        },
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(shape = CircleShape)
                                .background(color = MainColor)
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "강모종",
                            style = Typography.h1,
                            fontWeight = Medium,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.clickableWithoutRipple {
                            furTypeState.value = FurType.Curly
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }
                        },
                        verticalAlignment = CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(shape = CircleShape)
                                .background(color = MainColor)
                        )

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "권모종",
                            style = Typography.h1,
                            fontWeight = Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .verticalScroll(state = scrollState),
            verticalArrangement = SpaceBetween
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(id = R.string.activity_register_title),
                    fontWeight = Bold,
                    fontSize = 26.sp,
                    style = Typography.h1
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(id = R.string.activity_register_name),
                    fontWeight = Medium,
                    fontSize = 17.sp,
                    style = Typography.h1
                )

                Box(modifier = Modifier.height(36.dp)) {
                    BasicTextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        value = nameTextFieldState.value,
                        onValueChange = { nameTextFieldState.value = it },
                        textStyle = TextStyle(
                            fontWeight = Medium,
                            fontSize = 15.sp,
                        ),
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        decorationBox = { innerTextField ->
                            if (nameTextFieldState.value.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.activity_register_type_name),
                                    fontWeight = Normal,
                                    fontSize = 15.sp,
                                    color = Gray4,
                                    style = Typography.h1
                                )
                            }

                            innerTextField()
                        }
                    )
                }

                Divider(
                    modifier = Modifier.padding(top = 8.dp),
                    thickness = 1.dp,
                    color = Gray4
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = "성별을 알려주세요.",
                    fontWeight = Medium,
                    fontSize = 17.sp,
                    style = Typography.h1
                )

                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = CenterVertically
                ) {
                    val boyButtonBackgroundColorState = animateColorAsState(targetValue = if (isBoyState.value) MainColor else Color.White)
                    val boyButtonTextColorState = animateColorAsState(targetValue = if (isBoyState.value) Color.White else Color.Black)
                    val boyButtonBorderColorState = animateColorAsState(targetValue = if (isBoyState.value) MainColor else Gray4)
                    val girlButtonBackgroundColorState = animateColorAsState(targetValue = if (!isBoyState.value) MainColor else Color.White)
                    val girlButtonTextColorState = animateColorAsState(targetValue = if (!isBoyState.value) Color.White else Color.Black)
                    val girlButtonBorderColorState = animateColorAsState(targetValue = if (!isBoyState.value) MainColor else Gray4)

                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .border(
                                width = 2.dp,
                                shape = RoundedCornerShape(50.dp),
                                color = boyButtonBorderColorState.value
                            )
                            .width(60.dp)
                            .height(30.dp)
                            .background(color = boyButtonBackgroundColorState.value)
                            .clickable { isBoyState.value = true }
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.boy),
                            style = Typography.h1,
                            fontWeight = Medium,
                            color = boyButtonTextColorState.value
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .border(
                                width = 2.dp,
                                shape = RoundedCornerShape(50.dp),
                                color = girlButtonBorderColorState.value
                            )
                            .width(60.dp)
                            .height(30.dp)
                            .background(color = girlButtonBackgroundColorState.value)
                            .clickable { isBoyState.value = false }
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.girl),
                            style = Typography.h1,
                            fontWeight = Medium,
                            color = girlButtonTextColorState.value
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(id = R.string.activity_register_age),
                    fontWeight = Medium,
                    fontSize = 17.sp,
                    style = Typography.h1
                )

                Box(modifier = Modifier.height(36.dp)) {
                    BasicTextField(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        value = ageTextFieldState.value,
                        onValueChange = { ageTextFieldState.value = it },
                        textStyle = TextStyle(
                            fontWeight = Medium,
                            fontSize = 15.sp,
                        ),
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                        decorationBox = { innerTextField ->
                            if (ageTextFieldState.value.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.activity_register_type_age),
                                    fontWeight = Normal,
                                    fontSize = 15.sp,
                                    color = Gray4,
                                    style = Typography.h1
                                )
                            }

                            innerTextField()
                        }
                    )
                }

                Divider(
                    modifier = Modifier.padding(top = 8.dp),
                    thickness = 1.dp,
                    color = Gray4
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(id = R.string.activity_register_fur_type),
                    fontWeight = Medium,
                    fontSize = 17.sp,
                    style = Typography.h1
                )

                val selectFurTypeTextColorState = animateColorAsState(targetValue = if (furTypeState.value == null) MainColor else Color.Black)

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickableWithoutRipple { coroutineScope.launch { bottomSheetState.show() } },
                    text = "모종 선택하기",
                    style = Typography.h1,
                    color = selectFurTypeTextColorState.value
                )
            }


        }
    }
}

fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier {
    return this.clickable(
        interactionSource = MutableInteractionSource(),
        indication = null
    ) {
        onClick()
    }
}