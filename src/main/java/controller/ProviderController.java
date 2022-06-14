package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Provider;
import pojo.User;
import service.Provider.ProviderService;
import tools.PageSupport;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class ProviderController {


    @Resource
    private ProviderService providerService;


    @RequestMapping(value="/Providerlist")
    public String getUserList(Model model, HttpSession session,
                              @RequestParam(value="queryname",required=false) String proName,
                              @RequestParam(value="querycode",required=false) String proCode,
                              @RequestParam(value="pageIndex",required=false) String pageIndex) throws Exception{

        if(session.getAttribute("user") == null){
            //如果用户没有登录就直接来到userlist就回到syserror
            return "redirect:/syserror";
        }

        List<Provider> providerList = null;
        //设置页面容量
        int pageSize = 5;
        //当前页码
        int currentPageNo = 1;

        if(proName == null){
            proName = "";
        }
        if(proCode == null){
            proCode = "";
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/syserror";
            }
        }

        //总数量（表）
        int totalCount = providerService.getProviderCount(proName,proCode);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();

        //设置分页的每一页的显示从哪里开始
        int start = ((currentPageNo-1) * pageSize);

        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        providerList = providerService.getProviderListByPage(proName,proCode,start,pageSize);
        model.addAttribute("providerList",providerList);
        model.addAttribute("proName",proName);
        model.addAttribute("proCode",proCode);
        model.addAttribute("totalPageCount",totalPageCount);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("currentPageNo",currentPageNo);


        return "Providerlist";
    }








}
