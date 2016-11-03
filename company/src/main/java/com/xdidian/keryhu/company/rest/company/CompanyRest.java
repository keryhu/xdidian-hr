package com.xdidian.keryhu.company.rest.company;

import com.xdidian.keryhu.company.config.CreateDir;
import com.xdidian.keryhu.company.config.propertiesConfig.NewCompanyProperties;
import com.xdidian.keryhu.company.domain.company.*;
import com.xdidian.keryhu.company.domain.company.component.CompanyIndustry;
import com.xdidian.keryhu.company.domain.company.component.EnterpriseNature;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyDto;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyResolveInfo;
import com.xdidian.keryhu.company.domain.company.create.NewCompanyWaitCheckedDto;
import com.xdidian.keryhu.company.repository.CompanyRepository;
import com.xdidian.keryhu.company.service.AddressService;
import com.xdidian.keryhu.company.service.CompanyService;
import com.xdidian.keryhu.company.service.ConvertUtil;
import com.xdidian.keryhu.company.stream.NewCompanyProducer;
import com.xdidian.keryhu.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


import static com.xdidian.keryhu.util.Constants.NEW_COMPANY;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@EnableConfigurationProperties(NewCompanyProperties.class)
public class CompanyRest {

    private final CompanyRepository repository;
    private final CompanyService companyService;
    private final CreateDir createDir;
    private final ConvertUtil convertUtil;
    private final AddressService addressService;
    private final NewCompanyProperties newCompanyProperties;

    private final NewCompanyProducer newCompanyProducer;

    /**
     * 用户登录后，如果还没有公司的情况下，如果想创建公司，那么此就是一个提交的的rest
     * 第一步创建公司，需要提交的信息为： 公司名字，公司地址，公司管理员的userId，默认是当前user，公司营业执照图片)
     * <p>
     * 需要验证的信息有：
     * 1  公司名字是否已经存在，如果存在报错
     * 2  公司地址提交的是否符合要求   省份，地级市，县，是否是规定的名字
     * 3  管理员id，是否存在数据库
     * 4  营业执照 ，介绍信是否存在
     * 5  公司性质，公司行业不能位空
     * <p>
     * ## 营业执照和介绍信，不执行resize，只限制 最大不能超过500kb，原来是什么格式，还是保存成什么格式。例如
     * jpeg，还是保存为jpeg， png还是png
     * <p>
     * 6  如果注册成功后， 且审核通过。要发送  此userId 是公司管理员的 信息给  userAccount ，让userAccount更新权限。
     */


    @PostMapping("/company/createCompany")
    public ResponseEntity<?> newCompany(@RequestPart("body") final NewCompanyDto dto,
                                        @RequestPart("businessLicense") MultipartFile businessLicense,
                                        @RequestPart("intruduction") MultipartFile intruduction) throws IOException {

        Assert.notNull(businessLicense, "营业执照不能为空");
        Assert.notNull(intruduction, "介绍信不能为空");

        String businessType = businessLicense.getContentType().split("/")[1];   //获取上传文件的格式
        String instruductionType = intruduction.getContentType().split("/")[1];  // 获取上传文件的格式
        dto.setBusinessLicense(businessLicense);
        dto.setIntruduction(intruduction);

        companyService.validateNewCompanyPost(dto);

        Map<String, Boolean> map = new HashMap<String, Boolean>();
        log.info("新建公司，提交的注册信息，已经验证验证成功，提交的信息为： " + dto.toString());


        Company company = convertUtil.newCompanyDtoToCompany.apply(dto);

        // 经过String 转enum后，再次验证他们的结果
        Assert.notNull(company.getCompanyIndustry(), "公司行业不正确！");
        Assert.notNull(company.getEnterpriseNature(), "公司性质不正确！");

        String businessLicenseDir = new StringBuffer(createDir.getCompanyInfo())
                .append("/")
                .append(company.getId())
                .append("/businessLicense")
                .toString();

        //介绍信图片保存的 文件夹
        String intruductionDir = new StringBuffer(createDir.getCompanyInfo())
                .append("/")
                .append(company.getId())
                .append("/intruduction")
                .toString();

        File bf = new File(businessLicenseDir);
        if (!bf.exists()) {
            bf.mkdirs();  //创建多层目录，包含子目录
        }

        File inf = new File(intruductionDir);
        if (!inf.exists()) {
            inf.mkdirs();  //创建多层目录，包含子目录
        }

        // 设置各自的 文件全名，包含了  png名字
        String businessLicenseImgPath = new StringBuffer(businessLicenseDir)
                .append("/")
                .append(System.currentTimeMillis())
                .append(".")
                .append(businessType)
                .toString();


        String intruductionImgPath = new StringBuffer(intruductionDir)
                .append("/")
                .append(dto.getAdminId())
                .append(".")
                .append(instruductionType)
                .toString();

        BufferedImage businessSource = ImageIO.read(businessLicense.getInputStream());
        BufferedImage intruductionSource = ImageIO.read(intruduction.getInputStream());

        //保存到本地
        ImageIO.write(businessSource, businessType, new File(businessLicenseImgPath));
        ImageIO.write(intruductionSource, instruductionType, new File(intruductionImgPath));


        company.setBusinessLicensePath(businessLicenseImgPath);
        company.setIntruductionPath(intruductionImgPath);

        repository.save(company);

        map.put("result", true);

        //发送message给websocket app
        newCompanyProducer.send(NEW_COMPANY);

        return ResponseEntity.ok(map);
    }


