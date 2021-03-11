package com.yelloco.kotlin_test_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yelloco.kotlin_test_app.app_utils.GuiManager
import com.yelloco.kotlin_test_app.databinding.FragmentSubmitFoodBinding

class SubmitFoodFragment : Fragment() {

    private val _classTag = this.javaClass.simpleName

    private var _binding: FragmentSubmitFoodBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentSubmitFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListenerForViews()
    }

    private fun setListenerForViews()
    {
        binding.fragmentSubmitFoodCancelButton.setOnClickListener {
            GuiManager.removeSubFragment()
        }

        binding.fragmentSubmitFoodSubmitButton.setOnClickListener {
            Log.i(_classTag, "Text entered is: ${binding.fragmentSubmitFoodFoodInputEditText.text}")
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}