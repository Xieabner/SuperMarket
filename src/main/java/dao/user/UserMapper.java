package dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据userCode查询用户
     * @param userCode
     * @return
     */
    public User getUserByUserCode(String userCode);

    /**
     * 查记录数，用于分页底部的记录
     * @param userName
     * @param userRole
     * @return
     */
    public int getUserCount(@Param("userName")String userName, @Param("userRole")Integer userRole);

    /**
     * 根据条件查询用户列表
     * @param userName
     * @param userRole
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<User> getUserListByPage(@Param("userName")String userName,
                                        @Param("userRole")Integer userRole,
                                        @Param("currentPageNo")Integer currentPageNo,
                                        @Param("pageSize")Integer pageSize);


}
