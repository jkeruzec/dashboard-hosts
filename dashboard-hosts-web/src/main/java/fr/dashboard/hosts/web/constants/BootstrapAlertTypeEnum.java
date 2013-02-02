/**
 * 
 */
package fr.dashboard.hosts.web.constants;

import fr.dashboard.hosts.web.components.BootstrapAlert;

/**
 * Type d'alertes prise en compte par le composant {@link BootstrapAlert}
 * 
 * @see http://twitter.github.com/bootstrap/components.html#alerts
 * @author ystreibel
 */
public enum BootstrapAlertTypeEnum
{
    INFO, SUCCESS, ERROR;

    public String toString()
    {
        return super.toString().toLowerCase();
    };
}
