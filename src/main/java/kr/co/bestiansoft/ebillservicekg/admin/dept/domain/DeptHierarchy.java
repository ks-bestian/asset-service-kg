package kr.co.bestiansoft.ebillservicekg.admin.dept.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kr.co.bestiansoft.ebillservicekg.admin.dept.vo.DeptVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class DeptHierarchy {

    private List<DeptVo> rootDept;
    private List<DeptVo> children = new ArrayList<>();

    public void buildHierarchy(List<DeptVo> deptVos, boolean search) {
        Map<String, DeptVo> deptMap = new HashMap<>();

        for (DeptVo deptVo : deptVos) {
            deptMap.put(deptVo.getDeptCd(), deptVo);
        }

        //계층구조 만들기
        List<DeptVo> rootDept = new ArrayList<>();
        for (DeptVo deptVo : deptVos) {
            if (search) { //트리 부서명 검색
                rootDept.add(deptVo);
            } else {
                if (deptVo.getUprDeptCd().equals("0")) {
                    rootDept.add(deptVo);
                } else {
                    DeptVo parent = deptMap.get(deptVo.getUprDeptCd());
                    if (parent != null) {
                        parent.addChildren(deptVo);
                    }
                }
            }

        }

        this.rootDept = rootDept;
    }

    public ArrayNode getJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode rootNode = mapper.createArrayNode();
        for (DeptVo deptVo : this.rootDept) {
            rootNode.add(convertToJson(deptVo, mapper));
        }
        return rootNode;
    }


    public static JsonNode convertToJson(DeptVo deptVo, ObjectMapper mapper) {
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("key", deptVo.getDeptCd());
        jsonNode.put("label", deptVo.getDeptNm());

        ArrayNode childrenNode = mapper.createArrayNode();
        for (DeptVo child : deptVo.getChildren()) {
            childrenNode.add(convertToJson(child, mapper));
        }

        if (!childrenNode.isEmpty()) {
            jsonNode.set("children", childrenNode);
        }

        return jsonNode;
    }

}
