package com.dicoding.skivent.ui.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.dicoding.skivent.R
import com.dicoding.skivent.databinding.ActivityMainBinding
import com.dicoding.skivent.ui.dashboard.home.HomeFragment
import com.dicoding.skivent.ui.dashboard.profile.ProfileFragment
import com.dicoding.skivent.ui.dashboard.scan.CameraActivity
import com.dicoding.skivent.ui.dashboard.scan.ScanResultActivity
import com.dicoding.skivent.utils.Constanta
import com.dicoding.skivent.utils.Helper
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var fragmentHome: HomeFragment? = null
    private lateinit var startResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentProfile = ProfileFragment()
        fragmentHome = HomeFragment()
        switchFragment(fragmentHome!!)

        startResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    fragmentHome?.onRefresh()
                }
            }

        binding.bottomNavigationView.background = null // hide abnormal layer in bottom nav

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    switchFragment(fragmentHome!!)
                    true
                }
                R.id.navigation_camera -> {
                    if (Helper.isPermissionGranted(this, Manifest.permission.CAMERA)) {
                        val intent = Intent(this@MainActivity, CameraActivity::class.java)
                        startResult.launch(intent)
                        true
                    } else {
                        ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.CAMERA),
                            Constanta.CAMERA_PERMISSION_CODE
                        )
                        false
                    }
                }
                R.id.navigation_profile -> {
//                    switchFragment(fragmentProfile)
                    val intent = Intent(this, ScanResultActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constanta.CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Helper.notifyGivePermission(this, "Berikan aplikasi izin mengakses kamera  ")
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}