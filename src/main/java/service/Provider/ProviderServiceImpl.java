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



}
