package com.dicoding.skivent.ui.dashboard.scan

import android.animation.LayoutTransition
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.dicoding.skivent.R
import com.dicoding.skivent.UserPreference
import com.dicoding.skivent.api.APIConfig
import com.dicoding.skivent.api.APIService
import com.dicoding.skivent.databinding.ActivityScanResultBinding
import com.dicoding.skivent.ui.authentication.FactoryViewModel
import com.dicoding.skivent.ui.authentication.UserLoginViewModel
import com.dicoding.skivent.ui.authentication.dataStore
import com.dicoding.skivent.ui.dashboard.MainActivity
import com.dicoding.skivent.ui.dashboard.MainViewModel
import java.io.IOException

class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding
    private lateinit var token: String

    private val viewModel: ScanResultViewModel by lazy {
        val apiService = APIConfig.getApiService() // Pastikan ini sesuai dengan cara Anda mendapatkan APIService
        val factory = ViewModelFactory(apiService)
        ViewModelProvider(this, factory)[ScanResultViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* get Token from preference */
        val preferences = UserPreference.getInstance(dataStore)
        val userLoginViewModel =
            ViewModelProvider(this, FactoryViewModel(preferences))[UserLoginViewModel::class.java]
        userLoginViewModel.getToken().observe(this) {
            token = it

            val imageUriString = intent.getStringExtra(EXTRA_PHOTO_RESULT)
            if (imageUriString != null) {
                val imageUri = Uri.parse(imageUriString)
                displayImage(imageUri)
                viewModel.predicSkin(imageUri, this, token)
            } else {
                Log.e("ScanResultActivity", "No image URI received")
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.linearLayoutDesc.layoutTransition = LayoutTransition()
        binding.linearLayoutDesc.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.cardviewDes.setOnClickListener{
            val v = if (binding.tvDesc.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.tvDesc.visibility = v
            binding.dividerDesc.visibility = v
        }

        binding.linearLayoutPrev.layoutTransition = LayoutTransition()
        binding.linearLayoutPrev.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.cardviewPrev.setOnClickListener{
            val v = if (binding.tvPrev.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.tvPrev.visibility = v
            binding.dividerPrev.visibility = v
        }

        binding.linearLayoutTreat.layoutTransition = LayoutTransition()
        binding.linearLayoutTreat.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.cardviewTreat.setOnClickListener{
            val v = if (binding.tvTreat.visibility == View.GONE) View.VISIBLE else View.GONE
            binding.tvTreat.visibility = v
            binding.dividerTreat.visibility = v
        }

        observePredictionResult()
    }

    private fun displayImage(imageUri: Uri) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            binding.imgScanResult.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ScanResultActivity", "Error loading image: ${e.message}")
        }
    }

    private fun observePredictionResult() {
        viewModel.predictionResult.observe(this) { result ->
            if (result.success) {
                result.result?.let { prediction ->
                    binding.textView.text = prediction.predictedDisease
                    binding.tvAcc.text = String.format("%.2f%%", prediction.confidenceScore).replace("\\s+(?=%)", "")
                    binding.tvDesc.text = prediction.description
                    binding.tvPrev.text = prediction.prevention
                    binding.tvTreat.text = prediction.treatment
                }
            } else {
                // Handle error
                Toast.makeText(this, "Failed to get prediction", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val EXTRA_PHOTO_RESULT = "PHOTO_RESULT"
        const val EXTRA_CAMERA_MODE = "CAMERA_MODE"
    }
}