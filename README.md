程序运行在 Tomcat 中，执行程序的线程是 Tomcat 的工作线程，而 Tomcat 的工作线程是基于线程池的。
顾名思义，线程池会重用固定的几个线程，一旦线程重用，那么很可能首次从 ThreadLocal 获取的值是之前其他用户的请求遗留的值。这时，
ThreadLocal 中的用户信息就是其他用户的信息。

我们可能会抱怨学多线程没用，因为代码里没有开启使用多线程。但其实，可能只是我们没有意识到，在 Tomcat 这种 Web 服务器下跑的业务代码，
本来就运行在一个多线程环境（否则接口也不可能支持这么高的并发），并不能认为没有显式开启多线程就不会有线程安全问题。
        
因为线程的创建比较昂贵，所以 Web 服务器往往会使用线程池来处理请求，这就意味着线程会被重用。这时，使用类似 ThreadLocal 工具来存放一些数据时，
需要特别注意在代码运行完后，显式地去清空设置的数据。如果在代码中使用了自定义的线程池，也同样会遇到这个问题。 

ThreadLocal 是利用独占资源的方式，来解决线程安全问题，那如果我们确实需要有资源在线程之间共享，应该怎么办呢？这时，我们可能就需要用到线程安全的容器了。  

之前在排查一个生产性能问题时，我们发现一段简单的非数据库操作的业务逻辑，消耗了超出预期的时间，在修改数据时操作本地缓存比回写数据库慢许多。
查看代码发现，开发同学使用了 CopyOnWriteArrayList 来缓存大量的数据，而数据变化又比较频繁

CopyOnWrite 是一个时髦的技术，不管是 Linux 还是 Redis 都会用到。在 Java 中，CopyOnWriteArrayList 虽然是一个线程安全的 ArrayList，
但因为其实现方式是，每次修改数据时都会复制一份数据出来，所以有明显的适用场景，即读多写少或者说希望无锁读的场景。

如果我们要使用 CopyOnWriteArrayList，那一定是因为场景需要而不是因为足够酷炫。如果读写比例均衡或者有大量写操作的话，
使用 CopyOnWriteArrayList 的性能会非常糟糕。

加锁前要清楚锁和被保护的对象是不是一个层面的

静态字段属于类，类级别的锁才能保护；而非静态字段属于类实例，实例级别的锁就可以保护。

在非静态的 wrong 方法上加锁，只能确保多个线程无法执行同一个实例的 wrong 方法，却不能保证不会执行不同实例的 wrong 方法。
而静态的 counter 在多个实例中共享，所以必然会出现线程安全问题。


加锁要考虑锁的粒度和场景问题在方法上加 synchronized 关键字实现加锁确实简单，也因此我曾看到一些业务代码中几乎所有方法都加了 synchronized，但这种滥用 synchronized 的做法：
一是，没必要。通常情况下 60% 的业务代码是三层架构，数据经过无状态的 Controller、Service、Repository 流转到数据库，没必要使用 synchronized 来保护什么数据。
二是，可能会极大地降低性能。使用 Spring 框架时，默认情况下 Controller、Service、Repository 是单例的，加上 synchronized 会导致整个程序几乎就只能支持单线程，造成极大的性能问题。

在程序中，我们会用各种池化技术来缓存创建昂贵的对象，比如线程池、连接池、内存池。一般是预先创建一些对象放入池中，使用的时候直接取出使用，用完归还以便复用，
还会通过一定的策略调整池中缓存对象的数量，实现池的动态伸缩。

ThreadLocal 适用于变量在线程间隔离，而在方法或类间共享的场景

虽然使用 new 声明的字符串调用 intern 方法，也可以让字符串进行驻留，但在业务代码中滥用 intern，可能会产生性能问题。

出现这种问题的主要原因是，计算机是以二进制存储数值的，浮点数也不例外。Java 采用了IEEE 754 标准实现浮点数的表达和运算，你可以通过这里查看数值转化为二进制的结果。
比如，0.1 的二进制表示为 0.0 0011 0011 0011… （0011 无限循环)，再转换为十进制就是 0.1000000000000000055511151231257827021181583404541015625。对于计算机而言，
0.1 无法精确表达，这是浮点数计算造成精度损失的根源。
使用 BigDecimal 表示和计算浮点数，且务必使用字符串的构造方法来初始化 BigDecimal：
BigDecimal 有 scale 和 precision 的概念，scale 表示小数点右边的位数，而 precision 表示精度，也就是有效数字的长度。


第一个方法是，使用 TreeSet 替换 HashSet。TreeSet 不使用 hashCode 方法，也不使用 equals 比较元素，而是使用 compareTo 方法，所以不会有问题。
第二个方法是，把 BigDecimal 存入 HashSet 或 HashMap 前，先使用 stripTrailingZeros 方法去掉尾部的零，比较的时候也去掉尾部的 0，确保 value 相同的 BigDecimal，scale 也是一致的：

第一，切记，要精确表示浮点数应该使用 BigDecimal。并且，使用 BigDecimal 的 Double 入参的构造方法同样存在精度丢失问题，
应该使用 String 入参的构造方法或者 BigDecimal.valueOf 方法来初始化。
第二，对浮点数做精确计算，参与计算的各种数值应该始终使用 BigDecimal，所有的计算都要通过 BigDecimal 的方法进行，切勿只是让 BigDecimal 来走过场。
任何一个环节出现精度损失，最后的计算结果可能都会出现误差。

不能直接使用 Arrays.asList 来转换基本类型数组。
Arrays.asList 返回的 List 不支持增删操作
对原始数组的修改会影响到我们获得的那个 List








