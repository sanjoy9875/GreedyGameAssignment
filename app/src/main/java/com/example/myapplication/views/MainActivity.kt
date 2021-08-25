package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ItemsAdapter
import com.example.myapplication.adapter.OnItemClick
import com.example.myapplication.data.model.ArticlesModel
import com.example.myapplication.data.model.ResponseEntity
import com.example.myapplication.viewmodel.MyViewModel
import com.example.myapplication.viewmodel.MyViewmodelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var viewModel: MyViewModel
    private lateinit var adapter: ItemsAdapter
    private var entityList = mutableListOf<ArticlesModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.VISIBLE

        /**
         * pass a empty-list to the adapter
         **/
        adapter = ItemsAdapter(entityList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        /**
         * creating a view-model object to access the methods of view-model
         **/
        val appObj = application as MyApplication
        val repository = appObj.repository
        val viewModelFactory = MyViewmodelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)

        /**calling getEntity() & observing the live data here to get the data from Room-Database
         * after getting the data I stop shimmer-effect here
         * then I add the data to our list & called notifyDataSetChanged()
         **/
        viewModel.getResponse().observe(this, Observer {
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            entityList.clear()
            entityList.addAll(it.articles as List<ArticlesModel>)
            adapter.notifyDataSetChanged()

        })
    }


    /**
     * passing data from MainActivity to ArticleDetailsActivity
     */
    override fun onEntityItemClicked(position: Int) {

        val intent = Intent(this, ArticleDetails::class.java)

        val image = entityList[position].urlToImage
        val date = entityList[position].publishedAt
        val title = entityList[position].title
        val author = entityList[position].author
        val source = entityList[position].source?.name
        val content = entityList[position].content

        intent.putExtra("image", image)
        intent.putExtra("date", date)
        intent.putExtra("title", title)
        intent.putExtra("author", author)
        intent.putExtra("source", source)
        intent.putExtra("content", content)

        startActivity(intent)
    }

    override fun onSaveClicked(position: Int) {

        var responseEntity = ResponseEntity()
        responseEntity.urlToImage = entityList[position].urlToImage
        responseEntity.author = entityList[position].author
        responseEntity.content = entityList[position].content
        responseEntity.publishedAt = entityList[position].publishedAt
        responseEntity.source = entityList[position].source?.name
        responseEntity.title = entityList[position].title

        viewModel.addItem(responseEntity)

        Toast.makeText(this,"item saved",Toast.LENGTH_SHORT).show()
    }
}