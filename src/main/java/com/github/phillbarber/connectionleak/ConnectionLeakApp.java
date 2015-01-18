package com.github.phillbarber.connectionleak;

import com.google.common.io.Resources;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.File;

public class ConnectionLeakApp extends Application<AppConfig> {


    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {

    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {

        final Client client = new JerseyClientBuilder(environment).using(appConfig.getJerseyClientConfiguration())
                .build(AppConfig.GOOGLE_RESOURCE_HTTP_CLIENT);


        //environment.jersey().register(new GoogleStatusResourceWithAConnectionLeak(client));
        environment.jersey().register(new GoogleStatusResourceWithNoConnectionLeak(client));
    }

    public static void main(String[] args) throws Exception{
        if (args == null || args.length == 0) {
            args = new String[]{"server", new File(Resources.getResource("config-default.yml").toURI()).getAbsolutePath()};
        }

        new ConnectionLeakApp().run(args);
    }
}
