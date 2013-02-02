package fr.dashboard.hosts.domain.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Give access through Java to configuration properties
 * 
 * @author ystreibel
 */
@Component
public class DashboardHostsConfiguration
{

    @Value("${mail.enabled}")
    private boolean mailEnabled;

    /**
     * @return the mailEnabled
     */
    public boolean isMailEnabled()
    {
        return mailEnabled;
    }

}
