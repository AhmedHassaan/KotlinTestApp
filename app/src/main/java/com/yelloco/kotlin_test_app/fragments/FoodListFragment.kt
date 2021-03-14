package com.yelloco.kotlin_test_app.fragments

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yelloco.kotlin_test_app.adapters.FoodsAdapter
import com.yelloco.kotlin_test_app.app_utils.GuiManager
import com.yelloco.kotlin_test_app.database.FoodDao
import com.yelloco.kotlin_test_app.database.FoodDatabase
import com.yelloco.kotlin_test_app.databinding.FragmentFoodListBinding
import com.yelloco.kotlin_test_app.views.CustomProgressDialog

class FoodListFragment : Fragment() {

    private var _binding: FragmentFoodListBinding? = null

    private val binding get() = _binding!!

    private lateinit var activity: Activity
    private lateinit var customProgressDialog: CustomProgressDialog
    private var foodsAdapter: FoodsAdapter = FoodsAdapter()
    private lateinit var foodDB: FoodDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        activity = getActivity()!!

        foodDB = FoodDatabase.get(activity).foodDao()

        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setListenerForViews()

        customProgressDialog.show()

        getAllFood()
    }

    private fun initViews()
    {
        customProgressDialog = CustomProgressDialog(activity, binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView()
    {
        binding.fragmentFoodListRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.fragmentFoodListRecyclerView.adapter = foodsAdapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                foodsAdapter.notifyItemChanged(position)

                val builder = AlertDialog.Builder(activity)
                builder.setCancelable(false)
                builder.setMessage("Are you sure that u want to delete ${foodsAdapter.foods[position].name}")
                builder.setPositiveButton("Yes") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    customProgressDialog.show()
                    removeFoodFromDB(position)
                }
                builder.setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }

                val dialog = builder.create()
                dialog.setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
                }

                dialog.show()
            }
        }).attachToRecyclerView(binding.fragmentFoodListRecyclerView)
    }

    private fun setListenerForViews()
    {
        binding.fragmentFoodListCancelButton.setOnClickListener {
            GuiManager.removeSubFragment()
        }
    }

    private fun removeFoodFromDB(position : Int)
    {
        Thread {
            foodDB.deleteFood(foodsAdapter.foods[position])
            activity.runOnUiThread {
                customProgressDialog.dismiss()
                foodsAdapter.foods.removeAt(position)
                foodsAdapter.notifyItemRemoved(position)
            }
        }.start()
    }

    private fun getAllFood()
    {
        Thread {
            val foodsList = foodDB.getAll()
            activity.runOnUiThread {
                customProgressDialog.dismiss()
                foodsAdapter.foods.addAll(foodsList)
                foodsAdapter.notifyDataSetChanged()
            }
        }.start()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}