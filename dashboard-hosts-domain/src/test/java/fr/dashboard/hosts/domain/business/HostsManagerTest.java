/**
 * 
 */
package fr.dashboard.hosts.domain.business;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.spring.annotation.SpringBeanByType;

import fr.dashboard.hosts.domain.base.DashboardHostsDomainBaseTest;
import fr.dashboard.hosts.domain.model.Eater;
import fr.dashboard.hosts.domain.model.Team;

/**
 * @author ystreibel
 */
@DataSet("/dataset/DashboardHostsBusinessTest.xml")
public class HostsManagerTest extends DashboardHostsDomainBaseTest
{
    @SpringBeanByType
    private HostsManager hostsManager;

    private static final String TEAM_PROJECT = "Team";

    @Test
    public void findByName()
    {
        Team expected = hostsManager.findByName(TEAM_PROJECT);
        ReflectionAssert.assertPropertyReflectionEquals("id", 1L, expected);
        ReflectionAssert.assertPropertyReflectionEquals("name", "Team", expected);
        ReflectionAssert.assertPropertyReflectionEquals("password", "te.am", expected);

        Assert.assertNotNull(expected.getEaters());
        Assert.assertFalse(expected.getEaters().isEmpty());
        Assert.assertEquals(expected.getEaters().size(), 3);
    }

    @Test
    public void findEatersByTeam()
    {
        Team expected = hostsManager.findByName(TEAM_PROJECT);

        List<Eater> eaters = hostsManager.findEatersByTeam(expected);

        Assert.assertNotNull(eaters);
        Assert.assertFalse(eaters.isEmpty());
        Assert.assertEquals(eaters.size(), 3);
    }

    @Test
    public void deleteEater()
    {
        Team expected = hostsManager.findByName(TEAM_PROJECT);

        Assert.assertNotNull(expected.getEaters());
        Assert.assertFalse(expected.getEaters().isEmpty());
        Assert.assertEquals(expected.getEaters().size(), 3);

        hostsManager.deleteEater(expected.getEaters().get(0).getId());

        List<Eater> eaters = hostsManager.findEatersByTeam(expected);

        Assert.assertNotNull(eaters);
        Assert.assertFalse(eaters.isEmpty());
        Assert.assertEquals(eaters.size(), 2);

    }

    @Test
    public void addEater()
    {
        Team team = hostsManager.findByName(TEAM_PROJECT);

        Assert.assertNotNull(team.getEaters());
        Assert.assertFalse(team.getEaters().isEmpty());
        Assert.assertEquals(team.getEaters().size(), 3);

        Eater e = new Eater();
        e.setName("tquaryan");

        hostsManager.addEater(team, e);

        List<Eater> eaters = hostsManager.findEatersByTeam(team);

        Assert.assertNotNull(eaters);
        Assert.assertFalse(eaters.isEmpty());
        Assert.assertEquals(eaters.size(), 4);

    }

    @Test
    public void updateEater()
    {
        Team team = hostsManager.findByName(TEAM_PROJECT);

        Assert.assertNotNull(team.getEaters());
        Assert.assertFalse(team.getEaters().isEmpty());
        Assert.assertEquals(team.getEaters().size(), 3);

        Eater e = team.getEaters().get(0);
        e.setPosition(15);

        hostsManager.updateEater(e);

        List<Eater> eaters = hostsManager.findEatersByTeam(team);

        Assert.assertNotNull(eaters);
        Assert.assertFalse(eaters.isEmpty());
        Assert.assertEquals(eaters.size(), 3);
        Assert.assertEquals(eaters.get(0).getPosition().longValue(), 15);

    }

    @Test
    public void getNextMaxPosition()
    {
        Team team = hostsManager.findByName(TEAM_PROJECT);

        Assert.assertEquals(hostsManager.getNextMaxPosition(team), 6);

        Eater e = team.getEaters().get(0);
        e.setPosition(15);

        hostsManager.updateEater(e);

        Assert.assertEquals(hostsManager.getNextMaxPosition(team), 16);
    }

    @Test
    public void incrementEaters()
    {
        Team team = hostsManager.findByName(TEAM_PROJECT);
        int positionJmx = 0;
        int positionEdo = 0;
        List<Eater> toIncrement = new ArrayList<Eater>();

        for (int i = 0; i < team.getEaters().size(); i++)
        {
            if (team.getEaters().get(i).getName().equals("eater1"))
            {
                positionJmx = team.getEaters().get(i).getPosition();
                toIncrement.add(team.getEaters().get(i));
            }
            else if (team.getEaters().get(i).getName().equals("eater2"))
            {
                positionEdo = team.getEaters().get(i).getPosition();
                toIncrement.add(team.getEaters().get(i));
            }
        }

        hostsManager.incrementEaters(toIncrement);

        List<Eater> eaters = hostsManager.findEatersByTeam(team);

        for (int i = 0; i < eaters.size(); i++)
        {
            if (eaters.get(i).getName().equals("eater1"))
            {
                Assert.assertEquals(eaters.get(i).getPosition().intValue(), positionJmx + 1);
            }
            else if (eaters.get(i).getName().equals("eater2"))
            {
                Assert.assertEquals(eaters.get(i).getPosition().intValue(), positionEdo + 1);
            }
        }
    }

    @Test
    public void topEaters()
    {
        Team team = hostsManager.findByName(TEAM_PROJECT);
        List<Eater> toTop = new ArrayList<Eater>();

        for (int i = 0; i < team.getEaters().size(); i++)
        {
            if (team.getEaters().get(i).getName().equals("eater1"))
            {
                toTop.add(team.getEaters().get(i));
            }
            else if (team.getEaters().get(i).getName().equals("eater2"))
            {
                toTop.add(team.getEaters().get(i));
            }
        }

        hostsManager.topEaters(team, toTop);

        List<Eater> eaters = hostsManager.findEatersByTeam(team);

        for (int i = 0; i < eaters.size(); i++)
        {
            if (eaters.get(i).getName().equals("eater1"))
            {
                Assert.assertEquals(eaters.get(i).getPosition().intValue(), 6);
            }
            else if (eaters.get(i).getName().equals("eater2"))
            {
                Assert.assertEquals(eaters.get(i).getPosition().intValue(), 6);
            }
            else
            {
                Assert.assertNotSame(eaters.get(i).getPosition().intValue(), 6);
            }
        }
    }

    @Test
    public void findEaterByName()
    {
        Team team = hostsManager.findByName(TEAM_PROJECT);
        Eater eater = hostsManager.findEaterByName(team, "eater1");
        Assert.assertNotNull(eater);
        Assert.assertEquals(eater.getPosition().intValue(), 3);
    }
}
