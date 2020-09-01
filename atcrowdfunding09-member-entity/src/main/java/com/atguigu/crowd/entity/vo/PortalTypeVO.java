package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2020/5/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortalTypeVO {
    private  Integer id;
    private String name;
    private String remark;
    private List<PortalProjectVO> portalProjectVOList;
    
}
