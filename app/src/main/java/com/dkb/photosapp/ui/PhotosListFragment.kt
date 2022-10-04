package com.dkb.photosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dkb.photosapp.data.models.PhotosListResponse
import com.dkb.photosapp.databinding.FragmentPhotosListBinding
import com.dkb.photosapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PhotosListFragment : Fragment() {
    private var _binding: FragmentPhotosListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PhotosListViewModel>()
    private lateinit var PhotosListAdapter: PhotosListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPhotos()

        setUpRecyclerView()
        observeUI()


    }

    private fun setUpRecyclerView() {


        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.layoutManager = linearLayoutManager


    }

    private fun observeUI() {
        viewModel.photosList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val onClicked: (photo: PhotosListResponse) -> Unit = { photo ->
                        val action =
                            PhotosListFragmentDirections.actionListFragmentToDetailFragment(photo.id.toString())
                        findNavController().navigate(action)

                    }
                    val photosList = it.data!!
                    PhotosListAdapter = PhotosListAdapter(requireContext(), onClicked, photosList)
                    binding.recyclerView.adapter = PhotosListAdapter


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