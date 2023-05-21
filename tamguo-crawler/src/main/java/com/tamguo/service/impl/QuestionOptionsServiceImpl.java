package com.tamguo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.dao.IQuestionOptionsMapper;
import com.tamguo.model.QuestionOptionsEntity;
import com.tamguo.service.IQuestionOptionsService;
import com.tamguo.service.IQuestionService;
import org.springframework.stereotype.Service;

/**
 * Created by TanGuo on 2019/3/30.
 */
@Service
public class QuestionOptionsServiceImpl extends ServiceImpl<IQuestionOptionsMapper, QuestionOptionsEntity> implements IQuestionOptionsService {

}
