package fr.dashboard.hosts.domain.base;

import org.unitils.UnitilsTestNG;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * Classe de base pour l'écriture des tests.
 * Permet de factoriser les éléments communs à l'ensemble des tests.
 * 
 * @author ystreibel
 */
@SpringApplicationContext("applicationContext-domain-test.xml")
public abstract class DashboardHostsDomainBaseTest extends UnitilsTestNG
{

}
