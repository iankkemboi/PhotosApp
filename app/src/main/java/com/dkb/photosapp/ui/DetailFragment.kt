package com.dkb.photosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.dkb.photosapp.data.models.PhotosListResponse
import com.dkb.photosapp.databinding.FragmentPhotosDetailsBinding
import com.dkb.photosapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentPhotosDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PhotosDetailsViewModel>()
    val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.photoId
        viewModel.getPhoto(id)
        observeUI()


    }

    private fun observeUI() {
        viewModel.photoDetails.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val photo = it.data
                    binding.nameDetail.text = photo!!.title
                    val url = GlideUrl(
                        photo.url, LazyHeaders.Builder()
                            .addHeader("User-Agent", "5")
                            .build()
                    )
                    Glide.with(this).load(url).into(binding.imageDetail)


                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()


                    }


                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                else -> {}
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}