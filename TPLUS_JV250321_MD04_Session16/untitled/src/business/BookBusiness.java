package business;

import dao.BookDaoImpl;
import entity.Book;

import java.util.List;
import java.util.Scanner;

public class BookBusiness {
    private BookDaoImpl bookDaoImpl;

    public BookBusiness() {
        bookDaoImpl = new BookDaoImpl();
    }

    public void displayAllBook() {
        List<Book> bookList = bookDaoImpl.getAllBook();
        if (bookList.isEmpty()) {
            System.out.println("There are no books in the system");
            return;
        }
        System.out.println("Books in the system:");
        bookList.forEach(System.out::println);
    }

    public void addBook(Scanner scanner) {
        Book book = new Book();
        try {
            book.InputData(scanner);
            boolean success = bookDaoImpl.addBook(book);
            if(success){
                System.out.println("Book added successfully");
            }else {
                System.err.println("Book could not be added");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Scanner scanner) {

    }
}
