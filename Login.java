/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poeparttwo;



public class Login extends Registration {

    String loginUsername;
    String loginPassword;

    public Login() {
        super();
    }

    public Login(String firstName, String lastName, String userName,
            String password, String cellphone) {

        super(firstName, lastName, userName, password, cellphone);
    }

    public void setLoginDetails(String username, String password) {
        this.loginUsername = username;
        this.loginPassword = password;
    }

    public boolean loginUser() {
        if (loginUsername == null || loginPassword == null) {
            return false;
        }
        return loginUsername.equals(userName)
                && loginPassword.equals(password);
    }

    public boolean loginUser(String username, String password,
            Registration registration) {

        if (username == null || password == null || registration == null) {
            return false;
        }
        return username.equals(registration.getUserName())
                && password.equals(registration.getPassword());
    }

    public String returnLoginStatus(boolean status) {
        if (status) {
            return "Welcome, it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    /** Login message that includes the user's first and last name (Part 1 brief). */
    public String returnLoginStatus(boolean status, String firstName,
            String lastName) {

        if (status) {
            return "Welcome " + firstName + ", " + lastName
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
