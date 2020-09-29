package com.example.a8apigit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.a8apigit.model.Data
import com.example.a8apigit.model.Reqres
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val dataList:MutableList<Data> = mutableListOf()
    private lateinit var myadapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        myadapter = MyAdapter(dataList)
        my_recycler_view.layoutManager= LinearLayoutManager(this)
        my_recycler_view.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        my_recycler_view.adapter = myadapter
        AndroidNetworking.initialize(this)

        AndroidNetworking.get("https://reqres.in/api/users?page=2")
            .build()
            .getAsObject(Reqres::class.java, object  : ParsedRequestListener<Reqres> {
                override fun onResponse(response: Reqres) {
                    dataList.addAll(response.data)
                    myadapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {
                    TODO("Not yet implemented")
                }

            })
    }
}