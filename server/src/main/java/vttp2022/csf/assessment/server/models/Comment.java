package vttp2022.csf.assessment.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// Do not modify this class
public class Comment {
	private String name;
	private int rating;
	private String restaurantId;
	private String text;

	public JsonObject toJson() {

		return Json.createObjectBuilder()
				.add("name", getName())
				.add("rating", getRating())
				.add("restaurant_id", getRestaurantId())
				.add("text", getText())
				.build();
	}

	public static final Comment createComment(JsonObject object) {
		Comment comment = new Comment();
		comment.setName(object.getString("name"));
		comment.setRating(object.getInt("rating"));
		comment.setRestaurantId(object.getString("restaurant_id"));
		comment.setText(object.getString("text"));
		return comment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantId() {
		return this.restaurantId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}
}
