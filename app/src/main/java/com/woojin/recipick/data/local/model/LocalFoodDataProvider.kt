package com.woojin.recipick.data.local.model

object FoodCategories {
    val meats = listOf(
        "소고기", "닭고기", "돼지고기", "양고기", "오리고기", "베이컨", "햄", "소시지"
    ).sorted()
    val vegetables = listOf(
        "양파", "마늘", "당근", "감자", "대파", "쪽파", "상추", "부추", "무", "고구마", "생강", "애호박", "토마토", "피망", "파프리카", "풋고추", "홍고추", "청양고추", "페페론치노", "콩나물", "숙주"
    ).sorted()
    val mushRooms = listOf(
        "표고버섯", "새송이버섯", "느타리버섯", "팽이버섯", "양송이버섯"
    ).sorted()
    val dairies = listOf(
        "우유", "두유", "치즈", "버터", "크림치즈", "요거트", "생크림"
    ).sorted()
    val seasonings = listOf(
        "소금", "설탕", "간장", "된장", "고추장", "식초", "참기름", "들기름", "고춧가루", "후추", "맛술", "굴소스", "케찹", "마요네즈", "돈까스소스"
    ).sorted()
    val grains = listOf(
        "쌀", "밀가루", "라면", "파스타", "떡", "식빵"
    ).sorted()
    val etc = listOf(
        "계란", "두부", "어묵", "김", "김치"
    ).sorted()
}