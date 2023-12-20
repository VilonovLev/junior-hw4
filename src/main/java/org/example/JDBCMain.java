package org.example;

import java.sql.SQLException;
import java.util.List;

public class JDBCMain {


    /**
     * Задания необходимо выполнять на ЛЮБОЙ СУБД (postgres, mysql, sqlite, h2, ...)
     *
     * 1. С помощью JDBC выполнить:
     * 1.1 Создать таблицу book с колонками id bigint, name varchar, author varchar, ...
     * 1.2 Добавить в таблицу 10 книг
     * 1.3 Сделать запрос select from book where author = 'какое-то имя' и прочитать его с помощью ResultSet

     * 3.* Создать сущность Автор (id bigint, name varchar), и в сущности Book сделать поле типа Author (OneToOne)
     * 3.1 * Выгрузить Список книг и убедиться, что поле author заполнено
     * 3.2 ** В классе Author создать поле List<Book>, которое описывает список всех книг этого автора. (OneToMany)
     */

    public static void main(String[] args) {
        try {
            BooksControllerJDBC dbBooks = new BooksControllerJDBC();
            dbBooks.initDb();
            List<Book> listBooks = new ListBooksBuilder()
                    .setAuthors("Bob","Max","Igor")
                    .setCountBooks(10)
                    .create();
            dbBooks.addBook(listBooks.toArray(new Book[0]));
            System.out.println(dbBooks.getAllBooksBy("Max"));
            dbBooks.dropDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}