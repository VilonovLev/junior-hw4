package org.example;

import java.sql.SQLException;
import java.util.List;

public class HiberMain {

    /**
     * 2. С помощью JPA(Hibernate) выполнить:
     * 2.1 Описать сущность Book из пункта 1.1
     * 2.2 Создать Session и сохранить в таблицу 10 книг
     * 2.3 Выгрузить список книг какого-то автора
     *
     * 3.* Создать сущность Автор (id bigint, name varchar), и в сущности Book сделать поле типа Author (OneToOne)
     * 3.1 * Выгрузить Список книг и убедиться, что поле author заполнено
     * 3.2 ** В классе Author создать поле List<Book>, которое описывает список всех книг этого автора. (OneToMany)
     */

    public static void main(String[] args) throws SQLException {
        BooksControllerHibernate dbBooksHiber = new BooksControllerHibernate();
        dbBooksHiber.initDb();
        List<Book> listBooks = new ListBooksBuilder()
                .setAuthors("Bob","Max","Igor")
                .setCountBooks(12)
                .create();
        dbBooksHiber.addBook(listBooks.toArray(listBooks.toArray(new Book[0])));
        System.out.println(dbBooksHiber.getAllBooksBy("Igor"));
        dbBooksHiber.dropDB();

    }
}
