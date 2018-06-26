package com.github.angads25.roomretrorxdagger.retrofit.repository

import com.github.angads25.roomretrorxdagger.architecture.model.ApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PropertyApiRepository {

    @GET("bins/bs67u")
    fun getPropertyList(): Observable<ApiResponse>
}