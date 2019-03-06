package local.ys.prototype.roles;

import java.sql.Connection;

public class SubUserPolicies {

    private final Connection connection;

    public SubUserPolicies(Connection connection) {
        this.connection = connection;
    }

    public Boolean subUserNotBelongToAccount(Integer subUserID, Integer accountID) {
        return true;
    }

    public Boolean subUserAlreadyExists(Integer accountID, String subUserLogin) {
        return true;
    }

    public Boolean maxNumberOfSubUsersExceeded(Integer accountID) {
        return true;
    }
}
