package com.visiansystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The DocsController is a Spring MVC web controller class which serves the Swagger user interface
 * HTML page.
 */
@Controller
public class DocsController {

    /**
     * Request handler to serve the Swagger user interface HTML page configured
     * to the mapped context path.
     *
     * @return A String name of the Swagger user interface HTML page name.
     */
    @RequestMapping("/docs")
    public void handleDocsRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
}
