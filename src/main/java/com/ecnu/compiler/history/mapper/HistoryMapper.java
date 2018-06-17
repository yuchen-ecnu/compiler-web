package com.ecnu.compiler.history.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecnu.compiler.rbac.domain.History;
import com.ecnu.compiler.rbac.domain.vo.HistoryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Michael Chen
 */
@Repository
public interface HistoryMapper extends BaseMapper<History> {
    List<History> getUserHistory(@Param("uid")Integer uid);

    void log(@Param("his")History history);
}
