package com.example.gt_4m.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.gt_4m.databinding.FragmentTaskBinding
import com.example.gt_4m.model.Task
import com.example.gt_4m.ui.home.HomeFragment

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val task = Task(
                binding.etTitle.text.toString(),
                binding.etDesc.text.toString()
            )

            setFragmentResult(HomeFragment.RESULT_REQUEST_KEY, bundleOf(HomeFragment.TASK_KEY to task))
            findNavController().navigateUp()

        }
    }

}