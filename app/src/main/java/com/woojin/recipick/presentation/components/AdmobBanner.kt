package com.woojin.recipick.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier,
    adUnitId: String
) {
    AndroidView(
        modifier = modifier.fillMaxWidth().background(Color.White),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER) // 기본 배너 크기
                setAdUnitId(adUnitId) //id 세팅
                loadAd(AdRequest.Builder().build()) // 광고 요청 생성 및 로드
            }
        }
    )
}