/**
 * 
 */
package fr.dashboard.hosts.web.jsConsole.impl;

import org.apache.tapestry5.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import fr.dashboard.hosts.web.constants.JsConsoleCommandEnum;
import fr.dashboard.hosts.web.util.JsConsoleResponseHolder;

/**
 * @author ystreibel
 */
@Service
@Scope("prototype")
public class LoginCommand extends AbstractJsConsoleCommand
{

    /*
     * (non-Javadoc)
     * @see
     * fr.dashboard.hosts.web.services.jsConsole.Command#execute(org.apache.tapestry5.json.JSONArray
     * )
     */
    @Override
    public JsConsoleResponseHolder execute(JSONArray args)
    {
        if (args.length() != 2) { return commandArgsMatchError(JsConsoleCommandEnum.LOGIN); }
        securityContext.login(args.getString(0), args.getString(1));
        return securityContext.isLoggedIn() ? holder.add("user-login-ok-msg") : holder.add("user-login-ko-msg").error();
    }
}
