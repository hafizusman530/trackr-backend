package de.techdev.trackr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * This class is needed to enable the global method security via {@link org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity}
 * in the controllers which are only in other contexts than the security configuration.
 * <p>
 * It provides the authentication manager from the root security context and sets the role hierarchy for
 * method security expressions so {@link org.springframework.security.access.prepost.PreAuthorize} works.
 *
 * @author Moritz Schulze
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    /**
     * This is needed so {@link org.springframework.security.access.prepost.PreAuthorize} and so on know the role hierarchy.
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler methodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        methodSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);
        return methodSecurityExpressionHandler;
    }

    @Autowired
    private RoleHierarchy roleHierarchy;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManager;
    }
}
