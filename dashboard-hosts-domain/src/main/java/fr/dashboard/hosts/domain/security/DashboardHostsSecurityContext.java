/**
 * 
 */
package fr.dashboard.hosts.domain.security;

import fr.dashboard.hosts.domain.model.Team;

/**
 * Contexte de sécurité de l'application DashboardHosts.
 * 
 * @author ystreibel
 */
public interface DashboardHostsSecurityContext
{
    /**
     * Authenticate a user.
     */
    void login(String login, String password);

    /**
     * Teste si un utilisateur est authentifié ou non.
     * 
     * @return true si authentifié
     */
    boolean isLoggedIn();

    /**
     * Retourne l'utilisateur {@link Team} authentifié.
     * 
     * @return utilisateur courant authentifié.
     */
    Team getTeam();

    /**
     * Deconnecte l'utilisateur courant
     */
    void logout();
}
