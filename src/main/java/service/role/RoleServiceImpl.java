package service.role;

import dao.role.RoleMapper;
import lombok.Data;
import org.springframework.stereotype.Service;
import pojo.Role;

import javax.annotation.Resource;
import java.util.List;

@Data
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper; //引用前面的RoleMapper

    public List<Role> getRoleList() {
        List<Role> roleList = roleMapper.getRoleList();
        return roleList;
    }
}
