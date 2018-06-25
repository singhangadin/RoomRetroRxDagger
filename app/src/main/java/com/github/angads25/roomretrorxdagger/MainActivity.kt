package com.github.angads25.roomretrorxdagger

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.angads25.roomretrorxdagger.adapters.PropertyListAdapter
import com.github.angads25.roomretrorxdagger.architecture.contract.PropertyContract
import com.github.angads25.roomretrorxdagger.architecture.presenter.MainActivityPresenter
import com.github.angads25.roomretrorxdagger.dagger.components.DaggerMainActivityComponent
import com.github.angads25.roomretrorxdagger.dagger.components.MainActivityComponent
import com.github.angads25.roomretrorxdagger.dagger.modules.MainActivityModule
import com.github.angads25.roomretrorxdagger.retrofit.model.PropertyListing
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), PropertyContract.PropertyView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mainActivityComponent: MainActivityComponent

    private lateinit var propertyData: ArrayList<PropertyListing>
    private lateinit var propertyListAdapter: PropertyListAdapter

    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        propertyData = ArrayList()

        val applicationComponent = DemoApplication.get(this@MainActivity)
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .mainActivityModule(MainActivityModule(this@MainActivity))
                .build()

        mainActivityComponent.injectActivity(this@MainActivity)

        propertyListAdapter = PropertyListAdapter(this@MainActivity, propertyData)
        list_property.adapter = propertyListAdapter
        list_property.isNestedScrollingEnabled = false

        mainActivityPresenter.loadData()

        layout_refresh.setOnRefreshListener(this@MainActivity)
    }

    override fun showProgress() {
        layout_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        if(layout_progress.visibility == View.VISIBLE) {
            layout_progress.visibility = View.GONE
        }
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
    }

    override fun showData(propertyListing: List<PropertyListing>) {
        val size = propertyData.size
        this.propertyData.clear()
        propertyListAdapter.notifyItemRangeRemoved(0, size)
        this.propertyData.addAll(propertyListing)
        propertyListAdapter.notifyItemRangeInserted(0, this.propertyData.size)
    }

    override fun onRefresh() {
        layout_refresh.isRefreshing = false
        mainActivityPresenter.loadData()
    }

    override fun onDestroy() {
        mainActivityPresenter.dumpData()
        super.onDestroy()
    }
}
