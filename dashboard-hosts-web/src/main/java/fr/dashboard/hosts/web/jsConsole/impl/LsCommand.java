/**
 * 
 */
package fr.dashboard.hosts.web.jsConsole.impl;

import java.util.List;

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
public class LsCommand extends AbstractJsConsoleCommand
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
        if (args.length() > 1) { return commandArgsMatchError(JsConsoleCommandEnum.LS); }

        Integer limit = null;
        if (args.length() == 1)
        {
            if (!GenericValidator.isInt(args.getString(0))) { return holder.add("ls-format-ko-msg").error(); }
            limit = Integer.valueOf(args.getString(0));
        }

        Team team = manager.findByName(securityContext.getTeam().getName());
        List<Eater> eaters = manager.findEatersByTeam(team);
        int index = 0;
        for (Eater eater : eaters)
        {
            index++;
            String username = index + ". " + eater.getName();
            if (index < eaters.size())
            {
                username += "\n";
            }

            if (limit != null && limit == index)
            {
                holder.add(username);
                break;
            }

            holder.add(username);
        }
        return holder;
    }
}
