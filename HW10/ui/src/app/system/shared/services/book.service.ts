import {BaseApiService} from './base-api.service';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Book} from '../models/book.model';

export interface BooksResponse {
  _embedded: {
    books: Book[]
  };
  _links: {
    first: {
      href: string
    },
    prev: {
      href: string
    },
    self: {
      href: string
    },
    next: {
      href: string
    },
    last: {
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



  public getAllBooks(page?: number): Observable<BooksResponse> {
    const params = new HttpParams().set('page', page + '');
    return this.get<BooksResponse>('api/books', params);
  }

  public getFilteredBook(search: string, type: string): Observable<BooksResponse> {
    const params = new HttpParams().set('search', search).append('type', type);
    return this.get<BooksResponse>('api/books', params);
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
