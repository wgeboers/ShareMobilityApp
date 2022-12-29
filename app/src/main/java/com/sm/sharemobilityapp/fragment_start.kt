package com.sm.sharemobilityapp

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.adapter.ItemAdapter
import com.sm.sharemobilityapp.data.Datasource
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
        val myDataset = Datasource().loadCars()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = ItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)

        val amountOfResult: TextView = view.findViewById(R.id.start_filter_results_amount)
        amountOfResult.text = myDataset.size.toString()

        binding.filterButton.setOnClickListener {
            view -> view.findNavController().navigate(R.id.fragment_filter)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}