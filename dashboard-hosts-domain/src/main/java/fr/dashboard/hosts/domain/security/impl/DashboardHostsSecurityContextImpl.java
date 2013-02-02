/**
 * 
 */
package fr.dashboard.hosts.domain.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import fr.dashboard.hosts.domain.business.HostsManager;
import fr.dashboard.hosts.domain.model.Team;
import fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext;

/**
 * Implementation du SecurityContext propre à dashboard-hosts
 * 
 * @author ystreibel
 */
@Component("dashboard-hostsSecurityContext")
public class DashboardHostsSecurityContextImpl implements DashboardHostsSecurityContext
{
    /**
     * DashboardHosts {@link HostsManager}
     */
    @Autowired
    private HostsManager hostsManager;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * (non-Javadoc)
     * @see
     * fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext#login(fr.dashboard.
     * hosts.domain.model
     * .User)
     */
    @Override
    public void login(String login, String password)
    {
        Assert.notNull(login, "login");
        Assert.notNull(password, "password");

        Team exist = hostsManager.findByName(login);
        if (exist != null)
        {
            String toCheck = passwordEncoder.encodePassword(password, saltSource.getSalt(exist));
            if (exist.getPassword().equals(toCheck))
            {
                UsernamePasswordAuthenticationToken logged = new UsernamePasswordAuthenticationToken(exist, exist.getPassword(), exist.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(logged);
                return;
            }
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    /*
     * (non-Javadoc)
     * @see fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext#isLoggedIn()
     */
    @Override
    public boolean isLoggedIn()
    {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null)
        {
            if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) { return false; }
            return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext#getUser()
     */
    @Override
    public Team getTeam()
    {
        Team team = null;
        if (isLoggedIn())
        {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Team)
            {
                team = ((Team) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            }
        }
        return team;
    }

    /*
     * (non-Javadoc)
     * @see fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext#logout()
     */
    @Override
    public void logout()
    {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

}
