package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.FloatAction;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.enums.KappaTags;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KikuIchimonji extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(KikuIchimonji.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 1;

    public KikuIchimonji() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(KappaTags.RIPPLE);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage), KappaAttackEffect.SPLASH));
        addToBot(new DamageAction(m, new DamageInfo(p, damage), KappaAttackEffect.SPLASH));
        if (FloodPower.surging(p)) {
            addToBot(new FloatAction(magicNumber));
        }
        addToBot(new RippleAction(p, 2));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = FloodPower.surging(AbstractDungeon.player) ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void applyPowers() {
        int realBase = baseDamage;
        if (FloodPower.receding(AbstractDungeon.player)) {
            baseDamage++;
        }
        super.applyPowers();
        baseDamage = realBase;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBase = baseDamage;
        if (FloodPower.receding(AbstractDungeon.player)) {
            baseDamage++;
        }
        super.calculateCardDamage(mo);
        baseDamage = realBase;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
    }

    @Override
    public float getTitleFontSize()
    {
        if (NAME.length() > 16) {
            return 15;
        } else {
            return 20;
        }
    }
}
