import numpy as np

class PSO:
    def __init__(self, num_part, D, W, C1, C2, Mini, Maxi, func):
        self.n = num_part
        self.d = D
        self.w = W
        self.c1 = C1
        self.c2 = C2
        self.mini = Mini
        self.maxi = Maxi
        self.func = func

        self.X = np.empty((self.n, self.d))
        self.V = np.zeros((self.n, self.d))
        self.pbest = np.empty((self.n, self.d))
        self.setup()


    def setup(self):
        self.X = np.random.uniform(low=self.mini, high=self.maxi, size=(self.n, self.d))
        self.pbest = self.X.copy()
        self.fit_values = np.apply_along_axis(self.func, 1, self.X)
        self.pbest_values = np.apply_along_axis(self.func, 1, self.pbest)
        self.gbest = self.fit_values.argmin()
        self.gbest_value = self.fit_values[self.gbest]

    def atualiza_part(self):
        r1 = np.random.uniform(0.0,1.0,size=(self.n,1))
        r2 = np.random.uniform(0.0,1.0,size=(self.n,1))
        self.V = (self.w * self.V) + (self.c1 * r1 * (self.pbest - self.X)) + \
                (self.c2 * r2 * (self.X[self.gbest,:] - self.X))

        self.X = self.X + self.V
        # avalia condicoes
        self.X = np.clip(self.X, self.mini, self.maxi)


    def evaluate(self):
        self.fit_values = np.apply_along_axis(self.func, 1, self.X)
        for (i, el) in enumerate(self.fit_values):
    
            if el < self.pbest_values[i]:
                self.pbest[i] = self.X[i]
                self.pbest_values[i] = el

            if el < self.gbest_value:
                self.gbest = i
                self.gbest_value = el





