/**
 * 
 */
package fr.dashboard.hosts.web.jsConsole.impl;

import org.apache.commons.validator.GenericValidator;
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
public class PositionCommand extends AbstractJsConsoleCommand
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
        if (args.length() != 2) { return commandArgsMatchError(JsConsoleCommandEnum.POSITION); }

        if (!GenericValidator.isInt(args.getString(1))) { return holder.add("position-format-ko-msg").error(); }
        Integer position = Integer.valueOf(args.getString(1));

        // Validate username
        String username = args.getString(0);
        Team current = securityContext.getTeam();
        Eater eater = manager.findEaterByName(current, username);
        if (eater == null) { return holder.add("position-username-ko-msg").error(); }
        eater.setPosition(position);
        manager.updateEater(eater);
        return holder.add("position-ok-msg");
    }
}
