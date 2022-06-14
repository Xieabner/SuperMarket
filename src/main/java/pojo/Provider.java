package pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Provider {

  private int id;                             // id
  private String proCode;                     // 供应商编码
  private String proName;                     // 供应商名称
  private String proDesc;                     // 供应商描述
  private String proContact;                  // 供应商负责人
  private String proPhone;                    // 供应商电话
  private String proAddress;                  // 供应商地址
  private String proFax;                      // 供应商传真
  private int createdBy;                      // 创建者
  private Date creationDate;                  // 创建时间
  private Date modifyDate;                    // 更新时间
  private int modifyBy;                       // 更新者

}