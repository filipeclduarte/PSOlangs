import numpy as np
from PSO import PSO

def square(x):
    return np.power(x, 2).sum()

def main():
    t = 0
    
    while t < 100:
        Iter = 0
        pPSO = PSO(30, 5, 0.75,1.65, 1.65, -3.5, 3.5, square)
        
        while Iter < 100: 
            pPSO.atualiza_part()
            pPSO.evaluate()
            print(f'iter: {Iter} - gbest_value: {pPSO.gbest_value}')
            Iter += 1
        
        t += 1

if __name__ == "__main__":
    main()