package other;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ApplicationPaths
{
    public static String APP_DIR;
    public static String RESOURCES_VIEWS;

    /***
     * Sets the application's directory paths differently whether it is a JAR file calling or an IDE
     */
    public static void setApplicationPaths() throws IOException {

        final File jarFile = new File(ApplicationPaths.class.getProtectionDomain().getCodeSource().getLocation().getPath());

        if(jarFile.isFile()) {
            String jarPath = jarFile.getParentFile().getPath();
            APP_DIR = Paths.get(jarPath,"resources").toString();
        } else {
            APP_DIR = Paths.get(new File(".").getCanonicalPath(),"src","main","resources").toFile().getPath();
        }

        RESOURCES_VIEWS = Paths.get(APP_DIR, "views").toAbsolutePath().toString();

    }
}