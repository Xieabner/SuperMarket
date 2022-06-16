package service.Provider;


import org.apache.ibatis.annotations.Param;
import pojo.Provider;

import java.util.List;

public interface ProviderService {


    /**
     * 根据条件查询供应商表记录数
     * @param proName
     * @param proCode
     * @return
     */
    public int getProviderCount(String proName,String proCode);


    /**
     * 通过条件查询分页providerListByPage
     * @param proName
     * @param proCode
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<Provider> getProviderListByPage (String proName,String proCode,Integer currentPageNo,Integer pageSize);

    /**
     * 添加供应商
     * @param provider
     * @return
     */
    public boolean add(Provider provider);

    /**
     * 根据供应商Id获取供应商信息
     * @param id
     * @return
     */
    public Provider getProviderById(int id);

    /**
     * 修改供应商
     * @param provider
     * @return
     */
    public boolean modify(Provider provider);


    /**
     * 删除供应商
     * @param id
     * @return
     */
    public boolean deleteProviderById(Integer id);








}
