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
  totalPages: Array<number> = [];
  pageNumber: number;

  ngOnInit() {
    this.getListOfBooks(0);
  }

  getListOfBooks(page?: number) {
    this.service.getAllBooks(page)
      .subscribe(b => {
        if (b.page.totalElements !== 0) {
          this.books = b._embedded.books;
        } else {this.noBooks = true; }
        this.totalPages.splice(0, this.totalPages.length);
        for (let i = 1; i <= b.page.totalPages; i++ ) {
          this.totalPages.push(i);
        }
        this.pageNumber = b.page.number + 1;
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
    // const i = this.books.findIndex(b => b.id === id);
    // console.log(i);
    this.service.deleteBookById(id)
      .subscribe(r => {
        this.getListOfBooks(this.pageNumber - 1);
      });
  }

  onPage(n: number) {
    this.getListOfBooks(n - 1);
  }

  onNext() {
    this.getListOfBooks(this.pageNumber);
  }

  onPrevious() {
    this.getListOfBooks(this.pageNumber - 2);
  }

}
