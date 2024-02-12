package com.tpov.mornhouse.data.remote

import com.tpov.mornhouse.DOMAIN
import com.tpov.mornhouse.PATH
import com.tpov.mornhouse.PROTOCOL
import com.tpov.mornhouse.UNKNOWN_NUMBER_DETAIL
import okhttp3.OkHttpClient
import okhttp3.Request

class NumbersRepository {
    private val client = OkHttpClient()

    fun fetchNumberTrivia(number: Int): String {
        val url = if (number == UNKNOWN_NUMBER_DETAIL) "$PROTOCOL$DOMAIN$PATH"
        else "$PROTOCOL$DOMAIN/$number"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Unexpected code $response")
            return response.body!!.string()
        }
    }
}