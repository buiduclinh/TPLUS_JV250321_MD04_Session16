import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập ID sinh viên cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nhập tên mới: ");
        String name = scanner.nextLine();

        System.out.print("Nhập tuổi mới: ");
        int age = Integer.parseInt(scanner.nextLine());

        boolean result = dao.updateStudent(id, name, age);
        if (result) {
            System.out.println("Sinh viên đã được cập nhật.");
        } else {
            System.out.println("Cập nhật thất bại.");
        }
    }
}