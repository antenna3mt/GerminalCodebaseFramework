package com.peruselab.server;

import java.io.*;
import java.net.*;

public class ElasticDemo {
	private final String elasticUrl;

	ElasticDemo() {
		if (System.getenv("GERMINAL_ELASTIC_ADDR") == null) {
			elasticUrl = "http://localhost:9200/";
		} else {
			elasticUrl = "http://" + System.getenv("GERMINAL_ELASTIC_ADDR") + "/";
		}
	}

	public String getHealth() {
		HttpURLConnection con;
		try {
			URL url = new URL(elasticUrl + "_cat/health");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			InputStream response = con.getInputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(response));
			String output = "";
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				output += inputLine;
			in.close();
			return output;
		} catch (Exception e) {
			return "Connection refused";
		}
	}

}
