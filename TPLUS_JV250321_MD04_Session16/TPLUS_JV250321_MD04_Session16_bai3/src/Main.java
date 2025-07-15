import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập tuổi để xóa sinh viên nhỏ hơn: ");
        int age = Integer.parseInt(scanner.nextLine());

        int count = dao.deleteStudentsByAge(age);
        if (count > 0) {
            System.out.println("Đã xóa " + count + " sinh viên thành công.");
        } else {
            System.out.println("Không có sinh viên nào bị xóa.");
        }
    }
}