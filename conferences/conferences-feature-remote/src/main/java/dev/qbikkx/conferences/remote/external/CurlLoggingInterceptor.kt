package dev.qbikkx.conferences.remote.external

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

/**
 * Okhttp interceptor that logs current request in curl
 */
internal class CurlLoggingInterceptor : Interceptor {
    private var curlOptions: String? = null

    /** Set any additional curl command options (see 'curl --help').  */
    fun setCurlOptions(curlOptions: String) {
        this.curlOptions = curlOptions
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var compressed = false

        val curlCmd = StringBuilder("curl")
        if (curlOptions != null) {
            curlCmd.append(" ").append(curlOptions)
        }
        curlCmd.append(" -X ").append(request.method())

        val headers = request.headers()
        var i = 0
        val count = headers.size()
        while (i < count) {
            val name = headers.name(i)
            val value = headers.value(i)
            if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(value, ignoreCase = true)) {
                compressed = true
            }
            curlCmd.append(" -H " + "\"").append(name).append(": ").append(value).append("\"")
            i++
        }

        val requestBody = request.body()
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            // try to keep to a single line and use a subshell to preserve any line breaks
            curlCmd.append(" --data $'")
                .append(buffer.readString(charset!!).replace("\n", "\\n"))
                .append("'")
        }

        curlCmd.append(if (compressed) " --compressed " else " ")
            .append("\"")
            .append(request.url())
            .append("\"")

        Log.d("CURL", "╭--- cURL (" + request.url() + ")")
        Log.d("CURL", curlCmd.toString())
        Log.d("CURL", "╰--- (copy and paste the above line to a terminal)")

        return chain.proceed(request)
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")
    }
}