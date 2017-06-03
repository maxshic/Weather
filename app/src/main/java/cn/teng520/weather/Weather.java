package cn.teng520.weather;

import java.util.Arrays;

/**
 * Created by Lenovo on 2017/5/31.
 */

public class Weather {
    private String city;
    private String wendu;
    private Forecast[] forecasts;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public Forecast[] getForecasts() {
        return forecasts;
    }

    public void setForecasts(Forecast[] forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", wendu='" + wendu + '\'' +
                ", forecasts=" + Arrays.toString(forecasts) +
                '}';
    }
}
