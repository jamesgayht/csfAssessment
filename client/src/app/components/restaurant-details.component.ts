import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Comment, Restaurant } from '../models';
import { RestaurantService } from '../restaurant-service';

@Component({
  selector: 'app-restaurant-details',
  templateUrl: './restaurant-details.component.html',
  styleUrls: ['./restaurant-details.component.css']
})
export class RestaurantDetailsComponent implements OnInit, OnDestroy {
	
	// TODO Task 4 and Task 5
	// For View 3

  routeSub$!: Subscription;
  restaurantName!: string
  restaurant!: Restaurant
  form!: FormGroup

  constructor(private activatedRoute: ActivatedRoute, private restaurantSvc: RestaurantService, private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {

    this.form = this.createForm()

    this.routeSub$ = this.activatedRoute.params.subscribe((params) => {
      this.restaurantName = params['restaurant'];
      console.info("restaurant name >>> ", this.restaurantName)
    })

    this.restaurantSvc.getRestaurantByName(this.restaurantName)
      .then((res) => {
        this.restaurant = res;
        console.info("restaurant >>> ", this.restaurant)
      })
      .catch((err) => {
        console.log(err);
      });

  }

  ngOnDestroy(): void {
    this.routeSub$.unsubscribe();
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control('', [Validators.required, Validators.minLength(3)]),
      rating: this.fb.control(0, [Validators.min(1), Validators.max(5)]),
      text: this.fb.control('', [Validators.required]),
    })
  }

  comment = new Comment('', 0, '', '')

  submitForm() {
    const name = this.form.get('name')?.value
    const rating = this.form.get('rating')?.value
    const text = this.form.get('text')?.value

    this.comment = new Comment(name, rating, this.restaurant.restaurantId, text)

    console.info("comment >>> ", this.comment);
    this.restaurantSvc.postComment(this.comment); 
    this.router.navigate(['/'])
  }

}
