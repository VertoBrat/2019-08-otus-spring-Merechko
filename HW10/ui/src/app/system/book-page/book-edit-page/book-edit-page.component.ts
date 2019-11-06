import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {BookService} from '../../shared/services/book.service';
import {Book} from '../../shared/models/book.model';
import {Genre} from '../../shared/models/genre.model';
import {Author} from '../../shared/models/author.model';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-book-edit-page',
  templateUrl: './book-edit-page.component.html',
  styleUrls: ['./book-edit-page.component.css']
})
export class BookEditPageComponent implements OnInit {

  constructor(private service: BookService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  pageNumber: number;
  form: FormGroup;
  id: string;
  title: string;
  content: string;
  genres: string[];
  authors: string[];

  ngOnInit() {
    this.pageNumber = this.route.snapshot.queryParams.page;
    this.route.queryParams.subscribe((p: Params) => {
      if (p.id) {
        this.service.getBookById(p.id)
          .subscribe(b => {
            this.id = b.id;
            this.title = b.title;
            this.content = b.content;
            this.genres = b.genres.map(g => g.name);
            this.authors = b.authors.map(a => a.fullName);
          });
      }
    });
    this.form = new FormGroup({
      id: new FormControl(this.id),
      title: new FormControl('', Validators.required),
      content: new FormControl(''),
      genres: new FormControl('', Validators.required),
      authors: new FormControl('', Validators.required)
    });

  }

  onSubmit() {
    const formData = this.form.value;
    const book = new Book(
      formData.title,
      formData.content,
      this.getGenresFromForm(formData.genres),
      this.getAuthorsFromForm(formData.authors)
    );
    book.setId(formData.id);
    this.service.saveOrUpdateBook(book)
      .subscribe(b => {
        this.router.navigate(['/books', 'list'], {
          queryParams: {page: this.pageNumber}
        });
      });
  }

  private getGenresFromForm(formValue: string): Genre[] {
    const genreArray: Genre[] = [];
    const genreStringArray = formValue.toString().trim().split(',');
    genreStringArray.forEach(g => genreArray.push(new Genre(g)));
    return genreArray;
  }

  private getAuthorsFromForm(formValue: string): Author[] {
    const authorArray: Author[] = [];
    const authorStringArray = formValue.toString().split(',');
    authorStringArray.forEach(a => authorArray.push(new Author(a)));
    return authorArray;
  }

}