    /**
     * 公司注册时候，查看公司名字是否已经注册过，还有申请加入公司的时候，查看公司名字是否存在
     *
     * @param name
     * @return
     */

    @GetMapping("/company/findCompanyExistByName")
    public ResponseEntity<?> findCompanyExistByName(@RequestParam("name") String name) {

        boolean e = repository.findByName(name).isPresent();
        return ResponseEntity.ok(e);
    }



    /**
     * 当打开  新建 公司帐户 页面的时候，，首先需要 加载 3个信息数据
     * 1  所有的 省市，直辖市的 数据
     * 2  所有的公司行业数据
     * 3  所有的公司性质数据
     *
     * @return
     */

    @GetMapping("/company/createCompanyResolveInfo")
    public ResponseEntity<?> NewCompanyResolveInfo() {

        NewCompanyResolveInfo info = new NewCompanyResolveInfo();

        //加入所有的省份
        info.setProvinces(addressService.getProvinces());

        // 加入所有的 行业名字。

        List<String> cc = new ArrayList<String>();

        for (CompanyIndustry c : CompanyIndustry.values()) {
            cc.add(c.getName());
        }
        info.setCompanyIndustries(cc);

        // 加入所有 的 公司性质数据
        List<String> ee = new ArrayList<String>();
        for (EnterpriseNature e : EnterpriseNature.values()) {
            ee.add(e.getName());
        }

        info.setEnterpriseNatures(ee);

        String adminId = SecurityUtils.getCurrentLogin();

        //该用户 注册 公司 数量不能超过 规定的最大数量。
        long newCompanyQuantity = repository.findByAdminId(adminId)
                .stream()
                .filter(n -> n != null)
                .count();

        boolean hasRegister = repository.findByAdminId(adminId)
                .stream()
                .filter(n -> n != null)
                .anyMatch(n -> !n.isChecked());


        String msg = new StringBuffer("最多只能注册")
                .append(newCompanyProperties.getMaxNewCompanyQuantity())
                .append("个公司,您的帐户已经超过限制！")
                .toString();

        if (hasRegister) {
            info.setNewCompanyErrMsg("您已经提交过公司帐户申请，请等待审核！");
        } else if (newCompanyQuantity > newCompanyProperties.getMaxNewCompanyQuantity()) {
            info.setNewCompanyErrMsg(msg);
        } else {
            info.setNewCompanyErrMsg("");
        }

        return ResponseEntity.ok(info);

    }


    //用在 用户刚刚登录  后，点击"新建公司"，查看 刚用户是否注册过  公司帐户，是否存在需要 审核的 公司帐户。如果存在未审核
    // 的公司帐户，取出companyId，然后前台再通过，companyId，查看已经提交的公司信息，查看新地点工作人员有没有
    // 审核该公司，有没有拒绝的理由。  返回companyId 和 checked，如果companyId存在，且checked为false，那么就进行上面的判断


    //客户自己，当点击 新建公司的时候，首先需要 查看系统中是否存在 未审核的自己注册的公司。
    @GetMapping("/company/findUncheckedCompanyBySelf")
    public ResponseEntity<?> companyChecked() {

        // 将useId转为 string 数组。
        String id = SecurityUtils.getCurrentLogin();
        String[] ids = {id};
        log.info("id is : " + ids[0]);

        NewCompanyWaitCheckedDto dtos =
                companyService.findUncheckedCompany(ids)
                        .stream()
                        .findFirst()
                        .orElse(null);

        return ResponseEntity.ok(dtos);
    }


}