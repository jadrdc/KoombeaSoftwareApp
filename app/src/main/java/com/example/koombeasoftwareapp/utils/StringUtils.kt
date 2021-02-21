package com.example.koombeasoftwareapp.utils

import kotlinx.android.synthetic.main.userpost_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    fun formatDateOrdinal(date: String): String {
        var postDate = Date(date)
        var suffix = "th"
        var dayOfMonth = SimpleDateFormat("d").format(postDate.time).toInt()

        if (dayOfMonth == 1 || dayOfMonth == 21 || dayOfMonth == 31) {
            suffix = "st"
        } else if (dayOfMonth == 2 || dayOfMonth == 22) {
            suffix = "nd"
        } else if (dayOfMonth == 3 || dayOfMonth == 23) {
            suffix = "rd"
        }
        var formattedDate = SimpleDateFormat("MMM d").format(postDate.time) + suffix
        return formattedDate
    }
}