package net.abadguy.dao;

import net.abadguy.entity.User;

import java.util.List;

public interface UserDao {
    User getUserByUsersName(String userName);

    List<String> queryRolesByUserName(String userName);
}
