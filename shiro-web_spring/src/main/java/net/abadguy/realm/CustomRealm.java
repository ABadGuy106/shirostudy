package net.abadguy.realm;

import net.abadguy.dao.UserDao;
import net.abadguy.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从主体传过来的认证信息中，获取用户名
        String userName= (String) principalCollection.getPrimaryPrincipal();
        //从数据库中获取角色数据
        Set<String> roles=getRolesByUserName(userName);

        Set<String> permissions=getPermissionsByUserName(userName);

        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets=new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:update");
        return sets;
    }

    private Set<String> getRolesByUserName(String userName) {
        List<String> list=userDao.queryRolesByUserName(userName);
        Set<String> sets=new HashSet<String>(list);
        return sets;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
        String userName= (String) authenticationToken.getPrincipal();
        //2.通过用户名到数据库中获取凭证
        String password=getPasswordByUserName(userName);
        if(password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(userName,password,"coutemRole");
        //加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));

        return authenticationInfo;
    }


    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        User user=userDao.getUserByUsersName(userName);
        if(user!=null){
            return user.getPassword();
        }
        return null;
    }

    //计算MD5加密后的值
    public static void main(String[] args){
        Md5Hash md5Hash=new Md5Hash("1234","user");
        System.out.println(md5Hash.toString());
    }
}
