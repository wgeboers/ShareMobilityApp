package com.sm.sharemobilityapp

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.databinding.ActivityMainBinding
import com.sm.sharemobilityapp.databinding.FragmentStartBinding

class fragment_start : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView

        binding.filterButton.setOnClickListener {
            view -> view.findNavController().navigate(R.id.fragment_filter)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}