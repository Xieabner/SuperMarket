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


    public User getUserByUserCode(String userCode) {
        return userMapper.getUserByUserCode(userCode);
    }

    public int getUserCount(String queryUserName, int queryUserRole) {
        int count = 0;
        count=userMapper.getUserCount(queryUserName,queryUserRole);
        return count;
    }


    public List<User> getUserListByPage(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        List<User> userList=userMapper.getUserListByPage(queryUserName,queryUserRole,currentPageNo,pageSize);
        return userList;
    }
}
