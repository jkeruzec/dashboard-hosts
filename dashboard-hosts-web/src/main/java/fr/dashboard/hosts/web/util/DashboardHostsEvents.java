/**
 * 
 */
package fr.dashboard.hosts.web.util;

import fr.dashboard.hosts.web.components.JsConsole;

/**
 * Evénements déclenchés depuis un composant ou un mixin de la lib dashboard-hosts.
 * 
 * @author ystreibel
 */
public class DashboardHostsEvents
{
    /**
     * Evenement déclenché par l'execution de commandes via la {@link JsConsole}
     */
    public static final String JSCONSOLE_COMMAND_EVENT = "jsConsoleCommandEvent";
}
