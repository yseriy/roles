package local.ys.prototype.roles;

import java.util.Optional;

public class SubUserService {

    private final SubUserPolicies policies;
    private final SubUserGateway gateway;

    public SubUserService(SubUserPolicies policies, SubUserGateway gateway) {
        this.policies = policies;
        this.gateway = gateway;
    }

    public Long insertSubUser(Integer accountID, String subUserLogin, String subUserPassword, String description) {
        SubUser subUser = createSubUser(accountID, subUserLogin, subUserPassword, description);

        if (policies.subUserAlreadyExists(accountID, subUserLogin)) {
            throw new RuntimeException();
        }

        if (policies.maxNumberOfSubUsersExceeded(accountID)) {
            throw new RuntimeException();
        }

        gateway.insert(subUser);
        return subUser.getId();
    }

    public void updateSubUser(Integer accountID, Integer subUserID, String subUserPassword, String description) {
        SubUser subUser = getSubUser(accountID, subUserID);
        subUser.setPassword(subUserPassword);
        subUser.setDescription(description);
        gateway.update(subUser);
    }

    public void deleteSubUser(Integer accountID, Integer subUserID) {
        SubUser subUser = getSubUser(accountID, subUserID);
        gateway.delete(subUser);
    }

    private SubUser createSubUser(Integer accountID, String subUserLogin, String subUserPassword, String description) {
        Long nextSubUserID = gateway.getNextSubUserID();
        return new SubUser(nextSubUserID, accountID, subUserLogin, subUserPassword, description);
    }

    private SubUser getSubUser(Integer accountID, Integer subUserID) {
        Optional<SubUser> optSubUser = gateway.find(subUserID);
        SubUser subUser = optSubUser.orElseThrow(() -> new SubUserNotFoundException(subUserID.toString()));

        if (policies.subUserNotBelongToAccount(subUserID, accountID)) {
            throw new RuntimeException();
        }

        return subUser;
    }
}
