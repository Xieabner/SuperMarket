package service.Provider;

import dao.Provider.ProviderMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Provider;
import pojo.User;

import java.util.List;


@Data
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderMapper providerMapper;

    /**
     * 根据条件查询供应商表记录数
     * @param proName
     * @param proCode
     * @return
     */
    public int getProviderCount(String proName,String proCode) {
        int count = 0;
        count=providerMapper.getProviderCount(proName,proCode);
        return count;
    }


    /**
     * 通过条件查询分页providerListByPage
     * @param proName
     * @param proCode
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<Provider> getProviderListByPage(String proName, String proCode, Integer currentPageNo, Integer pageSize) {
        List<Provider> providerList = providerMapper.getProviderListByPage(proName,proCode,currentPageNo,pageSize);
        return providerList;
    }

    /**
     * 添加供应商
     * @param provider
     * @return
     */
    public boolean add(Provider provider) {
        boolean result=false;
        try {
            int count=providerMapper.add(provider);
            System.out.println(count);
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
    public Provider getProviderById(int id) {
        Provider provider= null;
        try {
            provider = providerMapper.getProviderById(id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return provider;
    }

    /**
     * 修改供应商信息
     * @param provider
     * @return
     */
    public boolean modify(Provider provider) {
        boolean result=false;
        try {
            int count=providerMapper.modify(provider);
            if(count>0)  //如果添加成功就返回true
                return true;
            else
                return false;//如果添加失败就返回true
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除供应商
     * @param id
     * @return
     */
    public boolean deleteProviderById(Integer id) {
        boolean result = false;
        try {
            if(providerMapper.deleteProviderById(id) > 0){
                return true;//删除成功
            }else
                return false;//删除失败
        }catch(Exception e){
            e.printStackTrace();

        }
        return false;
    }


}
