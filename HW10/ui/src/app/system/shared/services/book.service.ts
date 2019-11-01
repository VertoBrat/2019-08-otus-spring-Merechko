import {BaseApiService} from './base-api.service';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Book} from '../models/book.model';

export interface BooksResponse {
  _embedded: {
    books: Book[]
  };
  _links: {
    self: {
      href: string
    }
  };
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  };
}

@Injectable()
export class BookService extends BaseApiService {
  constructor(public http: HttpClient) {
    super(http);
  }

  public getAllBooks(): Observable<Book[]> {
    return this.get<BooksResponse>('api/books')
      .pipe(
        map(res => res._embedded.books)
      );
  }

  public getBookById(id: string): Observable<Book> {
    return this.getById<Book>('api/books/', id);
  }

  public updateBook(id: string, book: Book): Observable<Book> {
    return this.update<Book>('api/books/', id, book);
  }

  public saveBook(book: Book): Observable<Book> {
    return this.post<Book>('api/books', book);
  }

  public deleteBookById(id: string): Observable<any> {
    return this.delete('api/books/', id);
  }
}
