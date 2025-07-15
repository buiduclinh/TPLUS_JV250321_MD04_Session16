package dao;

import entity.BookType;

import java.util.List;

//********************BOOK TYPE****************
//        1. Danh sách loại sách
//2. Thêm mới loại sách
//3. Cập nhật loại sách
//4. Xóa loại sách

public interface BookTypeDAO {
    List<BookType> findAllBookTypes();
    boolean addBookType(BookType bookType);
    boolean updateBookType(BookType bookType);
    boolean deleteBookType(int id);
}
