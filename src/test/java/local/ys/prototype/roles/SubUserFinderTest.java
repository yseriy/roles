package local.ys.prototype.roles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class SubUserFinderTest {
    private Connection connection;

    @Before
    public void setup() throws SQLException {
        String sql1 = ""
                + "create schema auth;"

                + "create table auth.platform_subusers ("
                + "    id serial primary key,"
                + "    user_id integer not null,"
                + "    login varchar(256) not null,"
                + "    password varchar(512) not null,"
                + "    description text,"
                + "    created timestamp with time zone not null default now(),"
                + "    modified timestamp with time zone not null default now(),"
                + "    deleted boolean not null default false"
                + ");"

                + "insert into auth.platform_subusers(user_id, login, password, description) values (1, 'test1', 'pass1', 'desc1');"
                + "insert into auth.platform_subusers(user_id, login, password, description) values (2, 'test2', 'pass2', 'desc2');"
                + "insert into auth.platform_subusers(user_id, login, password, description) values (2, 'test3', 'pass3', 'desc3')";

        connection = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
        Statement statement = connection.createStatement();
        statement.execute(sql1);
        statement.close();
    }

    @After
    public void clean() throws SQLException {
        connection.close();
    }

    @Test
    public void find() throws SQLException {
        Integer id = 1;
        SubUser expectSubUser = new SubUser(id, 1, "test1", "pass1", "desc1");

        SubUserFinder subUserFinder = new SubUserFinder();
        SubUser subUser = subUserFinder.find(connection, id);

        assertEquals(expectSubUser, subUser);
    }

    @Test
    public void findByAccountID() throws SQLException {
        Integer accountID = 2;
        SubUser expectSubUser2 = new SubUser(2, accountID, "test2", "pass2", "desc2");
        SubUser expectSubUser3 = new SubUser(3, accountID, "test3", "pass3", "desc3");

        Collection<SubUser> expectedSubUsers = new HashSet<>();
        expectedSubUsers.add(expectSubUser2);
        expectedSubUsers.add(expectSubUser3);

        SubUserFinder subUserFinder = new SubUserFinder();
        Collection<SubUser> subUsers = subUserFinder.findByAccountID(connection, accountID);

        assertEquals(expectedSubUsers, subUsers);
    }
}
