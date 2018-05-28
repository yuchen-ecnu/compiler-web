package com.ecnu.compiler.lexical.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecnu.compiler.lexical.domain.Regex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Handsome Zhao
 */

@Repository
public interface LexicalMapper extends BaseMapper<Regex>{
    List<Regex> getTargetLanguageRegexs(@Param("language")String language);
}
