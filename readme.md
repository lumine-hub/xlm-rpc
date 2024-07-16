本项目为采用java实现的rpc框架
## 项目架构
![](https://cdn.nlark.com/yuque/0/2024/jpeg/38561378/1721110017251-dc488b9d-f16c-4373-989e-f36be6c10b16.jpeg)
## 项目结构

- xlm-rpc-easy: 简单的rpc框架
- xlm-rpc-core: 升级版rpc框架
- xlm-rpc-spring-boot-starter：框架整合springboot
- [example-common](https://github.com/lumine-hub/xlm-rpc/tree/master/example-common): 存放公共接口
- [example-consumer](https://github.com/lumine-hub/xlm-rpc/tree/master/example-consumer)：消费者测试案例
- [example-provider](https://github.com/lumine-hub/xlm-rpc/tree/master/example-provider)：生产者测试案例
- [example-springboot-consumer](https://github.com/lumine-hub/xlm-rpc/tree/master/example-springboot-consumer)：使用springboot框架消费者案例
- [example-springboot-provider](https://github.com/lumine-hub/xlm-rpc/tree/master/example-springboot-provider)：使用springboot生产者案例

## 项目迭代
从0开始的RPC的迭代过程：

- [version0版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#0.%E4%B8%80%E4%B8%AA%E6%9C%80%E7%AE%80%E5%8D%95%E7%9A%84RPC%E8%B0%83%E7%94%A8)：简单代码完成一个RPC例子
- [version1版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#1.MyRPC%E7%89%88%E6%9C%AC1)：管理全局配置，用户可以通过配置文件进行配置
- [version2版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#2.MyRPC%E7%89%88%E6%9C%AC2)：添加mock，消费端使用mock返回数据，方便测试
- [version3版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#3.MyRPC%E7%89%88%E6%9C%AC3)：支持更多的序列化方式（java原生、json等），实现SPI机制，用户自定义实现方式。
- [version4版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#4.MyRPC%E7%89%88%E6%9C%AC4)：服务器注册与发现的实现，etcd作为注册中心
- [version5版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#5.MyRPC%E7%89%88%E6%9C%AC5): 注册中心优化，完成心跳检测、续期、节点下线、消费者缓存一致、zookeeper、SPI扩展
- [version6版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#MyRPC%E7%89%88%E6%9C%AC6): 自定义协议，才用TCP+自定义协议代替http
- [version7版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#7.MyRPC%E7%89%88%E6%9C%AC7): 负载均衡策略的实现
- [version8版本](https://github.com/he2121/MyRPCFromZero/blob/master/README.md#8.MyRPC%E7%89%88%E6%9C%AC8)：重试机制和容错机制
- version9版本：启动机制和注解驱动

## 项目使用
#### 基本使用
创建common接口
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
 
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
 
provider
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
consumer
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
provider
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
 consumer
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
 

