package com.dicoding.skivent.ui.dashboard.scan

import android.animation.LayoutTransition
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dicoding.skivent.R
import com.dicoding.skivent.databinding.ActivityScanResultBinding

class ScanResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add logging to check if views are initialized
        Log.d("ScanResultActivity", "linearLayoutDesc: ${binding.linearLayoutDesc}")
        Log.d("ScanResultActivity", "cardviewDes: ${binding.cardviewDes}")
        Log.d("ScanResultActivity", "tvDesc: ${binding.tvDesc}")

        Log.d("ScanResultActivity", "linearLayoutPrev: ${binding.linearLayoutPrev}")
        Log.d("ScanResultActivity", "cardviewDes: ${binding.cardviewDes}")
        Log.d("ScanResultActivity", "tvPrev: ${binding.tvPrev}")

        Log.d("ScanResultActivity", "linearLayoutTreat: ${binding.linearLayoutTreat}")
        Log.d("ScanResultActivity", "cardviewDes: ${binding.cardviewDes}")
        Log.d("ScanResultActivity", "tvTreat: ${binding.tvTreat}")

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
    }
}