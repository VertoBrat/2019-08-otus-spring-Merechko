import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {GenreListPageComponent} from './system/genre-page/genre-list-page/genre-list-page.component';
import {AuthorListPageComponent} from './system/author-page/author-list-page/author-list-page.component';
import {MainPageComponent} from './system/main-page/main-page.component';
import {CustomModule} from './custom.module';
import {HttpClientModule} from '@angular/common/http';
import {AuthorService} from './system/shared/services/author.service';
import {GenreService} from './system/shared/services/genre.service';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    AuthorListPageComponent,
    GenreListPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CustomModule
  ],
  providers: [
    AuthorService, GenreService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
