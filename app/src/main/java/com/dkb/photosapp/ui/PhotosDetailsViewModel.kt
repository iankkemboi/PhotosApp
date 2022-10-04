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
class PhotosDetailsViewModel @Inject constructor(
    private val PhotosListRepository: PhotosRepository,

    ) : ViewModel() {

     val photoDetails: MutableLiveData<Resource<PhotosListResponse>> = MutableLiveData()



    fun getPhoto(id: String) {
        photoDetails.postValue(Resource.Loading())
        viewModelScope.launch {
            try {


                val response = PhotosListRepository.getPhoto(id)

                photoDetails.postValue(Resource.Success(response.body()!!))


            } catch (ex: Exception) {
                when (ex) {
                    is IOException -> photoDetails.postValue(Resource.Error("Network Failure " + ex.localizedMessage))
                    else -> photoDetails.postValue(Resource.Error(" Error" + ex.localizedMessage))
                }
            }
        }
    }


}