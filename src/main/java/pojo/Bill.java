package pojo;


import lombok.Data;

import java.util.Date;

@Data
public class Bill {

  private int id;                 // id
  private String billCode;        // 票据代码
  private String productName;     // 产品名字
  private String productDesc;     // 产品描述
  private String productUnit;     // 产品单位
  private double productCount;    // 产品数量
  private double totalPrice;      // 总价
  private int isPayment;          //
  private int createdBy;          // 创建者
  private Date creationDate;      // 创建时间
  private int modifyBy;           // 更新者
  private Date modifyDate;        // 更新时间
  private int providerId;         // 供应商id


}
