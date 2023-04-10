package com.learnreactiveprogramming.service;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
    
    @Test
    public void namesFlux(){
        
        var namesFlux = fluxAndMonoGeneratorService.fluxNames();
        
        StepVerifier.create(namesFlux)
                .expectNext("Juan")
                .expectNextCount(1)
                .expectNext("Carlos")
                .expectNextCount(1)
//                .expectNext("Juan", "Carlos", "Javier", "Lucia")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_map(){
        
        var namesFlux_map = fluxAndMonoGeneratorService.fluxNames_map();
        
        StepVerifier.create(namesFlux_map)                
                .expectNext("JUAN", "JAVIER", "CARLOS", "LUCIA")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_immutability(){
        
        var namesFlux_map = fluxAndMonoGeneratorService.fluxNames_immutability();
        
        StepVerifier.create(namesFlux_map)                
//                .expectNext("Juan", "Javier", "Carlos", "Lucia")
                .expectNext("JUAN", "JAVIER", "CARLOS", "LUCIA")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_filter(){
        
        var namesFlux_filter = fluxAndMonoGeneratorService.fluxNames_pipeline(5);
        
        StepVerifier.create(namesFlux_filter)                
                .expectNext("6-JAVIER", "6-CARLOS")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_flatMap(){
        
        var namesFlux_flatMap = fluxAndMonoGeneratorService.fluxNames_flatMap2(5);
                
        StepVerifier.create(namesFlux_flatMap) 
                .expectNextCount(2)
//                .expectNext("6","-","J","A","V","I","E","R","6","-","C","A","R","L","O","S")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_flatMap3(){
        
        var namesFlux_flatMap = fluxAndMonoGeneratorService.fluxNames_flatMap3(5);
                
        StepVerifier.create(namesFlux_flatMap)                
//                .expectNext("JAVIER", "CARLOS")
                .expectNextCount(12)
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_transform(){
        
        var namesFlux_transform = fluxAndMonoGeneratorService.fluxNames_transform(5);
                
        StepVerifier.create(namesFlux_transform)                
                .expectNext("JAVIER", "CARLOS")
//                .expectNextCount(12)
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_transform_1(){
        
        var namesFlux_transform = fluxAndMonoGeneratorService.fluxNames_transform_1(6);
                
        StepVerifier.create(namesFlux_transform)                
//                .expectNext("JAVIER", "CARLOS")
                .expectNextCount(7)
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_concatMap(){
        
        var namesFlux_concatMap = fluxAndMonoGeneratorService.fluxNames_concatMap(5);
                
        StepVerifier.create(namesFlux_concatMap)                
                .expectNext("J","A","V","I","E","R","C","A","R","L","O","S")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_concatFlux(){
        
        var namesFlux = fluxAndMonoGeneratorService.fluxconcat();
                
        StepVerifier.create(namesFlux)                
                .expectNext("Abel","Rolando","Graciela","Ana","Roberto","Boromir")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_concatwithFlux(){
        
        var namesFlux = fluxAndMonoGeneratorService.fluxConcatwith();
                
        StepVerifier.create(namesFlux)                
                .expectNext("Abel","Rolando","Graciela","Ana","Roberto","Boromir")
                .verifyComplete();
    }
    
    @Test
    public void namesFlux_merge(){
        
        var namesFlux = fluxAndMonoGeneratorService.exploreMerge();
        
        StepVerifier.create(namesFlux)
//                .expectNext("Abel","Ana","Rolando","Roberto","Graciela","Boromir")
//                .expectNext("Abel","Rolando","Graciela","Ana","Roberto","Boromir")
                .expectNextCount(6)
                .verifyComplete();        
    }
    
    @Test
    public void namesFlux_mergeSequential(){
        
        var namesFlux = fluxAndMonoGeneratorService.exploreMergeSequential();
        
        StepVerifier.create(namesFlux)
//                .expectNext("Abel","Ana","Rolando","Roberto","Graciela","Boromir")
                .expectNext("Abel","Rolando","Graciela","Ana","Roberto","Boromir")                
                .verifyComplete();        
    }
    
    @Test
    public void namesMono_mergeWithMono(){
        
        Flux<String> namesFlux = fluxAndMonoGeneratorService.exploreMergeWithMono();
                
        StepVerifier.create(namesFlux)                
                .expectNext("Juan Carlos", "Anacleta")
                .verifyComplete();
    }
        
    @Test
    public void namesMono_concatwithMono(){
        
        var namesFlux = fluxAndMonoGeneratorService.monoConcatWith();
                
        StepVerifier.create(namesFlux)                
                .expectNext("Juan Carlos", "Anacleta")
                .verifyComplete();
    }
    
    @Test
    public void namesMono(){
        
        var namesMono = fluxAndMonoGeneratorService.monoName();
        
        StepVerifier.create(namesMono)               
                .expectNext("Ramona")
                .verifyComplete();
    }
    
    @Test
    public void namesMonoFlatMap(){
        
        var namesMono = fluxAndMonoGeneratorService.monoName_flatMap(5);
        
        StepVerifier.create(namesMono)               
                .expectNext(List.of("R","A","M","O","N","A"))
                .verifyComplete();
    }
}