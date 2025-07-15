package entity;

import java.util.Scanner;

public class BookType {
    private int typeId;
    private String typeName;
    private String typeDescription;
    private int typeStatus;

    public BookType(int typeId, String typeName, String typeDescription, int typeStatus) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeDescription = typeDescription;
        this.typeStatus = typeStatus;
    }

    public BookType() {
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public int getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(int typeStatus) {
        this.typeStatus = typeStatus;
    }

    public void InputData(Scanner scanner) {
        this.typeName = scanner.nextLine();
        this.typeDescription = scanner.nextLine();
        this.typeStatus = Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String toString() {
        return String.format("Mã loại sách: %d, Tên loại sách: %s, Mô tả loại sách: %s, Trạng thái loại sách: %s",
                this.typeId, this.typeName, this.typeDescription, this.typeStatus
        );
    }

}
