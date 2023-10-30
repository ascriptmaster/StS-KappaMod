package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.enums.KappaTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SignboardSmash extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(SignboardSmash.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("SignboardSmash.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int RIPPLE_BONUS = 3;

    public SignboardSmash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseDamage = DAMAGE;
        tags.add(KappaTags.RIPPLE);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new RippleAction(p, 2));
    }

    @Override
    public void applyPowers() {
        int realBase = baseDamage;
        if (FloodPower.surging(AbstractDungeon.player)) {
            baseDamage += RIPPLE_BONUS;
        }
        int realBlock = baseBlock;
        if (FloodPower.receding(AbstractDungeon.player)) {
            baseBlock += RIPPLE_BONUS;
        }
        super.applyPowers();
        baseDamage = realBase;
        isDamageModified = baseDamage != damage;
        baseBlock = realBlock;
        isBlockModified = baseBlock != block;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBase = baseDamage;
        if (FloodPower.surging(AbstractDungeon.player)) {
            baseDamage += RIPPLE_BONUS;
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
            upgradeBlock(UPGRADE_PLUS_BLOCK);
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
