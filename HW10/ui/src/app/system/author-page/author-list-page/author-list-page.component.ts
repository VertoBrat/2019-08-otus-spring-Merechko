import { Component, OnInit } from '@angular/core';
import {AuthorService} from '../../shared/services/author.service';
import {Author} from '../../shared/models/author.model';

@Component({
  selector: 'app-author-list-page',
  templateUrl: './author-list-page.component.html',
  styleUrls: ['./author-list-page.component.css']
})
export class AuthorListPageComponent implements OnInit {

  constructor(private service: AuthorService) { }

  authors: Author[] = [];
  isLoaded = false;

  ngOnInit() {
    this.getAll();
  }

  getAll() {
    this.service.getAllAuthors()
      .subscribe(a => {
        this.authors = a;
        this.isLoaded = true;
      });
  }

}
