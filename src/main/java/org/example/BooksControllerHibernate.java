package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;

public class BooksControllerHibernate {

    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "11235";
    private final SessionFactory sessionFactory;

    public BooksControllerHibernate() throws SQLException {
        initDb();
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public void initDb() throws SQLException {
        Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        try (Statement statement = connection.createStatement()) {
            statement.execute("create schema if not exists books_market;");
        }
        connection.close();
    }

    public void addBook(Book... books) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Book book:books) {
            session.persist(book);
        }
        session.getTransaction().commit();
    }

    public List<Book> getAllBooksBy(String author){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long index = session
                .createQuery(String.format("select a from Author a where name='%s'", author), Author.class)
                .getSingleResult()
                .getId();
        List<Book> result = session
                .createQuery(String.format("select b from Book b where author='%s'", index), Book.class)
                .getResultList();
        session.getTransaction().commit();
        return result;
    }

    public void dropDB() throws SQLException{
        try (Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                Statement statement = connection.createStatement()){
            statement.execute("drop schema books_market;");
        }
    }
}
