package service.user;


import pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 根据userCode查询用户
     * @param userCode
     * @return
     */
    public User getUserByUserCode(String userCode);

    /**
     * 根据条件查询用户表记录数
     * @param queryUserName
     * @param queryUserRole
     * @return
     */
    public int getUserCount(String queryUserName,int queryUserRole);

    /**
     * 根据条件查询用户列表
     * @param queryUserName     用户名字
     * @param queryUserRole     角色
     * @param currentPageNo     分页起始数
     * @param pageSize          分页结尾数
     * @return
     */
    public List<User> getUserListByPage (String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
}
