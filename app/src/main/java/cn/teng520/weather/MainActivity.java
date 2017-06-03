package cn.teng520.weather;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView cityTextView;
    private ListView forecastListView;
    private String currentCity="常熟";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityTextView = (TextView) findViewById(R.id.text_city);
        forecastListView = (ListView) findViewById(R.id.list_forecast);
        //读取天气
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            currentCity=intent.getStringExtra("cityName");
        }
        loadWeather(currentCity);
    }

    private void loadWeather(final String cityName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //网络访问
                    Weather weather = new WeatherLoader().load(cityName);
                    //显示
                    showWeatherOnUiThread(weather);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showWeatherOnUiThread(final Weather weather) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在控件显示
                showWeather(weather);
            }
        });
    }

    private void showWeather(Weather weather) {
        cityTextView.setText(weather.getCity());
        ArrayAdapter<Forecast> adapter = new ArrayAdapter<Forecast>(
                this,
                android.R.layout.simple_list_item_1,
                weather.getForecasts());
        forecastListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.choose_city:
                Intent intent = new Intent(this,ChooseActivity.class);
                //intent.putExtra();
                startActivity(intent);
                return true;

        }
        return false;
    }
}
