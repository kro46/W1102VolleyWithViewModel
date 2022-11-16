package kr.ac.kumoh.s20170187com.example.w1102volleywithviewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SongViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val QUEUE_TAG = "SongVolleyRequest"
    }
    private val list = ArrayList<String>()
    val songs = MutableLiveData<ArrayList<String>>()
    private var queue = Volley.newRequestQueue(getApplication())

    init {
        songs.value = list
    }

    fun requestSong() {
        val url ="https://expresssongdb-jrezb.run.goorm.io/song"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                list.clear()
                parseJson(it)
                songs.value = list
               // Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()
            },
            {
               // Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items:JSONArray) {
        for (i in 0 until items.length()) {
            val item:JSONObject = items[i] as JSONObject
            val id = item.getInt("id")
            val title = item.getString("title")
            val singer = item.getString("singer")

            list.add("$title ($singer)")
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}