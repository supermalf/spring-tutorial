package com.visiansystems.controller;

import com.visiansystems.model.SimpleObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The SimpleController class is a RESTful web service controller. This controller is simple, and
 * create static objects just to illustrate how the endpoints should work without any database
 * integration.
 */

@RestController
// --> 'RestController' annotation informs Spring that should convert the objects returned from
// controller methods into json or XML responses.
public class SimpleController {

    // The code bellow is just an example to construct objects without using the repositories / DB
    private static Map<Long, SimpleObject> simpleObjectMap = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            simpleObjectMap.put((long)i, new SimpleObject((long)i, "Simple Object #" + i));
        }
    }

    /**
     * Web service endpoint to fetch all 'Simple' entities. The service returns the collection of
     * 'SimpleObject' entities as JSON.
     *
     * @return A ResponseEntity containing a Collection of 'SimpleObject' entities.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/example/simple",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleObject>> getSimpleObjects() {
        return new ResponseEntity<>(simpleObjectMap.values(), HttpStatus.OK);
    }

    /**
     * Web service endpoint to fetch a single Greeting entity by primary key identifier.
     * <p/>
     * If found, the Greeting is returned as JSON with HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body with HTTP status 404.
     *
     * @param id A Long URL path variable containing the 'SimpleObject' identifier.
     * @return A ResponseEntity containing a single 'SimpleObject' entity, if found, and a HTTP status
     * code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/example/simple/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleObject> getSimpleObject(@PathVariable Long id) {

        SimpleObject simpleModel = simpleObjectMap.get(id);

        if (simpleModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(simpleModel, HttpStatus.OK);
    }

    /**
     * Web service endpoint to create a single 'SimpleObject' entity. The HTTP request body is
     * expected to contain a entity in JSON format.
     * <p/>
     * If created successfully, the persisted 'SimpleObject' is returned as JSON with HTTP status
     * 201.
     * <p/>
     * If not created successfully, the service returns an empty response body with HTTP status 500.
     *
     * @param simpleObject The object to be created.
     * @return A ResponseEntity containing a single object, if created successfully, and a HTTP
     * status code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/example/simple",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleObject> createSimpleObject(@RequestBody SimpleObject simpleObject) {

        SimpleObject savedSimpleObject = simpleObjectMap.put(simpleObject.getId(), simpleObject);
        return new ResponseEntity<>(savedSimpleObject, HttpStatus.CREATED);
    }

    /**
     * Web service endpoint to update a single 'SimpleObject' entity. The HTTP request body is expected to
     * contain a 'SimpleObject' in JSON format. The object is updated in the data repository.
     * <p/>
     * If updated successfully, the persisted Greeting is returned as JSON with HTTP status 200.
     * <p/>
     * If not found, the service returns an empty response body and HTTP status 404.
     * <p/>
     * If not updated successfully, the service returns an empty response body with HTTP status 500.
     *
     * @param simpleObject The SimpleObject to be updated.
     * @return A ResponseEntity containing a single SimpleObject, if updated successfully, and a
     * HTTP status code as described in the method comment.
     * @throws Exception Thrown if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/example/simple/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleObject> updateSimpleObject(@RequestBody SimpleObject simpleObject) {

        SimpleObject savedSimpleObject =
                simpleObjectMap.replace(simpleObject.getId(), simpleObject);
        return new ResponseEntity<>(savedSimpleObject, HttpStatus.OK);
    }

    /**
     * Web service endpoint to delete a single Greeting entity. The HTTP request
     * body is empty. The primary key identifier of the Greeting to be deleted
     * is supplied in the URL as a path variable.
     * <p/>
     * If deleted successfully, the service returns an empty response body with
     * HTTP status 204.
     * <p/>
     * If not deleted successfully, the service returns an empty response body
     * with HTTP status 500.
     *
     * @param id A Long URL path variable containing the Greeting primary key
     *           identifier.
     * @return A ResponseEntity with an empty response body and a HTTP status
     * code as described in the method comment.
     * @throws Exception Throw if a problem occurs completing the request.
     */
    @RequestMapping(
            value = "/example/simple/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<SimpleObject> deleteSimpleObject(@PathVariable("id") Long id)
            throws Exception {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
