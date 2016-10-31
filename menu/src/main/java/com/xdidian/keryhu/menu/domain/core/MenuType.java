package com.xdidian.keryhu.menu.domain.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author keryhu keryhu@hotmail.com
 * @ClassName: MenuType
 * 平台可能的菜单种类, 目前一共是18个菜单名字
 *, 将url硬编码，因为url不常更改。)
 * @date 2016年8月9日 上午11:13:23
 */
public enum MenuType {
    HOME, // 会员首页
    COMPANY_INFO, // 公司信息
    RECRUIT_APPLY,         //招聘应聘
    INTERVIEW_MANAGEMENT,   //面试管理
    SUBORDINATE_MANAGEMENT, // 下属管理   5
    AUTHORITY_MANAGEMENT, //权限管理
    TALENT_MANAGEMENT,      // 人才管理
    CAREER_PLANNING, // 职业规划
    PERFORMANCE_APPRAISAL, // 绩效考核
    ATTENDANCE_SALARY, // 考勤薪资        10
    RELEASE_MANAGEMENT, // 发布管理
    REPORT_TRAINING, // 报表培训
    INNOVATION_SUGGESTIONS, // 创新建议
    COST_ACCOUNT,         //成本核算
    RECHARGE_PAYMENT,    //充值付费        15
    JOIN_COMPANY, // 加入公司
    CREATE_COMPANY, // 创建公司
    PERSONAL_SET, // 个人设置

    SERVICE_HOME,     //    客服首页
    CHECK_COMPANY,   //   审核公司         20
    RECHARGE,      //     充值
    SERVICE_SET,    //    客服设置
    ADD_SERVICE,    //     录入客服            只有新地点的管理员，才能有这个权限，客服人员无
    DEL_SERVICE;   //    编辑客服


    private static Map<String, MenuType> menuTypeMap = new HashMap<String, MenuType>(24);

    static {
        menuTypeMap.put("HOME", HOME);
        menuTypeMap.put("COMPANY_INFO", COMPANY_INFO);
        menuTypeMap.put("RECRUIT_APPLY", RECRUIT_APPLY);
        menuTypeMap.put("INTERVIEW_MANAGEMENT", INTERVIEW_MANAGEMENT);
        menuTypeMap.put("SUBORDINATE_MANAGEMENT", SUBORDINATE_MANAGEMENT);   //5
        menuTypeMap.put("AUTHORITY_MANAGEMENT", AUTHORITY_MANAGEMENT);
        menuTypeMap.put("TALENT_MANAGEMENT", TALENT_MANAGEMENT);
        menuTypeMap.put("CAREER_PLANNING", CAREER_PLANNING);
        menuTypeMap.put("PERFORMANCE_APPRAISAL", PERFORMANCE_APPRAISAL);
        menuTypeMap.put("ATTENDANCE_SALARY", ATTENDANCE_SALARY);              //10
        menuTypeMap.put("RELEASE_MANAGEMENT", RELEASE_MANAGEMENT);
        menuTypeMap.put("REPORT_TRAINING", REPORT_TRAINING);
        menuTypeMap.put("INNOVATION_SUGGESTIONS", INNOVATION_SUGGESTIONS);
        menuTypeMap.put("COST_ACCOUNT", COST_ACCOUNT);
        menuTypeMap.put("RECHARGE_PAYMENT", RECHARGE_PAYMENT);                //15
        menuTypeMap.put("JOIN_COMPANY", JOIN_COMPANY);
        menuTypeMap.put("CREATE_COMPANY", CREATE_COMPANY);
        menuTypeMap.put("PERSONAL_SET", PERSONAL_SET);
        menuTypeMap.put("SERVICE_HOME", SERVICE_HOME);
        menuTypeMap.put("CHECK_COMPANY", CHECK_COMPANY);                         //20
        menuTypeMap.put("RECHARGE", RECHARGE);
        menuTypeMap.put("SERVICE_SET", SERVICE_SET);
        menuTypeMap.put("ADD_SERVICE", ADD_SERVICE);
        menuTypeMap.put("DEL_SERVICE", DEL_SERVICE);

    }

