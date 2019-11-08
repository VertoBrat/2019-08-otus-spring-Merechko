import {BaseApiService} from './base-api.service';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Genre} from '../models/genre.model';

@Injectable()
export class GenreService extends BaseApiService {
    constructor(public http: HttpClient) {
      super(http);
    }

    public getAllGenres(): Observable<Genre[]> {
      return this.get<Genre[]>('api/genres');
    }
}
