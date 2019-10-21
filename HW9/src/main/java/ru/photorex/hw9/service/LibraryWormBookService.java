package ru.photorex.hw9.service;

import ru.photorex.hw9.model.Author;
import ru.photorex.hw9.model.Book;
import ru.photorex.hw9.to.BookTo;
import ru.photorex.hw9.to.Filter;

import java.util.List;

public interface LibraryWormBookService {

    List<BookTo> findAllBooks();

    void deleteBook(String id);

    BookTo findBookById(String id);

    BookTo updateSaveBook(BookTo to);

    List<BookTo> filteredBooks(Filter filter);

    Book saveBook(Book book);

    Book updateTitle(String bookId, String title);

    List<Book> findBookByAuthor(Author author);

    List<Book> findBookByGenre(String genre);
}
