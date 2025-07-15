import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankDAO bankDAO = new BankDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Chuyển tiền giữa các tài khoản");
        System.out.print("Nhập ID tài khoản nguồn: ");
        int fromId = scanner.nextInt();
        System.out.print("Nhập ID tài khoản đích: ");
        int toId = scanner.nextInt();
        System.out.print("Nhập số tiền cần chuyển: ");
        double amount = scanner.nextDouble();

        boolean success = bankDAO.transferFunds(fromId, toId, amount);
        if (success) {
            System.out.println("Chuyển tiền thành công!");
        } else {
            System.out.println("Chuyển tiền thất bại.");
        }
    }
}