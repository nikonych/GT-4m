package com.example.gt_4m.ui.onBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.gt_4m.R
import com.example.gt_4m.data.local.Pref
import com.example.gt_4m.databinding.FragmentOnBoardingBinding
import com.example.gt_4m.ui.onBoard.adapter.OnBoardingAdapter
import me.relex.circleindicator.CircleIndicator3

class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var pref: Pref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        val adapter = OnBoardingAdapter{
            pref.saveUserSeen()
            findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToNavigationHome())
        }
        binding.viewPager.adapter = adapter
//        indicator.setViewPager(binding.viewPager)
//        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
        binding.indicator.attachTo(binding.viewPager)
    }

}