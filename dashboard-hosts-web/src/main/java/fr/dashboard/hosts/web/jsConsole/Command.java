/**
 * 
 */
package fr.dashboard.hosts.web.jsConsole;

import org.apache.tapestry5.json.JSONArray;

import fr.dashboard.hosts.web.util.JsConsoleResponseHolder;

/**
 * @author ystreibel
 */
public interface Command
{
    JsConsoleResponseHolder execute(JSONArray args);
}
