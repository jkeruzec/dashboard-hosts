package fr.dashboard.hosts.web.pages;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.dashboard.hosts.domain.business.HostsManager;
import fr.dashboard.hosts.domain.model.Eater;
import fr.dashboard.hosts.domain.security.DashboardHostsSecurityContext;

/**
 * Manage page
 */
@Import(stylesheet = "${exanpe.asset-base}/css/exanpe-t5-lib-skin.css")
public class Manage
{
    @Persist
    @Property
    private List<Eater> eaters;

    @Property
    private Eater currentEater;

    @Property
    @Persist
    private Eater editEater;

    @Inject
    private HostsManager hostsManager;

    @Inject
    private DashboardHostsSecurityContext securityContext;

    @InjectComponent
    private Zone editZone;

    @InjectComponent
    private Grid gridEaters;

    void onActivate()
    {
        // always need a fresh copy for a crud page
        eaters = hostsManager.findEatersByTeam(securityContext.getTeam());

        if (CollectionUtils.isNotEmpty(eaters))
        {
            if (gridEaters.getSortModel().getSortConstraints().isEmpty())
            {
                gridEaters.getSortModel().updateSort("position");
            }
        }

    }

    @OnEvent(value = EventConstants.ACTION, component = "initEdit")
    Object initEdit(Integer id)
    {
        editEater = null;
        for (Eater e : eaters)
        {
            if (id.equals(e.getId()))
            {
                editEater = e;
                break;
            }
        }
        if (editEater == null) { return NiceTry.class; }
        return editZone.getBody();

    }

    @OnEvent(value = EventConstants.ACTION, component = "initAdd")
    Object initAdd()
    {
        editEater = new Eater();
        editEater.setPosition(hostsManager.getNextMaxPosition(securityContext.getTeam()));
        return editZone.getBody();
    }

    @OnEvent(value = EventConstants.ACTION, component = "delete")
    Object delete(Integer id)
    {
        // security check
        if (secureId(id))
        {
            hostsManager.deleteEater(id);
        }
        else
        {
            return NiceTry.class;
        }
        editEater = null;
        return this;
    }

    @OnEvent(value = EventConstants.SUCCESS, component = "form")
    void edit()
    {
        if (null != editEater.getId())
        {
            hostsManager.updateEater(editEater);
        }
        else
        {
            hostsManager.addEater(securityContext.getTeam(), editEater);
        }
        editEater = null;
    }

    private boolean secureId(Integer id)
    {
        for (Eater e : eaters)
        {
            if (e.getId().equals(id)) { return true; }
        }

        return false;
    }
}
