package com.ecnu.compiler.history.service;

import com.ecnu.compiler.history.mapper.HistoryMapper;
import com.ecnu.compiler.rbac.domain.History;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HistoryService {
    @Resource
    private HistoryMapper historyMapper;
    public List<History> getUserHistory(int uid) {
        List<History> res = historyMapper.getUserHistory(uid);
        return res;
    }
}
