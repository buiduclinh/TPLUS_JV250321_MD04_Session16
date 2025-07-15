package dao;

import entity.Book;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class BookBusinessDao implements dao.BookDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/book_data_base";
    private static final String USER = "root";
    private static final String PASS = "123456789";

    @Override
    public List<entity.Book> getAllBook() {
        List<entity.Book> bookList = new ArrayList<>();
        String sql = "{CALL view_book_information()}";
        try (Connection conn = DriverManager.getConnection(URL);
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                entity.Book book = new entity.Book();
                book.setBookId(rs.getNString("book_id"));
                book.setBookName(rs.getString("book_name"));
                book.setBookTitle(rs.getString("book_title"));
                book.setBookPages(rs.getInt("book_pages"));
                book.setBookAuthor(rs.getString("book_author"));
                book.setBookPrice(rs.getFloat("book_price"));
                book.setTypeId(rs.getInt("type_id"));
                book.setBookStatus(rs.getInt("book_status"));
                bookList.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public boolean addBook(entity.Book book) {
        String sql = "{CALL add_book(?,?,?,?,?,?,?,?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, book.getBookId());
            cs.setString(2, book.getBookName());
            cs.setString(3, book.getBookTitle());
            cs.setInt(4, book.getBookPages());
            cs.setString(5, book.getBookAuthor());
            cs.setFloat(6, book.getBookPrice());
            cs.setInt(7, book.getTypeId());
            cs.setInt(8, book.getBookStatus());

            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateBook(entity.Book book) {
        String sql = "{CALL update_book(?,?,?,?,?,?,?)}";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            cs.setString(1, book.getBookId());
            cs.setString(2, book.getBookName());
            cs.setString(3, book.getBookTitle());
            cs.setInt(4, book.getBookPages());
            cs.setString(5, book.getBookAuthor());
            cs.setFloat(6, book.getBookPrice());
            cs.setInt(7, book.getTypeId());
            cs.setInt(8, book.getBookStatus());

            int row = cs.executeUpdate();
            return (row > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addBooks(List<entity.Book> books)
//    update nhiều sách
    {
        String sql = "{CALL update_book(?,?,?,?,?,?,?)}";
        boolean suscess = false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql);) {
            books.stream().forEach(book -> {
                try {
                    cs.setString(1, book.getBookId());
                    cs.setString(2, book.getBookName());
                    cs.setString(3, book.getBookTitle());
                    cs.setInt(4, book.getBookPages());
                    cs.setString(5, book.getBookAuthor());
                    cs.setFloat(6, book.getBookPrice());
                    cs.setInt(7, book.getTypeId());
                    cs.setInt(8, book.getBookStatus());

                    int rows = cs.executeUpdate();
                    if (rows == 0) {
                        throw new RuntimeException("Không thể thêm sách: " + book.getBookId());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Lỗi khi thêm thêm sách: " + book.getBookId());
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suscess;
    }

    @Override
    public boolean deleteBook(String id) {
        String sql = "{CALL delete_book(?)}";
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, id);
            int row = cs.executeUpdate();

            if (row > 0) {
                System.out.println(" Xóa sách thành công!");
                success = true;
            } else {
                System.out.println(" Không tìm thấy sách với id = " + id);
            }

        } catch (Exception e) {
            System.err.println(" Lỗi khi xóa sách: " + e.getMessage());
        }

        return success;
    }

    @Override
    public boolean findBookById(String id) {
        List<Book> books = getAllBook();
        Optional<Book> result = books.stream()
                .filter(book -> book.getBookId().equals(id))
                .findFirst();
        result.ifPresentOrElse(
                book -> {
                    System.out.println("Found book: " + book);
                },
                () -> {
                    System.out.println("Không tìm thấy sách với ID: " + id);
                }
        );
        return result.isPresent();
    }

    @Override
    public boolean totalBookByAuthor(Book book) {
        List<Book> books = getAllBook();
        Map<String, Long> booksByAuthor = books.stream()
                .collect(Collectors.groupingBy(
                        Book::getBookAuthor,
                        Collectors.counting()
                ));
        booksByAuthor.forEach((author, count) -> {
            System.out.println("Tác giả: " + author + " - Số sách: " + count);
        });

        String targetAuthor = book.getBookAuthor();
        if (booksByAuthor.containsKey(targetAuthor)) {
            Long total = booksByAuthor.get(targetAuthor);
            System.out.println("Tổng số sách của tác giả \"" + targetAuthor + "\": " + total);
            return true;
        } else {
            System.out.println(" Không tìm thấy sách nào của tác giả \"" + targetAuthor + "\"");
            return false;
        }
    }
}
