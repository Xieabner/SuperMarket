package controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.Provider;
import pojo.User;
import service.Provider.ProviderService;
import tools.PageSupport;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


@Controller
public class ProviderController {


    @Resource
    private ProviderService providerService;


    @RequestMapping(value="/providerList")
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


    /**
     * 当用户点击“添加供应商”的时候，打开这个链接，并通过它转向对应的provideradd.jsp.jsp页面
     * @return
     */
    @RequestMapping(value="/providerAdd")
    public String addUser(){
        return "provideradd";
    }
    @RequestMapping(value="/providerAddSave",method= RequestMethod.POST)
    public String addPoviderSave(Provider provider,HttpSession session){
        //添加供应商的createby值：
        provider.setCreatedBy(((User)session.getAttribute("user")).getId());
        //添加供应商的createdate值：
        provider.setCreationDate(new Date());

        if(providerService.add(provider)==true){
            //如果添加成功就返回userlist
            return "redirect:/providerList";
        }
        return "provideradd"; //添加不成功留在provideradd
    }

    /**
     * 查看用户
     */
    @RequestMapping(value="/providerview")
    public String view(@RequestParam int proid,Model model){
        Provider provider = providerService.getProviderById(proid);
        model.addAttribute(provider);
        return "providerview";
    }

    @RequestMapping(value="/providermodify")
    public String getProviderById(@RequestParam int id,Model model){
        Provider provider = providerService.getProviderById(id);
        model.addAttribute(provider);
        return "providermodify";
    }

    @RequestMapping(value="/providerModifySave",method=RequestMethod.POST)
    public String modifyProviderSave(Provider provider,HttpSession session){
        provider.setModifyBy(((User)session.getAttribute("user")).getId());
        provider.setModifyDate(new Date());
        if(providerService.modify(provider)){
            return "redirect:/providerList";
        }
        return "providermodify";
    }


    //删除用户数据
    @RequestMapping(value="/deleteProvider")
    @ResponseBody
    public Object deluser(@RequestParam int id){
        String data="{\"delResult\":\"false\"}";  //初始化字符串
        boolean result= providerService.deleteProviderById(id);
        if(result==true)
            data="{\"delResult\":\"true\"}"; //删除成功
        else
            data="{\"delResult\":\"false\"}"; //删除失败
        return JSONArray.toJSONString(data);//将data转为json对象,并将结果发回给当前页面
    }

}
