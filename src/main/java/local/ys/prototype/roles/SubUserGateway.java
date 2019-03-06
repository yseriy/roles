package local.ys.prototype.roles;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

class SubUserGateway {

    private static final String GET_NEXT_ID_STATEMENT = "select nextval('auth.platform_subusers_id_seq') as id";

    private static final String INSERT_STATEMENT = "insert into auth.platform_subusers(user_id, login, password, description) values(?, ?, ?, ?)";
    private static final String UPDATE_STATEMENT = "update auth.platform_subusers set user_id = ?, login = ?, password = ?, description = ? where id = ?";
    private static final String DELETE_STATEMENT = "delete from auth.platform_subusers where id = ?";

    private static final String FIND_STATEMENT = "select id, user_id, login, password, description from auth.platform_subusers where id = ?";
    private static final String FIND_BY_ACCOUNT_ID_STATEMENT = "select id, user_id, login, password, description from auth.platform_subusers where user_id = ?";

    private final Connection connection;

    SubUserGateway(Connection connection) {
        this.connection = connection;
    }

    Long getNextSubUserID() {
        try (PreparedStatement statement = connection.prepareStatement(GET_NEXT_ID_STATEMENT)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getLong("id");

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    void insert(SubUser subUser) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)) {
            statement.setLong(1, subUser.getId());
            statement.setInt(2, subUser.getAccountID());
            statement.setString(3, subUser.getLogin());
            statement.setString(4, subUser.getPassword());
            setDescription(statement, 5, subUser.getDescription());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    void update(SubUser subUser) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT)) {
            statement.setInt(1, subUser.getAccountID());
            statement.setString(2, subUser.getLogin());
            statement.setString(3, subUser.getPassword());
            setDescription(statement, 4, subUser.getDescription());
            statement.setLong(5, subUser.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private void setDescription(PreparedStatement statement, Integer index, String description) throws SQLException {
        if (Objects.isNull(description)) {
            statement.setNull(index, Types.VARCHAR);
        } else {
            statement.setString(index, description);
        }
    }

    private void delete(Long subUserID) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT)) {
            statement.setLong(1, subUserID);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    void delete(SubUser subUser) {
        delete(subUser.getId());
    }

    Optional<SubUser> find(Integer subUserID) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_STATEMENT)) {
            statement.setInt(1, subUserID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createSubUser(resultSet));
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    Collection<SubUser> findByAccountID(Integer accountID) {
        Collection<SubUser> subUsers = new HashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ACCOUNT_ID_STATEMENT)) {
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subUsers.add(createSubUser(resultSet));
            }

            return subUsers;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private SubUser createSubUser(ResultSet resultSet) throws SQLException {
        return new SubUser(resultSet.getLong("id"), resultSet.getInt("user_id"),
                resultSet.getString("login"), resultSet.getString("password"),
                resultSet.getString("description"));
    }
}
