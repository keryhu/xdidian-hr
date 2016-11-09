package com.xdidian.keryhu.company.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;


import com.xdidian.keryhu.company.domain.address.Address;
import com.xdidian.keryhu.company.domain.company.check.CheckCompanyInfoForRead;
import com.xdidian.keryhu.company.domain.company.check.CompanySignupItems;
import com.xdidian.keryhu.company.domain.company.check.Reject;
import com.xdidian.keryhu.company.domain.company.common.CheckCompanyByteItem;
import com.xdidian.keryhu.company.domain.company.common.CheckCompanyStringItem;
import com.xdidian.keryhu.company.domain.company.common.Company;
import com.xdidian.keryhu.company.domain.company.component.CompanyIndustry;
import com.xdidian.keryhu.company.domain.company.component.EnterpriseNature;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyDto;
import com.xdidian.keryhu.service.imageService.FileService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Component
@Slf4j
public class ConvertUtil {


    private final FileService fileService = new FileService();
    public Function<String, CompanyIndustry> stringToCompanyIndustry =
            x -> {
                CompanyIndustry result = null;
                switch (x) {
                    case "农、林、牧、渔业":
                        result = CompanyIndustry.NONG_LIN;
                        break;
                    case "采矿业":
                        result = CompanyIndustry.CAI_KUANG;
                        break;
                    case "制造业":
                        result = CompanyIndustry.ZHI_ZAO;
                        break;
                    case "电力、热力、燃气及水生产和供应业":
                        result = CompanyIndustry.DIAN_LI;
                        break;
                    case "建筑业":
                        result = CompanyIndustry.JIAN_ZU; //5
                        break;
                    case "批发和零售业":
                        result = CompanyIndustry.PI_FA;
                        break;
                    case "交通运输、仓储和邮政业":
                        result = CompanyIndustry.JIAO_TONG;
                        break;
                    case "住宿和餐饮业":
                        result = CompanyIndustry.ZU_SU;
                        break;
                    case "信息传输、软件和信息技术服务业":
                        result = CompanyIndustry.XIN_XI;
                        break;
                    case "金融业":               //10
                        result = CompanyIndustry.JIN_RONG;
                        break;
                    case "房地产业":
                        result = CompanyIndustry.FANG_CHAN;
                        break;
                    case "租赁和商务服务业":
                        result = CompanyIndustry.ZU_LIN;
                        break;
                    case "科学研究和技术服务业":
                        result = CompanyIndustry.KE_YAN;
                        break;
                    case "水利、环境和公共设施管理业":
                        result = CompanyIndustry.SHUI_LI;
                        break;
                    case "居民服务、修理和其他服务业":                  //15
                        result = CompanyIndustry.JU_MIN_FU_WU;
                        break;
                    case "教育":
                        result = CompanyIndustry.JIAO_YU;
                        break;
                    case "卫生和社会工作":
                        result = CompanyIndustry.WEI_SHENG;
                        break;
                    case "文化、体育和娱乐业":
                        result = CompanyIndustry.WEN_HUA;
                        break;
                    case "公共管理、社会保障和社会组织":
                        result = CompanyIndustry.GONG_GONG_GUAN_LI;
                        break;
                    case "国际组织":               //20
                        result = CompanyIndustry.GUO_JI_ZU_ZHI;
                        break;
                    default:
                        break;
                }
                return result;
            };
    public Function<String, EnterpriseNature> stringToEnterpriseNature =

            x -> {
                EnterpriseNature result = null;
                switch (x) {
                    case "政府机关/事业单位":
                        result = EnterpriseNature.SHI_YE;
                        break;
                    case "国营":
                        result = EnterpriseNature.GUO_QI;
                        break;
                    case "私营":
                        result = EnterpriseNature.SI_YING;
                        break;
                    case "中外合资":
                        result = EnterpriseNature.ZHONG_WAI;
                        break;
                    case "外资":
                        result = EnterpriseNature.WAI_ZI;
                        break;
                    case "其他":
                        result = EnterpriseNature.OTHER;
                        break;
                    default:
                        break;
                }

                return result;
            };
    public Function<NewCompanyDto, Company> newCompanyDtoToCompany =
            x -> {
                Company company = new Company();
                company.setName(x.getName());
                company.setAdminId(x.getAdminId());
                Address a = this.stringToAddress.apply(x.getAddress());
                company.setAddress(a);
                company.setFullAddress(x.getFullAddress());
                company.setRegisterTime(LocalDateTime.now());
                CompanyIndustry ci = this.stringToCompanyIndustry.apply(x.getCompanyIndustry());
                Assert.notNull(ci, "公司行业错误");
                company.setCompanyIndustry(ci);
                EnterpriseNature en = this.stringToEnterpriseNature.apply(x.getEnterpriseNature());
                Assert.notNull(en, "公司性质错误");
                company.setEnterpriseNature(en);
                return company;
            };



