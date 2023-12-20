package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksControllerJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "11235";

    private final Connection connection;

    public BooksControllerJDBC() throws SQLException {
        this.connection = DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public void initDb() throws SQLException {
       try (Statement statement = connection.createStatement()) {
           statement.execute("create schema if not exists books_market;");
           statement.execute("create table if not exists books_market.books " +
                   "(id bigint auto_increment, title varchar(45), author varchar(45), primary key (id));");
       }
    }

    public void addBook(Book... books) throws SQLException {
        String request = "insert into books_market.books (title,author) values (?,?)";
        PreparedStatement statement = connection.prepareStatement(request);
        for (Book book:books) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor().getName());
            statement.execute();
        }
        statement.close();
    }

    public List<Book> getAllBooksBy(String author) throws SQLException {
        String request = "select * from books_market.books where author=?";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1,author);
            ResultSet resultSet = statement.executeQuery();
            return mapResultSetToList(resultSet);
        }
    }

    private static List<Book> mapResultSetToList(ResultSet resultSet) throws SQLException {
        List<Book> list = new ArrayList<>();
        while (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(new Author(resultSet.getString("author")));
            list.add(book);
        }
        return list;
    }

    public void dropDB() throws SQLException{
        try (Statement statement = connection.createStatement()){
            statement.execute("drop schema books_market;");
        }
    }
}
