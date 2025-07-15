package dao;

import entity.Book;

import java.util.List;

public interface BookDAO {
//********************BOOK****************
//1. Danh sách sách
//2. Thêm mới sách
//3. Thêm mới nhiều sách
//4. Cập nhật sách
//5. Xóa sách
//6. Tìm kiếm sách
//7. Thông kê số lượng sách theo tác giả
    List<Book> getAllBook();
    boolean addBook(Book book);
    boolean updateBook(Book book);
    boolean addBooks(List<Book> books);
    boolean deleteBook(String id);
    boolean findBookById(String id);
    boolean totalBookByAuthor(Book book);
}
