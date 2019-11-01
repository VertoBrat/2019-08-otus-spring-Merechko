import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

export class ErrorResponse {
  status: number;
  message: string;
  timeStamp: Date;
}

@Injectable()
export class BaseApiService {
  constructor(public http: HttpClient) {}

  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error.message}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  }

  public get<T>(url: string, params?: HttpParams): Observable<T> {
    return this.http.get<T>(url, {params}).pipe(catchError(this.handleError));
  }

  public getById<T>(url: string, id: string): Observable<T> {
    return this.http.get<T>(url + id).pipe(catchError(this.handleError));
  }

  public update<T>(url: string, id: string, body: T): Observable<T> {
    return this.http.patch<T>(url + id, body).pipe(catchError(this.handleError));
  }

  public post<T>(url: string, body: T): Observable<T> {
    return this.http.post<T>(url, body).pipe(catchError(this.handleError));
  }

  public delete(url: string, id: string): Observable<any> {
    return this.http.delete(url + id).pipe(catchError(this.handleError));
  }
}
