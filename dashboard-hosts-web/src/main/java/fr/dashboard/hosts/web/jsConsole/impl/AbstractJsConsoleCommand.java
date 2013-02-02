/**
 * 
 */
package fr.dashboard.hosts.web.jsConsole.impl;

import org.apache.tapestry5.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import fr.dashboard.hosts.domain.business.HostsManager;
import fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext;
import fr.dashboard.hosts.web.constants.JsConsoleCommandEnum;
import fr.dashboard.hosts.web.jsConsole.Command;
import fr.dashboard.hosts.web.util.JsConsoleResponseHolder;

/**
 * @author ystreibel
 */
@Service
@Scope("prototype")
public abstract class AbstractJsConsoleCommand implements Command
{
    protected JsConsoleResponseHolder holder;
    protected final static String ARGS_SEPARATOR = "|";

    @Autowired
    protected HostsManager manager;

    @Autowired
    protected DashboardHostsSecurityContext securityContext;

    public AbstractJsConsoleCommand()
    {
        holder = JsConsoleResponseHolder.create();
    }

    protected JsConsoleResponseHolder commandArgsMatchError(JsConsoleCommandEnum commandType)
    {
        return holder.add(
                "Les arguments fournis a la commande: '" + commandType + "' ne sont pas corrects.\nUtilisez la commande 'help' pour obtenir de l'aide.")
                .error();
    }

    /*
     * (non-Javadoc)
     * @see
     * fr.dashboard.hosts.web.services.jsConsole.Command#execute(org.apache.tapestry5.json.JSONArray
     * )
     */
    @Override
    public abstract JsConsoleResponseHolder execute(JSONArray args);
}
