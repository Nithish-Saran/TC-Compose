package com.apps.tc.tccompose2025

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.apps.tc.tccompose2025.models.StickersData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.net.toUri

object ApiRepository {
    fun downloadStickers(app: App, stickersData: StickersData) {
        val url = "${Constant.ContentURL}calendar/tamil/etc/${stickersData.folder}.zip"
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Important for Compose
        app.startActivity(intent)
    }

    fun showInstructions() : String {
        return "${Constant.ContentURL}calendar/tamil/etc/whatsapp/whats_app_stickers_instrn.html"
    }

    private fun httpGetJSON(app: App, url: String,
                            responder: (Boolean, Int, JSONObject?, JSONArray?) -> Unit) {
        AsyncHttpClient().let {

            it.setMaxRetriesAndTimeout(2, 0)
            it.get(app, url, object: JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int, headers: Array<out Header>?,
                    response: JSONArray?
                ) {
                    responder(true, statusCode, null, response)
                }

                override fun onSuccess(
                    statusCode: Int, headers: Array<out Header>?,
                    response: JSONObject?
                ) {
                    responder(true, statusCode, response, null)
                }

                override fun onSuccess(
                    statusCode: Int, headers: Array<out Header>?,
                    responseString: String?
                ) {
                    responder(true, statusCode, null, null)
                }

                override fun onFailure(
                    statusCode: Int, headers: Array<out Header>?,
                    responseString: String?, throwable: Throwable?
                ) {
                    log("HTTP Error: Code: $statusCode, Response: $responseString")
                    logE(throwable, "HTTP Error")
                    responder(false, statusCode, null, null)
                }

                override fun onFailure(
                    statusCode: Int, headers: Array<out Header>?,
                    throwable: Throwable?, errorResponse: JSONArray?
                ) {
                    log("HTTP Error: Code: $statusCode, Response: $errorResponse")
                    logE(throwable, "HTTP Error")
                    responder(false, statusCode, null, null)
                }

                override fun onFailure(
                    statusCode: Int, headers: Array<out Header>?,
                    throwable: Throwable?, errorResponse: JSONObject?
                ) {
                    log("HTTP Error: Code: $statusCode, Response: $errorResponse")
                    logE(throwable, "HTTP Error")
                    responder(false, statusCode, null, null)
                }
            })
        }
    }
}