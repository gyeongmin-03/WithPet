package com.akj.withpet.apiService

class PlaceApiOutput(
    val title : String, //이름
    val category1 : String, //카테고리1
    val category2 : String, //카테고리2
    val description : String,   //시설 정보
    val tel : String,   //전화번호
    val address : String,   //주소
    val url : String,
    val coordinates : String    //좌표
) {

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
