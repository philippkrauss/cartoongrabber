package cartoongrabber.service;

/**
 * Entry point to the cartoon grabber functionality. Used as a spring-integration gateway.
 * Created by Philipp Krauß on 14.07.2017.
 */
public interface GrabberService {

    void grab(String name);
}
