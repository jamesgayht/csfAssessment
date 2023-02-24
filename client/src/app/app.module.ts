import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { AppComponent } from './app.component';
import { CuisineListComponent } from './components/cuisine-list.component';
import { RestaurantCuisineComponent } from './components/restaurant-cuisine.component';
import { RestaurantDetailsComponent } from './components/restaurant-details.component';
import { RouterModule, Routes } from '@angular/router';
import { RestaurantService } from './restaurant-service';

const appRoutes: Routes = [
  { path: '', component: CuisineListComponent },
  { path: ':cuisine/restaurants', component: RestaurantCuisineComponent },
  { path: ':restaurant/details', component: RestaurantDetailsComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    CuisineListComponent,
    RestaurantCuisineComponent,
    RestaurantDetailsComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [RestaurantService],
  bootstrap: [AppComponent]
})
export class AppModule { }
