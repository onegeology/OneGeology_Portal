package fr.brgm.mapClient.utils.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.StringWriter;
import java.util.Properties;

/**
 * Creates XML with Velocity.
 */
public class VelocityCreator {

    /**
     * Creates XML with Velocity.
     *
     * @param context      Velocity Context for the XML.
     * @param templateName TemplateDTO to Use to create the XML.
     * @return Created XML.
     * @throws Exception
     */
    public static String createXMLbyContext(VelocityContext context, String templateName) throws Exception {

        VelocityEngine ve = new VelocityEngine();
        try {
            Properties p = new Properties();
            p.setProperty("resource.loaders", "class");
            p.setProperty("resource.loader.class.description", "Resource Loader");
            p.setProperty("resource.loader.class.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader ");
            // Settings to maximize backward compatibility of Velocity v2.3 with previously used v1.7
            // See https://velocity.apache.org/engine/2.3/upgrading.html
            p.setProperty("introspector.conversion_handler.class", "none");
            p.setProperty("parser.space_gobbling", "bc");
            p.setProperty("directive.if.empty_check", "false");
            p.setProperty("parser.allow_hyphen_in_identifiers", "true");
            p.setProperty("velocimacro.enable_bc_mode", "true");
            p.setProperty("event_handler.invalid_references.quiet", "true");
            p.setProperty("event_handler.invalid_references.null", "true");
            p.setProperty("event_handler.invalid_references.tested", "true");
            ve.init(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Template template = ve.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString().trim();
    }
}
