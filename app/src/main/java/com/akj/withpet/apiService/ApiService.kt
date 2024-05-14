package com.akj.withpet.apiService

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object ApiService {
    suspend fun getAnimal() : List<AnimalApiOutput>? {
        return withContext(Dispatchers.IO){
            try {
                val pageNo : Int = (1..100).random()

                val urlBuilder = StringBuilder("https://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic")

                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="+ MyServiceKey.getAnimalServiceKey())
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8"))
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo.toString(), "UTF-8"))
                urlBuilder.append("&" + URLEncoder.encode("state", "UTF-8") + "=" + URLEncoder.encode("protect", "UTF-8"))

                val responseBody : String

                with(URL(urlBuilder.toString()).openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    setRequestProperty("Content-type", "application/json")
                    println("Response code: $responseCode")

                    responseBody = if (responseCode in 200..299) {
                        inputStream.bufferedReader().use { it.readText() }
                    } else {
                        errorStream.bufferedReader().use { it.readText() }
                    }

                    disconnect()
                }

                val document = Jsoup.parse(responseBody)

                if(document.select("resultcode").text() == "00"){
                    val itemsList = mutableListOf<AnimalApiOutput>()
                    val items = document.select("item")
                    for (item in items){
                        val desertionNo = item.select("desertionNo").text() //유기 번호
                        val happenDt = item.select("happenDt").text() //접수일
                        val kindCd = item.select("kindCd").text()   //품종
                        val age = item.select("age").text() //나이
                        val weight = item.select("weight").text() //체중
                        val popfile = item.select("popfile").text() //사진
                        val sexCd = item.select("sexCd").text() //성별
                        val neuterYn = item.select("neuterYn").text() //중성화 여부
                        val specialMark = item.select("specialMark").text() //특징
                        val careNm = item.select("careNm").text() //보호소 이름
                        val careTel = item.select("careTel").text() //보호소 전화번호
                        val careAddr = item.select("careAddr").text() //보호소 주소
                        val orgNm = item.select("orgNm").text() //관할기관
                        val chargeNm = item.select("chargeNm").text() //담당자
                        val officetel = item.select("officetel").text() //담당자 연락처

                        itemsList.add(AnimalApiOutput(desertionNo, happenDt, kindCd, age, weight, popfile, sexCd, neuterYn, specialMark, careNm, careTel, careAddr, orgNm, chargeNm, officetel))
                    }
                    itemsList.toList()
                } else {
                    null
                }
            }catch (e : Exception){
                e.printStackTrace()
                null
            }
        }
    }
}