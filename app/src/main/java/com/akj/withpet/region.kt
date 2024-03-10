package com.akj.withpet

const val SEOUL = "서울"
const val INCHEON = "인천"
const val BUSAN = "부산"
const val DAEJEON = "대전"
const val DAEGU = "대구"
const val ULSAN = "울산"
const val GWANGJU = "광주"
const val JEJU = "제주"
const val SEJONG = "세종"
const val GYEONGGI = "경기"
const val GANGWON = "강원"
const val CHUNGCHEONG_BUK = "충청북도"
const val CHUNGCHEONG_NAM = "충청남도"
const val GYEONGSANG_BUK = "경상북도"
const val GYEONGSANG_NAM = "경상남도"
const val JEOLLA_BUK = "전라북도"
const val JEOLLA_NAM = "전라남도"
const val REGION_ALL = ""


val seoulArr = arrayOf(REGION_ALL, "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구")
val incheonArr = arrayOf(REGION_ALL, "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "중구")
val busanArr = arrayOf(REGION_ALL, "강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구")
val daejeonArr = arrayOf(REGION_ALL, "대덕구", "동구", "서구", "유성구", "중구")
val daeguArr = arrayOf(REGION_ALL, "남구", "달서구", "동구", "북구", "서구", "수성구", "중구")
val ulsanArr = arrayOf(REGION_ALL, "남구", "동구", "북구", "중구", "울주군")
val gwangjuArr = arrayOf(REGION_ALL, "광산구", "남구", "동구", "북구", "서구")
val jejuArr = arrayOf(REGION_ALL, "서귀포시", "제주시")
val sejongArr = arrayOf(REGION_ALL)
val gyeonggiArr = arrayOf(REGION_ALL, "고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "여주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군", "연천군")
val gangwonArr = arrayOf(REGION_ALL, "강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군")
val chungcheong_bukArr = arrayOf(REGION_ALL, "제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "증평군", "진천군")
val chungcheong_namArr = arrayOf(REGION_ALL, "계룡시", "공주시", "논산시", "당진시", "보령시", "서산시", "아산시", "천안시", "금산군", "부여군", "서천군", "예산군", "청양군", "태안군", "홍성군")
val gyeongsang_bukArr = arrayOf(REGION_ALL, "경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군")
val gyeongsang_namArr = arrayOf(REGION_ALL, "거제시", "김해시", "밀양시", "사천시", "양산시", "진주시", "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군", "함양군", "합천군")
val jeolla_bukArr = arrayOf(REGION_ALL, "군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군")
val jeolla_namArr = arrayOf(REGION_ALL, "광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군")

val regionName = arrayOf(REGION_ALL, SEOUL, INCHEON, BUSAN, DAEJEON, DAEGU, ULSAN, GWANGJU, JEJU, SEJONG, GYEONGGI, GANGWON, CHUNGCHEONG_BUK, CHUNGCHEONG_NAM, GYEONGSANG_BUK, GYEONGSANG_NAM, JEOLLA_BUK, JEOLLA_NAM)

val region = mapOf<String, Array<String>>(
    REGION_ALL to arrayOf(REGION_ALL),
    SEOUL to seoulArr,
    INCHEON to incheonArr,
    BUSAN to busanArr,
    DAEJEON to daejeonArr,
    DAEGU to daeguArr,
    ULSAN to ulsanArr,
    GWANGJU to gwangjuArr,
    JEJU to jejuArr,
    SEJONG to sejongArr,
    GYEONGGI to gyeonggiArr,
    GANGWON to gangwonArr,
    CHUNGCHEONG_BUK to chungcheong_bukArr,
    CHUNGCHEONG_NAM to chungcheong_namArr,
    GYEONGSANG_BUK to gyeongsang_bukArr,
    GYEONGSANG_NAM to gyeongsang_namArr,
    JEOLLA_BUK to jeolla_bukArr,
    JEOLLA_NAM to jeolla_namArr,
)

fun EmptyToAll(str:String): String{
    return if(str == REGION_ALL) "전체" else str
}