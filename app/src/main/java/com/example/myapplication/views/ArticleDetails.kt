package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.ResponseEntity
import com.example.myapplication.viewmodel.MyViewModel
import com.example.myapplication.viewmodel.MyViewmodelFactory
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetails : AppCompatActivity() {

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        /**
         * creating a view-model object to access the methods of view-model
         **/
        val appObj = application as MyApplication
        val repository = appObj.repository
        val viewModelFactory = MyViewmodelFactory(repository)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MyViewModel::class.java)

        /**
         * getting all the data via intent
         */
        val data = intent.getStringExtra("data")
        val image = intent.getStringExtra("image")
        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val source = intent.getStringExtra("source")
        val content = intent.getStringExtra("content")

        /**
         * setting the article image
         */
        Glide.with(applicationContext)
            .load(image)
            .into(ivArticleImage)

        /**
         * setting the author image
         */
        Glide.with(applicationContext)
            .load(R.drawable.author)
            .into(ivAuthorImage)


        /**
         * removing the unnecessary parameter from date string
         */
        var newDate = ""

        for(i in 0..(date?.length?.minus(1)!!)){

            if (date[i] == 'T'){
                break
            }
            newDate += date[i]
        }

        tvArticleDate.text = newDate

        tvArticleTitle.text = title

        /**
         * setting author name
         */
        if (author==null){
            tvAuthorName.text = "Unknown"
        }
        else {
            tvAuthorName.text = author
        }

        tvSourceName.text = source

        /**
         * setting the content
         * and removing the unnecessary parameter from content
         */
        if (content==null){
            tvErrorText.visibility = View.VISIBLE
            tvArticleContent.visibility = View.GONE
        }
        else{
            var newContent = ""

            for(i in 0..(content?.length?.minus(1)!!)){

                if (content[i] == '['){
                    break
                }
                newContent += content[i]
            }
            tvArticleContent.text = newContent
            tvErrorText.visibility = View.GONE
            tvArticleContent.visibility = View.VISIBLE
        }

        btnArrowBack.setOnClickListener {

            if(data=="flag"){
                val intent = Intent(applicationContext,SavedArticles::class.java)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        /**
         * saving the news after click the save into our database
         */
        btnSave.setOnClickListener {

                var responseEntity = ResponseEntity()
                responseEntity.urlToImage = image
                responseEntity.author = author
                responseEntity.content = content
                responseEntity.publishedAt = date
                responseEntity.source = source
                responseEntity.title = title

                viewModel.addItem(responseEntity)

                Toast.makeText(this,"item saved", Toast.LENGTH_SHORT).show()
        }
    }
}