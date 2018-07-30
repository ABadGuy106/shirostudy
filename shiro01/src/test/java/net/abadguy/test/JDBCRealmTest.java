package net.abadguy.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JDBCRealmTest {

    @Test
    public void testAuthentication(){

        DruidDataSource dataSource=new DruidDataSource();
        {
            dataSource.setUrl("jdbc:mysql://118.24.166.176:3306/shiro_test");
            dataSource.setUsername("root");
            dataSource.setPassword("w3V2c0HEKWDyEd_mv1r_");
        }
        JdbcRealm realm=new JdbcRealm();
        realm.setDataSource(dataSource);
        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("mak1","1234");
        try {
            subject.login(token);
        }catch (UnknownAccountException e1){
            System.out.println("用户名不正确");
        }catch (IncorrectCredentialsException e2){
            System.out.println("密码错误");
        }




        System.out.println("登录是否成功："+ subject.isAuthenticated());
        //退出登陆
        subject.logout();


    }
}
