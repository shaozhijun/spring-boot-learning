package com.shaozj.redis.config.properties;

public class JedisPool {
	
	/**
	 * 最大空闲数
	 */
	private Integer maxIdle;
	
	/**
	 * 连接池最大数据库连接数，设为0标识无限制，如果jedis2.4以后用redismaxTotal
	 */
	private Integer maxActive;
	
	/**
	 * 设置一个pool可分配多少个jedis实例，用来替换上面的redis.maxActive,如果是jedis2.4以后用该属性
	 */
	private Integer maxTotal;
	
	/**
	 * 最大链接等待时间。如果超过此事件将链接到异常，设为-1表示无限制
	 */
	private Integer maxWaitMillis;
	
	/**
	 * 链接最小的空闲时间，默认1800000毫秒(30分钟)  
	 */
	private Integer minEvictableIdleTimeMillis;
	
	/**
	 * 每次释放连接的最大数目,默认3  
	 */
	private Integer numTestsPerEvictionRun;
	
	/**
	 * 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1  
	 */
	private Integer timeBetweenEvictionRunsMillis;
	
	/**
	 * 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个  
	 */
	private boolean testOnBorrow;
	
	/**
	 * 在空闲时检查有效性, 默认false
	 */
	private boolean testWhileIdle;

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Integer maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public Integer getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public Integer getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public Integer getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
}
