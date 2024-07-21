package top.wangzhitao.easy2learn.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 王志涛 2024/7/21
 */
@Data
@TableName(value = "media", autoResultMap = true)
public class MediaVo {
    @TableId(type = IdType.ASSIGN_UUID)
    private String mediaId;
    private String mediaName;
    private String mediaData;
}
