package com.yelloco.kotlin_test_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yelloco.kotlin_test_app.app_utils.GuiManager
import com.yelloco.kotlin_test_app.databinding.FragmentMainBinding

class MainFragment : Fragment()
{
    private val classTag = this.javaClass.simpleName

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListenerForViews()
    }

    private fun setListenerForViews()
    {
        binding.fragmentMainAddToListButton.setOnClickListener {
            Log.i(classTag, "View is: ${it.id}")
            GuiManager.setSubFragment(SubmitFoodFragment())
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}