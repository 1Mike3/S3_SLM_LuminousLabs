package helpers;


/**
 * The {@code OSChecker} class is a utility to determine the operating system of the current environment.
 * It provides methods to check if the operating system is Windows, MacOS, Unix/Linux, or Solaris.
 * The class also includes a method to retrieve a short string representation of the operating system.
 */

/*
!Disclaimer!
I didn't write this code myself, the source is listed below
https://stackoverflow.com/questions/14288185/detecting-windows-or-linux
 */

public class OSchecker {

    private static String OS;

        public OSchecker() {
             OS = System.getProperty("os.name").toLowerCase();
        }

        public static boolean isWindows() {
            return OS.contains("win");
        }

        public static boolean isMac() {
            return OS.contains("mac");
        }

        public static boolean isUnix() {
            return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
        }

        public static boolean isSolaris() {
            return OS.contains("sunos");
        }
 }



