package kr.co.bestiansoft.ebillservicekg.test.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import kr.co.bestiansoft.ebillservicekg.test.vo.CommentsVo;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommentsHierarchy {
    private List<CommentsVo> rootComments;
    private List<CommentsVo> children = new ArrayList<>();

    public void buildCommentsHierarchy(List<CommentsVo> commentsVos) {
        Map<Long, CommentsVo> commentsMap = new HashMap<>();

        for (CommentsVo vo : commentsVos) {
            commentsMap.put(vo.getId(), vo);
        }

        List<CommentsVo> rootComments = new ArrayList<>();

        for (CommentsVo commentsVo : commentsVos) {
            Long parentId = commentsVo.getParentId();

            if(parentId != null && !commentsMap.containsKey(parentId)) {
                commentsVo.setParentYn("Y");
                rootComments.add(commentsVo);
            } else {
                CommentsVo parent = commentsMap.get(commentsVo.getParentId());
                if(parent != null) {
                    parent.addChildren(commentsVo);
                }
            }

        }

        this.rootComments = rootComments;
    }

    public ArrayNode getCommentsJson() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode rootNode = mapper.createArrayNode();
        for (CommentsVo vo : this.rootComments) {
            rootNode.add(convertToJson(vo, mapper));
        }
        return rootNode;
    }


    public static JsonNode convertToJson(CommentsVo vo, ObjectMapper mapper) {
        ObjectNode jsonNode = mapper.createObjectNode();
        ObjectNode dataNode = mapper.createObjectNode();

        dataNode.put("label", vo.getContent());
        dataNode.put("key", vo.getId());
        dataNode.put("id", vo.getId());
        dataNode.put("regNm", vo.getCreatedBy());


        jsonNode.put("key", vo.getId());
        jsonNode.put("label", vo.getContent());
        jsonNode.put("parentYn", vo.getParentYn());

        if(vo.getCreatedAt() != null) {
            jsonNode.put("regDt", vo.getCreatedAt().toString());
        }

        jsonNode.put("lawId", vo.getLawId());
        jsonNode.set("data", dataNode);

        ArrayNode childrenNode = mapper.createArrayNode();
        for (CommentsVo child : vo.getChildren()) {
            childrenNode.add(convertToJson(child, mapper));
        }

        if (!childrenNode.isEmpty()) {
            jsonNode.set("children", childrenNode);
        }

        return jsonNode;
    }


}
