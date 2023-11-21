package com.ascript.KappaMod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.ascript.KappaMod.cards.*;
import com.ascript.KappaMod.patches.KappaFields;
import com.ascript.KappaMod.relics.KappaKap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ascript.KappaMod.KappaMod;

import java.util.ArrayList;

import static com.ascript.KappaMod.KappaMod.*;

public class TheKappa extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(KappaMod.class.getName());
    
    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass KAPPA;
        @SpireEnum(name = "KAPPA_AQUA_COLOR")
        public static AbstractCard.CardColor COLOR_AQUA;
        @SpireEnum(name = "KAPPA_AQUA_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
    
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    
    private static final String ID = makeID("Kappa");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    
    public static final String[] orbTextures = {
            "KappaModResources/images/char/kappa/orb/layer1.png",
            "KappaModResources/images/char/kappa/orb/layer2.png",
            "KappaModResources/images/char/kappa/orb/layer3.png",
            "KappaModResources/images/char/kappa/orb/layer4.png",
            "KappaModResources/images/char/kappa/orb/layer5.png",
            "KappaModResources/images/char/kappa/orb/layer6.png",
            "KappaModResources/images/char/kappa/orb/layer1d.png",
            "KappaModResources/images/char/kappa/orb/layer2d.png",
            "KappaModResources/images/char/kappa/orb/layer3d.png",
            "KappaModResources/images/char/kappa/orb/layer4d.png",
            "KappaModResources/images/char/kappa/orb/layer5d.png",};
    
    public TheKappa(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "KappaModResources/images/char/kappa/orb/vfx.png", null,
                new SpriterAnimation(
                        "KappaModResources/images/char/kappa/Spriter/kappa.scml"));
        
        
        initializeClass(null,
                KAPPA_SHOULDER_1,
                KAPPA_SHOULDER_2,
                KAPPA_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
        
        /*
        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        */
        
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    @Override
    protected void initializeClass(String imgUrl, String shoulder2ImgUrl, String shouldImgUrl, String corpseImgUrl,
                                   CharSelectInfo info, float hb_x, float hb_y, float hb_w, float hb_h, EnergyManager energy) {
        super.initializeClass(imgUrl, shoulder2ImgUrl, shouldImgUrl, corpseImgUrl, info, hb_x, hb_y, hb_w, hb_h, energy);
        KappaFields.masterMaxBubbles.set(this, 3);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }
    
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        
        logger.info("Begin loading starter Deck Strings");

        for (int i=0; i<4; i++) {
            retVal.add(KappaBasicStrike.ID);
        }
        retVal.add(Washout.ID);
        for (int i=0; i<4; i++) {
            retVal.add(KappaBasicDefend.ID);
        }
        retVal.add(BubbleBlower.ID);

        return retVal;
    }
    
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        //retVal.add("PrismaticShard"); // Temporary to allow card rewards to work!
        retVal.add(KappaKap.ID);

        UnlockTracker.markRelicAsSeen(KappaKap.ID);
        
        //retVal.add(PlaceholderRelic.ID);
        //retVal.add(PlaceholderRelic2.ID);
        //retVal.add(DefaultClickableRelic.ID);
        
        //UnlockTracker.markRelicAsSeen(PlaceholderRelic.ID);
        //UnlockTracker.markRelicAsSeen(PlaceholderRelic2.ID);
        //UnlockTracker.markRelicAsSeen(DefaultClickableRelic.ID);
        
        return retVal;
    }
    
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }
    
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }
    
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }
    
    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.COLOR_AQUA;
    }
    
    @Override
    public Color getCardTrailColor() {
        return KappaMod.KAPPA_AQUA;
    }
    
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }
    
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    
    @Override
    public AbstractCard getStartCardForEvent() {
        return new KappaBasicStrike();
    }
    
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }
    
    @Override
    public AbstractPlayer newInstance() {
        return new TheKappa(name, chosenClass);
    }
    
    @Override
    public Color getCardRenderColor() {
        return KappaMod.KAPPA_AQUA;
    }
    
    @Override
    public Color getSlashAttackColor() {
        return KappaMod.KAPPA_AQUA;
    }
    
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }
    
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }
    
    @Override
    public String getVampireText() {
        return TEXT[2];
    }
}
