package com.company;

import java.util.Arrays;
import java.util.function.Function;

public class Main {

    public static void main(String[] args) {

        Function<double[], Double> square = x -> Arrays.stream(x)
                .map(i -> i * i)
                .sum();

        long start = System.currentTimeMillis();
        int nPart = 30;
        int dim = 5;
        int iter = 0;
        PSO modeloPSO = new PSO(nPart, dim, 0.75, 1.5, 1.5, -5.12, 5.12, square);
        while (iter < 100){

            modeloPSO.atualizaVel();
            modeloPSO.atualizaPart();
            modeloPSO.fitness();
            System.out.println("iter: " + iter + " - gBestValue: " + modeloPSO.gBestValue);
            iter += 1;
        }
        long end = System.currentTimeMillis();
        System.out.println("Time exec: " + (end - start));
    }

}
