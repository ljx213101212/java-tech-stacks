package com.ljx213101212.spring_boot.Rest;

import com.ljx213101212.spring_boot.Component.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SpringCoreController {

    // define a private field for the dependency
    private final Coach swimCoach;
    private final Coach swimCoach2;
    private Coach trackCoach;
    //Field Injection
    @Autowired
    private Coach baseballCoach;
    //Java config bean
    @Autowired
    @Qualifier("aquatic")
    private Coach aquaticCoach;

    private Coach onBenchGuy1;
    private Coach onBenchGuy2;

    // @Qualifier: make the class name first character as lower case.
    // @Autowired: allows fields to be final
    @Autowired
    public SpringCoreController(@Qualifier("swimCoach") Coach theCoach,
                                @Qualifier("swimCoach") Coach theCoach2,
                                @Qualifier("onBenchGuy") Coach theBenchGuy,
                                @Qualifier("onBenchGuy") Coach theBenchGuy2,
                                @Qualifier("aquatic") Coach theAquaticCoach) {
        System.out.println("In constructor: " + getClass().getSimpleName());
        swimCoach = theCoach;
        swimCoach2 = theCoach2;
        onBenchGuy1 = theBenchGuy;
        onBenchGuy2 = theBenchGuy2;
        aquaticCoach = theAquaticCoach;
    }


    //Setter Injection
    @Autowired
    public void setCoach(@Qualifier("trackCoach")Coach theCoach) {
        trackCoach = theCoach;
    }

    //Constructor Injection Mapping Sample
    @GetMapping("/ci/workout")
    public String getDailyWorkout() {
        return swimCoach.getDailyWorkout();
    }

    //Setter Injection Mapping Sample
    @GetMapping("/si/workout")
    public String getTrackWorkout() {
        return trackCoach.getDailyWorkout();
    }

    //Field Injection Mapping Sample
    @GetMapping("/fi/workout")
    public String getBaseballWorkout() {
        return baseballCoach.getDailyWorkout();
    }

    @GetMapping("/bean-scope")
    public Object checkBeanScope() {
        String scopeSingleton = "Comparing beans: swimCoach and swimCoach2: " + (swimCoach == swimCoach2);

        String scopeProtoType = "Comparing beans: benchGuy and benchGuy2: " + (onBenchGuy1 == onBenchGuy2);

        Map<String, String> map = new HashMap<>();
        map.put("SingletonScope", scopeSingleton);
        map.put("PrototypeScope", scopeProtoType);

        return map;
    }

    @GetMapping("java-bean-config")
    public String checkBeanConfig() {
        return aquaticCoach.getDailyWorkout();
    }
}
