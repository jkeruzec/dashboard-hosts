package fr.dashboard.hosts.web.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.javascript.InitializationPriority;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import fr.exanpe.t5.lib.constants.ExanpeEventConstants;
import fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext;
import fr.dashboard.hosts.web.pages.Commit;
import fr.dashboard.hosts.web.pages.Manage;
import fr.dashboard.hosts.web.pages.TimeToEat;
import fr.dashboard.hosts.web.services.DashboardHostsApplicationScope;

/**
 * Layout component for pages of application dashboard-hosts-web.
 */
@Import(stylesheet = "context:/layout/css/dashboard-hosts.css", library =
{ "context:/layout/js/modernizr/2.5.3/modernizr-2.5.3-min.js", "${exanpe.yui2-base}/yahoo-dom-event/yahoo-dom-event.js",
        "context:/layout/js/dashboard-hosts.js" })
public class Layout
{
    /** The page title, for the <title> element and the <h1>element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Inject
    private DashboardHostsSecurityContext securityContext;

    @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
    @Property
    private boolean productionMode;

    @Inject
    private RequestGlobals globals;

    @Inject
    private JavaScriptSupport jsSupport;

    @Inject
    @Property
    private DashboardHostsApplicationScope aScope;

    @AfterRender
    void initJs()
    {
        JSONObject data = new JSONObject();
        jsSupport.addInitializerCall(InitializationPriority.LATE, "dashboardHostsInitializer", data);
    }

    @OnEvent(ExanpeEventConstants.VERTICALMENU_EVENT)
    Object selectMenu(String menuId)
    {
        if (menuId.equals("commit")) { return Commit.class; }
        if (menuId.equals("manage")) { return Manage.class; }
        return TimeToEat.class;
    }

    public String getUsername()
    {
        return securityContext.getTeam().getName();
    }

    public boolean isAuthenticated()
    {
        return securityContext.isLoggedIn();
    }

    public String getLessMode()
    {
        return productionMode ? "production" : "development";
    }

    public String getContextRoot()
    {
        return globals.getRequest().getContextPath();
    }

}
