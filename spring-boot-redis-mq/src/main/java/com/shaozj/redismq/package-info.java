/**
 * 
 * 通过redis实现消息队列
 * 
 * 目前常用的消息队列主要有RabbitMQ等AMQP系列, Kafka, Redis等kev value系列，它们的使用场景分别是：
 * 1.RabbitMQ：相对重量级高并发的情况，比如数据的异步处理、任务的串行执行等。
 * 2.Kafka：基于pull的模式来处理，具体很高的吞吐量，一般用来进行日志存储和收集
 * 3.Redis：轻量级高并发，实时性要求高的情况，比如缓存、秒杀，及时的数据分析（elk日志分析框架，使用的就是redis）
 * @author Administrator 
 *
 */
package com.shaozj.redismq;