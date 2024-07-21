package top.wangzhitao.easy2learn.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.wangzhitao.easy2learn.mapper.CardMapper;
import top.wangzhitao.easy2learn.service.CardService;
import top.wangzhitao.easy2learn.vo.CardVo;

/**
 * @author 王志涛 2024/7/21
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, CardVo> implements CardService, IService<CardVo> {
}
