import {Component, OnInit} from '@angular/core';
import {BookService} from '../../shared/services/book.service';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {Book} from '../../shared/models/book.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Comment} from '../../shared/models/comment.model';
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
              private router: Router) {
  }

  book: Book;
  isLoaded = false;
  form: FormGroup;
  bookId: string;
  pageNumber: number;
  id: string = null;
  text: string;

  ngOnInit() {
    this.bookId = this.route.snapshot.params.id;
    this.pageNumber = this.route.snapshot.queryParams.page;
    this.getBookById();
    this.form = new FormGroup({
      commentId: new FormControl(this.id),
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

  edit(comment: Comment) {
    this.id = comment.id;
    this.text = comment.text;
  }

  onSubmit() {
    const comment = new Comment(this.commentText.value, null, this.bookId);
    comment.setId(this.commentId.value);
    this.form.reset();
    this.commentService.saveOrUpdateComment(comment)
      .subscribe(c => {
        this.getBookById();
      });
  }

  get commentText() {
    return this.form.get('commentText');
  }

  get commentId() {
    return this.form.get('commentId');
  }

  delete(comment: Comment) {
    this.commentService.deleteComment(comment.id)
      .subscribe(res => {
        this.getBookById();
      });
  }

}
