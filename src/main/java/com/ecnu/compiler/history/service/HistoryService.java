package com.ecnu.compiler.history.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ecnu.compiler.history.mapper.HistoryMapper;
import com.ecnu.compiler.rbac.domain.History;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HistoryService {
    @Resource
    private HistoryMapper historyMapper;

    public List<History> getUserHistory(int uid) {
        return historyMapper.getUserHistory(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    public void logUserHistory(History history){
        historyMapper.log(history);
    }
}
