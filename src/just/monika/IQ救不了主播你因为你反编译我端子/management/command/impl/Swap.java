/*
 * Decompiled with CFR 0_122.
 */
package just.monika.IQ救不了主播你因为你反编译我端子.management.command.impl;

import just.monika.IQ救不了主播你因为你反编译我端子.event.Event;
import just.monika.IQ救不了主播你因为你反编译我端子.management.command.Command;
import just.monika.IQ救不了主播你因为你反编译我端子.util.misc.ChatUtil;
import net.minecraft.client.Minecraft;

public class Swap
extends Command {
    protected final Minecraft mc = Minecraft.getMinecraft();

    public Swap(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        if (args == null) {
            this.printUsage();
            return;
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("m") && args.length > 1) {
                if (Integer.valueOf(args[1]) > 0 && Integer.valueOf(args[1]) < 10) {
                    int swapSlotsTo = Integer.valueOf(args[1]) - 1;
                    for (int i = 0; i <= swapSlotsTo; ++i) {
                        this.swap(i + 27, i);
                    }
                    return;
                }
            } else {
                if (args[0].toString().contains("m") && args.length == 1) {
                    ChatUtil.printChat("\u00a7c[\u00a7fS\u00a7c]\u00a77 That is not a valid hotbar number. (Slot 1 to set arg)");
                    return;
                }
                if (!args[0].toString().contains("m") && Integer.valueOf(args[0]) > 0 && Integer.valueOf(args[0]) < 10) {
                    int swapSlot = Integer.valueOf(args[0]) - 1;
                    this.swap(swapSlot + 27, swapSlot);
                    return;
                }
            }
        }
        this.printUsage();
    }

    @Override
    public String getUsage() {
        return "swap <m [Multi] | hotbarslot> [Multi Only] <hotbars>";
    }

    protected void swap(int slot, int hotbarNum) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, this.mc.thePlayer);
    }

    public void onEvent(Event event) {
    }
}

