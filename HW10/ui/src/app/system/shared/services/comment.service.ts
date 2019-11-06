import {BaseApiService} from './base-api.service';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import { Comment } from '../models/comment.model';

@Injectable()
export class CommentService extends BaseApiService{
   constructor(public htpp: HttpClient) {
     super(htpp);
   }

   public postComment(comment: Comment): Observable<Comment> {
     return this.post<Comment>('api/comments', comment).pipe(catchError(this.handleError));
   }

   public deleteComment(id: string): Observable<any> {
     return this.delete('api/comments/', id).pipe(catchError(this.handleError));
   }
}
