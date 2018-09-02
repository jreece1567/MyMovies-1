<html>
    <head>
    </head>
    <body>
        <h2>MyMovies</h2><h3>A View Into My DVD Collection</h3>
        The MyMovies application provides a searchable database of all videos in the collection.<br/><br/>
        For each video, the database contains:<br/>
        <ul>
            <li>The video 'poster' image.</li>
            <li>The video title.</li>
            <li>The release year.</li>
            <li>The run-time of the video.</li>
            <li>The name of the director.</li>
            <li>The complete cast list, a list of actor-names.</li>
            <li>A plot synopsis.</li>
            <li>A list of 'genres' to which the video belongs.</li>
            <li>The MPAA rating of the video.</li>
            <li>A list of arbitrary 'tags' associated with the video.</li>
        </ul>
        For episodic shows, the database also contains:<br/>
        <ul>
            <li>The title of each episode.</li>
            <li>The season and episode number of each episode.</li>
            <li>A plot synopsis of each episode.</li>
        </ul>
        A number of fields are indexed, providing access to:
        <ul>
            <li>Movies by 'actor'</li>
            <li>Movies by 'director'</li>
            <li>Movies by 'genre'</li>
            <li>Movies by 'MPAA rating'</li>
            <li>Movies by 'tag'</li>
            <li>Movies by 'title'</li>
            <li>Movies by 'year'</li>
        </ul>
        <hr/>
        <h2>Usage</h2>
        The application is packaged as a JAR file, targeted at Java 1.8+. You must have the Java JRE (1.8+) installed in order to run this application.<br/><br/>
        <b>Run the application</b><br/>
        The JAR file is run with the following command-line:
        <pre>
            java -jar MyMovies.jar
        </pre>
        The application starts an HTTP server on port 8080. All logging goes to the console that started the application.<br/><br/>
<b>Note:</b>&nbsp;The application, and all data, is supplied in the JAR file. An internet connection is <b>not required</b> for the application to operate.<br/><br/>
        <b>Access the application</b><br/>
        To access the application from a web browser, go to the following URL on the same computer's browser:
        <pre>
            <a href="http://localhost:8080" target="new">http://localhost:8080</a>
        </pre>
        <b>Stop the application</b><br/>
        To stop the application, go to the console where the app was started, and type Ctrl+C. The application will then shut down.<br/><br/>
        <hr/>
        <h2>Technical Details</h2>
        The application is written in Java, using the <a href="http://spring.io/projects/spring-boot" target="new">Spring Boot framework</a>, version 2.0.4. See the <a href="http://spring.io/projects/spring-boot" target="new">Spring Boot documentation</a> for more details.<br/><br/>
        The application architecture is pure MVC, and is completely stateless on the server-side, and supports a REST/JSON API.<br/>
        For example, to access a REST/JSON endpoint, you would issue an HTTP-GET to the following URL:
        <pre>
            <a href="http://localhost:8080/index/keys/all" target="new">http://localhost:8080/index/keys/all</a>
        </pre>
        <h2>Client-side caching</h2>
        Responses from the service are delivered 'Cache-Control' headers to ensure that these assets are cached appropriately.<br/><br/>
        The max-client-cache-age value (in seconds) is configurable via <b>application.properties</b> using the <b>cache.clientCacheMaxAge</b> property.
        <h2>CORS support for REST/JSON endpoints</h2>
        Access to the REST/JSON endpoints is controlled by a CORS filter. By default, this filter will only allow REST access from 'localhost'.<br/><br/>
        The allowed CORS origins and preflight-max-age are configurable via <b>application.properties</b> using the <b>cors.maxAge</b> and <b>cors.allowedHosts</b> properties.<br/><br/>
        <b>cors.maxAge</b> is specified in seconds.<br/>
        <b>cors.allowedHosts</b> is a comma-delimited list of strings specifying the allowed hosts.<br/>
        <h2>Building the app</h2>
        The application is a Gradle project. The 'build' goal is used to build a self-contained 'shaded' JAR, which contains all dependencies.<br/><br/>
        No 'webapp container' (eg. Tomcat, Glassfish, etc.) is needed to run the app. The 'shaded' JAR contains embedded-Tomcat to provide HTTP/HTTPS server support.<br/><br/>
        In addition, the application JAR contains the entire 'video database' and all images - no external resources are required (internet access is not required to run the app).<br/><br/>
        To build the application and run all tests:<br/>
        <pre>./gradlew clean build</pre>
        To run the application:<br/>
        <pre>./gradlew bootRun</pre>
        To build the application and skip all tests:<br/>
        <pre>./gradlew clean build -x test</pre>
        <h2>Gradle build output</h2>
        The application, and its 'sources' and 'javadoc' JAR files are in:
        <pre>./build/libs</pre>
        The unpacked 'javadoc' files are in:
        <pre>./build/docs/javadoc</pre>
        The 'checkstyle' report files are in:
        <pre>./build/reports/checkstyle</pre>
        The application 'test' output is in:
        <pre>./build/reports/tests/test</pre>
        <h2>Configuring the app</h2>
        The app is configured using the SpringBoot-standard mechanism, a 'properties' file, <b>application.properties</b>. This configuration file contains reasonable default values for all configurable settings. In addition to application-level configuration parameters, this file also contains 'server-level' parameters, for example port-assignments and logging settings. See the <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html" target="new">SpringBoot-configuration documentation</a> for details. Values specified in the <b>application.properties</b> file can be overridden at service-startup by supplying them as defined values on the command-line, for example:<br/>
        <pre>
            java -Dserver.port=9090 -jar MyMovies.jar 
        </pre>
        <h2>Monitoring the app</h2>
        Once the application is running, you can check its status at any time from a browser using the SpringBoot 'Actuator' endpoints, for example:<br/>
        <pre>
            <a href="http://localhost:8080/actuator/health" target="new">http://localhost:8080/actuator/health</a>
        </pre>
        This is the SpringBoot-standard 'actuator' interface, which allows you to see various statistics on the application as it runs (service-endpoint timings, JVM stats, etc.). See the <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html" target="new">Actuator documentation</a> for details.<br/>
        <br/><pre>
            <a href="http://localhost:8080/actuator" target="new">http://localhost:8080/actuator</a> 
        </pre><br/>
        <pre>
            <a href="http://localhost:8080/profile" target="new">http://localhost:8080/profile</a>
        </pre>
        <h2>Source code organization</h2>
        </ul>
        The Java code is organized into the following packages (under <b>src/main/java</b>):
        <ul>
            <li><b>com.sandy.mymovies</b> - the root package, contains the Spring Boot 'Application' class - i.e. the application entry point.</li>
            <li><b>com.sandy.mymovies.controllers</b> - contains the Spring 'Rest Controller' classes which accept HTTP requests to defined endpoints.</li>
            <li><b>com.sandy.mymovies.models</b> - root package for Java beans (POJOs).</li>
            <li><b>com.sandy.mymovies.models.domain</b> - contains Java Beans (POJOs) describing the 'domain' (DB) entities.</li>
            <li><b>com.sandy.mymovies.models.dto</b> - contains Java beans (POJOs) describing the 'data transfer object' (DTO) entities used to interact with the REST API.</li>
            <li><b>com.sandy.mymovies.repositories</b> - contains Spring 'repository' classes which define the data-access layer between the service and the actual DB.</li>
            <li><b>com.sandy.mymovies.services</b> - contains Spring 'service' classes which mediate between the 'transport' layer (i.e. the 'controllers') and the 'persistence' layer (i.e. the 'repositories').</li>
        </ul>
        <hr/>
    </body>
</html>
