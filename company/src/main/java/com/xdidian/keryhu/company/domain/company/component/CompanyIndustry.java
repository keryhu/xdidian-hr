package com.xdidian.keryhu.company.domain.company.component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hushuming on 2016/9/23.
 * <p>
 * 主要的 行业分类。
 */


public enum CompanyIndustry {

    NONG_LIN,  //  农、林、牧、渔业
    CAI_KUANG,  //  采矿业
    ZHI_ZAO,    //  制造业
    DIAN_LI,    //  电力、热力、燃气及水生产和供应业
    JIAN_ZU,     //   建筑业                             5
    PI_FA,      //    批发和零售业
    JIAO_TONG,    //   交通运输、仓储和邮政业
    ZU_SU,       //     住宿和餐饮业
    XIN_XI,       //    信息传输、软件和信息技术服务业
    JIN_RONG,     //     金融业                         10
    FANG_CHAN,    //     房地产业
    ZU_LIN,       //     租赁和商务服务业
    KE_YAN,       //      科学研究和技术服务业
    SHUI_LI,       //      水利、环境和公共设施管理业
    JU_MIN_FU_WU,    //    居民服务、修理和其他服务业     15
    JIAO_YU,        //     教育
    WEI_SHENG,        //    卫生和社会工作
    WEN_HUA,            //  文化、体育和娱乐业
    GONG_GONG_GUAN_LI,    //    公共管理、社会保障和社会组织
    GUO_JI_ZU_ZHI;        //    国际组织               20

    private static Map<String, CompanyIndustry> companyIndustryMap =
            new HashMap<String, CompanyIndustry>(20);

    static {
        companyIndustryMap.put("NONG_LIN", NONG_LIN);
        companyIndustryMap.put("CAI_KUANG", CAI_KUANG);
        companyIndustryMap.put("ZHI_ZAO", ZHI_ZAO);
        companyIndustryMap.put("DIAN_LI", DIAN_LI);
        companyIndustryMap.put("JIAN_ZU", JIAN_ZU);
        companyIndustryMap.put("PI_FA", PI_FA);
        companyIndustryMap.put("JIAO_TONG", JIAO_TONG);
        companyIndustryMap.put("ZU_SU", ZU_SU);
        companyIndustryMap.put("XIN_XI", XIN_XI);
        companyIndustryMap.put("JIN_RONG", JIN_RONG);
        companyIndustryMap.put("FANG_CHAN", FANG_CHAN);
        companyIndustryMap.put("ZU_LIN", ZU_LIN);
        companyIndustryMap.put("KE_YAN", KE_YAN);
        companyIndustryMap.put("SHUI_LI", SHUI_LI);
        companyIndustryMap.put("JU_MIN_FU_WU", JU_MIN_FU_WU);
        companyIndustryMap.put("JIAO_YU", JIAO_YU);
        companyIndustryMap.put("WEI_SHENG", WEI_SHENG);
        companyIndustryMap.put("WEN_HUA", WEN_HUA);
        companyIndustryMap.put("GONG_GONG_GUAN_LI", GONG_GONG_GUAN_LI);
        companyIndustryMap.put("GUO_JI_ZU_ZHI", GUO_JI_ZU_ZHI);
    }


    @JsonCreator
    public static CompanyIndustry forValue(String value) {
        return companyIndustryMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, CompanyIndustry> companyIndustry : companyIndustryMap.entrySet()) {
            if (companyIndustry.getValue() == this)
                return companyIndustry.getKey();
        }
        return null;
    }

    public String getName() {
        CompanyIndustry companyIndustry = this;
        String result = "";
        switch (companyIndustry) {
            case NONG_LIN:
                result = "农、林、牧、渔业";
                break;
            case CAI_KUANG:
                result = "采矿业";
                break;
            case ZHI_ZAO:
                result = "制造业";
                break;
            case DIAN_LI:
                result = "电力、热力、燃气及水生产和供应业";
                break;
            case JIAN_ZU:
                result = "建筑业";   //5
                break;
            case PI_FA:
                result = "批发和零售业";
                break;
            case JIAO_TONG:
                result = "交通运输、仓储和邮政业";
                break;
            case ZU_SU:
                result = "住宿和餐饮业";
                break;
            case XIN_XI:
                result = "信息传输、软件和信息技术服务业";
                break;
            case JIN_RONG:               //10
                result = "金融业";
                break;
            case FANG_CHAN:
                result = "房地产业";
                break;
            case ZU_LIN:
                result = "租赁和商务服务业";
                break;
            case KE_YAN:
                result = "科学研究和技术服务业";
                break;
            case SHUI_LI:
                result = "水利、环境和公共设施管理业";
                break;
            case JU_MIN_FU_WU:                  //15
                result = "居民服务、修理和其他服务业";
                break;
            case JIAO_YU:
                result = "教育";
                break;
            case WEI_SHENG:
                result = "卫生和社会工作";
                break;
            case WEN_HUA:
                result = "文化、体育和娱乐业";
                break;
            case GONG_GONG_GUAN_LI:
                result = "公共管理、社会保障和社会组织";
                break;
            case GUO_JI_ZU_ZHI:               //20
                result = "国际组织";
                break;
            default:
                break;
        }

        return result;
    }


}
