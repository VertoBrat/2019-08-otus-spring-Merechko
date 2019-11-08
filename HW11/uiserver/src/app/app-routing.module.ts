import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainPageComponent} from './system/main-page/main-page.component';
import {AuthorListPageComponent} from './system/author-page/author-list-page/author-list-page.component';
import {GenreListPageComponent} from './system/genre-page/genre-list-page/genre-list-page.component';


const routes: Routes = [
  {path: '', component: MainPageComponent},
  {path: 'books', loadChildren: './system/system.module#SystemModule'},
  {path: 'authors', component: AuthorListPageComponent},
  {path: 'genres', component: GenreListPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
