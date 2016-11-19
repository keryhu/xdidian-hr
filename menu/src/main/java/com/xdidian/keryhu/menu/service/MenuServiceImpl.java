package com.xdidian.keryhu.menu.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.xdidian.keryhu.menu.client.UserClient;
import com.xdidian.keryhu.menu.domain.core.Menu;
import com.xdidian.keryhu.menu.domain.core.MenuDto;
import com.xdidian.keryhu.menu.domain.core.MenuType;
import com.xdidian.keryhu.menu.domain.core.ResponseWhenQueryMenus;
import com.xdidian.keryhu.menu.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.xdidian.keryhu.util.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Component("menuService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final UserClient userClient;
    private final MenuRepository repository;

    @Override
    public List<MenuDto> getMenu(String userId, String token) {

        // 当前用户的权限。
        Collection<String> authorities = SecurityUtils.getAuthorities();
        boolean isInCompany = userClient.getIsInCompanyAndName(userId, token).isInCompany();
        boolean defaultMenu = repository.
                findByUserId(userId).map(Menu::isDefaultMenus).orElse(true);
        //非新地点，客服人员，也非新地点管理人员
        boolean notXi = !(authorities.contains("ROLE_XDIDIAN_SERVICE") ||
                authorities.contains("ROLE_XDIDIAN_ADMIN"));

        List<MenuDto> dto = new ArrayList<MenuDto>();

        // 新地点的客服人员
        if (authorities.contains("ROLE_XDIDIAN_SERVICE")) {
            for (MenuType m : MenuType.values()) {
                // 除了 新地点管理员的 菜单选项
                if (m.getId() >= 30 && m.getId() < 90) {
                    dto.add(new MenuDto(m.getId(), m.toValue(), m.getUrl()));
                }
            }
            return dto;
        }

        // 新地点的公司管理员
        else if (authorities.contains("ROLE_XDIDIAN_ADMIN")) {
            for (MenuType m : MenuType.values()) {
                // 除了 新地点管理员的 菜单选项
                if (m.getId() >= 30) {
                    dto.add(new MenuDto(m.getId(), m.toValue(), m.getUrl()));
                }
            }
            return dto;
        }


        // 如果未加入任何公司，那么就返回这4个。加入公司，创建公司，个人设置，会员主页
        else if ((!isInCompany) && notXi) {

            dto.add(new MenuDto(MenuType.HOME.getId(), MenuType.HOME.toValue(),
                    MenuType.HOME.getUrl()));
            dto.add(new MenuDto(MenuType.CREATE_COMPANY.getId(),
                    MenuType.CREATE_COMPANY.toValue(),
                    MenuType.CREATE_COMPANY.getUrl()));
            dto.add(new MenuDto(MenuType.JOIN_COMPANY.getId(),
                    MenuType.JOIN_COMPANY.toValue(),
                    MenuType.JOIN_COMPANY.getUrl()));
            dto.add(new MenuDto(MenuType.PERSONAL_SET.getId(),
                    MenuType.PERSONAL_SET.toValue(),
                    MenuType.PERSONAL_SET.getUrl()));

            return dto;
        }
        // 如果用户的权限 是 公司的 管理员，则含有所有的 默认菜单，，不是
        else if (authorities.contains("ROLE_COMPANY_ADMIN")) {
            for (MenuType m : MenuType.values()) {
                // 除了 新地点管理员的 菜单选项
                if (m.getId() < 30) {
                    dto.add(new MenuDto(m.getId(), m.toValue(), m.getUrl()));
                }
            }
            return dto;

            // 当用户的菜单是默认菜单，即已经是公司 员工。
        } else if (defaultMenu) {

            dto.add(new MenuDto(MenuType.HOME.getId(), MenuType.HOME.toValue(),
                    MenuType.HOME.getUrl()));
            dto.add(new MenuDto(MenuType.COMPANY_INFO.getId(),
                    MenuType.COMPANY_INFO.toValue(),
                    MenuType.COMPANY_INFO.getUrl()));
            dto.add(new MenuDto(MenuType.CAREER_PLANNING.getId(),
                    MenuType.CAREER_PLANNING.toValue(),
                    MenuType.CAREER_PLANNING.getUrl()));
            dto.add(new MenuDto(MenuType.PERFORMANCE_APPRAISAL.getId(),
                    MenuType.PERFORMANCE_APPRAISAL.toValue(),
                    MenuType.PERFORMANCE_APPRAISAL.getUrl()));
            dto.add(
                    new MenuDto(MenuType.ATTENDANCE_SALARY.getId(),
                            MenuType.ATTENDANCE_SALARY.toValue(), MenuType.ATTENDANCE_SALARY.getUrl()));
            dto.add(
                    new MenuDto(MenuType.RELEASE_MANAGEMENT.getId(),
                            MenuType.RELEASE_MANAGEMENT.toValue(), MenuType.RELEASE_MANAGEMENT.getUrl()));
            dto.add(new MenuDto(MenuType.REPORT_TRAINING.getId(),
                    MenuType.REPORT_TRAINING.toValue(),
                    MenuType.REPORT_TRAINING.getUrl()));
            dto.add(new MenuDto(MenuType.INNOVATION_SUGGESTIONS.getId(),
                    MenuType.INNOVATION_SUGGESTIONS.toValue(),
                    MenuType.INNOVATION_SUGGESTIONS.getUrl()));
            dto.add(new MenuDto(MenuType.PERSONAL_SET.getId(), MenuType.PERSONAL_SET.toValue(),
                    MenuType.PERSONAL_SET.getUrl()));

            return dto;
        }


        // 自定义菜单。
        else if (!defaultMenu) {
            List<MenuType> t = repository.findByUserId(userId).map(Menu::getMenuTypes).get();

            return t.stream().map(e -> new MenuDto(e.getId(), e.toValue(), e.getUrl()))
                    .collect(Collectors.toList());
        }
        return null;
    }


    //用户登录的时候，除了获取菜单，还有用户姓名，是否在company
    @Override
    public ResponseWhenQueryMenus getMenuAndUserInfo(
            String userId, String token) {
        ResponseWhenQueryMenus responseWhenQueryMenus = new ResponseWhenQueryMenus();

        String name = userClient.getIsInCompanyAndName(userId, token).getPeopleName();

        responseWhenQueryMenus.setMenus(getMenu(userId, token));
        responseWhenQueryMenus.setName(name);

        return responseWhenQueryMenus;
    }

}
