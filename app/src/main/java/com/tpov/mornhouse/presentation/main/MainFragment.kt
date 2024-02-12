package com.tpov.mornhouse.presentation.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tpov.mornhouse.*
import com.tpov.mornhouse.data.database.DatabaseBuilder
import com.tpov.mornhouse.data.database.models.NumberDetailEntity
import com.tpov.mornhouse.databinding.FragmentMainBinding
import com.tpov.mornhouse.domain.Repository
import com.tpov.mornhouse.presentation.detail.DetailFragment
import java.util.Collections.emptyList


class MainFragment : Fragment(), MainHistoryAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val detailFragment = DetailFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        initAdapter()

        binding.bUserNumber.setOnClickListener { startDetailFragment() }

        binding.bGenerateNumber.setOnClickListener { startDetailFragment(RANDOM_STRING_KEY) }

        binding.edtNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateButtonStateAfterNumberChanged(s)
            }
        })
    }

    private fun initAdapter() {
        val adapter = MainHistoryAdapter(emptyList(), this)

        binding.rvHistory.layoutManager = LinearLayoutManager(activity)
        binding.rvHistory.adapter = adapter

        val repository = Repository(DatabaseBuilder.getInstance(requireContext()).itemDao())
        val mainViewModel: MainViewModel by lazy {
            ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]
        }
        mainViewModel.getAllItemsLiveData().observe(this) { items ->
            adapter.setData(items.sortedWith(compareByDescending { it.id }))
        }


    }

    private fun updateButtonStateAfterNumberChanged(number: Editable?) {
        val text = number.toString()
        binding.bUserNumber.isClickable = text.isNotEmpty()
        binding.bUserNumber.isEnabled = text.isNotEmpty()
    }

    private fun startDetailFragment(textItem: String = "") {
        val edtNumberValue = binding.edtNumber.text.toString()
        val number = if (edtNumberValue.isEmpty()) UNKNOWN_NUMBER_DETAIL
        else edtNumberValue.toInt()
        val bundle = Bundle().apply {
            putInt(KEY_SHARED_NUMBER, number)
            putString(KEY_SHARED_TEXT, textItem)
        }

        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle

        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val existingFragment = fragmentManager.findFragmentById(R.id.fr_container)
        if (existingFragment is DetailFragment) {
            existingFragment.arguments = bundle
        } else {
            transaction.replace(R.id.fr_container, detailFragment)
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(itemText: NumberDetailEntity) {
        startDetailFragment(itemText.name)
    }



}

