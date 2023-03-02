package com.example.gt_4m.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import com.example.gt_4m.data.local.Pref
import com.example.gt_4m.databinding.FragmentProfileBinding
import java.net.URI


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
            binding.imgProfile.setImageURI(Uri.parse(pref.getUserImg()))
        } catch (e: IllegalArgumentException) {
            Log.d("gg", e.toString())
        }






        binding.imgProfile.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_OPEN_DOCUMENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            // Start the Intent
            resultLauncher?.launch(galleryIntent)

        }

    }


    override fun onPause() {
        super.onPause()
        pref.saveNewName(binding.etProfile.text.toString())
    }


    companion object {
        private const val RESULT_LOAD_IMG = 1
    }

}