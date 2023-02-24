import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Restaurant, Comment } from './models';

@Injectable()
export class RestaurantService {
  constructor(private http: HttpClient) {}

  // TODO Task 2
  // Use the following method to get a list of cuisines
  // You can add any parameters (if any) and the return type
  // DO NOT CHNAGE THE METHOD'S NAME
  public getCuisineList(): Promise<string[]> {
    // Implememntation in here
    return lastValueFrom(this.http.get<string[]>('/api/cuisines'));
  }

  // 	// TODO Task 3
  // 	// Use the following method to get a list of restaurants by cuisine
  // 	// You can add any parameters (if any) and the return type
  // 	// DO NOT CHNAGE THE METHOD'S NAME
  // 	public getRestaurantsByCuisine(???) {
  // 		// Implememntation in here
  public getRestaurantByCuisine(cuisine: string): Promise<string[]> {
    // Implememntation in here
    return lastValueFrom(
      this.http.get<string[]>(`/api/${cuisine}/restaurants`)
    );
  }

  // TODO Task 4
  // Use this method to find a specific restaurant
  // You can add any parameters (if any)
  // DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
  public getRestaurantByName(restaurantName: string): Promise<Restaurant> {
    // Implememntation in here
    return lastValueFrom(
      this.http.get<Restaurant>(`/api/restaurant/${restaurantName}`)
    );
  }

  // TODO Task 5
  // Use this method to submit a comment
  // DO NOT CHANGE THE METHOD'S NAME OR SIGNATURE
  public postComment(comment: Comment): Promise<any> {
    // Implememntation in here
    
    const body = {
      name: comment.name,
      rating: comment.rating,
      restaurant_id: comment.restaurantId,
      text: comment.text,
    };

    return lastValueFrom(this.http.post('/api/comments', body));
  }
}
