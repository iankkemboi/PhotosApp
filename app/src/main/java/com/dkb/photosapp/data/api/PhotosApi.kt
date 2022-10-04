package com.dkb.photosapp.data.api

import com.dkb.photosapp.data.models.PhotosListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotosApi {

    @GET("photos")
    suspend fun getPhotos(): Response<List<PhotosListResponse>>

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ): Response<PhotosListResponse>
}
