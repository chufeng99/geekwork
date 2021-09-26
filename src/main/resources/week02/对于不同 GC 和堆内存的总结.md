1、串行gc：对年轻代使用mark-copy算法，对老年代使用mark-sweep-compact算法，因为只对有一个线程进行垃圾回收，会触发STW，停止所有的应用线程。-XX:UseSerialGC配置串行GC。适合在几百MB的堆内存并且单核CPU的情况下比较有用。
2、并行GC：对年轻代使用mark-copy算法，对老年代使用mark-sweep-compact算法，和串行GC的回收算法一样。年轻代和老年代的垃圾回收都会触发STW事件。-XX:UseParallelGC配置，-XX:ParallelGCThreads=N来指定GC线程数，jdk6、7、8版本默认使用该算法，默认值为CPU核心数。因为对系统资源的有效使用，能达到更高的吞吐量: 
• 在 GC 期间，所有 CPU 内核都在并行清理垃圾，所以总暂停时间更短；
• 在两次 GC 周期的间隔期，没有 GC 线程在运行，不会消耗任何系统资源。
3、CMS GC（Mostly Concurrent Mark and Sweep GC）：-XX:+UseConcMarkSweepGC配置，其对年轻代采用并行 STW 方式的mark-copy算法，对老年代主要使用并发mark-sweep算法。CMS GC 的设计目标是避免在老年代垃圾收集时出现长时间的卡顿，主要通过两种手段来达成此目标：
. 不对老年代进行整理，而是使用空闲列表（free-lists）来管理内存空间的回收。
. 在 mark-sweep阶段的大部分工作和应用线程一起并发执行。
进行老年代的并发回收时，可能会伴随着多次年轻代的minor GC。
4、G1 GC：最主要的设计目标是：将 STW 停顿的时间和分布，变成可预期且可配置的。-XX:+UseG1GC配置，G1 GC 有一些独特的实现。首先，堆不再分成年轻代和老年代，而是划分为多个（通常是2048个）可以存放对象的小块堆区域(smaller heap regions)。每个小块，可能一会被定义成 Eden 区，一会被指定为 Survivor区或者Old 区。在逻辑上，所有的 Eden 区和 Survivor 区合起来就是年轻代，所有的 Old 区拼在一起那就是老年代。另一项创新是，在并发阶段估算每个小堆块存活对象的总数。构建回收集的原则是：垃圾最多的小块会被优先收集。这也是 G1 名称的由来。


实际测试数据及结论：
测试环境：jdk8 macOS 11.x系统 内存16g 8核CPU

**串行GC：**
java -XX:+PrintGCDetails -XX:+UseSerialGC -Xmx256m -Xms256m com.k.week02.GCLogAnalysis
9次youngGC之后开始一直FullGC，每次持续时间大概是10ms以下，之后oom了。
java -XX:+PrintGCDetails -XX:+UseSerialGC -Xmx512m -Xms512m com.k.week02.GCLogAnalysis
大概十几次youngGC之后开始频繁FullGC，每次持续时间大概是20ms左右
java -XX:+PrintGCDetails -XX:+UseSerialGC -Xmx1g -Xms1g com.k.week02.GCLogAnalysis
多次youngGC，每次持续时间大概是25ms
java -XX:+PrintGCDetails -XX:+UseSerialGC -Xmx4g -Xms4g com.k.week02.GCLogAnalysis
3次youngGC，每次持续时间大概是100ms左右

结论：
1、堆内存越大，执行youngGC核FullGC的次数越少；
2、单次回收的持续时间随着内存增大，时间变长。


**并行GC：**
java -XX:+PrintGCDetails -XX:+UseParallelGC -Xmx256m -Xms256m com.k.week02.GCLogAnalysis
10次youngGC之后开始一直FullGC，每次持续时间大概是10ms，之后oom了。
java -XX:+PrintGCDetails -XX:+UseParallelGC -Xmx512m -Xms512m com.k.week02.GCLogAnalysis
大概十几次youngGC之后开始频繁FullGC，每次持续时间大概是20ms左右
java -XX:+PrintGCDetails -XX:+UseParallelGC -Xmx1g -Xms1g com.k.week02.GCLogAnalysis
多次youngGC，每次持续时间大概是10ms
java -XX:+PrintGCDetails -XX:+UseParallelGC -Xmx4g -Xms4g com.k.week02.GCLogAnalysis
4次youngGC，每次持续时间大概是50ms左右

结论：
1、堆内存越大，执行youngGC和fullGC的次数越少
2、单次youngGC和fullGC的时间随着内存的增大而增大
3、采用并行GC比串行GC每次GC的时间更少


**CMS GC：**
java -XX:+PrintGC -XX:+UseConcMarkSweepGC -Xmx256m -Xms256m com.k.week02.GCLogAnalysis
5次youngGC之后开始一直FullGC，每次持续时间大概是10ms，之后oom了。
java -XX:+PrintGC -XX:+UseConcMarkSweepGC -Xmx512m -Xms512m com.k.week02.GCLogAnalysis
youngGC在12毫秒左右，fullGC在57毫秒左右
java -XX:+PrintGC -XX:+UseConcMarkSweepGC -Xmx1g -Xms1g com.k.week02.GCLogAnalysis
基本都是youngGC，只有2次fullGC，每次持续时间大概是25ms
java -XX:+PrintGC -XX:+UseConcMarkSweepGC -Xmx4g -Xms4g com.k.week02.GCLogAnalysis
10次左右youngGC，没有fullGC，每次持续时间大概是40ms

结论：
1、堆内存越大，执行youngGC和fullGC的次数越少
2、单次youngGC和fullGC的时间随着内存的增大而增大


**G1 GC：**
java -XX:+PrintGC -XX:+UseG1GC -Xmx256m -Xms256m com.k.week02.GCLogAnalysis
GC时间1.6ms左右，oom
java -XX:+PrintGC -XX:+UseG1GC -Xmx512m -Xms512m com.k.week02.GCLogAnalysis
GC时间2.5ms左右
java -XX:+PrintGC -XX:+UseG1GC -Xmx1g -Xms1g com.k.week02.GCLogAnalysis
GC时间5ms左右
java -XX:+PrintGC -XX:+UseG1GC -Xmx4g -Xms4g com.k.week02.GCLogAnalysis
25～70ms
结论：整体比其他gc算法要快

**总结：**
1、由于堆空间小于创建对象占用的大小，导致oom，这种情况下：不管是yong gc 和 full gc次数会频繁触发，导致大量时间在做gc；
2、内存越大的情况下 G1 GC的效率越高，gc时间比其他回收器时间短、内存越大g1效果越好；



