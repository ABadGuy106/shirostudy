package net.abadguy.dao.imp;

import net.abadguy.dao.UserDao;
import net.abadguy.entity.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDaoImp implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public User getUserByUsersName(String userName) {
        String sql="select username,password from users where username=?";
        List<User> list=jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user=new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUserName(String userName) {

        String sql="select role_name FROM user_roles where username=?";
        return jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
    }
}
