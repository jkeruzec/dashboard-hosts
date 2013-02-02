/**
 * 
 */
package fr.dashboard.hosts.domain.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.dashboard.hosts.domain.business.HostsManager;
import fr.dashboard.hosts.domain.model.Team;

/**
 * Implémentation de l'interface {@link UserDetailsService} fournie par Spring Security.
 * Permet de faire le pont entre notre implémentation spécifique et le gestionnaire
 * d'authentification de Spring Security.
 * 
 * @author ystreibel
 */
@Component("dashboard-hostsUserDetailService")
public class UserDetailsServiceImpl implements UserDetailsService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * DashboardHosts {@link HostsManager}
     */
    @Autowired
    private HostsManager hostsManager;

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.
     * lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug(">>> loadUserByUsername username: " + username);
        }
        Team team = hostsManager.findByName(username);

        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("<<< User: " + team);
        }
        return team;
    }

}
