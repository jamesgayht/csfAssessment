package vttp2022.csf.assessment.server.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String C_RESTAURANTS = "restaurants";
    public static final String C_COMMENTS = "comments";

    // TODO Task 2
    // Use this method to retrive a list of cuisines from the restaurant collection
    // You can add any parameters (if any) and the return type
    // DO NOT CHNAGE THE METHOD'S NAME
    // Write the Mongo native query above for this method
    //

    /*
     * db.restaurants.distinct(
     * "cuisine"
     * );
     */
    public List<String> getCuisines() {
        // Implmementation in here
        List<String> cuisines = mongoTemplate.findDistinct(new Query(), "cuisine", C_RESTAURANTS, String.class);

        System.out.println("cuisines in repo >>> " + cuisines);

        return cuisines;

    }

    // TODO Task 3
    // Use this method to retrive a all restaurants for a particular cuisine
    // You can add any parameters (if any) and the return type
    // DO NOT CHNAGE THE METHOD'S NAME
    // Write the Mongo native query above for this method
    //

    /*
     * db.restaurants.find({
     * cuisine: {$regex: "asian", $options:"i"}
     * });
     */
    public List<String> getRestaurantsByCuisine(String cuisine) {
        // Implmementation in here
        Criteria c = Criteria.where("cuisine").regex(cuisine, "i");
        Query query = Query.query(c);
        List<Document> results = mongoTemplate.find(query, Document.class, C_RESTAURANTS);

        List<String> restaurants = new LinkedList<>();
        for (Document d : results)
            restaurants.add(d.getString("name"));

        return restaurants;
    }

    // TODO Task 4
    // Use this method to find a specific restaurant
    // You can add any parameters (if any)
    // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
    // Write the Mongo native query above for this method
    //

    /*
     * db.restaurants.aggregate([
     * {
     * $match:{name: "China Grill"}
     * },
     * {
     * $project: {_id: 0, restaurant_id: 1, name: 1, cuisine: 1, coordinates:
     * "$address.coord",
     * address: {$concat: ["$address.building", ", ", "$address.street", ", ",
     * "$address.zipcode", ", ", "$borough"]}}
     * }
     * ])
     */
    public Optional<Restaurant> getRestaurant(String restaurantName) {
        // Implmementation in here

        // $match the restaurant name
        MatchOperation matchName = Aggregation.match(Criteria.where("name").regex(restaurantName, "i"));

        ProjectionOperation selectFields = Aggregation
                .project("restaurant_id", "name", "cuisine")
                .and("address.coord").as("coordinates")
                .and(StringOperators.Concat.valueOf("address.building").concat(", ").concatValueOf("address.street")
                        .concat(", ").concatValueOf("address.zipcode").concat(", ").concatValueOf("borough"))
                .as("address")
                .andExclude("_id");

        Aggregation pipeline = Aggregation.newAggregation(matchName, selectFields);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, C_RESTAURANTS, Document.class);

        Document document = results.iterator().next();

        System.out.println("D >>>> " + document.toString());
        Restaurant restaurant = Restaurant.createRestaurant(document);

        return Optional.of(restaurant);

    }

    // TODO Task 5
    // Use this method to insert a comment into the restaurant database
    // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
    // Write the Mongo native query above for this method
    //

    /*
     * db.comments.insertOne({
     * "name": "test",
     * "rating": 3,
     * "restaurant_id": "123412",
     * "text": "the food was pretty good"
     * })
     */
    public void addComment(Comment comment) {
        // Implmementation in here
        JsonObject toInsert = comment.toJson(); 

        Document commentDoc = Document.parse(toInsert.toString());
        Document inserted = mongoTemplate.insert(commentDoc, C_COMMENTS);
        System.out.println(inserted.toJson());
    }

    // You may add other methods to this class

}
