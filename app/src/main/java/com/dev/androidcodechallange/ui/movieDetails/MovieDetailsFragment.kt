package com.dev.androidcodechallange.ui.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dev.androidcodechallange.R
import com.dev.androidcodechallange.databinding.FragmentMovieDetailsBinding
import com.dev.androidcodechallange.utils.Constants
import com.dev.androidcodechallange.utils.StateResource
import com.kaopiz.kprogresshud.KProgressHUD

class MovieDetailsFragment : Fragment() {

    private lateinit var detailsBinding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args by navArgs<MovieDetailsFragmentArgs>()
    private var movieId: Int? = null
    private lateinit var progressDialog: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return detailsBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = Constants.setProgressDialog(requireContext())

        args.let {

            it.id.let {
                movieId = it
            }
        }
        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        subscribeObserver()
        viewModel.getMovieDetails(movieId.toString())


        detailsBinding.navBack.setOnClickListener {

            findNavController().navigateUp()
        }
    }


    private fun subscribeObserver() {


        viewModel.getMovieDetailsData().observe(viewLifecycleOwner, {
            it?.let {

                progressDialog.dismiss()
                detailsBinding.movies = it

            }
        })


        viewModel.getMovieDetailsError().observe(viewLifecycleOwner, {
            it?.let {
                progressDialog.dismiss()

                it.error?.let {
                    it.getContentIfNotHandled()?.let {
                        it.response.let {

                            when (it.responseType) {

                                is StateResource.ResponseType.Dialog -> {
                                    Constants.showError(
                                        requireContext(),
                                        it.message.plus(" ").plus(
                                            it.errorCode?.let {
                                                it
                                            } ?: ""
                                        )
                                    )
                                }
                                is StateResource.ResponseType.Toast -> {

                                }
                                is StateResource.ResponseType.None -> {

                                }

                            }

                        }
                    }
                }
            }
        })
    }


}