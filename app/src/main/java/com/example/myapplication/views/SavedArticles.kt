package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ItemsAdapter
import com.example.myapplication.adapter.OnItemClick
import com.example.myapplication.adapter.SavedItemsAdapter
import com.example.myapplication.data.model.ArticlesModel
import com.example.myapplication.data.model.ResponseEntity
import com.example.myapplication.viewmodel.MyViewModel
import com.example.myapplication.viewmodel.MyViewmodelFactory
import kotlinx.android.synthetic.main.activity_article_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_saved_articles.*

class SavedArticles : AppCompatActivity() ,OnItemClick{

    private lateinit var viewModel: MyViewModel
    private lateinit var adapter: SavedItemsAdapter
    private var entityList = mutableListOf<ResponseEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_articles)

        /**
         * navigate to the mainActivity if backArrow pressed
         */
        ivArrowBack.setOnClickListener {
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        /**
         * pass a empty-list to the adapter
         **/
        adapter = SavedItemsAdapter(entityList, this)
        savedRecyclerView.layoutManager = LinearLayoutManager(this)
        savedRecyclerView.adapter = adapter

        /**
         * creating a view-model object to access the methods of view-model
         **/
        val appObj = application as MyApplication
        val repository = appObj.repository
        val viewModelFactory = MyViewmodelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)

        /**
         * calling getEntity() & observing the live data here to get the data from RoomDatabase
         **/
        viewModel.getEntity().observe(this, Observer {

            savedRecyclerView.visibility = View.VISIBLE

            entityList.clear()
            entityList.addAll(it)
            adapter.notifyDataSetChanged()

        })

        /**
         * adding a textChangedListener to the EditText for search our itemList
         * */
        etSavedItemSearch.addTextChangedListener(object : TextWatcher {
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
        var tempList = mutableListOf<ResponseEntity>()
        entityList.forEach {
            if (it.title?.toLowerCase()?.contains(text)!!){
                tempList.add(it)
            }
        }
        adapter.filteredList(tempList)
    }

    /**
     * onClick of any item it will navigate to ArticleDetails page
     */
    override fun onEntityItemClicked(position: Int) {

        val intent = Intent(this, ArticleDetails::class.java)

        val data = "flag"
        val image = entityList[position].urlToImage
        val date = entityList[position].publishedAt
        val title = entityList[position].title
        val author = entityList[position].author
        val source = entityList[position].source
        val content = entityList[position].content

        intent.putExtra("data",data)
        intent.putExtra("image", image)
        intent.putExtra("date", date)
        intent.putExtra("title", title)
        intent.putExtra("author", author)
        intent.putExtra("source", source)
        intent.putExtra("content", content)

        startActivity(intent)
    }

    override fun onSaveClicked(position: Int) {

    }
}