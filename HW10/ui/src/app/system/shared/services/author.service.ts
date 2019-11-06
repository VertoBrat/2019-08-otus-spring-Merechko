import {BaseApiService} from './base-api.service';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Author} from '../models/author.model';

@Injectable()
export class AuthorService extends BaseApiService{
    constructor(public http: HttpClient) {
      super(http);
    }

    public getAllAuthors(): Observable<Author[]> {
      return this.get<Author[]>('api/authors');
    }
}
