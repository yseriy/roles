package local.ys.prototype.roles;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class SubUserTest {

    Connection connection;

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

    @Test
    public void save() throws SQLException {
//        SubUser subUser = new SubUser(12, "test_login", "test_pass", "test_desc");
//        subUser.save(connection);
//
//        assertNotNull(subUser.getId());
//        assertEquals(4L, subUser.getId().longValue());
    }
}
