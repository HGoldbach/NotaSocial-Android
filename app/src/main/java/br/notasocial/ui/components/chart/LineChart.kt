package br.notasocial.ui.components.chart

import android.annotation.SuppressLint
import android.graphics.Typeface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import br.notasocial.data.model.Catalog.PriceHistory
import br.notasocial.ui.theme.interFamily
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollState
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.extension.ceil


@SuppressLint("DefaultLocale")
@Composable
fun LineChart(
    priceHistory: List<PriceHistory>
) {
    val refreshDataset = remember { mutableIntStateOf(0) }
    val modelProducer = remember { ChartEntryModelProducer() }
    val datasetForModel = remember { mutableStateListOf(listOf<FloatEntry>()) }
    val datasetLineSpec = remember { arrayListOf<LineChart.LineSpec>() }
    val scrollState = rememberChartScrollState()

    LaunchedEffect(key1 = refreshDataset.intValue) {
        datasetForModel.clear()
        datasetLineSpec.clear()
        val dataPoints = arrayListOf<FloatEntry>()
        datasetLineSpec.add(
            LineChart.LineSpec(
                lineColor = Color.hsl(123f, .63f, .33f, 1f).toArgb(),
                lineBackgroundShader = DynamicShaders.fromBrush(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.hsl(123f, .63f, .33f, 1f)
                                .copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                            Color.hsl(123f, .63f, .33f, 1f)
                                .copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                        )
                    )
                )
            )
        )

        priceHistory.forEachIndexed { index, price ->
            dataPoints.add(FloatEntry(x = index.toFloat(), y = price.price!!.toFloat()))
        }

        datasetForModel.add(dataPoints)
        modelProducer.setEntries(datasetForModel)
    }

    Column(
        modifier = Modifier.fillMaxSize().height(150.dp)
    ) {
        if (datasetForModel.isNotEmpty()) {
            ProvideChartStyle {
                val marker = rememberMarker()
                Chart(
                    chart = LineChart(
                        lines = datasetLineSpec
                    ),
                    chartModelProducer = modelProducer,
                    startAxis = rememberStartAxis(
                        label = textComponent {
                            color = Color.Black.toArgb()
                            textSizeSp = 11f
                        },
                        title = "Preço (R$)",
                        tickLength = 0.dp,
                        valueFormatter = { value, _ ->
                            String.format("R$ %.2f", value.ceil)
                        },
                        itemPlacer = AxisItemPlacer.Vertical.default(
                            maxItemCount = priceHistory.size,
                        ),
                    ),
                    bottomAxis = rememberBottomAxis(
                        label = textComponent {
                            color = Color.Black.toArgb()
                            textSizeSp = 11f
                        },
                        title = "Datas",
                        tickLength = 0.dp,
                        valueFormatter = { value, _ ->
                            priceHistory.getOrNull(value.toInt())?.priceChangeDate ?: ""
                        },
                        guideline = null
                    ),
                    marker = marker,
                    chartScrollState = scrollState,
                    isZoomEnabled = true,
                )
            }
        }
    }
}