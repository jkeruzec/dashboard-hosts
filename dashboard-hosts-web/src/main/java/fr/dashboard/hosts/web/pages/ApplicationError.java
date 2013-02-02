package fr.dashboard.hosts.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ExceptionReporter;

import fr.dashboard.hosts.web.services.DashboardHostsApplicationScope;

public class ApplicationError implements ExceptionReporter
{
    @Property
    private String message;

    @Inject
    @Property
    private DashboardHostsApplicationScope aScope;

    @Override
    public void reportException(Throwable exception)
    {
        this.message = exception.getMessage();
    }

}
