/**
 * 
 */
package fr.dashboard.hosts.web.services;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import fr.dashboard.hosts.domain.business.HostsManager;
import fr.dashboard.hosts.domain.model.Team;

/**
 * @author ystreibel
 */
public class DashboardHostsApplicationScope
{
    /**
     * Logger de la classe
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardHostsApplicationScope.class);

    private String adminMail;

    /**
     * Constructeur.
     * 
     * @param context Contexte Spring
     * @param productionMode Mode Tapestry (PRODUCTION ou DEVELOPPEMENT)
     * @throws SQLException
     */
    public DashboardHostsApplicationScope(ApplicationContext context) throws SQLException
    {
        adminMail = initAdminMail(context);

    }

    private String initAdminMail(ApplicationContext context)
    {
        HostsManager hostsManager = (HostsManager) context.getBean(HostsManager.class);
        Team p = hostsManager.findByName("admin");
        if (p == null)
            return "";
        return p.getEmail();
    }

    public String getAdminMail()
    {
        return adminMail;
    }
}
