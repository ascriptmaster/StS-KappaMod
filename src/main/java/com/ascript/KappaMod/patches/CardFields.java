package com.ascript.KappaMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz= AbstractCard.class,
        method= SpirePatch.CLASS
)
public class CardFields {
    public static SpireField<Boolean> bubbleOnUse = new SpireField<>(() -> false);
}
