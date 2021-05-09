package com.company;

import java.util.Random;

public class PSO {
    int nPart, dim, gBest;
    double w, c1, c2, mini, maxi, gBestValue;
    double[][] X, V, pBest;
    double[] fit, pBestValue;
    Random rand = new Random();

    static double f(double[] x) {
        double res = 0.0;
        for (int i = 0; i < x.length; i++) {
            res += x[i] * x[i];
        }
        return res;
    }

    PSO(int numPart, int Dim, double W, double C1, double C2, double Mini, double Maxi) {
        nPart = numPart;
        dim = Dim;
        w = W;
        c1 = C1;
        c2 = C2;
        mini = Mini;
        maxi = Maxi;
        X = new double[nPart][dim];
        V = new double[nPart][dim];
        pBest = new double[nPart][dim];
        fit = new double[nPart];
        pBestValue = new double[nPart];
        gBestValue = 1.0e+10;
        for (int i = 0; i < nPart; i++) {
            for (int j = 0; j < dim; j++) {
                X[i][j] = (maxi - mini) * rand.nextDouble() + mini;
                V[i][j] = 0.0;
                pBest[i][j] = X[i][j];
            }
            fit[i] = f(X[i]);
            pBestValue[i] = f(pBest[i]);
            if (fit[i] < gBestValue) {
                gBestValue = fit[i];
                gBest = i;
            }
        }
    }

    void atualizaVel() {
        for (int i = 0; i < nPart; i++) {
            double r1 = rand.nextDouble();
            double r2 = rand.nextDouble();
            for (int j = 0; j < dim; j++) {
                V[i][j] = w * V[i][j] + (c1 * r1 * (pBest[i][j] - X[i][j])) +
                        (c2 * r2 * (X[gBest][j] - X[i][j]));
            }
        }
    }

    void atualizaPart() {
        for (int i = 0; i < nPart; i++) {
            for (int j = 0; j < dim; j++) {
                X[i][j] = X[i][j] + V[i][j];
            }
        }
        for (int i = 0; i < nPart; i++) {
            for (int j = 0; j < dim; j++) {
                if (X[i][j] < mini){
                    X[i][j] = mini;
                }
                else if (X[i][j] > maxi){
                    X[i][j] = maxi;
                }
            }
        }
    }

    void fitness(){
        for (int i = 0; i < nPart; i++) {
            fit[i] = f(X[i]);
        }
        for (int i = 0; i < nPart; i++) {
            if(fit[i] < pBestValue[i]){
                pBestValue[i] = fit[i];
                for (int j = 0; j < dim; j++){
                    pBest[i][j] = X[i][j];
                }
            }
            if (fit[i] < gBestValue){
                gBest = i;
                gBestValue = fit[i];
            }
        }
    }
}
