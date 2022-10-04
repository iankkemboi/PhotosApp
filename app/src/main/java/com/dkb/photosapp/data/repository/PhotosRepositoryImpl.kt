package com.dkb.photosapp.data.repository

import com.dkb.photosapp.data.api.PhotosApi
import com.dkb.photosapp.data.models.PhotosListResponse
import com.dkb.photosapp.di.AppDispatchers



import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepositoryImpl @Inject constructor(
    private val PhotosApi: PhotosApi,

    private val appDispatchers: AppDispatchers,
) : PhotosRepository {


    override suspend fun getPhotos(): Response<List<PhotosListResponse>> {

        return withContext(
            appDispatchers.Main
        ) {
            PhotosApi.getPhotos()
        }

    }

    override suspend fun getPhoto(id: String): Response<PhotosListResponse> {

        return withContext(
            appDispatchers.Main
        ) {
            PhotosApi.getPhoto(id)
        }

    }

}


