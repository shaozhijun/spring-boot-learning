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
	 * Description: ShiroFilterFactoryBean����������Դ�ļ����� Filter Chain����˵��
	 * 1��һ��url�������ö��Filter��ʹ�÷ֺŷָ� 2�������ö����������ʱ��ȫ����֤ͨ��������Ϊͨ��
	 * 3�����ֹ�������ָ����������perms��roles
	 * </p>
	 * 
	 * @return
	 */
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		
		// ����Լ��Ĺ�������ȡ��Ϊjwt
		Map<String, Filter> filtersMap = factoryBean.getFilters();
		filtersMap.put("jwt", new JwtFilter());
		factoryBean.setFilters(filtersMap);
		
		// �������� SecurityManager
		factoryBean.setSecurityManager(securityManager);
		factoryBean.setUnauthorizedUrl("/401");
		
		/*
		 * �Զ���url����
		 * http://shiro.apache.org/web.html#urls-
		 */
		Map<String, String> filterRuleMap  = new LinkedHashMap<String, String>();
		// ��������ͨ�������Լ���jwt Filter
		filterRuleMap .put("/**", "jwt");
		// ����401��404ҳ�治ͨ�����ǵ�Filter
		filterRuleMap .put("/401", "anon");
		filterRuleMap .put("/404", "anon");
		factoryBean.setFilterChainDefinitionMap(filterRuleMap );
		return factoryBean;
	}

	@Bean
	public SecurityManager securityManager(ShiroRealm realm) {

		// �����Լ������realm
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(realm);
		// ���û��������
		// securityManager.setCacheManager(null);

		// �ر�shiro�Դ���session
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
	 * Description: ���ע��֧��
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		// ǿ��ʹ��cglib����ֹ�ظ�����Ϳ������������������
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
	 * Description: ����shiro aopע�⣬ʹ�ô���ʽ��������Ҫ��������֧��
	 * </p>
	 * 
	 * @param securityManager
	 *            ��ȫ������
	 * @return ��ȨAdvisor
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
	 * Description: shiro������� ��Ҫע���Ӧ������ʵ������ 1����ȫ��������securityManager
	 * �ɼ�securityManager������shiro�ĺ��ģ�
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
