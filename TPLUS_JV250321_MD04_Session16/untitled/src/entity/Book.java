package entity;

import java.util.Scanner;

public class Book {
    private String bookId;
    private String bookName;
    private String bookTitle;
    private int bookPages;
    private String bookAuthor;
    private float bookPrice;
    private int typeId;
    private int bookStatus;

    public Book(String bookId, String bookName, String bookTitle, int bookPages, String bookAuthor, float bookPrice, int typeId, int bookStatus) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookTitle = bookTitle;
        this.bookPages = bookPages;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.typeId = typeId;
        this.bookStatus = bookStatus;
    }

    public Book() {

    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookPages() {
        return bookPages;
    }

    public void setBookPages(int bookPages) {
        this.bookPages = bookPages;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }

    public void InputData(Scanner scanner) {
        this.bookId = scanner.nextLine();
        this.bookName = scanner.nextLine();
        this.bookTitle = scanner.nextLine();
        this.bookPages = Integer.parseInt(scanner.nextLine());
        this.bookAuthor = scanner.nextLine();
        this.bookPrice = Float.parseFloat(scanner.nextLine());
        this.typeId = Integer.parseInt(scanner.nextLine());
        this.bookStatus = Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String toString() {
        return String.format("Mã sách: %s, Tên sách: %s, Tiêu đề sách: %s, Số trang sách: %d, Tên tác giả: %s, Giá sách: %f, Mã loại sách: %s, Trạng thái loại sách: %d",
                this.bookId, this.bookName, this.bookTitle, this.bookPages, this.bookAuthor, this.bookPrice, this.typeId,this.bookStatus);
    }



}
