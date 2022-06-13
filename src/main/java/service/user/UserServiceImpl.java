package service.user;

import dao.user.UserMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.User;

import java.util.List;

@Data
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据userCode查询用户
     * @param userCode
     * @return
     */
    public User getUserByUserCode(String userCode) {
        return userMapper.getUserByUserCode(userCode);
    }

    /**
     * 根据条件查询用户表记录数
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public int getUserCount(String queryUserName, int queryUserRole) {
        int count = 0;
        count=userMapper.getUserCount(queryUserName,queryUserRole);
        return count;
    }

    /**
     * 根据条件查询用户列表
     * @param queryUserName     用户名字
     * @param queryUserRole     角色
     * @param currentPageNo     分页起始数
     * @param pageSize          分页结尾数
     * @return
     */
    public List<User> getUserListByPage(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        List<User> userList=userMapper.getUserListByPage(queryUserName,queryUserRole,currentPageNo,pageSize);
        return userList;
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    public boolean add(User user) {
        boolean result=false;
        try {
            int count=userMapper.add(user);
            if(count>0)  //如果添加成功就返回true
                return true;
            else
                return false;//如果添加失败就返回true
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据用户ID获取用户信息
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(int id) {
        User user=null;
        try{
            user=userMapper.getUserById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            user = null;
        }
        return user;
    }


    /**
     * 修改用户信息
     * @param user
     * @return
     * @throws Exception
     */
    public boolean modify(User user) {
        boolean result=false;
        try {
            int count=userMapper.modify(user);
            if(count>0)  //如果添加成功就返回true
                return true;
            else
                return false;//如果添加失败就返回true
        }
        catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

















}
