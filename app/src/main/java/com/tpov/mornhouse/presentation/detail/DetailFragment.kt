package com.tpov.mornhouse.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tpov.mornhouse.*
import com.tpov.mornhouse.data.database.DatabaseBuilder
import com.tpov.mornhouse.databinding.FragmentDetailBinding
import com.tpov.mornhouse.domain.Repository
import com.tpov.mornhouse.presentation.main.MainViewModel
import com.tpov.mornhouse.presentation.main.ViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private var number: Int = 0
    private var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            number = it.getInt(KEY_SHARED_NUMBER, UNKNOWN_NUMBER_DETAIL)
            text = it.getString(KEY_SHARED_TEXT, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Repository(DatabaseBuilder.getInstance(requireContext()).itemDao())
        val mainViewModel =  ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]

        initToolbar()
        Log.d("dawasaew", "number: $number, text: $text")

        if (text == "" || text == RANDOM_STRING_KEY) {
            mainViewModel.numberTrivia.observe(viewLifecycleOwner) { trivia ->
                hideProgressBar()
                showData(trivia)
                mainViewModel.saveDataInDatabase(trivia)
            }
            if (text == RANDOM_STRING_KEY) mainViewModel.fetchNumberTrivia(UNKNOWN_NUMBER_DETAIL)
            else mainViewModel.fetchNumberTrivia(number)
        } else {
            hideProgressBar()
            showData(text)
        }

    }

    private fun initToolbar() {
        val toolbar = view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationIcon(android.R.drawable.ic_menu_revert)

        toolbar?.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun hideProgressBar() {
        binding?.pbLoadText?.visibility = View.GONE
    }

    private fun showData(trivia: String) {
        binding?.textView?.text = trivia
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}