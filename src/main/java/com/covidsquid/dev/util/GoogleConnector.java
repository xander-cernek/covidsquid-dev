package com.covidsquid.dev.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.covidsquid.dev.model.ParentLocation;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleConnector {

  private static String URL_STRING = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
  private static String AUTH_KEY = "AIzaSyCI1xtmDg0TwwFlN0GKyH5VCVcQBxTST8I";

  public String buildGoogleMapsRequest(ParentLocation parentLocation, String tag) {
    String result = "";
    try {
      URL url = new URL(urlString(parentLocation, tag));
      log.info("Establishing a connection with url: {}", url.toString());
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      int status = con.getResponseCode();
      log.info("Status from Google: {}", status);
      BufferedReader in = getStreamReader(con, status);
      result = readResultFromBuffer(in);
      con.disconnect();
      log.info("Successfully received result!");
    } catch (Exception e) {
      log.error("Error building connection: {}", e);
    }
    return result;
  }

  private String urlString(ParentLocation parentLocation, String tag) {
    StringBuilder result = new StringBuilder();
    result.append(URL_STRING).append("?")
          .append("key=").append(AUTH_KEY)
          .append("&location=").append(buildLocationString(parentLocation))
          .append("&radius=").append(parentLocation.getRadius())
          .append("&type=").append(tag);
    return result.toString();
  }

  private String buildLocationString(ParentLocation parentLocation) {
    StringBuilder result = new StringBuilder();
    result.append(parentLocation.getLatitude());
    result.append(",");
    result.append(parentLocation.getLongitude());
    return result.toString();
  }

  private BufferedReader getStreamReader(HttpURLConnection con, int status) throws IOException {
    Reader result;
    if (status > 299) {
      result = new InputStreamReader(con.getErrorStream());
    } else {
      result = new InputStreamReader(con.getInputStream());
    }
    return new BufferedReader(result);
  }

  private String readResultFromBuffer(BufferedReader in) throws IOException {
    String inputLine;
    StringBuffer content = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    return content.toString();
  }
}
