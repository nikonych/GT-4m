package com.example.gt_4m.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gt_4m.databinding.FragmentDashboardBinding
import com.example.gt_4m.model.Quote
import com.example.gt_4m.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        binding.btnSave.setOnClickListener {
            if (binding.etAuthor.text?.isNotEmpty() == true && binding.etQuotes.text?.isNotEmpty() == true) {
                save()
            } else if (binding.etAuthor.text?.isEmpty() == true) {
                binding.etAuthor.error = "Input Value"
            } else if (binding.etQuotes.text?.isEmpty() == true) {
                binding.etQuotes.error = "Input Value"
            } else {
                binding.etAuthor.error = "Input Value"
                binding.etQuotes.error = "Input Value"

            }
        }
    }

    private fun save() {
        val quote = Quote(binding.etQuotes.text.toString(), binding.etAuthor.text.toString())
        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).add(quote)
            .addOnSuccessListener {
                context?.toast("gg")
                binding.apply {
                    etQuotes.text?.clear()
                    etAuthor.text?.clear()
                }
            }.addOnFailureListener {
                Log.d("ggf", it.message.toString())
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}