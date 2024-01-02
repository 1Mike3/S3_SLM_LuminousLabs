package helpers;


/**
 * The {@code OSChecker} class is a utility to determine the operating system of the current environment.
 * It provides methods to check if the operating system is Windows or Linux, other systems not supported anyway
 *
 */


public class OSchecker {

    private static String OS;

        public OSchecker() {
             OS = System.getProperty("os.name").toLowerCase();
        }

        public static boolean isWindows() {
            return OS.contains("win");
        }

        public static boolean isUnix() {
            return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
        }

 }



