package com.cxdmg.config;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
/**
 * Security配置
 * @author 60157
 *@EnableWebSecurity完成的工作便是加载了
 *WebSecurityConfiguration
 *，AuthenticationConfiguration
 *这两个核心配置类，
 *也就此将spring security的职责划分为了配置安全信息
 *，配置认证信息两部分
 */

import com.cxdmg.handler.MyAuthenticationFailureHandler;
import com.cxdmg.handler.MyAuthenticationSuccessHandler;
import com.cxdmg.service.MbUserService;
import com.cxdmg.service.MyUserDetailsService;
import com.cxdmg.service.PermissionService;
import com.cxdmg.util.MD5Util;
@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private MyAuthenticationFailureHandler failureHandler;
	@Autowired
	private MyAuthenticationSuccessHandler successHandler;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * 配置认证用户信息和权限
	 */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 如果想实现动态账号与数据库关联 在该地方改为查询数据库
		auth.userDetailsService(myUserDetailsService).passwordEncoder(new PasswordEncoder() {
			// 加密的密码与数据库密码进行比对CharSequence rawPassword 表单字段 encodedPassword
			// 数据库加密字段
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				System.out.println("rawPassword:" + rawPassword + ",encodedPassword:" + encodedPassword);
				// 返回true 表示认证成功 返回fasle 认证失败
				return MD5Util.encode((String) rawPassword).equals(encodedPassword);
			}

			// 对表单密码进行加密
			public String encode(CharSequence rawPassword) {
				System.out.println("rawPassword:" + rawPassword);
				return MD5Util.encode((String) rawPassword);
			}
		});
	}
	/**
	 * 配置拦截请求资源
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>
		.ExpressionInterceptUrlRegistry authorizeRequests
		//授权请求
		=http.authorizeRequests();
		//读取数据库权限列表
		List<Map<String,Object>>perList=permissionService.getPermissionList();
		for (int i = 0; i < perList.size(); i++) {
			//设置权限 访问的路径
			authorizeRequests.antMatchers(perList.get(i).get("url").toString())
			//多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回true
			.hasAnyAuthority(perList.get(i).get("perm_tag").toString());
		}
		//antMatchers()方法所设定的路径支持Ant风格的通配符,permitAll 许可所有
		//用户重定向到应用的登录界面, 默认不拦截静态资源的url
		authorizeRequests
		.antMatchers("/login").permitAll()
		.antMatchers("/toLogin").permitAll()
		.antMatchers("/getForgetPwd").permitAll()
		.antMatchers("/sendOut").permitAll()
		.antMatchers("/updateNewPwd").permitAll()
		.antMatchers("/register").permitAll()
		//qq登陆
		.antMatchers("/qqLoginCallback").permitAll()
		//.antMatchers("/index").permitAll()
		//.antMatchers("/index").permitAll()
		//完全认证的 拦截所有请求, formLogin以表单形式进行认证
		//.antMatchers("/**").fullyAuthenticated().and().formLogin()
		//需要认证才能访问
		.anyRequest().authenticated().and().formLogin()
		//自定义登录页url,默认为/login,跳到后台/login方法
		 // 关闭csrf保护功能（跨域访问,出现403错误并且提示信息为“Could not verify the provided CSRF token because your session was not found in spring security
		.loginPage("/login").successHandler(successHandler)
		.and()
	    .rememberMe()
	    .tokenRepository(persistentTokenRepository())
	    // 失效时间
	    .tokenValiditySeconds(3600)
	    .userDetailsService(myUserDetailsService)
	    .and().csrf().disable();

		http
         .logout()
         .logoutUrl("/logout").permitAll()//自定义退出的地址
         //.logoutSuccessUrl("/login")//退出之后跳转到注册页面
         .logoutSuccessUrl("/exit").permitAll()
         .deleteCookies("JSESSIONID").permitAll()//删除当前的JSESSIONID
         .invalidateHttpSession(true)//默认为true,用户在退出后Http session失效
         .and()
        /* .rememberMe()//启用记住我
 		.tokenRepository(persistentTokenRepository())
 		.tokenValiditySeconds(3600)//有效时间,单位秒
 		.userDetailsService(myUserDetailsService)*/;
		
        
	}
	/**
	 * 不拦截静态资源的访问
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
	}
	
	/**
	 * SpringBoot2.0抛弃了原来的NoOpPasswordEncoder，要求用户保存的密码必须要使用加密算法后存储，在登录验证的时候Security会将获得的密码在进行编码后再和数据库中加密后的密码进行对比
	 * @return
	 */
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	/**
	 * 防止authenticationManager 无法注入
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	/**
	 * 持久token仓库
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl=new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}
}
