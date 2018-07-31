package net.abadguy.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testAuthentication(){
        IniRealm iniRealm=new IniRealm("classpath:user.ini");
        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token = token=new UsernamePasswordToken("mak","1234");
        try {
            subject.login(token);
        }catch (UnknownAccountException e1){
            System.out.println("用户名不正确");
        }catch (IncorrectCredentialsException e2){
            System.out.println("密码错误");
        }

        subject.checkRole("admin");
        subject.checkPermission("user:delete");


        System.out.println("登录是否成功："+ subject.isAuthenticated());
        //退出登陆
        subject.logout();


    }
}
