package nl.avwie.aoc.common

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class QuaternionTests {

    @Test
    fun basicUnitRotationsX() {
        val z0 = Quaternion.fromAngle(0.0, Vector3D.Z)
        val z90 = Quaternion.fromAngle(PI / 2, Vector3D.Z)
        val z270 = Quaternion.fromAngle(3 * PI / 2, Vector3D.Z)
        val z360 = Quaternion.fromAngle(4 * PI / 2, Vector3D.Z)

        val y0 = Quaternion.fromAngle(0.0, Vector3D.Y)
        val y90 = Quaternion.fromAngle(PI / 2, Vector3D.Y)
        val y270 = Quaternion.fromAngle(3 * PI / 2, Vector3D.Y)
        val y360 = Quaternion.fromAngle(4 * PI / 2, Vector3D.Y)

        assertEquals(1.0, Vector3D.X.rotate(z0).x, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(z0).y, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(z0).z, 1E-6)

        assertEquals(0.0, Vector3D.X.rotate(z90).x, 1E-6)
        assertEquals(1.0, Vector3D.X.rotate(z90).y, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(z90).z, 1E-6)

        assertEquals(0.0, Vector3D.X.rotate(z270).x, 1E-6)
        assertEquals(-1.0, Vector3D.X.rotate(z270).y, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(z270).z, 1E-6)

        assertEquals(1.0, Vector3D.X.rotate(z360).x, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(z360).y, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(z360).z, 1E-6)

        assertEquals(1.0, Vector3D.X.rotate(y0).x, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(y0).y, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(y0).z, 1E-6)

        assertEquals(0.0, Vector3D.X.rotate(y90).x, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(y90).y, 1E-6)
        assertEquals(-1.0, Vector3D.X.rotate(y90).z, 1E-6)

        assertEquals(0.0, Vector3D.X.rotate(y270).x, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(y270).y, 1E-6)
        assertEquals(1.0, Vector3D.X.rotate(y270).z, 1E-6)

        assertEquals(1.0, Vector3D.X.rotate(y360).x, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(y360).y, 1E-6)
        assertEquals(0.0, Vector3D.X.rotate(y360).z, 1E-6)
    }

    @Test
    fun multipleRotations() {
        val x = Vector3D.X
        val y = x.rotate(0.0, 0.0, PI / 2)
        val z = y.rotate(PI / 2, 0.0, 0.0)
        val x2 = z.rotate(0.0, PI / 2, 0.0)
        assertEquals(x.x, x2.x, 1E-6)
        assertEquals(0.0, x2.y, 1E-6)
        assertEquals(0.0, x2.z, 1E-6)
    }
}