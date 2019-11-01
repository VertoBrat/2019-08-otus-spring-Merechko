import {Genre} from './genre.model';
import {Author} from './author.model';
import {Comment} from './comment.model';

export class Book {

  title: string;
  content: string;
  genres: Genre[];
  authors: Author[];
  comments: Comment[];
  id?: string;

  constructor(title: string,
              content: string,
              genres: Genre[],
              authors: Author[]) {
    this.title = title;
    this.content = content;
    this.genres = genres;
    this.authors = authors;
  }

  setId(id: string) {
    this.id = id;
  }
}
