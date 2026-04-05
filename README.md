# AppleSkinSpigot

A server-side implementation of the [AppleSkin](https://modrinth.com/mod/appleskin) mod for Paper/Spigot servers.  
Syncs the saturation & exhaustion values to any connected clients.

Supported versions: 1.20.5+

This is an updated version of the original:  
https://github.com/jmattingley23/AppleSkinSpigot

## Building

Building AppleSkinSpigot requires at least a Java 17 JDK.  

To build, run `./gradlew build`.  
The built jar will be in `build/libs`.

You can run a test server using:
- `./gradlew runServer` for a Paper server
- `./gradlew runFolia` for a Folia server
