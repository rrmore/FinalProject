package com.example.futzm.finalproject;

/**
 * Created by futzm on 12/8/2017.
 */
import com.example.futzm.finalproject.POJO.Example;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
public interface RetrofitMaps {
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDN7RJFmImYAca96elyZlE5s_fhX-MMuhk")
    Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}