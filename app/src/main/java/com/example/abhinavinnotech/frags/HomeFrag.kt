package com.example.abhinavinnotech.frags

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.abhinavinnotech.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFrag : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseDatabase: FirebaseDatabase

    private lateinit var databaseReference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val view= binding.root
        firebaseDatabase = FirebaseDatabase.getInstance("https://abhinav-innotech-default-rtdb.asia-southeast1.firebasedatabase.app")
        databaseReference = firebaseDatabase.getReference("data")
        Log.d("Firebase", "Database Reference: $databaseReference")


        getData()
        return view
    }
    private fun getData() {
        // Add a value event listener to retrieve data from Firebase
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Check if the snapshot exists and contains the expected data
                if (snapshot.exists()) {
                    val moisture = snapshot.child("moisture_percent").getValue(Double::class.java)
                    val ph_value = snapshot.child("ph_value").getValue(Double::class.java)
                    val water_tds_value = snapshot.child("water_tds_value").getValue(Double::class.java)
                    Log.d("Firebase", "Moisture: $moisture, pH: $ph_value, TDS: $water_tds_value")


                    // Update the TextViews with the retrieved data
                    binding.moistureValue.text = moisture?.toString() ?: "N/A"
                    binding.waterTdsValue.text = water_tds_value?.toString() ?: "N/A"
                    binding.phValue.text = ph_value?.toString() ?: "N/A"

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database retrieval error
                Toast.makeText(requireContext(), "Failed to get data.${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
