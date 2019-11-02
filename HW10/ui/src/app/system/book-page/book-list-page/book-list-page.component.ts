import {Component, OnInit} from '@angular/core';
import {BookService} from '../../shared/services/book.service';
import {Book} from '../../shared/models/book.model';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-book-list-page',
  templateUrl: './book-list-page.component.html',
  styleUrls: ['./book-list-page.component.css']
})
export class BookListPageComponent implements OnInit {

  constructor(private service: BookService,
              private router: Router) {
  }

  books: Array<Book> = [];
  isLoaded = false;
  noBooks = false;
  totalPages: Array<number> = [];
  pageNumber: number;
  form: FormGroup;
  paged = true;

  ngOnInit() {
    this.getListOfBooks(0);
    this.form = new FormGroup({
      search: new FormControl(''),
      type: new FormControl('genre')
    });
  }

  getListOfBooks(page?: number) {
    this.service.getAllBooks(page)
      .subscribe(b => {
        if (b.page.totalElements !== 0) {
          this.books = b._embedded.books;
        } else {
          this.noBooks = true;
        }
        this.totalPages.splice(0, this.totalPages.length);
        for (let i = 1; i <= b.page.totalPages; i++) {
          this.totalPages.push(i);
        }
        this.pageNumber = b.page.number + 1;
        this.isLoaded = true;
      });
  }

  getFilteredBooks(search: string, type: string) {
    this.service.getFilteredBook(search, type)
      .subscribe(b => {
        if (b === null) {
          this.noBooks = true;
        } else {
          this.books = b._embedded.books;
          this.noBooks = false;
        }
        this.isLoaded = true;
        this.paged = false;
      });
  }

  onView(book: Book) {
    this.router.navigate(['/books', book.id]);
  }

  edit(book: Book) {
    this.router.navigate(['/books', 'edit'], {
      queryParams: {id: book.id}
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

  onSubmitSearch() {
    const formData: { search: string, type: string } = this.form.value;
    if (formData.search.length > 0) {
      this.getFilteredBooks(formData.search, formData.type);
    } else {
      this.getListOfBooks(0);
    }
  }
}
