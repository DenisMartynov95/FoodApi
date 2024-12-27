package ReceiptService.PositiveTests.Pojo.Requests.t2;

public class t2_1_checkSuccessRegistration {
    private String username = "user";
    private String firstName = "First Name";
    private String lastName = "Last Name";
    private String email = "test@mail.com";


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Первый конструктор для теста №2_1 передаю статичные данные полей
    public t2_1_checkSuccessRegistration(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public t2_1_checkSuccessRegistration() {
    }


}
