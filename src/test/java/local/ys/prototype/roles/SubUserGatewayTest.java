package local.ys.prototype.roles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class SubUserGatewayTest {
    private Connection connection;

    @Before
    public void setup() throws SQLException {
        String sql1 = ""
                + "create schema auth;"

                + "create table auth.platform_subusers ("
                + "    id bigint primary key,"
                + "    user_id integer not null,"
                + "    login varchar(256) not null,"
                + "    password varchar(512) not null,"
                + "    description text,"
                + "    created timestamp with time zone not null default now(),"
                + "    modified timestamp with time zone not null default now(),"
                + "    deleted boolean not null default false"
                + ");"

                + "create sequence auth.platform_subusers_id_seq;"

                + "insert into auth.platform_subusers(id, user_id, login, password, description) values (nextval('auth.platform_subusers_id_seq'), 1, 'test1', 'pass1', 'desc1');"
                + "insert into auth.platform_subusers(id, user_id, login, password, description) values (nextval('auth.platform_subusers_id_seq'), 2, 'test2', 'pass2', 'desc2');"
                + "insert into auth.platform_subusers(id, user_id, login, password, description) values (nextval('auth.platform_subusers_id_seq'), 2, 'test3', 'pass3', 'desc3')";

        connection = DriverManager.getConnection("jdbc:h2:mem:", "sa", "");
        Statement statement = connection.createStatement();
        statement.execute(sql1);
        statement.close();
    }

    @After
    public void cleanup() throws SQLException {
        connection.close();
    }

    @Test
    public void getNextSubUserID() throws SQLException {
        SubUserGateway gateway = new SubUserGateway(connection);
        Long nextSubUserID = gateway.getNextSubUserID();

        assertEquals(4L, nextSubUserID.longValue());
    }
}