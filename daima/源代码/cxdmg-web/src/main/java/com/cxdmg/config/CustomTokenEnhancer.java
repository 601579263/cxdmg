package com.cxdmg.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.cxdmg.vo.MbUserVo;

public class CustomTokenEnhancer implements TokenEnhancer {

	/**
	 * 获取令牌时自定义用户信息
	 */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        if(authentication.getAuthorities()!=null) {
        	MbUserVo mbUserVo=(MbUserVo) authentication.getUserAuthentication().getPrincipal();
        	additionalInfo.put("name", mbUserVo.getName());
        	additionalInfo.put("username", mbUserVo.getUsername());
        	additionalInfo.put("authorities", mbUserVo.getAuthorities());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
