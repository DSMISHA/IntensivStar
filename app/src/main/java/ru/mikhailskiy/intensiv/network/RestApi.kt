package ru.mikhailskiy.intensiv.network

import okhttp3.ResponseBody
import rx.Observable

class RestApi {

    /*fun downloadFile(url: String?): Observable<DownloadResult?>? {
        return downloadDataEndpoint.downloadFile(url).map({ response ->
            Util.tryOrNull({
                val body: ResponseBody = response.body()
                if (body != null) DownloadResult(url, body.bytes()) else null
            })
        })
    }*/
}