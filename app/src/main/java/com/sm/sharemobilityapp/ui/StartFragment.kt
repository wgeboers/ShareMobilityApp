package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.adapter.ItemAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentStartBinding
import com.sm.sharemobilityapp.utils.GPSUtils

class StartFragment : Fragment() {
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
        GPSUtils.initPermissions(requireActivity())

        val myDataset = Datasource().loadCars()
        recyclerView = binding.recyclerView
        recyclerView.adapter = ItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)

        val amountOfResult: TextView = view.findViewById(R.id.start_filter_results_amount)
        amountOfResult.text = myDataset.size.toString()

        binding.filterButton.setOnClickListener {
            view -> view.findNavController().navigate(R.id.action_fragment_start_to_fragment_filter)
        }

        binding.mapButton.setOnClickListener {
            view -> view.findNavController().navigate(R.id.action_home_to_fragment_map)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}