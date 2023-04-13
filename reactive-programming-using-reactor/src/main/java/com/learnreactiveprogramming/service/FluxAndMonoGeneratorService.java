package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

public class FluxAndMonoGeneratorService {
    
    Function<String, Publisher<String>> fx;
    
    //metodo prueba Flux
    public Flux<String> fluxNames(){        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .log();
    }
    
    //metodo prueba inmutabildad Flux
    public Flux<String> fluxNames_immutability(){        
        var namesFlux = Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .log();
        
        namesFlux = namesFlux.map(String::toUpperCase).log();
        
        return namesFlux;
    }
    
    //metodo prueba Flux, transformacion de elementos por mapeo
    public Flux<String> fluxNames_map(){        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .map(String::toUpperCase)
//                .map(name -> name.toUpperCase())
                .log();
    }
    
    //metodo prueba Flux, operadores map y filter concatenados formando un pipeline
    public Flux<String> fluxNames_pipeline(int stringLength){        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .map(String::toUpperCase)
                .filter(name -> name.length()> stringLength)
                .map(name -> name.length() + "-" + name)
                .log();
    }
    
    //metodo prueba Flux, operador flatMap
    public Flux<String> fluxNames_flatMap(int stringLength){        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .map(String::toUpperCase)
                .filter(name -> name.length()> stringLength)
//                .map(name -> splitString2(name))
//                .map(name -> name.length() + "-" + name)
                .flatMap(name -> splitString(name))
                .log();
    }
            
    //metodo prueba Flux, operador flatMap
    public Flux<String> fluxNames_flatMap2(int stringLength){        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .map(String::toUpperCase)
                .filter(name -> name.length()> stringLength)
                .flatMap(name -> lengthPlusString(name))
                .log();
    }
    
