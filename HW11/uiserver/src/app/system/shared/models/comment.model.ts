export class Comment {
  text: string;
  dateTime: string;
  bookId: string;
  id?: string;

  constructor(text: string,
              dateTime: string,
              bookId: string,
              id?: string) {
    this.text = text;
    this.dateTime = dateTime;
    this.bookId = bookId;
  }

  setId(id: string) {
    this.id = id;
  }
}
