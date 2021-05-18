
using System;
using System.Diagnostics;

namespace Hello
{
    public class testePsoNet
    {
        public static void Main(string[] args)
        {
            Func<double[], double> f = x =>
            {
                var s = 0.0;
                for (var i = 0; i < x.GetLength(0); i++)
                {
                        s += x[i] * x[i];
                }
                return s;
            };
            
            Stopwatch stopWatch = new Stopwatch();
            stopWatch.Start();
            var modPso = new PsoNet(f, 30, 5, 0.75, 1.5, 1.5, -5.12, 5.12);
            Console.WriteLine($"Gbest Value: {modPso.gBestValue}");
            var t = 0;
            while (t < 100)
            { 
                modPso.AtualizaVelocidade(); 
                modPso.AtualizaParticulas();
                modPso.fitness();

                Console.WriteLine($"Iter: {t} -- Gbest Value: {modPso.gBestValue}");
                t += 1;
             }

             stopWatch.Stop();
             Console.WriteLine($"Time exec: ${stopWatch.ElapsedMilliseconds}");
        }
    }
}