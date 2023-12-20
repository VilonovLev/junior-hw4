package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListBooksBuilder {
    private Author[] authors;

    private int countBooks;

    private List<Book> books = new ArrayList<>();

    public ListBooksBuilder setAuthors(String... authors) {
        this.authors = Arrays.stream(authors)
                .map(Author::new)
                .toArray(Author[]::new);
        return this;
    }

    public ListBooksBuilder setCountBooks(int countBooks) {
        this.countBooks = countBooks;
        return this;
    }


    public List<Book> create() {
        if (!(authors.length > 0 && countBooks > 0)) {
            throw new IllegalStateException("builder not filled!");
        }
        while (countBooks-- >0) {
            int index = ThreadLocalRandom.current().nextInt(0, authors.length);
            books.add(new Book("title " + countBooks,authors[index]));
        }
        return books;
    }
}
