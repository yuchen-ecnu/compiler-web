package com.ecnu.compiler.lexical.service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.LexicalMapper;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.mapper.UserMapper;
import com.ecnu.compiler.utils.domain.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class LexicalService {
    @Resource
    private LexicalMapper lexicalMapper;

    public List<Regex> getRegexsFromTargetLaguage(String lanugage){
        return lexicalMapper.selectList(
                new EntityWrapper<Regex>().eq("language",lanugage));
    }
}
