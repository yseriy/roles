package local.ys.prototype.roles;

import java.util.Objects;

class Role {

    private final Integer id;
    private final Integer nextID;
    private final String name;
    private final Boolean guiOnly;

    public Role(Integer id, Integer nextID, String name, Boolean guiOnly) {
        this.id = id;
        this.nextID = nextID;
        this.name = name;
        this.guiOnly = guiOnly;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNextID() {
        return nextID;
    }

    public String getName() {
        return name;
    }

    public Boolean getGuiOnly() {
        return guiOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(nextID, role.nextID) &&
                Objects.equals(name, role.name) &&
                Objects.equals(guiOnly, role.guiOnly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nextID, name, guiOnly);
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id +
                ", nextID=" + nextID +
                ", name='" + name + '\'' +
                ", guiOnly=" + guiOnly +
                '}';
    }
}
