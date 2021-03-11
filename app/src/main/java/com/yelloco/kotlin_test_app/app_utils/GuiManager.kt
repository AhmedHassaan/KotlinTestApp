package com.yelloco.kotlin_test_app.app_utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object GuiManager {

    private val TAG = GuiManager.javaClass.simpleName

    private var fragmentManager: FragmentManager? = null

    private var containerResource: Int? = null

    fun setFragmentManagerObject(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun setContainerResource(containerResource: Int) {
        this.containerResource = containerResource
    }

    fun setCurrentFragment(currentFragment: Fragment) {
        containerResource?.let {
            fragmentManager?.beginTransaction()
                    ?.replace(it, currentFragment)
                    ?.commitAllowingStateLoss()

            Log.i(TAG, "Current fragment is ${currentFragment.javaClass.simpleName}")
        }
    }

    fun getCurrentFragment(): Fragment? {
        return containerResource?.let { fragmentManager?.findFragmentById(it) }
    }

    fun setSubFragment(fragment: Fragment) {
        containerResource?.let {
            fragmentManager?.beginTransaction()
                    ?.replace(it, fragment)
                    ?.addToBackStack(null)
                    ?.commitAllowingStateLoss()

            Log.i(TAG, "Current sub fragment is ${fragment.javaClass.simpleName}")
        }
    }

    fun removeSubFragment()
    {
        if(fragmentManager != null &&
                fragmentManager!!.backStackEntryCount > 0)
        {
            fragmentManager?.popBackStackImmediate()
        }
    }

}