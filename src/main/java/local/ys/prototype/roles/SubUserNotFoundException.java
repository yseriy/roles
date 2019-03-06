package local.ys.prototype.roles;

public class SubUserNotFoundException extends RuntimeException {
    SubUserNotFoundException(String message) {
        super("subuser: " + message + " not found");
    }
}
