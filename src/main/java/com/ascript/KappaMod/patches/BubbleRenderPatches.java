package com.ascript.KappaMod.patches;

import com.ascript.KappaMod.actions.PopAction;
import com.ascript.KappaMod.bubbles.AbstractBubble;
import com.ascript.KappaMod.bubbles.CardBubble;
import com.ascript.KappaMod.util.BubbleUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.List;

public class BubbleRenderPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class BubbleRenderPatch {
        @SpirePostfixPatch
        public static void renderBubble(AbstractPlayer __instance, SpriteBatch sb) {
            if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !__instance.isDead) {
                List<AbstractBubble> bubbles = KappaFields.bubbles.get(__instance);
                for (AbstractBubble b : bubbles) {
                    b.render(sb);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "combatUpdate"
    )
    public static class BubbleUpdatePatch {
        @SpirePostfixPatch
        public static void updateBubble(AbstractPlayer __instance) {
            List<AbstractBubble> bubbles = KappaFields.bubbles.get(__instance);
            for (AbstractBubble b : bubbles) {
                b.update();
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "update")
    public static class BubbleUpdateAnimPatch {
        @SpirePostfixPatch
        public static void updateBubbleAnim(AbstractPlayer __instance) {
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.EVENT) {
                for (AbstractBubble b : KappaFields.bubbles.get(__instance)) {
                    b.updateAnimation();
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
    public static class BubblePrepPatch {
        @SpirePostfixPatch
        public static void resetBubbles(AbstractPlayer __instance) {
            BubbleUtils.resetBubbles(__instance);
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class BubbleDamagePatch {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"damageAmount"}
        )
        public static void popBubbleIfDamaged(AbstractPlayer __instance, DamageInfo info, int damageAmount) {
            if (damageAmount > 0 && info.owner != __instance) {
                AbstractDungeon.actionManager.addToTop(new PopAction(__instance));
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "lastDamageTaken");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}
