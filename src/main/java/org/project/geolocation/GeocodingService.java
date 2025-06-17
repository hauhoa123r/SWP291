package org.project.geolocation;

import org.json.JSONArray;
import org.json.JSONObject;
import org.project.model.dai.GeolocationDAI;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class GeocodingService {

    private GeolocationDAI getGeoLocation(String address){
        GeolocationDAI geolocationDTO = new GeolocationDAI();
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String urlString = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject location = jsonArray.getJSONObject(0);
            geolocationDTO.setLng(location.getDouble("lat"));
            geolocationDTO.setLat(location.getDouble("lon"));
        } catch (Exception e) {
            geolocationDTO.setAddress("Location not found: " + address);
        }
        return geolocationDTO;
    }
}
