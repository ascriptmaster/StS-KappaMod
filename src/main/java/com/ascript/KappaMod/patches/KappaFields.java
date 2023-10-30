package com.ascript.KappaMod.patches;

import com.ascript.KappaMod.bubbles.AbstractBubble;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(
        clz= AbstractPlayer.class,
        method= SpirePatch.CLASS
)
public class KappaFields {
    public static SpireField<Integer> masterMaxBubbles = new SpireField<>(() -> 0);
    public static SpireField<List<AbstractBubble>> bubbles = new SpireField<>(() -> new ArrayList<>());
}
