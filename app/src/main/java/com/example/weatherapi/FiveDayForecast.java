package com.example.weatherapi;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.weatherapi.Model.CurrentWeatherResponse;
import com.example.weatherapi.common.Common;
import com.example.weatherapi.retrofit.IOpenWeatherMap;
import com.example.weatherapi.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class FiveDayForecast extends Fragment {

    ImageView img_weather;
    TextView txt_city_name,txt_wind,txt_humidity,txt_pressure,txt_temperature,txt_date_time,txt_geo_coords,txt_description;
    LinearLayout weather_panel;
    ProgressBar loading;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;


    static FiveDayForecast instance;

    public static FiveDayForecast getInstance(){
        if (instance == null)
            instance= new FiveDayForecast();
            return instance;

    }



    public FiveDayForecast() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView= inflater.inflate(R.layout.fragment_fiveday_weather, container, false);

        img_weather= (ImageView)itemView.findViewById(R.id.image_weather);
        txt_city_name=(TextView)itemView.findViewById(R.id.txt_city_name);
        txt_date_time=(TextView)itemView.findViewById(R.id.txt_date_time);
        txt_temperature=(TextView)itemView.findViewById(R.id.txt_temperature);
        txt_humidity=(TextView)itemView.findViewById(R.id.txt_humidity);
        txt_wind=(TextView)itemView.findViewById(R.id.txt_wind);
        txt_pressure=(TextView)itemView.findViewById(R.id.txt_pressure);
        txt_geo_coords=(TextView)itemView.findViewById(R.id.txt_geo_coords);
        txt_description=(TextView)itemView.findViewById(R.id.txt_description);

        weather_panel= (LinearLayout)itemView.findViewById(R.id.weather_panel);
        loading= (ProgressBar)itemView.findViewById(R.id.loading);

        getWeatherInformation();



        return itemView;
    }
    private void getWeatherInformation(){
        compositeDisposable.add(mService.getWeatherByLatLng(String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CurrentWeatherResponse>() {
            @Override
            public void accept(CurrentWeatherResponse currentWeatherResponse) throws Exception {
                Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                        .append(currentWeatherResponse.getWeather().get(0).getIcon())
                        .append(".png").toString()).into(img_weather);

                txt_city_name.setText(currentWeatherResponse.getName());
                txt_description.setText(new StringBuilder("Weather in")
                        .append(currentWeatherResponse.getName()).toString());
                txt_temperature.setText(new StringBuilder(String.valueOf(currentWeatherResponse.getMain().getTemp())).append("C").toString());
                txt_date_time.setText(Common.convertUnixtoDate(currentWeatherResponse.getDt()));
                txt_humidity.setText(new StringBuilder(String.valueOf(currentWeatherResponse.getMain().getHumidity())).append("%").toString());
                txt_pressure.setText(new StringBuilder(String.valueOf(currentWeatherResponse.getMain().getPressure())).append("kga").toString());
                txt_geo_coords.setText(new StringBuilder("[").append(currentWeatherResponse.getCoord().toString()).append("]"));

                //display panel
                weather_panel.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }

            }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })

        );
    }

}
    













