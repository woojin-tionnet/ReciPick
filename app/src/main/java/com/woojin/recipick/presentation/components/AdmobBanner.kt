package com.woojin.recipick.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

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
                // AdListener 설정
                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        // 광고 로드 성공
                        Log.d("AdmobBanner", "Ad loaded successfully.")
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Log.e("AdmobBanner", "Ad failed to load: ${adError.message} (Code: ${adError.code})")
                        if (adError.code == AdRequest.ERROR_CODE_NO_FILL) {
                            Log.w("AdmobBanner", "Ad request successful, but no ad was returned due to lack of ad inventory (NO_FILL).")
                        }
                    }

                    override fun onAdClicked() {
                        // 사용자가 광고를 클릭
                        Log.d("AdmobBanner", "Ad clicked.")
                    }

                    override fun onAdImpression() {
                        // 광고가 실제로 사용자에게 노출
                        Log.d("AdmobBanner", "Ad impression recorded.")
                    }
                }
                loadAd(AdRequest.Builder().build()) // 광고 요청 생성 및 로드
            }
        }
    )
}