    @JsonCreator
    public static MenuType forValue(String value) {
        return menuTypeMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Entry<String, MenuType> menu : menuTypeMap.entrySet()) {
            if (menu.getValue() == this)
                return menu.getKey();
        }
        return null;
    }

    /**
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getName
     * 根据不同的菜单类型，得到不同的中文含义，用于前台客户端，显示菜单的中文名字。)
     */

    public String getName() {
        MenuType menu = this;
        String result = "";
        switch (menu) {
            case HOME:
                result = "会员首页";
                break;
            case COMPANY_INFO:
                result = "公司信息";
                break;
            case RECRUIT_APPLY:
                result = "招聘应聘";
                break;
            case INTERVIEW_MANAGEMENT:
                result = "面试管理";
                break;
            case SUBORDINATE_MANAGEMENT:             //5
                result = "下属管理";
                break;
            case AUTHORITY_MANAGEMENT:
                result = "权限管理";
                break;
            case TALENT_MANAGEMENT:
                result = "人才管理";
                break;
            case CAREER_PLANNING:
                result = "职业规划";
                break;
            case PERFORMANCE_APPRAISAL:
                result = "绩效考核";
                break;
            case ATTENDANCE_SALARY:                   //10
                result = "考勤薪资";
                break;
            case RELEASE_MANAGEMENT:
                result = "发布管理";
                break;
            case REPORT_TRAINING:
                result = "报表培训";
                break;
            case INNOVATION_SUGGESTIONS:
                result = "创新建议";
                break;
            case COST_ACCOUNT:
                result = "成本核算";
                break;
            case RECHARGE_PAYMENT:                      //15
                result = "充值付费";
                break;
            case JOIN_COMPANY:
                result = "加入公司";
                break;
            case CREATE_COMPANY:
                result = "创建公司";
                break;
            case PERSONAL_SET:
                result = "个人设置";
                break;
            case SERVICE_HOME:
                result = "客服首页";
                break;
            case CHECK_COMPANY:                           //20
                result = "审核公司";
                break;
            case RECHARGE:
                result = "充值";
                break;
            case SERVICE_SET:
                result = "客服设置";
                break;
            case ADD_SERVICE:
                result="录入客服";
                break;
            case DEL_SERVICE:
                result = "删除客服";
                break;

            default:
                break;
        }
        return result;

    }

    /**
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getUrl
     * 不同的菜单导航到不同的url，右边显示该url对应的页面)
     */
    public String getUrl() {
        MenuType menu = this;
        String result = "";
        switch (menu) {
            case HOME:
                result = "/profile/home";
                break;
            case COMPANY_INFO:
                result = "/profile/companyInfo";
                break;
            case RECRUIT_APPLY:
                result = "/profile/recruit-apply";
                break;
            case INTERVIEW_MANAGEMENT:
                result = "/profile/interview-management";
                break;
            case SUBORDINATE_MANAGEMENT:
                result = "/profile/subordinate-management";        //5
                break;
            case AUTHORITY_MANAGEMENT:
                result = "/profile/authority-management";
                break;
            case TALENT_MANAGEMENT:
                result = "/profile/talent-management";
                break;
            case CAREER_PLANNING:
                result = "/profile/career-planning";
                break;
            case PERFORMANCE_APPRAISAL:
                result = "/profile/performance-appraisal";
                break;
            case ATTENDANCE_SALARY:
                result = "/profile/attendance-salary";               //10
                break;
            case RELEASE_MANAGEMENT:
                result = "/profile/release-management";
                break;
            case REPORT_TRAINING:
                result = "/profile/report-training";
                break;
            case INNOVATION_SUGGESTIONS:
                result = "/profile/innovation-suggestions";
                break;
            case COST_ACCOUNT:
                result = "/profile/cost-account";
                break;
            case RECHARGE_PAYMENT:                                    //15
                result = "/profile/recharge-payment";
                break;
            case JOIN_COMPANY:
                result = "/profile/join-company";
                break;
            case CREATE_COMPANY:
                result = "/profile/create-company";
                break;
            case PERSONAL_SET:
                result = "/profile/personal-set";
                break;
            case SERVICE_HOME:
                result = "/service/home";
                break;
            case CHECK_COMPANY:                                         //20
                result = "/service/check-company";
                break;
            case RECHARGE:
                result = "/service/recharge";
                break;
            case SERVICE_SET:
                result = "/service/service-set";
                break;
            case ADD_SERVICE:
                result = "/service/add-service";
                break;
            case DEL_SERVICE:
                result = "/service/del-service";
                break;
            default:
                break;
        }
        return result;
    }


    public int getId() {
        MenuType menu = this;
        int result = 0;
        switch (menu) {
            case HOME:
                result = 1;
                break;
            case COMPANY_INFO:
                result = 2;
                break;
            case RECRUIT_APPLY:
                result = 3;
                break;
            case INTERVIEW_MANAGEMENT:
                result = 4;
                break;
            case SUBORDINATE_MANAGEMENT:               //5
                result = 5;
                break;
            case AUTHORITY_MANAGEMENT:
                result = 6;
                break;
            case TALENT_MANAGEMENT:
                result = 7;
                break;
            case CAREER_PLANNING:
                result = 8;
                break;
            case PERFORMANCE_APPRAISAL:
                result = 9;
                break;
            case ATTENDANCE_SALARY:                         //10
                result = 10;
                break;
            case RELEASE_MANAGEMENT:
                result = 11;
                break;
            case REPORT_TRAINING:
                result = 12;
                break;
            case INNOVATION_SUGGESTIONS:
                result = 13;
                break;
            case COST_ACCOUNT:
                result = 14;
                break;
            case RECHARGE_PAYMENT:                      //15
                result = 15;
                break;
            case JOIN_COMPANY:
                result = 16;
                break;
            case CREATE_COMPANY:
                result = 17;
                break;
            case PERSONAL_SET:
                result = 18;
                break;
            case SERVICE_HOME:
                result = 30;
                break;
            case CHECK_COMPANY:                        //20
                result = 31;
                break;
            case RECHARGE:
                result = 32;
                break;
            case SERVICE_SET:
                result = 33;
                break;
            case ADD_SERVICE:
                result = 90;
                break;
            case DEL_SERVICE:                          //25
                result = 91;
                break;
            default:
                break;
        }
        return result;
    }

}
