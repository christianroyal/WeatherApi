package com.example.weatherapi.retrofit;


import com.example.weatherapi.Model.CurrentWeatherResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<CurrentWeatherResponse> getWeatherByLatLng(@Query("lat") String lat, @Query("lon") String lng, @Query("appid") String appid, @Query("units") String unit);
}
