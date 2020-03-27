package me.deecaad.weaponmechanics.listeners;

import me.deecaad.weaponmechanics.weapon.WeaponHandler;
import me.deecaad.weaponmechanics.weapon.trigger.TriggerType;
import me.deecaad.weaponmechanics.wrappers.IPlayerWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import static me.deecaad.weaponmechanics.WeaponMechanics.*;

public class PlayerListenersAbove_1_9 implements Listener {

    private WeaponHandler weaponHandler;

    public PlayerListenersAbove_1_9(WeaponHandler weaponHandler) {
        this.weaponHandler = weaponHandler;
    }

    @EventHandler (ignoreCancelled = true)
    public void swapHandItems(PlayerSwapHandItemsEvent e) {
        if (getBasicConfigurations().getBool("Disabled_Trigger_Checks.Swap_Main_And_Hand_Items")) return;

        ItemStack toMain = e.getMainHandItem();
        String toMainWeapon = weaponHandler.getInfoHandler().getWeaponTitle(toMain, false);

        ItemStack toOff = e.getOffHandItem();
        String toOffWeapon = weaponHandler.getInfoHandler().getWeaponTitle(toOff, false);
        if (toMainWeapon == null && toOffWeapon == null) return;

        Player player = e.getPlayer();

        if (toMainWeapon != null && getConfigurations().getBool(toMainWeapon + ".Info.Cancel.Swap_Hands")
                || toOffWeapon != null && getConfigurations().getBool(toOffWeapon + ".Info.Cancel.Swap_Hands")) {

            // Basically cancels the hand items swap
            // If I would use e.setCancelled(true), it would cancel everything
            // and the possible item modifications done below wouldn't work.
            e.setMainHandItem(toOff);
            e.setOffHandItem(toMain);
        }

        IPlayerWrapper playerWrapper = getPlayerWrapper(player);

        if (isValid(toMain)) {
            // SWAP_TO_MAIN_HAND
            if (!weaponHandler.getInfoHandler().allowDualWielding(TriggerType.SWAP_TO_MAIN_HAND, player, toMainWeapon, toOffWeapon)) return;

            // Only check off hand going to main hand
            if (toMainWeapon != null) weaponHandler.tryUses(playerWrapper, toMainWeapon, toMain, EquipmentSlot.OFF_HAND, TriggerType.SWAP_TO_MAIN_HAND);
        }
        if (isValid(toOff)) {
            // SWAP_TO_OFF_HAND
            if (!weaponHandler.getInfoHandler().allowDualWielding(TriggerType.SWAP_TO_OFF_HAND, player, toMainWeapon, toOffWeapon)) return;

            // Only check main hand going to off hand
            if (toOffWeapon != null) weaponHandler.tryUses(playerWrapper, toOffWeapon, toOff, EquipmentSlot.HAND, TriggerType.SWAP_TO_OFF_HAND);
        }
    }

    private boolean isValid(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR;
    }
}