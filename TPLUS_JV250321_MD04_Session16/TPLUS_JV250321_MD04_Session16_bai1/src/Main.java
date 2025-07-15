import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDAO studentDAO = new StudentDAO();

        List<String[]> students = List.of(
                new String[]{"Alice", "20"},
                new String[]{"Bob", "21"},
                new String[]{"Charlie", "22"}
        );

        boolean result = studentDAO.addStudents(students);

        if (result) {
            System.out.println("Tất cả sinh viên đã được thêm vào database.");
        } else {
            System.out.println("Thêm sinh viên thất bại.");
        }
    }
}