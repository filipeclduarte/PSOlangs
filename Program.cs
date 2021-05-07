using System;
using System.Diagnostics;
using NumSharp;

namespace Hello
{
    public class Pso
    {
        private Func<NDArray, double> f;
        public int numPart, dim, gBest;
        public double w, c1, c2, mini, maxi, gBestValue;
        public NDArray X, fit, V, pBest, pBestValue;
        private double g0;
        
        public Pso(Func<NDArray, double> F, int n, int d, double W, double C1, double C2, double Mini, double Maxi)
        {
            f = F;
            numPart = n;
            dim = d;
            w = W;
            c1 = C1;
            c2 = C2;
            mini = Mini;
            maxi = Maxi;

            g0 = 1.0e+8;
            X = (maxi - mini) * np.random.rand(numPart, dim) + mini;
            V = np.zeros(numPart, dim);
            pBest = X.copy();
            fit = np.zeros(numPart);
            pBestValue = np.zeros(numPart);
            for (var i = 0; i < numPart; i++)
            {
                g0 = f(X[i]);
                fit[i] = g0;
                pBestValue[i] = g0;
            }
            gBest = fit.argmin();
            gBestValue = fit[gBest];
        }

        public void AtualizaVel()
        {
            for (var i = 0; i < numPart; i++)
            {
                var r1 = np.random.rand();
                var r2 = np.random.rand();
                for (var j = 0; j < dim; j++)
                {
                    V[i, j] = w * V[i,j] + (c1 * r1 * (pBest[i,j] - X[i,j])) + (c2 * r2 * (X[gBest, j] - X[i,j]));
                }
                
            }
        }

        public void AtualizaPart()
        {
            for (var i = 0; i < numPart; i++)
            {
                for (var j = 0; j < dim; j++)
                {
                    X[i,j] = X[i,j] + V[i,j];    
                }
            }
            
            for (var i = 0; i < numPart; i++)
            {
                for (var j = 0; j < dim; j++)
                {
                    if (X[i, j] < mini)
                    {
                        X[i, j] = mini;
                    }
                    else if (X[i, j] > maxi)
                    {
                        X[i, j] = maxi;
                    }
                }
            }
        }

        public void Fitness()
        {
            var fitemp = 0.0;
            for (var i = 0; i < numPart; i++)
            {
                fit[i] = f(X[i]);
            }
            for (var i = 0; i < numPart; i++)
            {
                fitemp = f(X[i]);
                if (fitemp < pBestValue[i])
                {
                    pBestValue[i] = fitemp;
                    for (var j = 0; j < dim; j++)
                    {
                        pBest[i,j] = X[i,j];
                    }
                }
                if (fitemp < gBestValue)
                {
                    gBest = i;
                    gBestValue = fit[i];
                }
            }
        }

        internal class Program
        {
            public static void Main(string[] args)
            {
                Func<NDArray, double> f = x => (x*x).mean() * x.size;
                Stopwatch stopWatch = new Stopwatch();
                stopWatch.Start();
                var modPso = new Pso(f, 30, 5, 0.75, 1.5, 1.5, -5.12, 5.12);
                Console.WriteLine($"Gbest Value: {modPso.gBestValue}");
                var t = 0;
                while (t < 100)
                {
                    modPso.AtualizaVel();
                    modPso.AtualizaPart();
                    modPso.Fitness();

                    Console.WriteLine($"Iter: {t} -- Gbest Value: {modPso.gBestValue}");
                    t += 1;
                }

                stopWatch.Stop();
                Console.WriteLine($"Time exec: ${stopWatch.ElapsedMilliseconds}");
            }
        }
    }
}