## Inspect a Flight Recording
Execute JVM with two special parameters:
```
    -XX:+UnlockCommercialFeatures <-before java 11
    -XX:+FlightRecorder <- after java 11
```
```
    java -jar -Xmx100m -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=dumponexit=true,filename=flight.jfr heap/target/heap-1.0.0-SNAPSHOT.jar
    UnlockCommercialFeatures: This flag was used in Oracle JVMs before Java 11
    
    java -jar -Xmx100m -XX:+FlightRecorder -XX:StartFlightRecording=dumponexit=true,filename="flight.jfr" heap/target/heap-1.0.0-SNAPSHOT.jar
```
Enable Flight Recording on JVM without these parameters:
```
    java -jar -Xmx100m -XX:+UnlockCommercialFeatures heap-1.0.0-SNAPSHOT.jar <- before Java 11
    java -jar -Xmx100m -XX:+FlightRecorder heap/target/heap-1.0.0-SNAPSHOT.jar -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=5090 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
    java -jar -Xmx1000m -XX:+FlightRecorder heap/target/heap-1.0.0-SNAPSHOT.jar
    jps -lvm | grep heap
    jcmd <pid> JFR.start name=heap_recording filename=flight.jfr dumponexit=true
    jcmd 27356 JFR.start name=heap_recording filename=flight.jfr dumponexit=true
```
Open Java Mission Control and connect to default HotSpot of our JVM:
```
    jmc
    
    cd "C:\Users\Jixiang_Li\AppData\Local\Programs\jmc\jmc-9.0.0_windows-x64\JDK Mission Control"
    jmc.exe -vm "C:\Program Files\Java\jdk-22\bin"  //add -vm to make sure jmc uses the correct jdk
```

## jinfo
Print system properties and command-line flags that were used to start the JVM.
```
    java -jar simple-1.0.0-SNAPSHOT.jar
    jps -lvm | grep simple
    jinfo <pid>
```