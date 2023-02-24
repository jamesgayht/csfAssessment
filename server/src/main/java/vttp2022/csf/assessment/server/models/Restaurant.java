package vttp2022.csf.assessment.server.models;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

// Do not modify this class
public class Restaurant {

	private String restaurantId;
	private String name;
	private String cuisine;
	private String address;
	private LatLng coordinates;
	private String mapUrl;

	public JsonObject toJson() {

		return Json.createObjectBuilder()
				.add("restaurantId", getRestaurantId())
				.add("name", getName())
				.add("cuisine", getCuisine())
				.add("address", getAddress())
				.add("coordinates", getCoordinates().toJson())
				.build();
	}

	public static final Restaurant createRestaurant(Document d) {
		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(d.getString("restaurant_id"));
		restaurant.setName(d.getString("name"));
		restaurant.setCuisine(d.getString("cuisine"));
		restaurant.setAddress(d.getString("address"));

		List<Double> coordDoc = (List<Double>) d.get("coordinates");
		
		restaurant.setCoordinates(LatLng.createCoords(coordDoc));

		return restaurant;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantId() {
		return this.restaurantId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getCuisine() {
		return this.cuisine;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}

	public LatLng getCoordinates() {
		return this.coordinates;
	}

	public void setMapURL(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public String getMapURL() {
		return this.mapUrl;
	}
}
