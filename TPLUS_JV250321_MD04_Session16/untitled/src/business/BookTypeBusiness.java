package business;

import dao.BookTypeDAOImpl;
import entity.BookType;

import java.util.List;
import java.util.Scanner;

//1. Danh sách loại sách
//2. Thêm mới loại sách
//3. Cập nhật loại sách
//4. Xóa loại sách
public class BookTypeBusiness {
    private BookTypeDAOImpl bookTypeDAOImpl;

    public BookTypeBusiness() {
        bookTypeDAOImpl = new BookTypeDAOImpl();
    }

    public void displayAllBookTypes() {
        List<BookType> bookTypeList = bookTypeDAOImpl.findAllBookTypes();
        if (bookTypeList.isEmpty()) {
            System.out.println("There are no Book Types");
            return;
        }
        System.out.println("Book Types:");
        bookTypeList.forEach(System.out::println);
    }

    public void addBookType(Scanner scanner) {
        BookType bookType = new BookType();
        try {
            bookType.InputData(scanner);
            boolean success = bookTypeDAOImpl.addBookType(bookType);
            if (success) {
                System.out.println("Successfully added Book Type");
            } else {
                System.err.println("Failed to add Book Type");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void updateBookType(Scanner scanner) {
        boolean exit = false;
        System.out.println("Enter Book Type ID");
        int id = Integer.parseInt(scanner.nextLine());
        BookType bookType = bookTypeDAOImpl.findBookTypeById(id);
        try {
            if (bookType != null) {
                do {
                    System.out.println("Enter your choice");
                    System.out.println("1. Update Book Type Name");
                    System.out.println("2. Update Book Type Description");
                    System.out.println("3. Back to menu");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter new Book Type ID");
                            do {
                                String newBookTypeName = scanner.nextLine();
                                if (bookTypeDAOImpl.bookNameIsExist(newBookTypeName)) {
                                    System.out.println("Tên loại sách này đã tồn tại, vui lòng nhập tên khác.");;
                                } else {
                                    bookType.setTypeName(newBookTypeName);
                                    exit = true;
                                }
                            } while (!exit);
                            break;
                        case 2:
                            String newBookTypeDescription = scanner.nextLine();
                            bookType.setTypeDescription(newBookTypeDescription);
                            break;
                        case 3:
                            exit = true;
                            break;
                    }
                } while (!exit);
            } else {
                System.out.println("Invalid Input");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteBookType(Scanner scanner) {
        System.out.println("Enter Book Type ID");
        int deleteId = Integer.parseInt(scanner.nextLine());
        BookType bookType = bookTypeDAOImpl.findBookTypeById(deleteId);
        try {
            if (bookType != null) {
                bookTypeDAOImpl.deleteBookType(deleteId);
                System.out.println("Deleting Book Type ID");
            }else {
                System.out.println("Invalid Input");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
