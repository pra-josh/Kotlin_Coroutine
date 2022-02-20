package com.pra.coroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pra.coroutine.databinding.ActivityMainBinding
import com.pra.coroutine.model.Country
import com.pra.coroutine.viewmodel.ListViewModel


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    private lateinit var mBinding: ActivityMainBinding

    private var countriesList: List<Country> = ArrayList()
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.countriesList.adapter = countriesAdapter

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.countries.observe(this, Observer {
            it?.let {
                mBinding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer {
            mBinding.listError.visibility = if (it == "") View.GONE else View.VISIBLE

        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                mBinding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    mBinding.listError.visibility = View.GONE
                    mBinding.countriesList.visibility = View.GONE
                }
            }
        })
    }


}