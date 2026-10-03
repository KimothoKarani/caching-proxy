package com.simon;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {
    public static Map<Integer, String> argumentParser(String[] args) {
        String port = null;
        String origin = null;
        int portNumber = 0;
        Map<Integer, String> portAndOrigin = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            // We use modern switch expressions to handle arguments
            switch (args[i]) {
                case "--port" -> port = (i + 1 < args.length) ? args[++i] : "";
                case "--origin" -> origin = (i + 1 < args.length) ? args[++i] : "";
                default -> System.out.println("Skipping unexpected token: " + args[i]);
            }
        }

        // Validate the results
        boolean hasError = false;

        if (port == null) {
            System.err.println("Error: Missing required argument '--port'");
            hasError = true;
        } else if (port.isEmpty()) {
            System.err.println("Error: '--port' was provide but it's missing a definition");
            hasError = true;
        }

        if (origin == null) {
            System.err.println("Error: Missing required argument '--origin'");
            hasError = true;
        } else if (origin.isEmpty()) {
            System.err.println("Error: '--origin' was provide but it's missing a definition");
            hasError = true;
        }

        // Block execution if validation fails
        if (hasError) {
            System.out.println("\nUsage: java CachingProxy.java --port <value> --origin <value>");
            System.exit(1); // Exit the program with an error status code
        }

        // At this point, we're 100% sure 'port' is not null and not empty
        try {
            portNumber = Integer.parseInt(port);

            // Validate realistic networking bounds
            if (portNumber < 0 || portNumber > 65535) {
                System.err.println("Error: Port number must be between 0 and 65535. You provided " + portNumber);
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: The provided port '" + port + "' is not a valid integer.");
            System.out.println("\nUsage: java CachingProxy.java --port <value> --origin <value>");
            System.exit(1);
        }

        // 4. Safe to proceed with your program logic
        System.out.println("\nValidation passed successfully!");
        System.out.println("Parsed --port: " + port);
        System.out.println("Parsed --origin: " + origin);

        portAndOrigin.put(portNumber, origin);

        System.out.println("You entered port and origin: " + portAndOrigin);

        return portAndOrigin;
    }
}
