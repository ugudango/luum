package net.ugudango.luum.utils;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.ugudango.luum.Luum;

public class LuumVector3D extends Vector3d {
    /*
    public static final Vector3d ZERO = new Vector3d(0.0D, 0.0D, 0.0D);
    public final double x;
    public final double y;
    public final double z;
    */

    public LuumVector3D(double xIn, double yIn, double zIn) {
        super(xIn, yIn, zIn);
    }

    public LuumVector3D(Vector3f vec) {
        super(vec);
    }

    public <T extends LuumVector3D> LuumVector3D add(T vec) {
        return new LuumVector3D(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }

    @Override
    public LuumVector3D add(double x, double y, double z) {
        return new LuumVector3D(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Returns a new Vector3d, the result of a linear transform of
     * the initial Vector3d.
     * @param mat
     * @return Vector3d
     */
    public LuumVector3D linearTransform(double[][] mat) {
        if (mat.length != 3
           || mat[0].length != 3
           || mat[1].length != 3
           || mat[2].length != 3) {
            throw new IllegalArgumentException("Transformation matrix needs to be 3x3x3 in size.");
        }
        double xx = mat[0][0] * this.x + mat[0][1] * this.y + mat[0][2] * this.z;
        double yy = mat[1][0] * this.x + mat[1][1] * this.y + mat[1][2] * this.z;
        double zz = mat[2][0] * this.x + mat[2][1] * this.y + mat[2][2] * this.z;
        return new LuumVector3D(xx, yy, zz);
    }

    public enum R {
        DEG0,
        DEG90,
        DEG180,
        DEG270
    };

    public LuumVector3D rotateAxisY(R rotation) {
        switch (rotation) {
            case DEG90:
                return this.linearTransform(new double[][] {
                    new double[] {0, 0, 1},
                    new double[] {0, 1, 0},
                    new double[] {-1, 0, 0},
                }).add(0, 0, 1);
            case DEG180:
                return this.linearTransform(new double[][] {
                    new double[] {-1, 0, 0},
                    new double[] {0, 1, 0},
                    new double[] {0, 0, -1},
                }).add(1, 0, 1);
            case DEG270:
                return this.linearTransform(new double[][] {
                    new double[] {0, 0, -1},
                    new double[] {0, 1, 0},
                    new double[] {1, 0, 0},
                }).add(1, 0, 0);
            default:
                return this;
        }
    }

    public <T extends LuumVector3D> LuumVector3D rotateAxisY(R rotation, T customOffset) {
        switch (rotation) {
            case DEG90:
                return this.linearTransform(new double[][] {
                        new double[] {0, 0, 1},
                        new double[] {0, 1, 0},
                        new double[] {-1, 0, 0},
                }).add(customOffset);
            case DEG180:
                return this.linearTransform(new double[][] {
                        new double[] {-1, 0, 0},
                        new double[] {0, 1, 0},
                        new double[] {0, 0, -1},
                }).add(customOffset);
            case DEG270:
                return this.linearTransform(new double[][] {
                        new double[] {0, 0, -1},
                        new double[] {0, 1, 0},
                        new double[] {1, 0, 0},
                }).add(customOffset);
            default:
                return this;
        }
    }
}
