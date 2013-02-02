/**
 * 
 */
package fr.dashboard.hosts.web.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.dashboard.hosts.domain.business.HostsManager;
import fr.dashboard.hosts.domain.model.Team;
import fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext;

/**
 * User Profile page
 * 
 * @author ystreibel
 */
public class Profile
{
    @Property
    private Team team;

    @Persist
    @Property
    private String name;

    @Persist
    @Property
    private String email;

    @Property
    private String password;

    @Property
    private String verifyPassword;

    @Component
    private Form form;

    @Inject
    private Messages messages;

    @Inject
    private HostsManager hostsManager;

    @Inject
    private DashboardHostsSecurityContext securityContext;

    @InjectPage
    private Index index;

    @OnEvent(value = EventConstants.ACTIVATE)
    void activate()
    {
        team = hostsManager.find(securityContext.getTeam().getId());
        if (name == null)
        {
            name = team.getName();
        }
        if (email == null)
        {
            email = team.getEmail();
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "form")
    Object update()
    {
        if ((verifyPassword != null && !verifyPassword.equals(password)) || (password != null && !password.equals(verifyPassword)))
        {
            form.recordError(messages.get("password-match-error"));
            return null;
        }

        if (!securityContext.getTeam().getName().equals(name))
        {
            Team exist = hostsManager.findByName(name);
            if (exist != null)
            {
                form.recordError(messages.get("team-name-error"));
                return null;
            }
        }

        team.setName(name);
        team.setEmail(email);

        // Update Team and credentials if needed
        securityContext.getTeam().setName(team.getName());
        hostsManager.updateProfile(team, password);

        return index;
    }
}
