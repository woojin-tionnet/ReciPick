package com.woojin.recipick.domain.model

// 랜덤 레시피 API 응답을 위한 데이터 클래스 예시 (Kotlin)
// 각 변수의 의미를 상세 주석으로 설명

data class RecipeRandomResponse(
    val recipes: List<Recipes>
)

data class Recipes(
    val id: Int, // 레시피 고유 ID
    val image: String, // 레시피 대표 이미지 URL
    val imageType: String, // 이미지 파일 타입 (예: "jpg")
    val title: String, // 레시피 제목
    val readyInMinutes: Int, // 조리 완료까지 소요 시간(분)
    val servings: Int, // 총 제공 인분 수
    val sourceUrl: String, // 원본 레시피 상세 페이지 URL
    val vegetarian: Boolean, // 채식 레시피 여부
    val vegan: Boolean, // 비건 레시피 여부
    val glutenFree: Boolean, // 글루텐 프리 여부
    val dairyFree: Boolean, // 유제품 프리 여부
    val veryHealthy: Boolean, // 건강식 여부
    val cheap: Boolean, // 저렴한 레시피 여부
    val veryPopular: Boolean, // 인기 레시피 여부
    val sustainable: Boolean, // 지속가능성 친화 여부
    val lowFodmap: Boolean, // 저 FODMAP 식단 여부
    val weightWatcherSmartPoints: Int, // Weight Watchers 스마트 포인트
    val gaps: String, // GAPS(특정 식이요법) 여부
    val preparationMinutes: Int?, // 준비 시간(분), 값이 없을 수 있음
    val cookingMinutes: Int?, // 조리 시간(분), 값이 없을 수 있음
    val aggregateLikes: Int, // 좋아요(추천) 수
    val healthScore: Int, // 건강 점수(0~100)
    val creditsText: String, // 출처(크레딧) 텍스트
    val license: String, // 라이선스 정보
    val sourceName: String, // 출처명
    val pricePerServing: Double, // 1인분당 가격(센트 단위)
    val extendedIngredients: List<Ingredient>, // 재료 목록
    val summary: String, // 레시피 요약 설명 (HTML 포함 가능)
    val cuisines: List<String>, // 해당 요리의 국가/지역 분류
    val dishTypes: List<String>, // 요리 종류 (예: "dessert")
    val diets: List<String>, // 해당 레시피의 식단 분류
    val occasions: List<String>, // 어울리는 행사/시기
    val instructions: String, // 전체 조리법(HTML 포함)
    val analyzedInstructions: List<AnalyzedInstruction>, // 단계별 상세 조리법
    val originalId: Int?, // 원본 레시피 ID (없을 수 있음)
    val spoonacularScore: Double, // Spoonacular 내부 점수
    val spoonacularSourceUrl: String // Spoonacular 상세 페이지 URL
)

// 재료(Ingredient) 데이터 클래스
data class Ingredient(
    val id: Int, // 재료 고유 ID
    val aisle: String, // 마트 진열대/카테고리
    val image: String, // 재료 이미지 파일명 또는 URL
    val consistency: String, // 상태(고체/액체 등)
    val name: String, // 재료명
    val nameClean: String, // 정제된 재료명
    val original: String, // 원본 텍스트(수량+단위+이름)
    val originalName: String, // 원본 재료명
    val amount: Double, // 수량
    val unit: String, // 단위(예: "cup", "g")
    val meta: List<String>, // 추가 정보(예: "peeled", "diced")
    val measures: Measures // 단위별 수량 정보
)

// 단위별 수량 정보 클래스
data class Measures(
    val us: MeasureUnit, // 미국식 단위(컵, 테이블스푼 등)
    val metric: MeasureUnit // 미터법 단위(그램, 리터 등)
)

data class MeasureUnit(
    val amount: Double, // 수량
    val unitShort: String, // 단위(짧은 표기)
    val unitLong: String // 단위(긴 표기)
)

// 단계별 조리법 클래스
data class AnalyzedInstruction(
    val name: String, // 조리법 이름(대부분 빈 문자열)
    val steps: List<InstructionStep> // 단계별 설명 리스트
)

data class InstructionStep(
    val number: Int, // 단계 번호
    val step: String, // 단계별 설명
    val ingredients: List<StepIngredient>, // 사용 재료
    val equipment: List<StepEquipment>, // 사용 도구
    val length: StepLength? // 소요 시간(있을 경우)
)

data class StepIngredient(
    val id: Int, // 재료 ID
    val name: String, // 재료명
    val localizedName: String, // 현지화된 재료명
    val image: String // 이미지 파일명 또는 URL
)

data class StepEquipment(
    val id: Int, // 도구 ID
    val name: String, // 도구명
    val localizedName: String, // 현지화된 도구명
    val image: String, // 이미지 파일명 또는 URL
    val temperature: StepTemperature? = null // 온도 정보(있을 경우)
)

data class StepLength(
    val number: Int, // 시간
    val unit: String // 단위(예: "minutes")
)

data class StepTemperature(
    val number: Int, // 온도 값
    val unit: String // 단위(예: "Fahrenheit")
)

