package com.shaozj.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.shaozj.shiro.jwt.JwtFilter;
import com.shaozj.shiro.realm.ShiroRealm;

public class ShiroConfig extends BasicHttpAuthenticationFilter {

	/**
	 * 
	 * <p>
	 * Description: ShiroFilterFactoryBean处理拦截资源文件问题 Filter Chain定义说明
	 * 1、一个url可以配置多个Filter，使用分号分隔 2、当设置多个过滤器的时候，全部验证通过，才视为通过
	 * 3、不分过滤器可指定参数，如perms、roles
	 * </p>
	 * 
	 * @return
	 */
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		
		// 添加自己的过滤器并取名为jwt
		Map<String, Filter> filtersMap = factoryBean.getFilters();
		filtersMap.put("jwt", new JwtFilter());
		factoryBean.setFilters(filtersMap);
		
		// 必须设置 SecurityManager
		factoryBean.setSecurityManager(securityManager);
		factoryBean.setUnauthorizedUrl("/401");
		
		/*
		 * 自定义url规则
		 * http://shiro.apache.org/web.html#urls-
		 */
		Map<String, String> filterRuleMap  = new LinkedHashMap<String, String>();
		// 所有请求通过我们自己的jwt Filter
		filterRuleMap .put("/**", "jwt");
		// 访问401和404页面不通过我们的Filter
		filterRuleMap .put("/401", "anon");
		filterRuleMap .put("/404", "anon");
		factoryBean.setFilterChainDefinitionMap(filterRuleMap );
		return factoryBean;
	}

	@Bean
	public SecurityManager securityManager(ShiroRealm realm) {

		// 设置自己定义的realm
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		// 设置缓存管理器
		// securityManager.setCacheManager(null);

		// 关闭shiro自带的session
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
		return securityManager;
	}
	
	@Bean
	public ShiroRealm shiroRealm() {
		return new ShiroRealm();
	}

	/**
	 * 
	 * <p>
	 * Description: 添加注解支持
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// 强制使用cglib，防止重复代理和可能引起代理出错的问题
		// https://zhuanlan.zhihu.com/p/29161098
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 
	 * <p>
	 * Description: 开启shiro aop注解，使用代理方式，所以需要开启代码支持
	 * </p>
	 * 
	 * @param securityManager
	 *            安全管理器
	 * @return 授权Advisor
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 
	 * <p>
	 * Description: shiro缓存管理 需要注入对应的其他实体类中 1、安全管理器：securityManager
	 * 可见securityManager是整个shiro的核心；
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return cacheManager;
	}

}
