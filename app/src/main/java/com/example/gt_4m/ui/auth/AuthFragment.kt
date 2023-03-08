package com.example.gt_4m.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gt_4m.R
import com.example.gt_4m.databinding.FragmentAuthBinding
import com.example.gt_4m.ui.onBoard.OnBoardingFragmentDirections
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var signInOptions: GoogleSignInClient
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                requireActivity().finish() // Завершить активность
                return@setOnKeyListener true
            }
            false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        signInOptions = GoogleSignIn.getClient(requireActivity(), gso)
        binding.btnGoogle.setOnClickListener {
            signIn()

        }




    }



    private fun signIn() {
        val intent = signInOptions.signInIntent
//        startActivityForResult(intent, 0)
        intent.putExtra("REQUEST_CODE", true)

        resultLauncher.launch(intent)
    }

//    fun openSomeActivityForResult() {
//        val intent = Intent(this, SomeActivity::class.java)
//        resultLauncher.launch(intent)
//    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {

            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account)
//            } catch (e: ApiException) {
//
//            }
//        }
//    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener{ authResult ->
                findNavController().navigateUp()

            }
            .addOnFailureListener { e ->
                
            }


    }




}