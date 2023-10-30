package com.ascript.KappaMod.patches;

import basemod.ReflectionHacks;
import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static com.ascript.KappaMod.KappaMod.logger;

// Basically wholeheartedly stolen from https://github.com/Alchyr/Astrologer/blob/master/src/main/java/Astrologer/Patches/FlashAtkImgEffectPatches.java
public class AttackEffectPatches {
    private static final String SplashPath = "KappaModResources/images/vfx/splash.png";
    private static TextureAtlas.AtlasRegion SPLASH_REGION;

    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "loadImage"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "loadImage"
    )
    public static class loadImagePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<TextureAtlas.AtlasRegion> tryLoadImage(AbstractGameEffect __instance, AbstractGameAction.AttackEffect ___effect)
        {
            try {
                if (___effect == KappaAttackEffect.SPLASH)
                {
                    if (SPLASH_REGION == null) {
                        Texture tex = TextureLoader.getTexture(SplashPath);
                        SPLASH_REGION = new TextureAtlas.AtlasRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());
                    }

                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", MathUtils.random(360.0f));

                    return SpireReturn.Return(SPLASH_REGION);
                }
            }
            catch (Exception e)
            {
                logger.error("Failed to get splash texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "playSound"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "playSound"
    )
    public static class playSoundPatch
    {
        @SpirePrefixPatch
        public static SpireReturn tryPlaySound(AbstractGameEffect __instance, AbstractGameAction.AttackEffect effect)
        {
            if (effect == KappaAttackEffect.SPLASH)
            {
                CardCrawlGame.sound.play("ATTACK_POISON");
            }
            return SpireReturn.Continue();
        }
    }
}
