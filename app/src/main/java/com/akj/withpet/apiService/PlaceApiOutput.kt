package com.akj.withpet.apiService

class PlaceApiOutput(data : Array<String>) {
    val title : String = data[0] //시설명
    val description : String = data[1]   //시설 정보
    val latitude : String = data[2]    //위도
    val longitude : String = data[3] //경도
    val address : String = data[4]   //주소
    val tel : String = data[5]   //전화번호
    val homepage : String = data[6] //홈페이지
    val closedDay : String = data[7] //휴무일
    val operatingTime : String = data[8] //운영시간
    val parking : String = data[9] //주차가능 여부
    val sizeAble : String = data[10]  //입장 가능 동물 크기
    val limit : String = data[11] //제한 사항,
    val insideAble : String = data[12] // 실내 가능 여부
    val outsudeAble : String = data[13] //실외 가능 여부
}

class AnimalApiOutput(
    val desertionNo : String, //유기 번호
    val happenDt : String, //접수일
    val happenPlace : String, //발견 장소
    val kindCd : String,    //품종
    val colorCd  : String,  //색상
    val age  : String,  //나이
    val weight  : String,  //체중
    val popfile  : String,  //사진
    val processState  : String,  //상태
    val sexCd  : String,  //성별
    val neuterYn  : String,  //중성화 여부
    val specialMark  : String,  //특징
    val careNm  : String,  //보호소 이름
    val careTel  : String,  //보호소 전화번호
    val careAddr  : String,  //보호소 주소
    val orgNm  : String,  //관할기관
    val chargeNm  : String,  //담당자
    val officetel  : String,  //담당자 연락처
){

}
