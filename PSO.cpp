#include <iostream>
#include <vector>
#include <functional>
// #include <random>
#include <float.h>
#include <cmath>
#include <memory>
#include "PSO.hpp"

double square(const std::vector<double>& X)
{
    double res = 0.0;
    for (int i = 0; i < X.size(); i++){
            res += std::pow(X[i], 2);
        }
    return res;
};

double ackley(const std::vector<double>& X)
{
    double a = 20;
    double b = 0.2; 
    double c = 2 * M_PI;
    int d = 0;
    d = X.size();
    double sum1 = 0.0;
    double sum2 = 0.0;
    
    for (int i = 0; i < d; i++)
    {
        sum1 += std::pow(X[i],2);
        sum2 += std::cos(c*X[i]);
    }

    double term1 = 0.0;
    term1 = -a * std::exp(-b*std::sqrt(sum1/d));
    double term2 = 0.0;
    term2 = - 1.0 * std::exp(sum2/d);

    double y = 0.0;
    y = term1 + term2 + a + std::exp(1.0);

    return y;
}

int main(){
    // // criando classe com ponteiro
    // PSO *modeloPSO = new PSO(30, 5, 0.75, 1.65, 1.65, -3.5, 3.5, &square);
    // int iter = 0;
    // while(iter < 10){

    //     modeloPSO->atualizaPart();
    //     modeloPSO->evaluate();
    //     std::cout << modeloPSO->gBestValue << std::endl;
    //     iter++;
    // }
    // // deletando a classe
    // delete modeloPSO;

    // std::cout << "Testando existência do modeloPSO" << std::endl;
    // std::cout << modeloPSO->gBestValue << std::endl;

    // std::cout << "Iniciando com smart pointer" << std::endl;
    // // testando com smart pointer
    // std::unique_ptr<PSO> pPSO(new PSO(100, 10, 0.75, 1.65, 1.65,-32.768, 32.768, &ackley));
    // int iter2 = 0;
    // while(iter2 < 1000)
    // {
    //     std::cout << "iter: " << iter2 << " - gBestValue: ";
    //     pPSO->atualizaPart();
    //     pPSO->evaluate();
    //     std::cout << pPSO->gBestValue << std::endl;
    //     iter2++;
    // }
    // deleta sozinho
    
    // PSO modeloPSO2(30, 5, 0.75, 1.65, 1.65, -32.768, 32.768, &ackley);
    // int iter3 = 0;
    // while(iter3 < 1000){
    //     std::cout << "iter: " << iter3 << " - gBestValue: ";
    //     modeloPSO2.atualizaPart();
    //     modeloPSO2.evaluate();
    //     std::cout << modeloPSO2.gBestValue << std::endl;
    //     iter3++;
    // }

    // testar com while
    int t = 0;
    int iter2 = 0;
    while (t < 100){
        std::shared_ptr<PSO> pPSO(new PSO(30, 5, 0.75, 1.65, 1.65, -3.5, 3.5, &square));
        // std::shared_ptr<PSO> pPSO(new PSO(100, 10, 0.75, 1.65, 1.65,-32.768, 32.768, &ackley));
        // PSO pPSO(100, 10, 0.75, 1.65, 1.65,-32.768, 32.768, &ackley);
        iter2 = 0;
        while(iter2 < 100)
        {
            std::cout << "iter: " << iter2 << " - gBestValue: ";
            // pPSO.atualizaPart();
            // pPSO.evaluate();
            // std::cout << pPSO.gBestValue << std::endl;
            pPSO->atualizaPart();
            pPSO->evaluate();
            std::cout << pPSO->gBestValue << std::endl;
            iter2++;
        }

        t++;
    }

}