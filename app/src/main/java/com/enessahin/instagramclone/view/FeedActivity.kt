package com.enessahin.instagramclone.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.enessahin.instagramclone.R
import com.enessahin.instagramclone.adapter.InstaAdapter
import com.enessahin.instagramclone.databinding.ActivityFeedBinding
import com.enessahin.instagramclone.model.InstaPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList : ArrayList<InstaPost>
    private lateinit var instaAdapter : InstaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        postArrayList = ArrayList<InstaPost>()
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        instaAdapter = InstaAdapter(postArrayList)
        binding.recyclerView.adapter = instaAdapter
    }

    private fun getData() {
        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this@FeedActivity, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {
                        val documents = value.documents

                        postArrayList.clear()
                        for (document in documents) {
                            // casting
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val instaPost = InstaPost(userEmail, comment, downloadUrl)
                            postArrayList.add(instaPost)
                        }
                        instaAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.signOut) {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (item.itemId == R.id.addPost) {
            val intent = Intent(this, UploadActivity ::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}