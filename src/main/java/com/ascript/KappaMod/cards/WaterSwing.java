package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.enums.KappaTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WaterSwing extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(WaterSwing.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public WaterSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
        tags.add(KappaTags.RIPPLE);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new RippleAction(p, 1));
        if (FloodPower.receding(p)) {
            addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, KappaAttackEffect.SPLASH));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = FloodPower.receding(AbstractDungeon.player) ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void applyPowers() {
        int realBase = baseDamage;
        if (FloodPower.surging(AbstractDungeon.player)) {
            baseDamage += magicNumber;
        }
        super.applyPowers();
        baseDamage = realBase;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBase = baseDamage;
        if (FloodPower.surging(AbstractDungeon.player)) {
            baseDamage += magicNumber;
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
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
