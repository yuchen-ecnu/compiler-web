package com.ecnu.compiler.lexical.service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.lexical.mapper.RegexMapper;
import org.springframework.stereotype.Service;

/**
 * @author michaelchen
 */
@Service
public class ReService extends ServiceImpl<RegexMapper,Regex> {

}
