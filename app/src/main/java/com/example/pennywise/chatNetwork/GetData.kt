package com.example.pennywise.chatNetwork

import com.example.pennywise.chatNetwork.dto.apiRespone.ApiResponse
import com.example.pennywise.chatNetwork.dto.apiRespone.result.ExpenseResult
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.StringReader

object GetData
{
    fun getStatus(json: String): String {
        return try {

            // 💡 Dùng Regex để lọc JSON thật sự
            val jsonCleaned = json.replace(Regex("^```json|```$"), "").trim()

            // Dùng JsonReader để cho phép JSON bị lỗi nhẹ
            val reader = JsonReader(StringReader(jsonCleaned))
            reader.isLenient = true

            // Parse JSON
            val jsonElement: JsonElement = JsonParser().parse(reader)
            val jsonObject = jsonElement.asJsonObject

            // Lấy status
            val status = jsonObject["status"]?.takeIf { !it.isJsonNull }?.asString ?: "Unknown Status"
            status
        } catch (e: Exception) {
            println("Error parsing JSON in getStatus: ${e.message}")
            "Parsing Error"
        }
    }


    fun getExpenseApi(json: String): ApiResponse<List<ExpenseResult>> {
        val gson = Gson()
        return try {
            // Làm sạch chuỗi JSON nếu có ký tự thừa như dấu backticks (```)
            val cleanJson = json.replace("```json", "").replace("```", "").trim()

            // Kiểm tra xem JSON có phải là một đối tượng hợp lệ không
            if (cleanJson.startsWith("{") && cleanJson.endsWith("}")) {
                // Sử dụng TypeToken để ánh xạ JSON sang ApiResponse với kiểu generic
                val type = object : TypeToken<ApiResponse<List<ExpenseResult>>>() {}.type
                gson.fromJson(cleanJson, type)
            } else {
                // Trường hợp JSON không phải là đối tượng, in lỗi và trả về ApiResponse mặc định
                println("Received JSON is not an object: $cleanJson")
                ApiResponse(status = "Error", result = emptyList(), messages = "Invalid JSON format")
            }
        } catch (e: Exception) {
            // In thông báo lỗi và trả về giá trị mặc định nếu có lỗi
            println("Error parsing JSON in getExpenseApi: ${e.message}")
            ApiResponse(status = "Error", result = emptyList(), messages = "Parsing Error")
        }
    }




}


