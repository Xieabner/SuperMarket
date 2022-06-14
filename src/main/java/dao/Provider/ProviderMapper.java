package dao.Provider;


import org.apache.ibatis.annotations.Param;
import pojo.Provider;

import java.util.List;

public interface ProviderMapper {

    /**
     * 通过条件查询供应商记录数
     * @param proName
     * @param proCode
     * @return
     */
    public int getProviderCount(@Param("proName") String proName,@Param("proCode") String proCode);

    /**
     * 通过条件查询分页providerListByPage
     * @param proName
     * @param proCode
     * @param currentPageNo
     * @param pageSize
     * @return
     */
    public List<Provider> getProviderListByPage(@Param("proName") String proName,
                                          @Param("proCode") String proCode,
                                          @Param("currentPageNo") Integer currentPageNo,
                                          @Param("pageSize") Integer pageSize);















    /**
     * 增加供应商信息
     * @param provider
     * @return
     */
    public int add(@Param("provider") Provider provider);


    /**
     * 获取供应商列表
     * @return
     */
    public List<Provider> getProList();


    /**
     * 通过供应商Id删除供应商信息
     * @param delId
     * @return
     */
    public int deleteProviderById(@Param("id") Integer delId);


    /**
     * 根据供应商Id获取供应商信息
     * @param id
     * @return
     */
    public Provider getProviderById(@Param("id") Integer id);


    /**
     * 修改供应商
     * @param provider
     * @return
     */
    public int modify(Provider provider);


}
