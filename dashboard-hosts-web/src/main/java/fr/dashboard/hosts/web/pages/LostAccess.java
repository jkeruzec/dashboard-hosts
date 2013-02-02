/**
 * 
 */
package fr.dashboard.hosts.web.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
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
 * Sign Up page
 * 
 * @author ystreibel
 */
@Import(stylesheet = "${exanpe.asset-base}/css/exanpe-t5-lib-skin.css")
public class LostAccess
{
    @Property
    private String name;

    @Inject
    private HostsManager hostsManager;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private boolean sendOk;

    @InjectComponent
    private Form form;

    @Inject
    private Messages messages;

    @Inject
    private DashboardHostsSecurityContext securityContext;

    @OnEvent(value = EventConstants.ACTIVATE)
    public Object activate()
    {
        return securityContext.isLoggedIn() ? Index.class : null;
    }

    @OnEvent(value = EventConstants.VALIDATE, component = "form")
    public void validate()
    {
        Team exist = hostsManager.findByName(name);
        if (exist == null)
        {
            form.recordError(messages.get("name-unknown"));
        }
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "form")
    public void remind()
    {
        hostsManager.resetPassword(name);
        sendOk = true;
    }
}
