package local.ys.prototype.roles;

import java.util.Objects;

public class SubUser {

    private Long id;
    private Integer accountID;
    private String login;
    private String password;
    private String description;

    SubUser(Long id, Integer accountID, String login, String password, String description) {
        this.id = id;
        this.accountID = Objects.requireNonNull(accountID);
        this.login = Objects.requireNonNull(login);
        this.password = Objects.requireNonNull(password);
        this.description = description;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    Integer getAccountID() {
        return accountID;
    }

    void setAccountID(Integer accountID) {
        this.accountID = Objects.requireNonNull(accountID);
    }

    String getLogin() {
        return login;
    }

    void setLogin(String login) {
        this.login = Objects.requireNonNull(login);
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = Objects.requireNonNull(password);
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubUser subUser = (SubUser) o;
        return Objects.equals(id, subUser.id) &&
                Objects.equals(accountID, subUser.accountID) &&
                Objects.equals(login, subUser.login) &&
                Objects.equals(password, subUser.password) &&
                Objects.equals(description, subUser.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountID, login, password, description);
    }

    @Override
    public String toString() {
        return "SubUser{" + "id=" + id +
                ", accountID=" + accountID +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
