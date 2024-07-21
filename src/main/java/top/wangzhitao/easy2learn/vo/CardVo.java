package top.wangzhitao.easy2learn.vo;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 王志涛 2024/7/21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "card", autoResultMap = true)
public class CardVo {
    @TableId(type = IdType.NONE)
    private Long cardId;
    @TableField(typeHandler = Fastjson2TypeHandler.class)
    private JSONObject fields;
    private Integer fieldOrder;
    private String question;
    private String answer;
    private String modelName;
    private Integer ord;
    private Long deckId;
    private String deckName;
    private String css;
    private Integer factor;
    @JSONField(name = "interval")
    private Integer intervalNum;
    private Long note;
    private Integer type;
    private Integer queue;
    private Integer due;
    private Integer reps;
    private Integer lapses;
    @JSONField(name = "left")
    private Integer leftNum;
    @JSONField(name = "mod")
    private Long modTime;
    @TableField(typeHandler = Fastjson2TypeHandler.class)
    private JSONArray nextReviews;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
