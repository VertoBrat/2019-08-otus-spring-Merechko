import {Component, OnInit} from '@angular/core';
import {BookService} from '../../shared/services/book.service';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {Book} from '../../shared/models/book.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Comment } from '../../shared/models/comment.model';
import {CommentService} from '../../shared/services/comment.service';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-book-view',
  templateUrl: './book-view.component.html',
  styleUrls: ['./book-view.component.css']
})
export class BookViewComponent implements OnInit {

  constructor(private bookService: BookService,
              private commentService: CommentService,
              private route: ActivatedRoute,
              private router: Router) { }

  book: Book;
  isLoaded = false;
  form: FormGroup;
  bookId: string;
  pageNumber: number;

  ngOnInit() {
    this.bookId = this.route.snapshot.params.id;
    this.pageNumber = this.route.snapshot.queryParams.page;
    console.log(this.pageNumber);
    this.getBookById();
    this.form = new FormGroup({
      commentText: new FormControl('', Validators.required)
    });
  }

  getBookById() {
    this.bookService.getBookById(this.bookId)
      .subscribe(b => {
        this.book = b;
        this.isLoaded = true;
      });
  }

  onSubmit() {
    const comment = new Comment(this.commentText.value, null, this.bookId);
    this.form.reset();
    this.commentService.postComment(comment)
      .subscribe(c => {
        this.getBookById();
      });
  }

  get commentText() {
    return  this.form.get('commentText');
  }

  delete(comment: Comment) {
    this.commentService.deleteComment(comment.id)
      .subscribe(res => {
        this.getBookById();
      });
  }

}
