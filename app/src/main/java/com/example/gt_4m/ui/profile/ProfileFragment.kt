package com.example.gt_4m.ui.profile

import android.app.Activity
import android.content.Intent

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.gt_4m.R
import com.example.gt_4m.data.local.Pref
import com.example.gt_4m.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var pref: Pref
    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        pref = Pref(requireContext())

        resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                binding.imgProfile.setImageURI(data?.data)
                data?.data?.let { pref.saveNewImg(it) }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etProfile.setText(pref.getUserName())
        Log.d("gg", pref.getUserImg())
        try {
            if (pref.getUserImg().equals("Image")){
                binding.imgProfile.setImageDrawable(R.drawable.ic_profile.toDrawable())
            } else
                binding.imgProfile.setImageURI(Uri.parse(pref.getUserImg()))
        } catch (e: IllegalArgumentException) {
            Log.d("gg", e.toString())
            binding.imgProfile.setImageDrawable(R.drawable.ic_profile.toDrawable())
        }






        binding.imgProfile.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_OPEN_DOCUMENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            // Start the Intent
            resultLauncher?.launch(galleryIntent)

        }

        binding.btnExit.setOnClickListener {
            Firebase.auth.signOut()
            if (FragmentActivity().supportFragmentManager.backStackEntryCount > 0) {
                FragmentActivity().supportFragmentManager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            findNavController().navigate(R.id.authFragment)
        }

    }


    override fun onPause() {
        super.onPause()
        pref.saveNewName(binding.etProfile.text.toString())
    }



}