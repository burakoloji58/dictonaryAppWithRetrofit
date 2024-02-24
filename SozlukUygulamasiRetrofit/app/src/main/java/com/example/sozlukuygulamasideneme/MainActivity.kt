package com.example.sozlukuygulamasideneme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.telecom.Call
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sozlukuygulamasideneme.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener{
    private lateinit var ulas : ActivityMainBinding
    private lateinit var kelimelistesi: ArrayList<Kelimeler>
    private lateinit var adapter : KelimelerAdapter

    private lateinit var kdi : KelimelerDaoInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        ulas = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(ulas.root)

        ulas.toolbar.title="Sözlük Uygulaması"
        setSupportActionBar(ulas.toolbar)

        ulas.rv.setHasFixedSize(true)
        ulas.rv.layoutManager = LinearLayoutManager(this)

        kdi = ApiUtils.getKelimelerDaoInterface()

        tum_kelimeler()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_arama,menu)

        val item = menu.findItem(R.id.action_ara)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        kelime_ara(query)
        Log.e("aranan yer",query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        kelime_ara(newText)
        return true
    }

    fun tum_kelimeler(){

        kdi.tumKelimeler().enqueue(object : Callback<KelimelerCevap>{

            override fun onResponse(call: Call<KelimelerCevap>?, response: Response<KelimelerCevap>?) {

                if(response != null){

                    val liste = response.body().kelimeler

                    adapter = KelimelerAdapter(this@MainActivity,liste)

                    ulas.rv.adapter = adapter
                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })
    }


    fun kelime_ara(aramaKelime : String){

        kdi.kelimeAra(aramaKelime).enqueue(object : Callback<KelimelerCevap>{

            override fun onResponse(call: Call<KelimelerCevap>?, response: Response<KelimelerCevap>?) {

                if(response != null){

                    val liste = response.body().kelimeler

                    adapter = KelimelerAdapter(this@MainActivity,liste)

                    ulas.rv.adapter = adapter
                }
            }

            override fun onFailure(call: Call<KelimelerCevap>?, t: Throwable?) {

            }
        })
    }

    
}