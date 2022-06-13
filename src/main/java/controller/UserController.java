package controller;


import com.alibaba.fastjson.JSONArray;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Role;
import pojo.User;
import service.role.RoleService;
import service.user.UserService;
import tools.PageSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private UserService userService; //引用前面的service

    @Resource
    private RoleService roleService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value="/login")
    public String login(){
        System.out.println("进入登录页面");
        return "login";
    }

    /**
     * 登录验证
     * @param userCode
     * @param userPassword
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value="/dologin")
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, Model model, HttpSession session){
        //读取用户和密码
        System.out.println("帐号和密码是"+userCode+"-"+userPassword);
        User user;
        user = userService.getUserByUserCode(userCode);
        if(user!=null) { //如果有读到值
            if (userPassword.equals(user.getUserPassword())) {
                session.setAttribute("user", user);//添加session值
                return "redirect:/main"; //密码正确就去/main
            }else {
                //登录失败就回到login.jsp
                model.addAttribute("error", "密码不正确");
                return "login";
            }
        }
        else
        {
            //登录失败就回到login.jsp
            model.addAttribute("error", "用户名不正确");
            return "login";
        }
    }
    @RequestMapping(value="/main")
    public String welcome(HttpSession session)  {
        if(session.getAttribute("user") == null){ //如果用户没有登录就直接来到main.html就回到login
            return "redirect:/syserror";
        }
        else
        return "welcome";
    }


    /**
     * 登出功能
     * @param session
     * @return
     */
    @RequestMapping(value="/logout")  //退出登录页面
    public String logout(HttpSession session){
        session.removeAttribute("user"); //清除掉Session中user的值
        return "redirect:/login"; //回到login.sjp
    }

    /**
     * 出错页面
     * @return
     */
    @RequestMapping("/syserror") //出错页面
    public String sysError(){
        return "syserror";
    }


    /**
     * 分页查询
     * @param model
     * @param session
     * @param queryUserName
     * @param queryUserRole
     * @param pageIndex
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/userlist")
    public String getUserList(Model model,HttpSession session,
                              @RequestParam(value="queryname",required=false) String queryUserName,
                              @RequestParam(value="queryUserRole",required=false) String queryUserRole,
                              @RequestParam(value="pageIndex",required=false) String pageIndex) throws Exception{

        if(session.getAttribute("user") == null){
            //如果用户没有登录就直接来到userlist就回到syserror
            return "redirect:/syserror";
        }
        int _queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = 5;
        //当前页码
        int currentPageNo = 1;

        if(queryUserName == null){
            queryUserName = "";
        }
        if(queryUserRole != null && !queryUserRole.equals("")){
            _queryUserRole = Integer.parseInt(queryUserRole);
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/syserror";
            }
        }
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,_queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();
//        int start = 0;
        //控制首页和尾页

        //设置分页的每一页的显示从哪里开始
        int start = ((currentPageNo-1) * pageSize);

        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        //这里的查询limit需要进行改动
        userList = userService.getUserListByPage(queryUserName,_queryUserRole,start,pageSize);
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }


    /**
     * 当用户点击“添加用户”的时候，打开这个链接，并通过它转向对应的useradd.jsp页面
     * @return
     */
    @RequestMapping(value="/useradd")
    public String addUser(){
        return "useradd";
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping(value="/useraddsave",method= RequestMethod.POST)
    public String addUserSave(User user,HttpSession session){
        //添加用户表的createby值：
        user.setCreatedBy(((User)session.getAttribute("user")).getId());
        //添加用户表的createdate值：
        user.setCreationDate(new Date());
        if(userService.add(user)==true){ //如果添加成功就返回userlist
            return "redirect:/userlist";
        }
        return "useradd"; //添加不成功留在useradd
    }

    @RequestMapping(value="/usermodify")
    public String getUserById(@RequestParam int uid,Model model){
        User user = userService.getUserById(uid);
        model.addAttribute(user);
        return "usermodify";
    }

    @RequestMapping(value="/usermodifysave",method=RequestMethod.POST)
    public String modifyUserSave(User user,HttpSession session){
        user.setModifyBy(((User)session.getAttribute("user")).getId());
        user.setModifyDate(new Date());
        if(userService.modify(user)){
            return "redirect:/userlist";
        }
        return "usermodify";
    }


    /**
     * 查看用户
     */
    @RequestMapping(value="/userview")
    public String view(@RequestParam int uid,Model model){
        User user = userService.getUserById(uid);
        model.addAttribute(user);
        return "userview";
    }

    /**
     * 判断是否存在userCode
     * @userCode
     */
    @RequestMapping(value="/ucexist")
    @ResponseBody
    public Object ucexist(@RequestParam String userCode){
        String data="{\"userCode\":\"noexist\"}";  //初始化字符串
        if(userCode==null||userCode.length()==0){  //如果userCode是空值
            data="{\"userCode\":\"exist\"}";  //空值直接返回已存在
        }
        else{
            User user = userService.selectUserCodeExist(userCode);
            if(user!=null)
                data="{\"userCode\":\"exist\"}";
            else
                data="{\"userCode\":\"noexist\"}";
        }
        return JSONArray.toJSONString(data);//将data转为json对象,并将结果发回给当前页面
    }

    /**
     * 删除用户数据
     * @param uid
     * @return
     */
    @RequestMapping(value="/deluser")
    @ResponseBody
    public Object deluser(@RequestParam int uid){
        String data="{\"delResult\":\"false\"}";  //初始化字符串
        boolean result= userService.deleteUserById(uid);
        if(result==true)
            data="{\"delResult\":\"true\"}"; //删除成功
        else
            data="{\"delResult\":\"false\"}"; //删除失败
        return JSONArray.toJSONString(data);//将data转为json对象,并将结果发回给当前页面
    }


}
