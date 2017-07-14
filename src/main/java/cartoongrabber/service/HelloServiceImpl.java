package cartoongrabber.service;

import org.springframework.stereotype.Service;

/**
 * Created by Philipp Krauß on 14.07.2017.
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name + "!");
    }
}
