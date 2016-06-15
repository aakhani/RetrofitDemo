package practice.com.retrofitdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import practice.com.retrofitdemo.R;
import practice.com.retrofitdemo.model.Movie;
import practice.com.retrofitdemo.model.MovieResponse;
import practice.com.retrofitdemo.rest.ApiClient;
import practice.com.retrofitdemo.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "3cf84471eade530bd48b989421ac1331";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this,"Activity Created",Toast.LENGTH_SHORT).show();


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Log.e("Movie WS Called","Movie WS Called");
        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e("Responce recieved","Responce recieved");
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(movies,R.layout.list_item_movie,MainActivity.this))  ;
                Log.d(TAG, "No of movie recieved ==> " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("Responce Failure","Responce Failure");
                Log.d(TAG, t.toString());
            }
        });
    }
}
