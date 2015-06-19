package tools;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Display the manifest of a jar file.
 * @see <a href="http://java.sun.com/docs/books/tutorial/deployment/jar">JAR tutorial</a>
 * @author John B. Matthews
 */
public class Manifesto { 

    public static void main(String[] args) { 
        if (args.length < 1) showHelp();
        else for (String name : args)
            try {
                System.out.println("Manifest: " + name);
                JarFile jar = new JarFile(name);
                Manifest manifest = jar.getManifest();
                showMap(manifest.getMainAttributes());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                showHelp();
                System.exit(1);
            }
    }
    
    private static void showMap(Attributes map) {
        if (map == null) return;
        for (Map.Entry<Object, Object> e : map.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
    }
    
    private static void showHelp() {
        System.out.println(
            "Usage: java -jar Manifesto.jar <jarfile> [<jarfile>]");
    }
}