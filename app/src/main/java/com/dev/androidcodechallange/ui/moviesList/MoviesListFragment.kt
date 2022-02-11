package com.dev.androidcodechallange.ui.moviesList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.androidcodechallange.R
import com.dev.androidcodechallange.adapters.MoviesAdapter
import com.dev.androidcodechallange.dataClasses.Movie
import com.dev.androidcodechallange.databinding.FragmentMoviesListBinding
import com.dev.androidcodechallange.utils.Constants
import com.dev.androidcodechallange.utils.StateResource
import com.kaopiz.kprogresshud.KProgressHUD

class MoviesListFragment : Fragment(), MoviesAdapter.Interaction {


    lateinit var moviesListBinding: FragmentMoviesListBinding
    private var recyclerAdapter: MoviesAdapter? = null
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var progressDialog: KProgressHUD
    lateinit var movielist: ArrayList<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        moviesListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false)
        return moviesListBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movielist = arrayListOf()
        progressDialog = Constants.setProgressDialog(requireContext())

        initMovieRecyclerView()
        subscribeObserver()
        viewModel.getMovies()


        moviesListBinding.simpleSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val monthList =
                    movielist.filter { it.original_title?.contains(newText.toString(), true)!! }
                        .toList()


                monthList.let {

                    recyclerAdapter?.submitList(it)

                }
                return false
            }
        })
    }


    private fun initMovieRecyclerView() {

        moviesListBinding.moviesList.apply {
            layoutManager = GridLayoutManager(this@MoviesListFragment.context, 2)

            recyclerAdapter = MoviesAdapter(
                 this@MoviesListFragment
            )

            adapter = recyclerAdapter
        }

    }

    private fun subscribeObserver() {

        progressDialog.setLabel("Please Wait")
        progressDialog.show()
        viewModel.getMoviesData().observe(viewLifecycleOwner, {
            progressDialog.dismiss()
            it?.let {


                it.results?.let { it1 ->
                    recyclerAdapter?.submitList(it1)

                    movielist.addAll(it1)
                }

            }
        })


        viewModel.getMoviesError().observe(viewLifecycleOwner, {
            progressDialog.dismiss()
            it?.let {

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

    override fun onMovieItemSelected(position: Int, item: Movie) {

        val d = MoviesListFragmentDirections.actionMoviesListFragment2ToMovieDetailsFragment2(
            item.id ?: 0
        )

        findNavController().navigate(d)
    }


}