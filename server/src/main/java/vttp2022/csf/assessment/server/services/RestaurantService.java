package vttp2022.csf.assessment.server.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
// import vttp2022.csf.assessment.server.repositories.MapCache;
import vttp2022.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepo;

    // @Autowired
    // private MapCache mapCache;

    @Autowired
    private MongoTemplate mongoTemplate;

    // TODO Task 2
    // Use the following method to get a list of cuisines
    // You can add any parameters (if any) and the return type
    // DO NOT CHNAGE THE METHOD'S NAME
    public List<String> getCuisines() {
        // Implmementation in here
        List<String> cuisines = restaurantRepo.getCuisines();
        List<String> cuisineReplaced = new LinkedList<>();

        // convert / to _
        for (String c : cuisines) {
            c = c.replace('/', '_');
            System.out.println("replacing c >>> " + c);
            cuisineReplaced.add(c);
        }

        return cuisineReplaced;
    }

    // TODO Task 3
    // Use the following method to get a list of restaurants by cuisine
    // You can add any parameters (if any) and the return type
    // DO NOT CHNAGE THE METHOD'S NAME
    public List<String> getRestaurantsByCuisine(String cuisine) {
    // Implmementation in here
        
        cuisine = cuisine.replace('_', '/');
        System.out.println("cusine replacing _ with / >>> " + cuisine);

        List<String> restaurants = restaurantRepo.getRestaurantsByCuisine(cuisine);

        return restaurants; 
    }

    // TODO Task 4
    // Use this method to find a specific restaurant
    // You can add any parameters (if any)
    // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
    public Optional<Restaurant> getRestaurant(String restaurantName) {
    // Implmementation in here

        Optional<Restaurant> restaurant = restaurantRepo.getRestaurant(restaurantName); 

        return restaurant; 
    }

    // TODO Task 5
    // Use this method to insert a comment into the restaurant database
    // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
    public void addComment(Comment comment) {
    // Implmementation in here
        System.out.println("adding comment to repo");
        restaurantRepo.addComment(comment);
        System.out.println("comment added successfully to repo");
    }
    //
    // You may add other methods to this class
}
