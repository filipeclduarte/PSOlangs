using System;
using System.Linq;

namespace Hello
{
 
    public class PsoNet
    {
        private Func<double[], double> f;
        public int numPart, dim, gBest;
        public double w, c1, c2, mini, maxi, gBestValue;
        public double[,] X, V, pBest;
        public double[] fit, pBestValue;
        private double g0;
        public double[] Slice(double[,] x, int row)
        {
            var n = x.GetLength(1);
            var res = new double[n];
            for (var i = 0; i < n; i++)
            {
                res[i] = x[row, i];
            }
            return res;
        }
        
        public PsoNet(Func<double[], double> F, int n, int d, double W, double C1, double C2, double Mini, double Maxi)
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
            var rand = new Random();
            X = new double[numPart,dim];
            pBest = new double[numPart,dim];
            V = new double[numPart,dim];
            fit = new double[numPart];
            pBestValue = new double[numPart];
            
            for (var i = 0; i < numPart; i++)
            {
                for (var j = 0; j < dim; j++)
                {
                    var temp = (maxi - mini) * rand.NextDouble() + mini;
                    X[i, j] = temp;
                    pBest[i, j] = temp;
                    V[i, j] = 0.0;
                }
            }
            for (var i = 0; i < numPart; i++)
            {
                var xtemp = Slice(X, i);
                g0 = f(xtemp);
                fit[i] = g0;
                pBestValue[i] = g0;
            }
            
            gBest = Array.IndexOf(this.fit, fit.Min()); 
            gBestValue = fit[gBest];
        }
        
        public void AtualizaVelocidade()
        {
            for (var i = 0; i < numPart; i++)
            {
                var rand = new Random();
                var r1 = rand.NextDouble();
                var r2 = rand.NextDouble();
                for (var j = 0; j < dim; j++)
                {
                    V[i, j] = w * V[i,j] + (c1 * r1 * (pBest[i,j] - X[i,j])) + (c2 * r2 * (X[gBest, j] - X[i,j]));
                }
            }
        }

        public void AtualizaParticulas()
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
        
        public void fitness()
        {
            var fitemp = 0.0;
            double[] xtemp = new double[numPart];
            for (var i = 0; i < numPart; i++)
            {
                xtemp = Slice(X, i);
                fitemp = f(xtemp);
                fit[i] = fitemp;
            }
            for (var i = 0; i < numPart; i++)
            {
                fitemp = fit[i];
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
        
    }
}