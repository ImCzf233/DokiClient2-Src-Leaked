/*
 * Decompiled with CFR 0_122.
 */
package just.monika.IQ救不了主播你因为你反编译我端子.management.animate;

public class AnimationUtil {
    public static float calculateCompensation(float target, float current, long delta, int speed) {
        float diff = current - target;
        if (delta < 9) {
            delta = 9;
        }
        if (diff > (float)speed) {
            double xD = (double)((long)speed * delta / 16) < 0.05 ? 0.05 : (double)((long)speed * delta / 16);
            if ((current = (float)((double)current - xD)) < target) {
                current = target;
            }
        } else if (diff < (float)(- speed)) {
            double xD = (double)((long)speed * delta / 16) < 0.05 ? 0.05 : (double)((long)speed * delta / 16);
            if ((current = (float)((double)current + xD)) > target) {
                current = target;
            }
        } else {
            current = target;
        }
        return current;
    }
}

