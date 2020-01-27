package com.example.ghibliquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    // Initializing an empty ArrayList to be filled with character names
    var fullCharacterList: MutableList<Character> = mutableListOf<Character>()
    //var names: ArrayList<String> = ArrayList()
    var movieList: MutableList<Movie> = mutableListOf<Movie>()

    fun getMovies(Answer : Character, service : GhibliInteface) {
        val movieWsCallback: Callback<List<Movie>> = object : Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("Info", "Error When the Api was called")
            }

            override fun onResponse(
                call: Call<List<Movie>>,
                response: Response<List<Movie>>
            ) {
                if (response.code() == 200) {
                    val movie = response.body()!!
                    if (movie != null) {
                        for (i in 0..5) {
                            movieList.add(movie[i])
                        }



                        // put info in question
                        val question =
                            "Which one of these characters can be found in " + movieList[1].title
                        question_text.text = question
                    }
                }
            }
        }
        service.listAllMovies().enqueue(movieWsCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // A List to store or objects
        val data: ArrayList<String> = ArrayList()
        // The base URL where the WebService is located
        val baseURL = "https://ghibliapi.herokuapp.com"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: GhibliInteface = retrofit.create(GhibliInteface::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val wsCallback: Callback<List<Character>> = object : Callback<List<Character>> {
            override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                Log.d("Info", "Error When the Api was called")
            }

            override fun onResponse(
                call: Call<List<Character>>,
                response: Response<List<Character>>
            ) {
                if (response.code() == 200) {
                    val people = response.body()!!
                    if (people != null) {

                        for (i in people) {
                            fullCharacterList.add(i)
                            //names.add(characterList[i].name)
                        }
                        var shuffledCharList = fullCharacterList.shuffled()

                        var possibleAnswers : MutableList<Character> = mutableListOf<Character>()
                        for (i in 0..4)
                            possibleAnswers.add(shuffledCharList[i])
                        val correctAnswer = Random.nextInt(0,4)
                        getMovies(possibleAnswers[correctAnswer], service)
                        answers_list.adapter = QuestionAdapter(possibleAnswers, correctAnswer, this@MainActivity)
                    }
                }
            }
        }

        service.listAllCharacters().enqueue(wsCallback)


        // Creates a vertical Layout Manager
        answers_list.layoutManager = LinearLayoutManager(this)


    }
}