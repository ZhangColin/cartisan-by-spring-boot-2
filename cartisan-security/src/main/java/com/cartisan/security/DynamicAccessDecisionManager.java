package com.cartisan.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * 动态权限决策管理器，用于判断用户是否有访问权限
 * @author colin
 */
@Component
@ConditionalOnBean(DynamicSecurityService.class)
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未被配置资源时直接放行
        if (CollectionUtils.isEmpty(configAttributes)) {
            return;
        }

        final Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            final ConfigAttribute configAttribute = iterator.next();
            // 将访问资源与用户拥有资源进行比对
            final String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (needAuthority.trim().equals(authority.getAuthority())) {
                    return;
                }
            }
        }

        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
