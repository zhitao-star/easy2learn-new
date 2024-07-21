package top.wangzhitao.easy2learn.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author 王志涛 2024/7/20
 */
@Data
@TableName("deck")
@AllArgsConstructor
public class DeckVo {
    @TableId(type = IdType.INPUT)
    private Long deckId;
    private String deckName;
    private Long deckParentId;
    private String fillDeckPath;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public DeckVo(Long deckId, String deckName) {
        this.deckId = deckId;
        this.deckName = deckName;
    }

    public DeckVo(Long deckId, String deckName, Long deckParentId, String fillDeckPath) {
        this.deckId = deckId;
        this.deckName = deckName;
        this.deckParentId = deckParentId;
        this.fillDeckPath = fillDeckPath;
    }
}
