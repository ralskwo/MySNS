package com.example.myinstagram

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_melon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MelonActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_melon)

        (application as MasterApplication).service.getSongList()
            .enqueue(object : Callback<ArrayList<Song>> {
                override fun onFailure(call: Call<ArrayList<Song>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ArrayList<Song>>,
                    response: Response<ArrayList<Song>>
                ) {
                    if (response.isSuccessful) {
                        val songList = response.body()
                        val adapter = MelonAdpater(
                            songList!!,
                            LayoutInflater.from(this@MelonActivity),
                            Glide.with(this@MelonActivity),
                            this@MelonActivity
                        )

                        song_list.adapter = adapter
                    }
                }
            })

        MyInstagram.setOnClickListener { startActivity(Intent(this,MyInstagramPostListActivity::class.java))}
        Youtube.setOnClickListener { startActivity(Intent(this,MytubeActivity::class.java))}
        Melon.setOnClickListener {startActivity(Intent(this,MelonActivity::class.java))}
    }

    override fun onPause() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onPause()
    }

    inner class MelonAdpater(
        var songList: ArrayList<Song>,
        var inflater: LayoutInflater,
        val glide: RequestManager,
        val activity: Activity

    ) : RecyclerView.Adapter<MelonAdpater.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val thumbnail: ImageView
            val play: ImageView

            init {
                title = itemView.findViewById(R.id.song_title)
                thumbnail = itemView.findViewById(R.id.song_img)
                play = itemView.findViewById(R.id.song_play)

                itemView.setOnClickListener {
                    val position = adapterPosition
                    val path = songList.get(position).song

                    try {
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null
                        mediaPlayer = MediaPlayer.create(
                            this@MelonActivity,
                            Uri.parse(path)
                        )
                        mediaPlayer?.start()
                    } catch (e: Exception){

                    }

                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.song_item_view, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.setText(songList.get(position).title)
            glide.load(songList.get(position).thumbnail).into(holder.thumbnail)
        }

        override fun getItemCount(): Int {
            return songList.size
        }
    }
}