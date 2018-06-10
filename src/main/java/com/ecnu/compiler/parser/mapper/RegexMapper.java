package com.ecnu.compiler.parser.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ecnu.compiler.lexical.domain.Regex;
import org.springframework.stereotype.Repository;

/**
 * @author Lucto
 */

@Repository
public interface RegexMapper extends BaseMapper<Regex> {
}
