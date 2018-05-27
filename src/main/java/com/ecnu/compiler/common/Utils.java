package com.ecnu.compiler.common;

import com.ecnu.compiler.common.domain.EdgeVO;
import com.ecnu.compiler.common.domain.NodeVO;
import com.ecnu.compiler.component.lexer.domain.graph.Edge;
import com.ecnu.compiler.component.lexer.domain.graph.State;
import org.springframework.util.ObjectUtils;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;

/**
 * 通用工具类
 * @author Michael Chen
 */
public class Utils {

    /**
     * BFS
     * @param stateList 最终返回的点集合
     * @param edgeList 最终返回的边集合
     * @param start 图的起始节点
     */
    public static void travelGraph(List<NodeVO> stateList, List<EdgeVO> edgeList, State start){
        if(start == null){ return; }
        HashMap<Integer,Boolean> visited = new HashMap<>();
        ArrayDeque<State> queue= new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()){
            start = queue.remove();
            if(visited.containsKey(start.getId())){ continue; }
            visited.put(start.getId(),true);
            stateList.add(new NodeVO(start));

            List<Edge> edges = start.getEdgeList();
            for(Edge edge : edges){
                if(ObjectUtils.isEmpty(edge.getEndState())){
                    continue;
                }
                edgeList.add(new EdgeVO(edge));
                queue.add(edge.getEndState());
            }
        }
    }
}
