package com.jangjh123.shallwegoforawalk.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import com.jangjh123.shallwegoforawalk.data.model.Size
import com.jangjh123.shallwegoforawalk.ui.activity.home.HomeActivity
import com.jangjh123.shallwegoforawalk.ui.activity.register.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    private val viewModel: RegisterViewModel by viewModels()
    private val nameTextFieldState = mutableStateOf("")
    private val isBoyState = mutableStateOf<Boolean?>(null)
    private val ageTextFieldState = mutableStateOf("")
    private val furTypeState = mutableStateOf<FurType?>(null)
    private val sizeState = mutableStateOf<Size?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShallWeGoForAWalkTheme {
                RegisterActivityContent(
                    nameTextFieldState = nameTextFieldState,
                    isBoyState = isBoyState,
                    ageTextFieldState = ageTextFieldState,
                    furTypeState = furTypeState,
                    sizeState = sizeState,
                    onClickEnroll = {
                        viewModel.storeDog(
                            name = nameTextFieldState.value,
                            isBoy = isBoyState.value!!,
                            age = ageTextFieldState.value.toInt(),
                            furType = furTypeState.value!!,
                            size = sizeState.value!!,
                            onSuccess = { startActivity(Intent(this@RegisterActivity, HomeActivity::class.java)) },
                            onFailure = { Toast.makeText(this@RegisterActivity, "오류가 발생하였습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show() }
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private inline fun RegisterActivityContent(
    nameTextFieldState: MutableState<String>,
    isBoyState: MutableState<Boolean?>,
    ageTextFieldState: MutableState<String>,
    furTypeState: MutableState<FurType?>,
    sizeState: MutableState<Size?>,
    crossinline onClickEnroll: () -> Unit
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

                    FurTypeButton(
                        furType = FurType.Long,
                        furTypeState = furTypeState,
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = bottomSheetState,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FurTypeButton(
                        furType = FurType.Silky,
                        furTypeState = furTypeState,
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = bottomSheetState,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FurTypeButton(
                        furType = FurType.Short,
                        furTypeState = furTypeState,
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = bottomSheetState,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FurTypeButton(
                        furType = FurType.Strong,
                        furTypeState = furTypeState,
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = bottomSheetState,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FurTypeButton(
                        furType = FurType.Curly,
                        furTypeState = furTypeState,
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = bottomSheetState,
                    )
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
                    val boyButtonBackgroundColorState = animateColorAsState(targetValue = if (isBoyState.value == true) MainColor else Color.White)
                    val boyButtonTextColorState = animateColorAsState(targetValue = if (isBoyState.value == true) Color.White else Color.Black)
                    val boyButtonBorderColorState = animateColorAsState(targetValue = if (isBoyState.value == true) MainColor else Gray4)
                    val girlButtonBackgroundColorState = animateColorAsState(targetValue = if (isBoyState.value == false) MainColor else Color.White)
                    val girlButtonTextColorState = animateColorAsState(targetValue = if (isBoyState.value == false) Color.White else Color.Black)
                    val girlButtonBorderColorState = animateColorAsState(targetValue = if (isBoyState.value == false) MainColor else Gray4)

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
                    text = if (furTypeState.value == null) "모종 선택하기" else furTypeState.value!!.text,
                    style = Typography.h1,
                    color = selectFurTypeTextColorState.value
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(id = R.string.activity_register_size),
                    fontWeight = Medium,
                    fontSize = 17.sp,
                    style = Typography.h1
                )

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = spacedBy(20.dp)
                ) {
                    SizeButton(
                        sizeState = sizeState,
                        size = Size.Large
                    )

                    SizeButton(
                        sizeState = sizeState,
                        size = Size.Medium
                    )

                    SizeButton(
                        sizeState = sizeState,
                        size = Size.Small
                    )
                }

                Text(
                    text = stringResource(id = R.string.activity_register_size_help_desc),
                    style = Typography.h1,
                    fontSize = 10.sp,
                    color = SubColor
                )
            }

            val enrollButtonColorState = animateColorAsState(
                targetValue = if (nameTextFieldState.value.isNotEmpty() &&
                    isBoyState.value != null &&
                    ageTextFieldState.value.isNotEmpty() &&
                    furTypeState.value != null &&
                    sizeState.value != null
                ) MainColor else Gray3
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = enrollButtonColorState.value)
                    .clickable {
                        if (nameTextFieldState.value.isNotEmpty() &&
                            isBoyState.value != null &&
                            ageTextFieldState.value.isNotEmpty() &&
                            furTypeState.value != null &&
                            sizeState.value != null
                        ) {
                            onClickEnroll()
                        }
                    }
            ) {
                Text(
                    modifier = Modifier.align(Center),
                    text = "등록하기",
                    style = Typography.h1,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FurTypeButton(
    furType: FurType,
    furTypeState: MutableState<FurType?>,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState
) {
    Row(
        modifier = Modifier.clickableWithoutRipple {
            furTypeState.value = furType
            coroutineScope.launch {
                modalBottomSheetState.hide()
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
            text = furType.text,
            style = Typography.h1,
            fontWeight = Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun SizeButton(
    sizeState: MutableState<Size?>,
    size: Size
) {
    Row(
        modifier = Modifier.clickableWithoutRipple { sizeState.value = size },
        verticalAlignment = CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = if (sizeState.value == size) R.drawable.icon_radio_on else R.drawable.icon_radio_off),
            contentDescription = "sizeRadioButtonImage"
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = size.text,
            style = Typography.h1,
            fontSize = 14.sp
        )
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