    // 将company 对象转为 CheckCompanyInfoForRead，方便前台会员注册完公司，查看已经注册了的公司信息，
    // 新地点的工作人员，审核公司资料的时候，查看公司信息
    public Function<Company, CheckCompanyInfoForRead> companyToCheckCompanyInfoForRead =
            x -> {
                List<Reject> rejects = x.getRejects();
                CheckCompanyInfoForRead c = new CheckCompanyInfoForRead();
                CheckCompanyStringItem name = new CheckCompanyStringItem();
                name.setValue(x.getName());
                //如果name有错误，
                addRejectsToCheckCompanyStringItem(rejects,CompanySignupItems.NAME,name);
                c.setName(name);


                CheckCompanyStringItem address = new CheckCompanyStringItem();
                String a = this.addressToString.apply(x.getAddress());
                address.setValue(a);
                addRejectsToCheckCompanyStringItem(rejects,
                        CompanySignupItems.ADDRESS,address);
                c.setAddress(address);

                CheckCompanyStringItem fullAddress = new CheckCompanyStringItem();
                fullAddress.setValue(x.getFullAddress());
                addRejectsToCheckCompanyStringItem(rejects,
                        CompanySignupItems.FULLADDRESS,fullAddress);
                c.setFullAddress(fullAddress);

                CheckCompanyStringItem companyIndustry = new CheckCompanyStringItem();
                companyIndustry.setValue(x.getCompanyIndustry().getName());
                addRejectsToCheckCompanyStringItem(rejects,
                        CompanySignupItems.COMPANY_INDUSTRY,companyIndustry);
                c.setCompanyIndustry(companyIndustry);

                CheckCompanyStringItem enterpriseNature = new CheckCompanyStringItem();
                enterpriseNature.setValue(x.getEnterpriseNature().getName());
                addRejectsToCheckCompanyStringItem(rejects,
                        CompanySignupItems.ENTERPRISE_NATURE,enterpriseNature);
                c.setEnterpriseNature(enterpriseNature);

                // 将  image path 转为 base64， 格式还是原来的图片格式

                byte[] bb = fileService.filePathToOriginalByte(x.getBusinessLicensePath());
                byte[] bi = fileService.filePathToOriginalByte(x.getIntruductionPath());

                CheckCompanyByteItem businessLicense = new CheckCompanyByteItem();
                businessLicense.setValue(bb);
                addRejectsToCheckCompanyByteItem(rejects,
                        CompanySignupItems.BUSINESS_LICENSE,businessLicense);
                c.setBusinessLicense(businessLicense);

                CheckCompanyByteItem intruduction = new CheckCompanyByteItem();
                intruduction.setValue(bi);
                addRejectsToCheckCompanyByteItem(rejects,
                        CompanySignupItems.INSTRUDUCTION,intruduction);
                c.setIntruduction(intruduction);

                //获取img 的图片格式
                c.setBusinessLicenseType(fileService.getTypeFromImgPath(x.getBusinessLicensePath()));
                c.setIntruductionType(fileService.getTypeFromImgPath(x.getIntruductionPath()));

                return c;
            };


    // string "省 ， 地级市， 县" 转 address 对象，
    public Function<String, Address> stringToAddress =
            x -> {
                Address address = new Address();
                String[] m = x.split(",");
                address.setProvince(m[0]);
                address.setCity(m[1]);
                address.setCounty(m[2]);
                return address;
            };


    //  address 对象 转 string "省 ， 地级市， 县" ，
    public Function<Address, String> addressToString =
            x -> {
                Address address = new Address();
                return new StringBuffer(x.getProvince())
                        .append(" ")
                        .append(x.getCity())
                        .append(" ")
                        .append(x.getCounty())
                        .toString();
            };




    /**
     * reject 存在的情况下，将reject 保存到CheckCompanyStringItem 里面
     * rejects  list 对象
     * @param item  公司注册可选的item
     * @param ccsi   CheckCompanyStringItem 审核公司的最基本的string对象，应用到前台
     */
   private void addRejectsToCheckCompanyStringItem(
           List<Reject> rejects,CompanySignupItems item,CheckCompanyStringItem ccsi){

       if(rejects!=null&&rejects.stream().
               anyMatch(e -> e.getItem().equals(item))){

           ccsi.setReadWrite(1);
           String msg = rejects.stream().
                   filter(e -> e.getItem().equals(item))
                   .map(Reject::getMessage)
                   .findFirst()
                   .orElse(null);
           ccsi.setRejectMsg(msg);
       }
   }

    /**
     * reject 存在的情况下，将reject 保存到CheckCompanyByteItem 里面
     * rejects  list 对象
     * @param item  公司注册可选的item
     * @param ccbi   CheckCompanyByteItem 审核公司的最基本的byte对象，应用到前台
     */
    private void addRejectsToCheckCompanyByteItem(
            List<Reject> rejects,CompanySignupItems item,CheckCompanyByteItem ccbi){

        if(rejects!=null&&rejects.stream().
                anyMatch(e -> e.getItem().equals(item))){

            ccbi.setReadWrite(1);
            String msg = rejects.stream().
                    filter(e -> e.getItem().equals(item))
                    .map(Reject::getMessage)
                    .findFirst()
                    .orElse(null);
            ccbi.setRejectMsg(msg);
        }
    }


}
