package club.majoy.springbootmajoyqueuedPub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 1,需求分析:延时队列,
 *
 * 2,业务场景:
 * 外卖订单生成后30s给商户发送订单信息,然后根据后续操作不断进行下游处理状态的处理
 * 五分钟后判断商户接单状态进行接单或者取消或者商户其他反馈的处理流程
 * 商户接单后30s发送订单信息给配送员,
 * 五分钟后进行判断骑手接单状态进行接单或者指派或者返回报错信息
 * 骑手接单配送后生成配送时间预期,
 * 在指定预期时间判断业务进程返回信息,超时是给用户发送短信.进入赔偿流程
 *
 * 3,技术实现逻辑:
 * 使用pub/sub特性,pub端负责提交订单信息,sub端接收消息并调用处理方(有商户,骑手,用户,平台四个处理方),使用storedesc作为score,然后后台每隔30s进行一次score检查.
 * 这里我觉得创建key的时候就设置expire,然后到期移除时进行通知也可以.
 * 消息:key= merchantOrder;riderOrder;takeOutOrder;zset{orderid+storedset}
 *
 * 4,消息结构:
 * job:[Topic业务,Id唯一id,Delay延时时间,TTR超时时间,Status状态,Body具体内容]
 *
 * 5,状态status:
 * 0 初始化提交 ;1 用户下单等待商家接单;6 商家已接单;7商家取消订单,8商家超时未接单
 * 11:骑手已经接单,规划路线和时间;12 骑手申请取消 13 骑手申请延时 ;14骑手超时未接单
 * 16:送单完成,骑手抵达确认;17 骑手未抵达,送单完成确认;18 超时配送中 19超时配送完成
 *
 *  6,需要考虑的问题:
 * 及时性 消费端能按时收到
 * 同一时间消息的消费权重
 * 可靠性 消息不能出现没有被消费掉的情况
 * 可恢复 假如有其他情况 导致消息系统不可用了 至少能保证数据可以恢复
 * 可撤回 因为是延迟消息 没有到执行时间的消息支持可以取消消费
 * 高可用 多实例 这里指HA/主备模式并不是多实例同时一起工作
 * 消费端如何消费
 *
 * 7,结构简化,逻辑简化demo
 */
@SpringBootApplication
public class SpringbootMajoyQueuedPubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMajoyQueuedPubApplication.class, args);
	}

}