     //metodo prueba Flux, operador flatMap
    public Flux<String> fluxNames_flatMap3(int stringLength){        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))                
                .filter(name -> name.length()> stringLength)
                .flatMap(name -> toUpperCaseString(name))
                .flatMap(name -> splitStringDelay(name))
                .log();
    }
    
    //metodo prueba Flux, operador flatMap
    public Flux<String> fluxNames_concatMap(int stringLength){
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))
                .map(String::toUpperCase)
                .filter(name -> name.length()> stringLength)
                .concatMap(name -> splitStringDelay(name))
                .log();
    }
    
    //metodo prueba Flux, operador flatMap
    public Flux<String> fluxNames_transform(int stringLength){
        
        Function<Flux<String>, Flux<String>> fx = name -> name.map(String::toUpperCase)
                .filter(s -> s.length()> stringLength);
        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))                
                .transform(fx)
                .log();
    }
    
    //metodo prueba Flux, operador flatMap
    public Flux<String> fluxNames_transform_1(int stringLength){
        
        Function<Flux<String>, Flux<String>> fx = name -> name.map(String::toUpperCase)
                .filter(s -> s.length()> stringLength)
                .flatMap(s -> splitString(s));
        
        Flux<String> defaultFlux = Flux.just("default").transform(fx);
        
        return Flux.fromIterable(List.of("Juan", "Javier", "Carlos", "Lucia"))                
                .transform(fx)
//                .defaultIfEmpty("default")
                .switchIfEmpty(defaultFlux)
                .log();
    }
    
    public Flux<String> fluxconcat(){
        
        Flux<String> fluxUno = Flux.just("Abel","Rolando","Graciela");
        
        Flux<String> fluxDos = Flux.just("Ana","Roberto","Boromir");
        
        return Flux.concat(fluxUno, fluxDos).log();
        
    }
    
    public Flux<String> fluxConcatwith(){
        
        Flux<String> fluxUno = Flux.just("Abel","Rolando","Graciela");
        
        Flux<String> fluxDos = Flux.just("Ana","Roberto","Boromir");
        
        return fluxUno.concatWith(fluxDos).log();
        
    }
    
    public Flux<String> exploreMerge(){
        
        Flux<String> fluxUno = Flux.just("Abel","Rolando","Graciela")
                .delayElements(Duration.ofMillis(100));
        
        Flux<String> fluxDos = Flux.just("Ana","Roberto","Boromir")
                .delayElements(Duration.ofMillis(125));
             
        Flux<String> fluxMerge = Flux.merge(fluxUno, fluxDos).log();
        
        return fluxMerge;
        
    }
    
    public Flux<String> exploreMergeSequential(){
        
        Flux<String> fluxUno = Flux.just("Abel","Rolando","Graciela")
                .delayElements(Duration.ofMillis(100));
        
        Flux<String> fluxDos = Flux.just("Ana","Roberto","Boromir")
                .delayElements(Duration.ofMillis(125));
             
        Flux<String> fluxMerge = Flux.mergeSequential(fluxUno, fluxDos).log();
        
        return fluxMerge;
        
    }
     
    public Flux<String> exploreZip(){
        
        Flux<String> fluxUno = Flux.just("Abel","Rolando","Graciela")
                .delayElements(Duration.ofMillis(100));
        
        Flux<String> fluxDos = Flux.just("Ana","Roberto","Boromir")
                .delayElements(Duration.ofMillis(125));
        
        Flux<String> fluxTres = Flux.just("Julia","Marcela","Sofia")
                .delayElements(Duration.ofMillis(125));
                
        Flux<String> fluxZip = Flux.zip(fluxUno, fluxDos, (a, b)-> (a + " " + b)).log();
        
        Flux<String> fluxZip2 = Flux.zip(fluxUno, fluxDos, fluxTres)
                .map(n -> n.getT1() + " " + n.getT2() + " " + n.getT3() ).log();
        
        Flux<String> fluxZip3 = Flux.zip(fluxUno, fluxDos, fluxTres)
                .map(n -> n.getT1() + " " + n.mapT2(String::toUpperCase).getT2() 
                        + " " + n.getT3()).log();
        
//        Flux<Tuple3<String, String, String>> fluxZipAll = Flux.zip(fluxZip, fluxZip2, fluxZip3).log();
        
        return fluxZip3;
        
    }
    
    public Flux<String> exploreZip2(){
        
        Flux<String> fluxUno = Flux.just("Abel","Rolando","Graciela");
//                .delayElements(Duration.ofMillis(100));
        
        Flux<String> fluxDos = Flux.just("Ana","Roberto","Boromir", "Ernesto");
//                .delayElements(Duration.ofMillis(125));
        
        Flux<String> fluxTres = Flux.just("Julia","Marcela");
//                .delayElements(Duration.ofMillis(125));
                
        Flux<String> fluxZip = Flux.zip(fluxUno, fluxDos, (a, b)-> (a + " " + b)).log();
        
        Flux<String> fluxZip2 = Flux.zip(fluxUno, fluxDos, fluxTres)
                .map(n -> n.getT1() + " " + n.getT2() + " " + n.getT3() ).log();
        
        return fluxZip2;
    }    
    
    public Mono<String> exploreZipWith(){
        
        Mono<String> monoUno = Mono.just("Abel");
        
        Mono<String> monoDos = Mono.just("Ana");
                        
        Mono<String> monoTres = Mono.just("Julia");                
                
        Mono<String> monoZip = Mono.zip(monoUno, monoDos, (a, b)-> (a + " " + b)).log();
        
        Mono<String> monoZip2 = Mono.zip(monoUno, monoDos, monoTres)
                .map(n -> n.getT1() + " " + n.getT2() + " " + n.getT3() ).log();
        
        return monoZip2;
    }
    
    //metodo prueba Mono
    public Mono<String> monoName(){        
        return Mono.justOrEmpty("Ramona").log();
    }
    
    
     //metodo prueba Mono
    public Mono<String> monoName_map_filter(int stringLength){
        return Mono.justOrEmpty("Ramona")
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .log();
    }
    
     //metodo prueba Mono, flatMap
    public Mono<List<String>> monoName_flatMap(int stringLength){
        return Mono.justOrEmpty("Ramona")
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
//                .flatMap(name -> splitStringMono(name))
                .flatMap(this::splitStringMono)
                .log();
    }
    
      //metodo prueba Mono, flatMap
    public Flux<String> monoName_flatMapMany(int stringLength){
        return Mono.justOrEmpty("Ramona")
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
//                .flatMap(name -> splitStringMono(name))
                .flatMapMany(this::splitString)
                .log();
    }
    
    public Flux<String> monoConcatWith(){
        
        Mono<String> monoUno = Mono.just("Juan Carlos");
        Mono<String> monoDos = Mono.just("Anacleta");
        
        return monoUno.concatWith(monoDos).log();
    }
    
    public Flux<String> exploreMergeWithMono(){
                
        Mono<String> monoUno = Mono.just("Juan Carlos");//.delayElement(Duration.ofMillis(100));
        Mono<String> monoDos = Mono.just("Anacleta");//.delayElement(Duration.ofMillis(75));
        
        return monoUno.mergeWith(monoDos).log();
        
    }
    
    public static void main(String[] args){
        
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        
        fluxAndMonoGeneratorService.fluxNames().subscribe(name -> {
            System.out.println("Name is: " + name);            
        });
        
        System.out.println("");
        
        fluxAndMonoGeneratorService.monoName().subscribe(name -> {
            System.out.println("Name is (Mono): " + name);            
        });
        
        System.out.println("");
        
        fluxAndMonoGeneratorService.fluxNames_pipeline(5).subscribe(name -> {
            System.out.println("Name (Pipeline) is: " + name);
        });
        System.out.println("");
              
        fluxAndMonoGeneratorService.fluxNames_flatMap(5).subscribe(name -> {
            System.out.println("Name is (FlatMap): " + name);
        });
        
        System.out.println("");
        
        fluxAndMonoGeneratorService.fluxNames_flatMap2(5).subscribe(name -> {
            System.out.println("Name is (FlatMap2): " + name);
        });
    }
    
    public Flux<String> splitString(String name){
        
        String[] charArray = name.split("");
        
        return Flux.fromArray(charArray);
    }
    
    public String[] splitString2(String name){
        
        String[] charArray = name.split("");
                        
        return charArray;
    }
    
    public Flux<String> splitStringDelay(String name){
        
        String[] charArray = name.split("");
        
        return Flux.fromArray(charArray).delayElements(Duration.ofSeconds(1));
    }
    
    public Mono<List<String>> splitStringMono(String name){
        
        String[] charArray = name.split("");
        var charList = List.of(charArray);
        
        return Mono.just(charList);
    }
    
    public Flux<String> toUpperCaseString(String name){
        
        String nameUpCase = name.toUpperCase();
        var duration = new Random().nextInt(1000);
        
        return Flux.just(nameUpCase).delayElements(Duration.ofMillis(duration));
    }
    
    public Flux<String> lengthPlusString(String name){
        
        String namePlusLength = name.length() + "-" + name;
        
        return Flux.just(namePlusLength);
    }
}
