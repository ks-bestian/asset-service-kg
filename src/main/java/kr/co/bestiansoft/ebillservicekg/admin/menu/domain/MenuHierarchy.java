package kr.co.bestiansoft.ebillservicekg.admin.menu.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kr.co.bestiansoft.ebillservicekg.admin.authMenu.vo.AuthMenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuNode;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Data
public class MenuHierarchy {
    private boolean menuChecked;
    private Long authId;
    private Long menuId;
    private String menuNm1;
    private String menuNm2;
    private String menuNm3;
    private Long upperMenuId;
    private Integer menuAuth;
    private List<AuthMenuVo> rootAuthMenus;
    private List<MenuNode> rootMenus;


    //--메뉴관리 tree--
    public void buildMenuHierarchy(List<MenuVo> menuList, String lang)  {

        List<MenuNode> comMenuList = new ArrayList<MenuNode>();

        for (MenuVo menu : menuList) {
            String menuNm = "";
            if("lng_type2".equals(lang)) {
                menuNm = menu.getMenuNm2();
            } else {
                menuNm = menu.getMenuNm1();
            }
            comMenuList.add(MenuNode.builder()
                    .menuId(menu.getMenuId())
                    .menuNm(menuNm)
                    .uprMenuId(menu.getUprMenuId())
                    .build());
        }
        Map<Long, MenuNode> categoryMap = new HashMap<>();

        // 초기 카테고리 맵 채우기
        for (MenuNode menuNode : comMenuList) {
            categoryMap.put(menuNode.getMenuId(),menuNode);
        }

        // 계층 구조 만들기
        List<MenuNode> rootMenu = new ArrayList<>();
        for (MenuNode menuNode : comMenuList) {
            if (menuNode.getUprMenuId() == 0) {
                rootMenu.add(menuNode);
            } else {
                MenuNode parent = categoryMap.get(menuNode.getUprMenuId());
                if (parent != null) {
                    parent.addChildMenu(menuNode);
                }
            }
        }

        this.rootMenus = rootMenu;
    }

    public ArrayNode getMenuJson(String type) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode rootNode = mapper.createArrayNode();
        for (MenuNode comMenu : this.rootMenus) {
            if(type.equals("menu")) {
                rootNode.add(convertToJsonMenu(comMenu, mapper));
            }else if(type.equals("authMenu")) {
                rootNode.add(convertToJsonAuth(comMenu, mapper));
            }
        }

        return rootNode;
    }

    private static JsonNode convertToJsonMenu(MenuNode comMenu, ObjectMapper mapper) {
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("key", comMenu.getMenuId());
        jsonNode.put("label", comMenu.getMenuNm());

        ArrayNode childrenNode = mapper.createArrayNode();
        for (MenuNode child : comMenu.getChildren()) {
            childrenNode.add(convertToJsonMenu(child, mapper));
        }

        if(!childrenNode.isEmpty()) {
            jsonNode.set("children", childrenNode);
        }

        return jsonNode;
    }

    public static JsonNode convertToJsonAuth(MenuNode comMenu, ObjectMapper mapper) {
        ObjectNode jsonNode = mapper.createObjectNode();
        ObjectNode dataNode = mapper.createObjectNode();

        dataNode.put("label", comMenu.getMenuNm());
        dataNode.put("menuAuth", comMenu.getMenuAuth());
        jsonNode.put("key", comMenu.getMenuId());
        jsonNode.put("menuChecked", comMenu.isMenuChecked());
        jsonNode.set("data", dataNode);

        ArrayNode childrenNode = mapper.createArrayNode();
        for (MenuNode child : comMenu.getChildren()) {
            childrenNode.add(convertToJsonAuth(child, mapper));
        }

        if(!childrenNode.isEmpty()) {
            jsonNode.set("children", childrenNode);
        }

        return jsonNode;
    }
}
