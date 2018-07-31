package net.abadguy.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JDBCRealmTest1 {
    DruidDataSource dataSource=new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://118.24.166.176:3306/shiro_test");
        dataSource.setUsername("root");
        dataSource.setPassword("w3V2c0HEKWDyEd_mv1r_");
    }

    JdbcRealm realm=new JdbcRealm();


    @Test
    public void testAuthentication(){


        realm.setDataSource(dataSource);
        //开启权限查询
        realm.setPermissionsLookupEnabled(true);


        String sql="select passwd from test_user where username=?";
        String roleSql="select role_name FROM user_roles where username=?";
        //自定义sql用户登陆
        realm.setAuthenticationQuery(sql);
        //自定义角色查询
        realm.setUserRolesQuery(roleSql);

        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("test","123");

            subject.login(token);

        System.out.println("登录是否成功："+ subject.isAuthenticated());


        subject.checkRole("test_user");


    }
}
