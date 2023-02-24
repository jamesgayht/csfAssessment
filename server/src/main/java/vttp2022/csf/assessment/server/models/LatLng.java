package vttp2022.csf.assessment.server.models;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// Do not modify this class
public class LatLng {
	private float latitude;
	private float longitude;

	public JsonObject toJson() {

		return Json.createObjectBuilder()
				.add("latitude", getLatitude())
				.add("longitude", getLongitude())
				.build();
	}

	public static LatLng createCoords(List<Double> coordDoc) {
        LatLng latLng = new LatLng(); 

		double latD = (coordDoc.get(0));
		latLng.setLatitude((float)latD);
		System.out.println("latD >>> " + latD);

		double lngD = (coordDoc.get(1));
		latLng.setLongitude((float)lngD);
		System.out.println("lngD >>> " + lngD);

		return latLng;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLatitude() {
		return this.latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLongitude() {
		return this.longitude;
	}
}
