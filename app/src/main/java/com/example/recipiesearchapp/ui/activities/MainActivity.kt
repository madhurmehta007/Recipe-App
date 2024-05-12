package com.example.recipiesearchapp.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.databinding.ActivityMainBinding
import com.example.recipiesearchapp.ui.fragments.FavouriteFragment
import com.example.recipiesearchapp.ui.fragments.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.navView.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_favourite -> replaceFragment(FavouriteFragment())

                else -> {}

            }
            true
        }

        setContentView(binding.root)
    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}