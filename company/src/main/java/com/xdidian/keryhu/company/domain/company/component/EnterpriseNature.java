package com.xdidian.keryhu.company.domain.company.component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hushuming on 2016/9/23.
 *
 * 企业性质
 */
public enum EnterpriseNature {

    SHI_YE,   // 政府机关/事业单位
    GUO_QI,   //   国营
    SI_YING,    //  私营
    ZHONG_WAI,   //  中外合资
    WAI_ZI,      //  外资
    OTHER;      //   其他

    private static Map<String, EnterpriseNature > enterpriseNatureMap =
            new HashMap<String, EnterpriseNature >(6);

    static {
        enterpriseNatureMap.put("SHI_YE",SHI_YE);
        enterpriseNatureMap.put("GUO_QI",GUO_QI);
        enterpriseNatureMap.put("SI_YING",SI_YING);
        enterpriseNatureMap.put("ZHONG_WAI",ZHONG_WAI);
        enterpriseNatureMap.put("WAI_ZI",WAI_ZI);
        enterpriseNatureMap.put("OTHER",OTHER);

    }

    @JsonCreator
    public static EnterpriseNature forValue(String value) {
        return enterpriseNatureMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, EnterpriseNature> enterpriseNature : enterpriseNatureMap.entrySet()) {
            if (enterpriseNature.getValue() == this)
                return enterpriseNature.getKey();
        }
        return null;
    }

    public String getName(){
        EnterpriseNature enterpriseNature = this;
        String result = "";

        switch (enterpriseNature){
            case SHI_YE:
                result ="政府机关/事业单位";
                break;
            case GUO_QI:
                result ="国营";
                break;
            case SI_YING:
                result="私营";
                break;
            case ZHONG_WAI:
                result="中外合资";
                break;
            case WAI_ZI:
                result="外资";
                break;
            case OTHER:
                result="其他";
                break;
            default:
                break;

        }

        return result;
    }

}
