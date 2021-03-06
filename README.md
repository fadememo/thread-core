# thread-core
对java高并发和多线程,架构的学习
## 模块一 并发基础知识和容器

## 模块2 redis和springboot整合
### 使用redis实现抢红包springboot-majoy-queuedSub
/**
 * 主要用来给大量用户生成红包序列,是初始化时就完成红包划分,然后每次请求就返回一个金额
 * 使用了数据类型list,循环存入金额.key 使用的是sessionId,我觉得应该使用sessionid_时间戳会好一些
 */
### 使用redis实现抽奖springboot-majoy-lottery
/**
 * 核心用于实现两个功能:
 * 生成获奖号码
 * 返回获奖号码
 */
 ### 使用redis实现分布式锁springboot-majoy-lock
 /**
 * 分布式锁实现逻辑;基于redis的setnx指令,当key存在时,不进行操作,不存在key则新增
 * 所以:
 * 给所要修改的数据id作为key,修改时先去setnx redis数据,如果set成功进行后续修改,修改后删除gaikey,否则不执行操作.
 * 为防止死锁,给key设置超时时间
 *
 */
### 使用redis实现缓存springboot-majoy-cache
/**
 * 现在梳理一下这个程序的开发思路:
 * 使用redis做数据缓存,那么数据就同时存在于两个地方,数据库和redis数据库
 * 1,查询数据的的时候,先去redis查询,查不到查询数据库.查询出结果后,结果保存到redis.
 * 2,数据保存时,同时保存到redis数据库
 * 3,数据删除时,同时
 * 4,数据修改时,同时修改到redis
 * 5,参考service 配置 //@CachePut(value="usercache", key = "'user_' + #user.id.toString()", unless = "#result eq null")
 */
 ### 使用redis实现限流springboot-majoy-ratelimiter
 /**
         * 1,清除过期的信号量令牌,则我们需要一个请求的标示.
         * 2,将新的信号量放进来
         * 3,获取一个信号量的排名
         * 4,判断信号量排名,如果信号量不在限定的排名里面就移除这个信号量
         */
 ### 使用redis实现消息订阅
 ##### 消息推送方springboot-majoy-queuedPub
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
 
 ###### 消息订阅方springboot-majoy-queuedSub
 /**
  * 使用反序列化接收消息并还原为job
  */
 ###### springboot整合mybatis&druid -->springboot-mybatis-druid 
 /**
  * 1,引入依赖tk.mybatis,分页插件paginator,依赖依赖:mapper-spring-boot-starter,mybatis-paginator,使用mybatis 必须有@id和@idgenerator
  * 2,配置数据库连接池druid
  * 3,整合druid
      读取配置druiddatasourcesettig类
      配置数据源druiddatasourceconfig
      整合mybatis配置sqlsessionfactory等
      注入sqlsessionfactory
  * 4,整合mybatis  
      baseMapper接口
      mstdict数据字典类
      mapper数据库访问层和mapping映射文件
      使用generator生成maping xml,dao和mapper 
      继承mybatis的Mapper<T>, MySqlMapper<T>实现模板
      使用@Param("status")声明传回参数 用于sql#{status,jdbctype=...}
      传入pageBound 自带分页插件
      启用logback框架,配置输出 sql
  */
 
