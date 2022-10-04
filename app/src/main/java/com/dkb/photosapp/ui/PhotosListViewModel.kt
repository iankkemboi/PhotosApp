package com.dkb.photosapp.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkb.photosapp.data.models.PhotosListResponse
import com.dkb.photosapp.data.repository.PhotosRepository
import com.dkb.photosapp.util.Resource


import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch

import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PhotosListViewModel @Inject constructor(
    private val PhotosListRepository: PhotosRepository,

    ) : ViewModel() {

    val photosList: MutableLiveData<Resource<List<PhotosListResponse>>> = MutableLiveData()



    fun getPhotos() {
        photosList.postValue(Resource.Loading())
        viewModelScope.launch {
            try {


                val response = PhotosListRepository.getPhotos()

                photosList.postValue(Resource.Success(response.body()!!))


            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> photosList.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> photosList.postValue(Resource.Error(" Error" + ex.localizedMessage))
                }
            }
        }
    }


}