package club.majoy.springbootmajoyqueuedPub.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Job job) {
        //按照延时时间 距离当前最近 排序
        //long sysTime = System.currentTimeMillis();
        return (int)((baseStamp+delay)-(job.baseStamp+job.delay));
    }
}
