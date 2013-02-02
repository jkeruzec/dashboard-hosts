package fr.dashboard.hosts.web.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

import fr.dashboard.hosts.common.exception.BusinessException;
import fr.dashboard.hosts.web.services.exceptionHandler.ExceptionHandlerService;

/**
 * Displays a message caused by a {@link BusinessException}
 * 
 * @author ystreibel
 */
public class BusinessExceptionMessage
{
    @Inject
    private Logger logger;

    @Inject
    private Request request;

    @Inject
    private ComponentResources resources;

    @Property
    private String message;

    @SetupRender
    boolean init()
    {
        String businessMessage = (String) request.getAttribute(ExceptionHandlerService.REQ_BUSINESS_MESSAGE);

        if (businessMessage == null)
            return false;

        if (resources.getMessages().contains(businessMessage))
        {
            message = resources.getMessages().get(businessMessage);
        }
        else
        {
            message = businessMessage;
        }

        logger.debug("Displaying BusinessException message {}", message);

        return true;
    }
}
