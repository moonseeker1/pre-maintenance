package com.system.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wugou
 * @since 2024-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resource")
@ApiModel(value="Resource对象", description="")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id ")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("creat_time")
    private LocalDateTime creatTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("name")
    private String name;

    @TableField("url")
    private String url;


}
