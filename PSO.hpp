#include <vector>
#include <functional>
#include <random>
#include <float.h>
#include <cmath>
#include <memory>

class PSO{
private: 
    int numPart;
    double g0;

public:
    int gBest, dims;
    double gBestValue, w, c1, c2, r1, r2, mini, maxi;
    std::vector<std::vector<double> > X;
    std::vector<std::vector<double> > pBest;
    std::vector<std::vector<double> > V;
    std::vector<double> fitValues;
    std::vector<double> pBestValues;
    std::function<double (const std::vector<double>&)> costFunction;

    // construtor
    PSO(int n, int d, double W, double C1, double C2,
        double Mini, double Maxi, std::function<double (const std::vector<double>&)> CostFunction) : 
        numPart(n), dims(d), w(W), c1(C1), c2(C2), mini(Mini), maxi(Maxi), costFunction(CostFunction),
        X(numPart, std::vector<double>(dims, 0.0)), pBest(numPart, std::vector<double>(dims, 0.0)),   
        V(numPart, std::vector<double> (dims, 0.0)), fitValues(numPart,0.0), pBestValues(numPart, 0.0), 
        gBestValue(DBL_MAX)
        {
            // inicializando algumas variaveis
            std::default_random_engine generator;
            std::uniform_real_distribution<double> distribution(0.0,1.0);
            for (int i = 0; i < numPart;i++){
                for (int j = 0;j<dims;j++){
                    X[i][j] = (maxi-mini) * distribution(generator) + mini;
                    pBest[i][j] = X[i][j];
                }
            }
            gBest = 0;
            for (int i = 0; i < numPart;i++)
            {
                g0 = costFunction(X[i]);
                fitValues[i] = g0;
                pBestValues[i] = g0; 
                if (g0 < gBestValue){
                    gBest = i;
                    gBestValue = g0;
                }
            }
        }
    // ~PSO(); // destructor
    // metodo atualizacao de particulas
    void atualizaPart(){        
        std::default_random_engine generator;
        std::uniform_real_distribution<double> distribution(0.0,1.0);
        double vTemp = 0.0;
        // atualiza velocidade
        for (int i = 0;i<numPart;i++){
            for (int j = 0; j<dims;j++){
                r1 = distribution(generator);
                r2 = distribution(generator);
                vTemp = (w * V[i][j]) + (c1 * r1 * (pBest[i][j] - X[i][j])) + (c2 * r2 * (X[gBest][j] - X[i][j]));
                V[i][j] = vTemp;

            }
        }
        // atualiza partículas
        for (int i = 0;i<numPart;i++){
            for (int j = 0; j<dims;j++){
                X[i][j] = X[i][j] + V[i][j];
            }
        }
        // avalia conditions
        for (int i = 0; i < numPart;i++){
            for (int j = 0;j<dims;j++){
                if (X[i][j] < mini){
                    X[i][j] = mini;
                }
                if (X[i][j] > maxi){
                    X[i][j] = maxi;
                }
            }
        }
    }
    // metodo avalia fitness e atualiza pbest e gbest
    void evaluate(){
        // calcula fitness
        double fitTemp = 0;
        for (int i = 0; i < numPart;i++)
        {
            fitTemp = costFunction(X[i]);
            fitValues.at(i) = fitTemp;
        }
        // avalia fitness pbest e gbest
        for (int i = 0; i < numPart;i++)
        {
            g0 = costFunction(X[i]);
            fitValues.at(i) = g0;
            if (g0 < pBestValues[i]){
                pBest[i] = X[i];
                pBestValues.at(i) = g0;
            }
            if (g0 < gBestValue){
                gBest = i;
                gBestValue = g0;
            }
        }
    }
};
