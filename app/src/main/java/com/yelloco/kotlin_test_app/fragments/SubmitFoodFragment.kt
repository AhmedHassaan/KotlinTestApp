package com.yelloco.kotlin_test_app.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yelloco.kotlin_test_app.app_utils.AppUtils
import com.yelloco.kotlin_test_app.app_utils.AppUtils.Companion.hideKeyboard
import com.yelloco.kotlin_test_app.app_utils.GuiManager
import com.yelloco.kotlin_test_app.database.FoodDatabase
import com.yelloco.kotlin_test_app.database.models.Food
import com.yelloco.kotlin_test_app.databinding.FragmentSubmitFoodBinding
import com.yelloco.kotlin_test_app.views.CustomProgressDialog

class SubmitFoodFragment : Fragment() {

    private val _classTag = this.javaClass.simpleName

    private var _binding: FragmentSubmitFoodBinding? = null

    private val binding get() = _binding!!

    private lateinit var activity: Activity

    private lateinit var progressDialog: CustomProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity = getActivity()!!
        _binding = FragmentSubmitFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setListenerForViews()
    }

    private fun initViews() {
        progressDialog = CustomProgressDialog(activity, binding.root)
    }

    private fun setListenerForViews() {
        binding.fragmentSubmitFoodCancelButton.setOnClickListener {
            binding.fragmentSubmitFoodFoodInputEditText.hideKeyboard()
            GuiManager.removeSubFragment()
        }

        binding.fragmentSubmitFoodSubmitButton.setOnClickListener {
            binding.fragmentSubmitFoodFoodInputEditText.hideKeyboard()
            Log.i(_classTag, "Text entered is: ${binding.fragmentSubmitFoodFoodInputEditText.text}")
            if(binding.fragmentSubmitFoodFoodInputEditText.text.toString().trim().isEmpty())
            {
                Toast.makeText(activity, "Can't insert empty food name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressDialog.show()
            addToFoodDB(Food(name = binding.fragmentSubmitFoodFoodInputEditText.text.toString().trim()))
        }
    }

    private fun addToFoodDB(food: Food) {
        Thread()
        {
            val foodDB = FoodDatabase.get(activity).foodDao()
            if(foodDB.isFoodExist(food.name))
            {
                activity.runOnUiThread {
                    progressDialog.dismiss()
                    Toast.makeText(activity, "${food.name} is already exist", Toast.LENGTH_SHORT).show()
                }
                return@Thread
            }

            foodDB.insertFood(food)
            activity.runOnUiThread {
                progressDialog.dismiss()
                Toast.makeText(activity, "${food.name} added", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}