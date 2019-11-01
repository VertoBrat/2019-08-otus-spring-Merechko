import { Component, OnInit } from '@angular/core';
import {BookService} from '../../shared/services/book.service';
import {Book} from '../../shared/models/book.model';
import {Router} from '@angular/router';

@Component({
  selector: 'app-book-list-page',
  templateUrl: './book-list-page.component.html',
  styleUrls: ['./book-list-page.component.css']
})
export class BookListPageComponent implements OnInit {

  constructor(private service: BookService,
              private router: Router) { }

  books: Array<Book> = [];
  isLoaded = false;
  noBooks = false;

  ngOnInit() {
    this.getListOfBooks();
  }

  getListOfBooks() {
    this.service.getAllBooks()
      .subscribe(b => {
        this.books = b;
        if (b.length === 0) {this.noBooks = true;}
        this.isLoaded = true;
      });
  }

  onView(book: Book) {
    this.router.navigate(['/books', book.id]);
  }

  edit(book: Book) {
    this.router.navigate(['/books', 'edit'], {
      queryParams: {id : book.id}
    });
  }

  delete(id: string) {
    const i = this.books.findIndex(b => b.id === id);
    console.log(i);
    this.service.deleteBookById(id)
      .subscribe(r => {
        this.books.splice(i, 1);
      });
  }

}
