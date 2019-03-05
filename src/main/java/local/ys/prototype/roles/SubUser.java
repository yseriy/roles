package local.ys.prototype.roles;

import java.sql.*;
import java.util.Objects;

public class SubUser {
    private static final String INSERT_STATEMENT = "insert into auth.platform_subusers(" +
            "user_id, login, password, description) values(?, ?, ?, ?)";
    private static final String UPDATE_STATEMENT = "update auth.platform_subusers " +
            "set user_id = ?, login = ?, password = ?, description = ? where id = ?";
    private static final String DELETE_STATEMENT = "delete from auth.platform_subusers where id = ?";

    private Integer id;
    private Integer accountID;
    private String login;
    private String password;
    private String description;

    SubUser(Integer accountID, String login, String password, String description) {
        this(null, accountID, login, password, description);
    }

    SubUser(Integer id, Integer accountID, String login, String password, String description) {
        this.id = id;
        this.accountID = Objects.requireNonNull(accountID);
        this.login = Objects.requireNonNull(login);
        this.password = Objects.requireNonNull(password);
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = Objects.requireNonNull(accountID);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = Objects.requireNonNull(login);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void save(Connection connection) throws SQLException {
        if (Objects.isNull(id)) {
            insert(connection);
        } else {
            update(connection);
        }
    }

    private void insert(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, accountID);
            statement.setString(2, login);
            statement.setString(3, password);
            setDescription(statement, 4, description);
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
        }
    }

    private void update(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT)) {
            statement.setInt(1, accountID);
            statement.setString(2, login);
            statement.setString(3, password);
            setDescription(statement, 4, description);
            statement.setInt(5, id);
            statement.executeUpdate();
        }
    }

    private void setDescription(PreparedStatement statement, Integer index, String description) throws SQLException {
        if (Objects.isNull(description)) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, description);
        }
    }

    public void delete(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
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
