package top.wangzhitao.easy2learn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wangzhitao.easy2learn.mapper.MediaMapper;
import top.wangzhitao.easy2learn.service.AnkiService;
import top.wangzhitao.easy2learn.service.MediaService;
import top.wangzhitao.easy2learn.vo.MediaVo;

import java.util.HashSet;
import java.util.List;

/**
 * @author 王志涛 2024/7/21
 */
@Service
@Slf4j
public class MediaServiceImpl extends ServiceImpl<MediaMapper, MediaVo> implements IService<MediaVo>, MediaService {

    @Resource
    private AnkiService ankiService;

    @Override
    public void saveMedia(HashSet<String> mediaNames) {
        List<String> mediaNamesNotSave = this.list(new QueryWrapper<MediaVo>().notIn("media_name", mediaNames).select("media_name")).stream().map(MediaVo::getMediaName).toList();
        List<MediaVo> list = mediaNames.stream().map(mediaName -> {
            MediaVo mediaVo = new MediaVo();
            mediaVo.setMediaName(mediaName);
            mediaVo.setMediaData(ankiService.retrieveMediaFile(mediaName));
            return mediaVo;
        }).toList();
        log.info("current mediaVosSize:{}", list.size());
        this.saveOrUpdateBatch(list);
    }
}
