package com.system.repair_person.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2024-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("equipment")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("equipment_type_id")
    private Integer equipmentTypeId;

    @TableField("equipment_type_name")
    private String equipmentTypeName;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("last_preserve_time")
    private LocalDateTime lastPreserveTime;

    @TableField("state")
    private Integer state;

    /**
     * 维护次数
     */
    @TableField("preserve_cycle")
    private Integer preserveCycle;

    /**
     * 产出
     */
    @TableField("output")
    private Integer output;

    /**
     * 使用年限
     */
    @TableField("service_life")
    private Integer serviceLife;


}
