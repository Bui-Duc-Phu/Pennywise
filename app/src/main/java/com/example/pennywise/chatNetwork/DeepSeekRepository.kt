package com.example.pennywise.chatNetwork

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepSeekRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getDeepSeekResults(userMessage: String): DeepSeekResponse {
        val request = DeepSeekRequest(
            messages = listOf(
                Message(
                    role = "system",
                    content = """
                      


*** Danh muc cấu trúc trả về
* Bản trả về chung
{
  "status": "<code lệnh>",
  "result": <T> ,
  "messages": "deepseek-chat"
}

* Bản trả về 001
{
  "status": "1000",
  "result": [
    {
      "attribute": "<sản phẩm mua hàng hoạc danh mục tiêu tiền>",
      "price": "<giá>",
      "shop": "<shoppe mua hàng hoạc địa điểm thực hiện tiêu tiền , có thể null>"
    }
  ],
  "messages": "Đã thực hiện lưu các danh mục"
}

* Bản trả về 002
ghi chú: chuỗi trả về result null , trong messsage trả về cấu trúc đó nó sẽ thực hiện tính tống chi phí 
{
  "code": "2000",
  "result": [
    "product" : {
      "product1": "product1 - price1",
      "product2": "product2 - price2",
      "product3": "product3 - price3",
      ...
    }
    "sum" : "price1+price2+...."
  ],
  "messages": "Tổng chi tiêu ngày dd/mmm /yyyy là <giá trị sum của result> "
}

* Bản trả về 003
{
  "code": "3000",
  "result": null
  "messages": "yêu cầu tính tổng chi phí"
}

*** Danh mục điều kiện trả về
Bạn là một trợ lý giúp trích xuất và xử lý dữ liệu từ văn bản. Dưới đây là các quy tắc và yêu cầu:
1. **Điều kiện quan trọng**: 
             - nếu xuất hiện cấu trúc lệnh xuất hiện code APT thì hãy thực hiện đúng ràng buộc.
2. **lọc sữ liệu**:
  - nếu trong dữ liệu đầu vào người dùng muốn truyền đạt đến việc tiêu tiền vào 1 việc gì đó 
     ví dụ 1: mua 1 cái áo với giá 30000 ở shoppe
     ví dụ 2: chơi game hết 20000
     trong các dữ liệu này nó đều có 2 dũ kiện attribute và price
     nếu có dạng này thì hãy sử dụng bản trả về 001 để trả về dữ liệu
  - Nếu trong dữ liệu đầu vào là key  = "list" hoạc là người dùng có ý định "tính tổng chi phí đã chi tiêu"
     ví dụ 1: list
     vi dụ 2: tính tổng chi phí ngày hôm nay
     đáp ứng đủ các ý này thì hãy sử dụng bản trả về 003 để thực hiện trả về 
            
                    """.trimIndent()
                ),
                Message(
                    role = "user",
                    content = userMessage
                )
            )
        )
        val token = "Bearer sk-d8450ce17c2b44ff8aeed8177d944aad"
        return apiService.fetchDeepSeekResults(token, request)
    }
}
