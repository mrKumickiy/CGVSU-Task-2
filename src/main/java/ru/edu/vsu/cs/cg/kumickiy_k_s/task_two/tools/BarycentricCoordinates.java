package ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.tools;

import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.rasterizations.TriangleRasterizationV1;
import ru.edu.vsu.cs.cg.kumickiy_k_s.task_two.shapes.Vertex;

public class BarycentricCoordinates {
    private final double alpha;
    private final double beta;
    private final double gamma;

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getGamma() {
        return gamma;
    }

    public BarycentricCoordinates(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }
    public static BarycentricCoordinates getBarycentricCoordinatesForPoint(int x, int y, Vertex[] placed) {
        double det = (placed[0].getX() - placed[2].getX()) * (placed[1].getY() - placed[2].getY()) -
                (placed[1].getX() - placed[2].getX()) * (placed[0].getY() - placed[2].getY());
        double det1 = (x - placed[2].getX()) * (placed[1].getY() - placed[2].getY()) -
                (placed[1].getX() - placed[2].getX()) * (y - placed[2].getY());
        double det2 = (placed[0].getX() - placed[2].getX()) * (y - placed[2].getY()) -
                (x - placed[2].getX()) * (placed[0].getY() - placed[2].getY());
        double alpha = Math.clamp(det1 / det, 0, 1);
        double beta = Math.clamp(det2 / det, 0, 1);
        double gamma = Math.clamp(1 - alpha - beta, 0, 1);

        return new BarycentricCoordinates(alpha, beta, gamma);
    }
}
