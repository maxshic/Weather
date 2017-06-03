package cn.teng520.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Lenovo on 2017/5/31.
 */

public class WeatherLoader {
    public static final String BASE_URL =
            "http://wthrcdn.etouch.cn/weather_mini?city=CITY_NAME";

    public Weather load(String cityId) throws IOException, JSONException {
        //生成url
        String realURL = BASE_URL.replace("CITY_NAME", cityId);
        System.out.println(realURL);    /////////////////////////////
        //连接
        URLConnection c = new URL(realURL).openConnection();
        //输入流
        InputStream is = c.getInputStream();
        //转换为文本
        String jsonText = new TextReader().readText(is, "UTF-8");
        System.out.println(jsonText);   //////////////////////////////
        //解析
        Weather weather = parse(jsonText);
        //返回
        return weather;
    }

    private Weather parse(String jsonText) throws JSONException {
        Weather w = new Weather();
        JSONObject o = new JSONObject(jsonText);
        JSONObject data = o.getJSONObject("data");
        w.setCity(data.getString("city"));
        w.setWendu(data.getString("wendu"));
        JSONArray a = data.getJSONArray("forecast");
        w.setForecasts(parse(a));
        return w;
    }

    private Forecast[] parse(JSONArray a) throws JSONException {
        int n = a.length();
        Forecast[] forecasts = new Forecast[n];
        for (int i = 0; i < n; i++) {
            forecasts[i] = parseForecast(a.getJSONObject(i));
        }
        return forecasts;
    }

    private Forecast parseForecast(JSONObject o) throws JSONException{
        Forecast f = new Forecast();
        f.setDate(o.getString("date"));
        f.setHigh(o.getString("high"));
        f.setLow(o.getString("low"));
        f.setType(o.getString("type"));
        f.setFengli(o.getString("fengli"));
        f.setFengxiang(o.getString("fengxiang"));
        return f;
    }
}
