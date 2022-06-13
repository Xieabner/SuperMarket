package controller;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Role;
import pojo.User;
import service.role.RoleService;
import service.user.UserService;
import tools.PageSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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












}
