package vttp2022.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class RestaurantsRESTController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping(path = "/api/cuisines")
    public ResponseEntity<String> getCuisines() {

        System.out.println("getting cuisines");

        List<String> cuisines = restaurantService.getCuisines();

        System.out.println("cuisines >>> " + cuisines);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (String c : cuisines)
            arrayBuilder.add(c);

        JsonArray results = arrayBuilder.build();
        System.out.println("results >>> " + results.toString());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(results.toString());

    }

    @GetMapping(path = "/api/{cuisine}/restaurants")
    public ResponseEntity<String> getRestaurantsByCuisine(@PathVariable String cuisine) {

        System.out.println("getting restaurants");

        List<String> restaurants = restaurantService.getRestaurantsByCuisine(cuisine);

        System.out.println("cuisines >>> " + restaurants);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (String r : restaurants)
            arrayBuilder.add(r);

        JsonArray results = arrayBuilder.build();
        System.out.println("results >>> " + results.toString());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(results.toString());

    }

    @GetMapping(path = "/api/restaurant/{restaurantName}")
    public ResponseEntity<String> getRestaurantByName(@PathVariable String restaurantName) {

        System.out.println("getting restaurant by name");

        Optional<Restaurant> opt = restaurantService.getRestaurant(restaurantName);

        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Unable to find restaurant.\n");
        }

        Restaurant restaurant = opt.get();
        System.out.println(">>>> Restaurant: " + restaurant.toString());

        float lat = restaurant.getCoordinates().getLatitude();
        float lng = restaurant.getCoordinates().getLatitude();

        JsonObject results = restaurant.toJson();
        return ResponseEntity.ok(results.toString());

    }

    @PostMapping(path = "/api/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postComments(@RequestBody String body) {

        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject json = reader.readObject();

        String userName = json.getString("name");
        Integer rating = json.getInt("rating");
        String text = json.getString("text");
        System.out.println(">>> text: %s, >>> name: %s, >>>rating: %d".formatted(text, userName, rating));

        Comment comment = Comment.createComment(json);

        restaurantService.addComment(comment);

        JsonObject resp = Json
                .createObjectBuilder()
                .add("message", "Comment posted")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp.toString());
    }
}
