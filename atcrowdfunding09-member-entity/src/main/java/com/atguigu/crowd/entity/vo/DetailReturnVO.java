package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Administrator
 * @Date 2020/5/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailReturnVO {
    //回报信息的主键
    private Integer returnId;
    // 当前档位支持的金额
    private Integer supportMoney;
    // 单笔限额
    private Integer signalPurchase;
    // 档位支撑者数量
    private Integer supporterCount;
    // 运费
    private Integer freight;
    // 发货时间
    private Integer returnDate;
    // 回报内容
    private String content;


}