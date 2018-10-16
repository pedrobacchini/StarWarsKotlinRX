package pedrobacchini.github.com.starwarskotlinrx.model.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pedrobacchini.github.com.starwarskotlinrx.model.Character
import pedrobacchini.github.com.starwarskotlinrx.model.Movie
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class StartWarsApi {

    val service : StarWarsApiDef

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        service = retrofit.create<StarWarsApiDef>(StarWarsApiDef::class.java)
    }

    fun loadMovies() : Observable<Movie> {
        return service.listMoves()
                .flatMap { filmeResult -> Observable.from(filmeResult.results) }
                .flatMap { film -> Observable.just(Movie(film.title, film.episodeId, ArrayList<Character>())) }
    }

}
