package com.cxdmg.config;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;
/**
 * 自定义CustomerAccessTokenConverter 这个类的作用主要用于AccessToken的转换，
 * 默认使用DefaultAccessTokenConverter 这个装换器
 * DefaultAccessTokenConverter有个UserAuthenticationConverter，这个转换器作用是把用户的信息放入token中，默认只是放入user_name
 * <p>
 * 自定义这个方法，加入了额外的信息
 * <p>
 **/
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter{
	public CustomAccessTokenConverter() {
	    super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
	  }

	  private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

	    // 资源服务获得自定义信息  
	    @Override
	    public Authentication extractAuthentication(Map<String, ?> map) {
	      Collection <? extends GrantedAuthority> authorities = this.getAuthorities(map);
	      return new UsernamePasswordAuthenticationToken(map, "N/A", authorities);
	    }

	    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
	      if (!map.containsKey("authorities")) {
	        return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(new String[]{"USER"}));
	      } else {
	        Object authorities = map.get("authorities");
	        if (authorities instanceof String) {
	          return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
	        } else if (authorities instanceof Collection) {
	          return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection) authorities));
	        } else {
	          throw new IllegalArgumentException("权限必须是字符串或者集合");
	        }
	      }
	    }

	  }
}
