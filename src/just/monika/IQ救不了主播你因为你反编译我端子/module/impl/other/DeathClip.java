/*
 * Decompiled with CFR 0_122.
 */
package just.monika.IQ救不了主播你因为你反编译我端子.module.impl.other;

import just.monika.IQ救不了主播你因为你反编译我端子.event.Event;
import just.monika.IQ救不了主播你因为你反编译我端子.event.RegisterEvent;
import just.monika.IQ救不了主播你因为你反编译我端子.event.impl.EventMotion;
import just.monika.IQ救不了主播你因为你反编译我端子.module.Module;
import just.monika.IQ救不了主播你因为你反编译我端子.module.data.ModuleData;
import just.monika.IQ救不了主播你因为你反编译我端子.module.data.Options;
import just.monika.IQ救不了主播你因为你反编译我端子.module.data.Setting;
import just.monika.IQ救不了主播你因为你反编译我端子.util.misc.ChatUtil;

public class DeathClip
extends Module {
    private String DIST = "DIST";
    private String CLIP = "CLIP";
    private String MESSAGE = "MESSAGE";
    private boolean dead;
    private int waitTicks = 0;

    public DeathClip(ModuleData data) {
        super(data);
        this.settings.put(this.CLIP, new Setting<Options>(this.CLIP, new Options("Clip Mode", "VClip", new String[]{"VClip", "HClip"}), "Clip method."));
        this.settings.put(this.DIST, new Setting<Double>(this.DIST, 2.0, "Distance to clip.", 1.0, -10.0, 10.0));
        this.settings.put(this.MESSAGE, new Setting<String>(this.MESSAGE, "/sethome", "Command to execute after clipping."));
    }

    @RegisterEvent(events={EventMotion.class})
    public void onEvent(Event event) {
        EventMotion em = (EventMotion)event;
        String vclip = ((Options)((Setting)this.settings.get(this.CLIP)).getValue()).getSelected();
        float distance = ((Number)((Setting)this.settings.get(this.DIST)).getValue()).floatValue();
        if (em.isPre()) {
            if (vclip.equalsIgnoreCase("VClip") && DeathClip.mc.thePlayer.getHealth() == 0.0f && !this.dead) {
                DeathClip.mc.thePlayer.setPosition(DeathClip.mc.thePlayer.posX, DeathClip.mc.thePlayer.posY + (double)distance, DeathClip.mc.thePlayer.posZ);
                ++this.waitTicks;
                this.dead = true;
            } else if (DeathClip.mc.thePlayer.getHealth() == 0.0f && !this.dead) {
                float yaw = DeathClip.mc.thePlayer.rotationYaw;
                DeathClip.mc.thePlayer.setPosition(DeathClip.mc.thePlayer.posX + ((double)(distance * 2.0f) * Math.cos(Math.toRadians(yaw + 90.0f)) + 0.0 * Math.sin(Math.toRadians(yaw + 90.0f))), DeathClip.mc.thePlayer.posY + 0.0010000000474974513, DeathClip.mc.thePlayer.posZ + ((double)(distance * 2.0f) * Math.sin(Math.toRadians(yaw + 90.0f)) - 0.0 * Math.cos(Math.toRadians(yaw + 90.0f))));
                ++this.waitTicks;
                this.dead = true;
            }
            if (this.waitTicks > 0 && this.dead) {
                ++this.waitTicks;
                if (this.waitTicks >= 4) {
                    ChatUtil.sendChat((String)((Setting)this.settings.get(this.MESSAGE)).getValue());
                    this.waitTicks = 0;
                }
            }
            if (DeathClip.mc.thePlayer.getHealth() > 0.0f) {
                this.dead = false;
            }
        }
    }
}

