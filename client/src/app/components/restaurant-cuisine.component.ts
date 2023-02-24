import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-cuisine',
  templateUrl: './restaurant-cuisine.component.html',
  styleUrls: ['./restaurant-cuisine.component.css']
})
export class RestaurantCuisineComponent implements OnInit, OnDestroy {

  routeSub$!: Subscription;
  cuisine!: string
  restaurants: string[] = []

  constructor(private activatedRoute: ActivatedRoute, private restaurantSvc: RestaurantService) {}

  ngOnInit(): void {

    this.routeSub$ = this.activatedRoute.params.subscribe((params) => {
      this.cuisine = params['cuisine'];
      console.info("cuisine >>> ", this.cuisine)
    })

    this.restaurantSvc.getRestaurantByCuisine(this.cuisine)
      .then((res) => {
        this.restaurants = res;
        console.info("restaurants >>> ", this.restaurants)
      })
      .catch((err) => {
        console.log(err);
      });

  }

  ngOnDestroy(): void {
    this.routeSub$.unsubscribe();
  }
	
	// TODO Task 3
	// For View 2



}
