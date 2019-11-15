import {BaseApiService} from './base-api.service';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Comment} from '../models/comment.model';

@Injectable()
export class CommentService extends BaseApiService {
  constructor(public http: HttpClient) {
    super(http);
  }

  private saveComment(comment: Comment): Observable<Comment> {
    return this.post<Comment>('api/comments', comment).pipe(catchError(this.handleError));
  }

  private updateComment(id: string, comment: Comment): Observable<Comment> {
    return this.update<Comment>('api/comments/', id, comment);
  }

  public saveOrUpdateComment(comment: Comment): Observable<Comment> {
    if (comment.id) {
      return this.updateComment(comment.id, comment);
    } else {
      return this.saveComment(comment);
    }

  }

  public deleteComment(id: string): Observable<any> {
    return this.delete('api/comments/', id).pipe(catchError(this.handleError));
  }
}
