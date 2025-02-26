import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.djjakkqkllaas.doolaoqkksk.R
import com.djjakkqkllaas.doolaoqkksk.ui.roulette.SpinType
import com.djjakkqkllaas.doolaoqkksk.ui.theme.RedColor
import com.djjakkqkllaas.doolaoqkksk.ui.theme.ZeroColor

@Composable
fun SpinButton(spinType: SpinType, onClick: () -> Unit) {
    val (textRes, color) = when (spinType) {
        SpinType.RED -> R.string.Red to RedColor
        SpinType.BLACK -> R.string.Black to Color.Black
        SpinType.ZERO -> R.string.Zero to ZeroColor
    }

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(200.dp)
    ) {
        Text(
            text = stringResource(id = textRes),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
