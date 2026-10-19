/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poeparttwo;

/**
 *
 * @author Student
 */

    public class Registration {

    String firstName;
    String lastName;
    String userName;
    String password;
    String cellphone;

    public Registration() {
    }

    public Registration(String firstName, String lastName, String userName,
            String password, String cellphone) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.cellphone = cellphone;
    }

    public boolean checkUserName() {
        return userName != null
                && userName.contains("_")
                && userName.length() <= 5;
    }

    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkPasswordComplexity(String password) {
        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber() {
        return cellphone != null
                && cellphone.matches("^\\+27[0-9]{9}$");
    }

    public boolean checkCellPhoneNumber(String cellphone) {
        return cellphone != null
                && cellphone.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters long.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User successfully registered.";
    }

    public String registerUser(String username, String password,
            String cellphone) {

        this.userName = username;
        this.password = password;
        this.cellphone = cellphone;

        return registerUser();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}


