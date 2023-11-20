package com.ascript.KappaMod.patches;

import com.ascript.KappaMod.ui.FloodPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.OverlayMenu;

public class OverlayPatches {
    @SpirePatch(
            clz = OverlayMenu.class,
            method = SpirePatch.CLASS
    )
    public static class OverlayFields {
        public static SpireField<FloodPanel> floodPanel = new SpireField<>(() -> new FloodPanel());
    }

    @SpirePatch(
            clz = OverlayMenu.class,
            method = "update"
    )
    public static class OverlayUpdate {
        @SpirePrefixPatch
        public static void updatePatch(OverlayMenu __instance) {
            OverlayFields.floodPanel.get(__instance).updatePositions();
            OverlayFields.floodPanel.get(__instance).update();
        }
    }

    @SpirePatch(
            clz = OverlayMenu.class,
            method = "showCombatPanels"
    )
    public static class OverlayShow {
        @SpirePrefixPatch
        public static void showPatch(OverlayMenu __instance) {
            OverlayFields.floodPanel.get(__instance).show();
        }
    }

    @SpirePatch(
            clz = OverlayMenu.class,
            method = "hideCombatPanels"
    )
    public static class OverlayHide {
        @SpirePrefixPatch
        public static void hidePatch(OverlayMenu __instance) {
            OverlayFields.floodPanel.get(__instance).hide();
        }
    }

    @SpirePatch(
            clz = OverlayMenu.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class OverlayRender {
        @SpirePrefixPatch
        public static void renderPatch(OverlayMenu __instance, SpriteBatch sb) {
            OverlayFields.floodPanel.get(__instance).render(sb);
        }
    }
}
