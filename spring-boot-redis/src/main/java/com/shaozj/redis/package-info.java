/**
 * 
 * Redis优势：
 * 
 * 1、 性能极高-Redis能读的速度是110000次/s,写的速度是81000次/s 
 * 2、丰富的数据类型-Redis支持二进制的Strings、Lists、Hashs、Sets和Ordered Sets数据类型操作
 * 3、原子-Redis的原子操作都是原子性的，意思就是要么成功要么失败完全不执行。单个操作是原子性的。多个操作也支持事务，通过MULTI和EXEC指令抱起来。
 * 4、丰富的特性-Redis还支持public/subscribe，通知，key过期等特性
 * 
 * Redis可作为数据库，可降低数据IO的操作，减轻IO的压力，可以减少CPU和内存压力，并且可以修改表结构，特殊对待某一套数据。并且Redis是NoSql，读取速度快，
 * 对于较大数据处理快
 * 
 * Redis做缓存，可用于高频次访问的数据；也可用于分布式架构中的session共享。
 * 
 * 
 * 
 * 
 * 
 * 
 * @author szj
 *
 */
package com.shaozj.redis;