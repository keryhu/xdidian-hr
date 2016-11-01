package com.xdidian.keryhu.company.domain.company.create;


import com.xdidian.keryhu.company.domain.address.Address;
import com.xdidian.keryhu.company.domain.company.check.Reject;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hushuming on 2016/9/27.
 *
 * 新建的公司，等待审核，或者已经被审核过的情况下，在用户登录登录后，点击新建公司帐户，
 * 如果，该用户id已经存在新建的  公司帐户，则返回前台该dto信息。
 */

@Data
@NoArgsConstructor
public class NewCompanyWaitCheckedDto implements Serializable {

    private String name;   //公司名字
    private Address address;   //包含省份,地级市，县的address
    private String fullAddress;   // 自定义的address 全地址。

    private String companyIndustry;    //公司行业

    private String enterpriseNature;   // 企业性质

    private byte[] businessLicense;  //营业执照的图片

    private byte[] intruduction;     //介绍信的图片

    private String businessLicenseType;      // 营业执照的图片格式

    private String intruductionType;    //介绍信的图片格式

    private List<Reject> rejects;           // 审核被拒绝的理由。

}
