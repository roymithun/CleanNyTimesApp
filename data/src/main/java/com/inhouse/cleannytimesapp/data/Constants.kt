package com.inhouse.cleannytimesapp.data

object HttpClient {
    const val CONNECTION_TIMEOUT = 10 // 10 seconds
    const val READ_TIMEOUT = 2 // 2 seconds
    const val WRITE_TIMEOUT = 2 // 2 seconds
    const val BASE_URL = "http://api.nytimes.com/svc/mostpopular/v2/mostviewed/"
}