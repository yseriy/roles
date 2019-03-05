package local.ys.prototype.roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

class SubUserFinder {
    private static final String FIND_STATEMENT = "select id, user_id, login, password, description " +
            "from auth.platform_subusers where id = ?";
    private static final String FIND_BY_ACCOUNT_ID_STATEMENT = "select id, user_id, login, password, description " +
            "from auth.platform_subusers where user_id = ?";

    Optional<SubUser> find(Connection connection, Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_STATEMENT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(createSubUser(resultSet));
            }

            return Optional.empty();
        }
    }

    Collection<SubUser> findByAccountID(Connection connection, Integer accountID) throws SQLException {
        Collection<SubUser> subUsers = new HashSet<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ACCOUNT_ID_STATEMENT)) {
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                subUsers.add(createSubUser(resultSet));
            }

            return subUsers;
        }
    }

    private SubUser createSubUser(ResultSet resultSet) throws SQLException {
        return new SubUser(resultSet.getInt("id"), resultSet.getInt("user_id"),
                resultSet.getString("login"), resultSet.getString("password"),
                resultSet.getString("description"));
    }
}
