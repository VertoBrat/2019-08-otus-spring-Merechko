import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SystemComponent} from './system.component';
import {SystemRoutingModule} from './system-routing.module';
import { BookListPageComponent } from './book-page/book-list-page/book-list-page.component';
import { MainPageComponent } from './main-page/main-page.component';
import {AngularFontAwesomeModule} from 'angular-font-awesome';
import {HttpClientModule} from '@angular/common/http';
import {BookService} from './shared/services/book.service';
import { BookEditPageComponent } from './book-page/book-edit-page/book-edit-page.component';
import {ReactiveFormsModule} from '@angular/forms';
import {LoaderComponent} from './shared/loader/loader.component';
import { BookViewComponent } from './book-page/book-view/book-view.component';
import {MomentPipe} from './shared/pipes/moment.pipe';
import {CommentService} from './shared/services/comment.service';
import {AuthorListPageComponent} from './author-page/author-list-page/author-list-page.component';
import {GenreListPageComponent} from './genre-page/genre-list-page/genre-list-page.component';
import {CustomModule} from '../custom.module';

@NgModule({
  declarations: [
    SystemComponent,
    BookListPageComponent,
    BookEditPageComponent,
    BookViewComponent,
    MomentPipe
  ],
  imports: [
    CommonModule,
    SystemRoutingModule,
    AngularFontAwesomeModule,
    HttpClientModule,
    CustomModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})
  ],
  providers: [BookService, CommentService]
})
export class SystemModule {
}
