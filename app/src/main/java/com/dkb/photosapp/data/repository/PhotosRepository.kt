package com.dkb.photosapp.data.repository

import com.dkb.photosapp.data.models.PhotosListResponse
import retrofit2.Response

interface PhotosRepository {
    suspend fun getPhotos(): Response<List<PhotosListResponse>>

    suspend fun getPhoto(id: String): Response<PhotosListResponse>
}