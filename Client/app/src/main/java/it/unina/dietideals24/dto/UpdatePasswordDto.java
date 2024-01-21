package it.unina.dietideals24.dto;

public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;

    public UpdatePasswordDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
