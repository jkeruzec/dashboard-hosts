/**
 * 
 */
package fr.dashboard.hosts.web.jsConsole.impl;

import org.apache.tapestry5.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import fr.dashboard.hosts.domain.model.Eater;
import fr.dashboard.hosts.domain.model.Team;
import fr.dashboard.hosts.web.constants.JsConsoleCommandEnum;
import fr.dashboard.hosts.web.util.JsConsoleResponseHolder;

/**
 * @author ystreibel
 */
@Service
@Scope("prototype")
public class TopUserCommand extends AbstractJsConsoleCommand
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
        // Validate topuser args
        if (args.length() != 1) { return commandArgsMatchError(JsConsoleCommandEnum.TOPUSER); }

        // Validate username
        String username = args.getString(0);
        Team current = securityContext.getTeam();
        Eater eater = manager.findEaterByName(current, username);
        if (eater == null) { return holder.add("topuser-ko-msg").error(); }
        eater.setPosition(0);
        manager.updateEater(eater);

        return holder.add("topuser-ok-msg");
    }

}
