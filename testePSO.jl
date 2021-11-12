# testando PSO
using PSO
f(X) = X.^2 |> sum
iterations = 100

pso = Pso(30, 5, 0.75, 1.5, 1.5, -5.12, 5.12)

X, gBest, gBestValue = optimize(pso, f, iterations)

