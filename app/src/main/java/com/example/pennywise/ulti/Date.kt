import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Date {
    fun getCurrentDay(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    }

    fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1 // Tháng trong Calendar bắt đầu từ 0
    }

    fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }

    fun getCurrentMonthFormat(): String {
        val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault()) // Định dạng năm-tháng
        return sdf.format(Date(System.currentTimeMillis()))
    }



}
