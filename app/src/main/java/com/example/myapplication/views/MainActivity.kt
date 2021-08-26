package com.example.myapplication.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.example.myapplication.network_connectivity.ConnectivityLiveData
import com.example.myapplication.viewmodel.MyViewModel
import com.example.myapplication.viewmodel.MyViewmodelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnItemClick {

    private lateinit var viewModel: MyViewModel
    private lateinit var adapter: ItemsAdapter
    private var entityList = mutableListOf<ArticlesModel>()
    private lateinit var connectivityLiveData: ConnectivityLiveData

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.VISIBLE

        connectivityLiveData= ConnectivityLiveData(application)

        /**
         * going to SavedArticlesActivity
         * where all saved news are available
         */
        ivReadLater.setOnClickListener {
            val intent = Intent(this, SavedArticles::class.java)
            startActivity(intent)
        }

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


        /**
         * Observing LiveData if network is Connected then only call API
         * If Network not connected fetch the data from Database
         **/
        connectivityLiveData.observe(this, Observer {isAvailable->

            if(isAvailable) {
                /**calling getResponse() & observing the live data here to get the data from API
                 * after getting the data I am stopping progressBar
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
                else{
                Snackbar.make(recyclerView,
                    "check your internet connection",
                    Snackbar.LENGTH_LONG)
                    .show()

            }
        })


        /**
         * adding a textChangedListener to the EditText for search our itemList
         * */
        etSearchItem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                filter(s.toString().toLowerCase())
            }
        })
    }

    /**
     * This method will filter the searched item & add filtered item into a new list
     * Then this new list will pass to the adapter via filteredList() method
     **/
    fun filter(text : String){
        var tempList = mutableListOf<ArticlesModel>()
        entityList.forEach {
            if (it.title?.toLowerCase()?.contains(text)!!){
                tempList.add(it)
            }
        }
        adapter.filteredList(tempList)
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

    /**
     * saving the news after click the save into our database
     */
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