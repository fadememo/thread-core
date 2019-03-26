package club.majoy.springbootmajoyqueuedPub.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 任务消息:
 * 接收从订单页面发送过来的订单数据,建立任务消息
 ** 4,消息结构:
 * job:[Topic业务,Id唯一id,Delay延时时间,TTR超时时间,Status状态,Body具体内容]
 *
 * 5,状态status:
 * 0 初始化提交 ;1 用户下单等待商家接单;6 商家已接单;7商家取消订单,8商家超时未接单
 * 11:骑手已经接单,规划路线和时间;12 骑手申请取消 13 骑手申请延时 ;14骑手超时未接单
 * 16:送单完成,骑手抵达确认;17 骑手未抵达,送单完成确认;18 超时配送中 19超时配送完成
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job implements Comparable<Job>,Serializable{
    //
    private static final long serialVersionUID = 6666666666666L;
    //业务名称
    private String topic;
    //唯一id
    private String jobId;
    //延时时间
    private long   delay;
    //建立的基准时间戳
    private long  baseStamp;
    //超时时间
    private long   ttr;
    //当前状态
    private String status;
    //订单用户id
    private String userId;
    //订单商家id
    private String merchantId;
    //订单骑手id
    private String riderid;
    //订单详情
    private String jobDetail;


    @Override
    public int compareTo(Job job) {
        //按照延时时间 距离当前最近 排序
        //long sysTime = System.currentTimeMillis();
        return (int)((baseStamp+delay)-(job.baseStamp+job.delay));
    }
}
