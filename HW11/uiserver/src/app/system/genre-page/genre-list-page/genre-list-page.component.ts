import { Component, OnInit } from '@angular/core';
import {GenreService} from '../../shared/services/genre.service';
import {Genre} from '../../shared/models/genre.model';

@Component({
  selector: 'app-genre-list-page',
  templateUrl: './genre-list-page.component.html',
  styleUrls: ['./genre-list-page.component.css']
})
export class GenreListPageComponent implements OnInit {

  constructor(private service: GenreService) { }

  genres: Genre[] = [];
  isLoaded = false;

  ngOnInit() {
    this.getAll();
  }

  getAll() {
    this.service.getAllGenres()
      .subscribe(g => {
        this.genres = g;
        this.isLoaded = true;
      });
  }

}
