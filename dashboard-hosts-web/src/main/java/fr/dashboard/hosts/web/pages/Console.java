package fr.dashboard.hosts.web.pages;

import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import fr.dashboard.hosts.web.jsConsole.JsConsoleCommandInvoker;
import fr.dashboard.hosts.web.util.JsConsoleResponseHolder;
import fr.dashboard.hosts.web.util.DashboardHostsEvents;

/**
 * Console popup
 */
@Import(library =
{ "context:/layout/js/modernizr/2.5.3/modernizr-2.5.3-min.js" })
public class Console
{
    @Inject
    private JsConsoleCommandInvoker invoker;

    @OnEvent(value = DashboardHostsEvents.JSCONSOLE_COMMAND_EVENT)
    JsConsoleResponseHolder onJsConsoleEvent(String command, JSONArray args)
    {
        return invoker.invoke(command, args);
    }

    @Inject
    private RequestGlobals request;

    @Environmental
    private JavaScriptSupport jsSupport;

    @PageLoaded
    public void consoleLoaded()
    {
        request.getHTTPServletRequest().getSession(true);
    }

    public String getMotD()
    {
        String motd = " ";
        motd += "    ____             __    __                         ____  __           __      \n";
        motd += "   / __ \\____ ______/ /_  / /_  ____  ____ __________/ / / / /___  _____/ /______\n";
        motd += "  / / / / __ `/ ___/ __ \\/ __ \\/ __ \\/ __ `/ ___/ __  / /_/ / __ \\/ ___/ __/ ___/\n";
        motd += " / /_/ / /_/ (__  ) / / / /_/ / /_/ / /_/ / /  / /_/ / __  / /_/ (__  ) /_(__  ) \n";
        motd += "/_____/\\__,_/____/_/ /_/_.___/\\____/\\__,_/_/   \\__,_/_/ /_/\\____/____/\\__/____/  \n";
        motd += "\n";
        return motd;
    }
}
