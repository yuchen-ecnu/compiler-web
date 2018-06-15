package com.ecnu.compiler.parser.domain;

import com.ecnu.compiler.common.domain.EdgeVO;
import com.ecnu.compiler.common.domain.NodeVO;
import com.ecnu.compiler.component.lexer.domain.graph.Edge;
import com.ecnu.compiler.component.parser.domain.TD;
import org.springframework.util.ObjectUtils;

import javax.xml.soap.Node;
import java.util.*;

public class TDVO {
    private List<NodeVO> nodeVOList;
    private List<EdgeVO> edgeVOList;

    public TDVO(){}

    public TDVO(TD td){
        if(ObjectUtils.isEmpty(td))return;
        this.nodeVOList = new ArrayList();
        this.edgeVOList = new ArrayList();
        TD.TNode tNode = td.getRoot();
        Queue<TD.TNode> queue = new LinkedList();
        queue.add(tNode);
        int index = 0, nowNodeListIndex = index;
        while(queue.size() > 0){
            tNode = queue.poll();
            this.nodeVOList.add(new NodeVO(nowNodeListIndex, tNode.getContent()));
            for(TD.TNode x : tNode.getChildren()){
                index++;
                this.edgeVOList.add(new EdgeVO(nowNodeListIndex, index));
                queue.add(x);
            }
            nowNodeListIndex++;
        }
    }

    public TDVO(List<NodeVO> nodeVOList, List<EdgeVO> edgeVOList) {
        this.nodeVOList = nodeVOList;
        this.edgeVOList = edgeVOList;
    }

    public List<NodeVO> getNodeVOList() {
        return nodeVOList;
    }

    public void setNodeVOList(List<NodeVO> nodeVOList) {
        this.nodeVOList = nodeVOList;
    }

    public List<EdgeVO> getEdgeVOList() {
        return edgeVOList;
    }

    public void setEdgeVOList(List<EdgeVO> edgeVOList) {
        this.edgeVOList = edgeVOList;
    }
}
