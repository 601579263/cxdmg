package com.cxdmg.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.cxdmg.service.ApplyClientDetailService;
import com.cxdmg.service.MyUserDetailsService;
//配置授权中心信息
@Configuration
@EnableAuthorizationServer // 开启认证授权中心
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	//密码模式获取token
	//http://localhost:8080/cxdmg-web/oauth/token?username=zhangsan&password=1&client_id=clientId45&client_secret=1&grant_type=password
	
	
	@Autowired
	//@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	
	@Bean   // 声明ApplyClientDetailService
    public ApplyClientDetailService getClientDetails() {
        return new ApplyClientDetailService();
    }

	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
		//return new JdbcTokenStore(dataSource); /// 使用Jdbctoken store
	}
	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 自定义添加授权用户,配置客户端
		clients.withClientDetails(getClientDetails());
		
		/*clients.jdbc(dataSource)
		.withClient("clientId4").secret(bcrPasswordEncoder().encode("1"))
		.authorizedGrantTypes("password", "refresh_token", "authorization_code")// 允许授权范围
		.redirectUris("http://www.mayikt.com").authorities("ROLE_ADMIN", "ROLE_USER")// 客户端可以使用的权限
		.scopes("all").accessTokenValiditySeconds(7200).refreshTokenValiditySeconds(7200);*/
	}
	
	@Bean
	public MyUserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//将增强的token设置到增强链中
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtAccessTokenConverter()));
       
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
				.userDetailsService(myUserDetailsService)// 必须设置
				.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
				.accessTokenConverter(jwtAccessTokenConverter())
				.tokenEnhancer(enhancerChain);		          
	                							
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()")
				.allowFormAuthenticationForClients();// 允许表单登录
	}
	
	
	/**
	 * jwt对称加密
	 * @return
	 *//*
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}*/
	
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    KeyStoreKeyFactory keyStoreKeyFactory = 
	      new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
	    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
	    return converter;
	}
	
	@Bean
    public TokenEnhancer customTokenEnhancer() {
        return new CustomTokenEnhancer();
    }
}