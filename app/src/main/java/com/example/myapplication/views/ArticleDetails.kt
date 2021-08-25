package com.example.myapplication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_article_details.*

class ArticleDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        val image = intent.getStringExtra("image")
        val date = intent.getStringExtra("date")
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val source = intent.getStringExtra("source")
        val content = intent.getStringExtra("content")

        Glide.with(applicationContext)
            .load(image)
            .into(ivArticleImage)

        Glide.with(applicationContext)
            .load(R.drawable.author)
            .into(ivAuthorImage)

        var newDate = ""

        for(i in 0..(date?.length?.minus(1)!!)){

            if (date[i] == 'T'){
                break
            }
            newDate += date[i]
        }

        tvArticleDate.text = newDate
        tvArticleTitle.text = title

        if (author==null){
            tvAuthorName.text = "Unknown"
        }
        else {
            tvAuthorName.text = author
        }

        tvSourceName.text = source

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
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}