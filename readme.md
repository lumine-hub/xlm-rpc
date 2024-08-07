# xlm-rpc
本项目为采用java实现的rpc框架

## 项目架构
![jiagou](./images/jiagou.png)

## 项目结构

- xlm-rpc-easy: 简单的rpc框架
- xlm-rpc-core: 升级版rpc框架
- xlm-rpc-spring-boot-starter：框架整合springboot
- example-common:存放公共接口
- example-consumer：消费者测试案例
- example-provider：生产者测试案例
- example-springboot-consumer：使用springboot框架消费者案例
- example-springboot-provider：使用springboot生产者案例

## 项目迭代

从0开始的RPC的迭代过程：
- version0：简单代码完成一个RPC例子
- version1管理全局配置，用户可以通过配置文件进行配置
- version2版本：添加mock，消费端使用mock返回数据，方便测试
- version3版本：支持更多的序列化方式（java原生、json等），实现SPI机制，用户自定义实现方式。
- version4版本：服务器注册与发现的实现，etcd作为注册中心
- version5版本：注册中心优化，完成心跳检测、续期、节点下线、消费者缓存一致、zookeeper、SPI扩展
- version6版本: 自定义协议，才用TCP+自定义协议代替http
- version7版本: 负载均衡策略的实现
- version8版本：重试机制和容错机制
- version9版本：启动机制和注解驱动

## 项目使用
#### 基本使用
**创建common接口**
```java
public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
        "name='" + name + '\'' +
        '}';
    }
}
```

```java
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

}
```

**provider**
```java
public class ProviderBootstrapTest {
    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}

```
**consumer**
```java
public class ConsumerProtocol {
    public static void main(String[] args) {
        User user = new User();
        user.setName("xlm");
        ConsumerBootstrap.init();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User res = userService.getUser(user);
        System.out.println(res);
    }
}

```

#### 结合springboot
**provider**
```java
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("调用的用户名为：" + user.getName());
        return user;
    }
}
```
**consumer**
```java
@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("xlm");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
```