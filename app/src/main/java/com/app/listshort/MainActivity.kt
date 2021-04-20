package com.app.listshort

import android.content.ClipData
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.listshort.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listAdapter: ListAdapter
    private lateinit var items: MutableList<ListModel>
    private lateinit var onActionListener: OnActionListener<ListModel>

    private lateinit var cityList: MutableList<ListModel>
    private lateinit var newCityList: MutableList<ListModel>

    private var isSelect: Boolean? = false

    private var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        items = ArrayList()
        newCityList = ArrayList()
        cityList = ArrayList()

        initListerer()
        initAdapter()
    }

    private fun initAdapter() {

        cityList.clear()
        items.clear()
        for (i in 1..100) {
            var model: ListModel = ListModel()
            model.name = "City $i"
            model.id = i
            model.isSelected = false
            items.add(model)
        }
        cityList = items

        listAdapter = ListAdapter(this, items, onActionListener)
        binding.recyclerList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerList.adapter = listAdapter
    }

    private fun initListerer() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                searchText = s.toString()
                if (s.toString().isNotEmpty())
                    initLocalSearch()
                else {
                    items = cityList
                    listAdapter.notifyDataSetChanged()
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        onActionListener = object : OnActionListener<ListModel> {
            override fun notify(model: ListModel, position: Int) {
                model.isSelected = !model.isSelected!!
                var stringBuilder: StringBuilder = java.lang.StringBuilder()
                for (newModel in items) {
                    if (newModel.isSelected == true)
                        stringBuilder.append(newModel.name + " ,")
                }
                binding.tvNames.text = stringBuilder

                val sortedList = items.sortedWith(compareBy({ it.isSelected }, { it.isSelected }))

                items.clear()

                items.addAll(sortedList)
                listAdapter.notifyDataSetChanged()

                var isAllSelected: Boolean = true

                for (new in items) {
                    if (new.isSelected == false) {
                        isAllSelected = false
                        break
                    }
                }

                if (isAllSelected) {
                    binding.tvNames.text = "All Selected"
                } else {
                    var stringBuilder: StringBuilder = java.lang.StringBuilder()
                    for (newModel in items) {
                        if (newModel.isSelected == true)
                            stringBuilder.append(newModel.name + " ,")
                    }
                    binding.tvNames.text = stringBuilder
                }
            }
        }
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                for (model in items) {
                    model.isSelected = true
                }
                binding.tvNames.text = "All Selected"
                listAdapter.notifyDataSetChanged()
            } else {
                items.clear()
                for (i in 1..100) {
                    var model: ListModel = ListModel()
                    model.name = "City $i"
                    model.id = i
                    model.isSelected = false
                    items.add(model)
                }
                binding.tvNames.text = ""
                listAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun initLocalSearch() {
        newCityList.clear()
        for (model in items) {
            if (searchText.equals(model.name,true)) {
                newCityList.add(model)
                break
            }
        }
        items=newCityList
        listAdapter.notifyDataSetChanged()
    }

}