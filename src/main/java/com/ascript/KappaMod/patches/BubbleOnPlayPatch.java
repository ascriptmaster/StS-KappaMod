package com.ascript.KappaMod.patches;

import com.ascript.KappaMod.actions.BubbleAction;
import com.ascript.KappaMod.cards.AbstractKappaCard;
import com.ascript.KappaMod.relics.KappaKap;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

// ref: https://github.com/Alchyr/Astrologer/blob/master/src/main/java/Astrologer/Patches/UseCardActionPatch.java
@SpirePatch(
        clz = UseCardAction.class,
        method = "update"
)
public class BubbleOnPlayPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = { "targetCard", "duration" }
    )
    public static SpireReturn<?> ReturnToBottom(UseCardAction __instance, AbstractCard targetCard, @ByRef float[] duration)
    {
        if (KappaCardFields.bubbleOnUse.get(targetCard)) {
            AbstractDungeon.actionManager.addToTop(new BubbleAction(targetCard));

            // tickDuration()
            duration[0] -= Gdx.graphics.getDeltaTime();
            if (duration[0] < 0.0f)
                __instance.isDone = true;

            targetCard.exhaustOnUseOnce = false;
            targetCard.dontTriggerOnUseCard = false;
            AbstractDungeon.actionManager.addToBottom(new HandCheckAction());

            return SpireReturn.Return(null);
        } else {
            return SpireReturn.Continue();
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "moveToDiscardPile");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
