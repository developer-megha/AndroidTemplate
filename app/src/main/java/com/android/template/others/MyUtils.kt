package com.android.template.others

import android.os.Build
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.google.gson.JsonObject
import com.android.template.others.Toaster.shortToast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern

class MyUtils {
    val min = 0
    val max = 3


    fun viewGone(view: View?) {
        if (view != null) {
            view.visibility = View.GONE

        }
    }

    fun viewInvisible(view: View?) {
        if (view != null && view.visibility == View.VISIBLE) {
            view.visibility = View.INVISIBLE
        }
    }

    fun viewVisible(view: View?) {
        if (view != null && (view.visibility == View.INVISIBLE || view.visibility == View.GONE)) {
            view.visibility = View.VISIBLE

        }
    }

    fun isEmptyString(value: String?): Boolean {
        return TextUtils.isEmpty(value) || TextUtils.isEmpty(value?.trim())
    }

    @Throws(IOException::class)
    fun downloadUrl(myUrl: String?): String? {
        var input: InputStream? = null
        return try {
            val url = URL(myUrl)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "GET"
            conn.doInput = true
            conn.connect()
            input = conn.inputStream
            readIt(input)
        } finally {
            input?.close()
        }
    }

    @Throws(IOException::class)
    private fun readIt(stream: InputStream?): String? {
        val reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
        val sb = StringBuilder()
        var line: String? = ""

        while (reader.readLine()?.let { line = it } != null) {
            if (line?.contains("fmt_stream_map") == true) {
                sb.append(
                    """
                    $line
                    
                    """.trimIndent()
                )
                break
            }
        }
        reader.close()
        val result = decode(sb.toString())
        val url = result!!.split("\\|".toRegex()).toTypedArray()
        return if (url.size > 1) url[1] else url[0]
    }

    private fun decode(input: String): String? {
        var working = input
        var index: Int
        index = working.indexOf("\\u")
        while (index > -1) {
            val length = working.length
            if (index > length - 6) break
            val numStart = index + 2
            val numFinish = numStart + 4
            val substring = working.substring(numStart, numFinish)
            val number = substring.toInt(16)
            val stringStart = working.substring(0, index)
            val stringEnd = working.substring(numFinish)
            working = stringStart + number.toChar() + stringEnd
            index = working.indexOf("\\u")
        }
        return working
    }

    fun returnCamelCaseWord(str:String): String {
        val words: List<String> = str.split(" ")
        val sb = StringBuilder()
        if (words[0].isNotEmpty()) {
            sb.append(
                Character.toUpperCase(words[0][0]).toString() + words[0].subSequence(
                    1,
                    words[0].length
                ).toString().lowercase()
            )
            for (i in 1 until words.size) {
                sb.append(" ")
                sb.append(
                    Character.toUpperCase(words[i][0]).toString() + words[i].subSequence(
                        1,
                        words[i].length
                    ).toString().lowercase()
                )
            }
        }

        return sb.toString()
    }

    companion object {
        fun viewGone(view: View?) {
            if (view != null) {
                view.visibility = View.GONE

            }
        }

        fun viewsGone(view: List<View?>?) {
            if (!view.isNullOrEmpty()) {
                for (item in view) {
                    viewGone(item)
                }
            }
        }

        fun viewsVisible(view: List<View?>?) {
            if (!view.isNullOrEmpty()) {
                for (item in view) {
                    viewVisible(item)
                }
            }
        }


        fun viewInvisible(view: View?) {
            if (view != null) {
                view.visibility = View.INVISIBLE

            }
        }

        fun viewVisible(view: View?) {
            if (view != null && (view.visibility == View.INVISIBLE || view.visibility == View.GONE)) {
                view.visibility = View.VISIBLE

            }
        }

        fun isEmptyString(value: String?): Boolean {
            return TextUtils.isEmpty(value) || TextUtils.isEmpty(value?.trim())
        }

        fun isValidPassword(password: String): Boolean {
            val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])\\S{7,}\$".toRegex()
            return passwordPattern.matches(password)
        }

        fun isValidEmail(email: String): Boolean {
            val emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
            return emailPattern.matches(email)
        }

        fun contains(value: String?, match: String?): Boolean {
            return !isEmptyString(value) && !isEmptyString(match) && value!!.contains(match!!)
        }

        fun convertToJSON(body: JsonObject?): JSONObject {
            return JSONObject(body?.toString() ?: "")
        }

        fun setText(view: TextView?, value: String?) {
            viewVisible(view)
            if (view != null) {
                if (value != null && value != "") {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        view?.text = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        view?.text = Html.fromHtml(value)
                    }
                } else {
                    view.text = "--"
                }


            }
        }

        fun capitalize(input: String): String? {
            val words = input.lowercase().split(" ").toTypedArray()
            val builder = java.lang.StringBuilder()
            for (i in words.indices) {
                val word = words[i]
                if (i > 0 && word.isNotEmpty()) {
                    builder.append(" ")
                }
                val cap = word.substring(0, 1).uppercase() + word.substring(1)
                builder.append(cap)
            }
            return builder.toString()
        }

        fun returnCamelCaseWord(str:String): String {
            val words: List<String> = str.split(" ")
            val sb = StringBuilder()
            if (words[0].isNotEmpty()) {
                sb.append(
                    Character.toUpperCase(words[0][0]).toString() + words[0].subSequence(
                        1,
                        words[0].length
                    ).toString().lowercase()
                )
                for (i in 1 until words.size) {
                    sb.append(" ")
                    sb.append(
                        Character.toUpperCase(words[i][0]).toString() + words[i].subSequence(
                            1,
                            words[i].length
                        ).toString().lowercase()
                    )
                }
            }

            return sb.toString()
        }

        fun isYouTubeUrlValid(videoUrl: String?): Boolean {
            if (isEmptyString(videoUrl)) {
                //shortToast("Empty YouTube link ...")
                return false
            } else if (videoUrl!!.startsWith(Cons.YOUTUBE_LINK_MATCHER)) {
                //shortToast("Invalid YouTube link ...")
                return true
            } else if (videoUrl.startsWith(Cons.SHORT_YOUTUBE_LINK_MATCHER)) {
                return true
            }
            return false
        }

        fun isValidVideoUrl(videoUrl: String?): Boolean {
            if (isEmptyString(videoUrl)) {
                shortToast("Empty YouTube link ...")
                return false
            } else if (!videoUrl!!.contains(Cons.YOUTUBE_LINK_MATCHER)) {
                shortToast("Invalid YouTube link ...")
                return false
            }
            return true
        }

    }

}
