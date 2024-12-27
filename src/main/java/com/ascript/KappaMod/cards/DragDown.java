package com.ascript.KappaMod.cards;

import basemod.AutoAdd;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.BubbleFromHandAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.DrownPower;
import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class DragDown extends AbstractKappaCard {

    public static final String ID = KappaMod.makeID(DragDown.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = KappaMod.makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 2;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    public DragDown() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster mo) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.hasPower(SubmergePower.POWER_ID)) {
                addToBot(new ApplyPowerAction(m, p, new DrownPower(m, p, magicNumber), magicNumber, true));
            }
            addToBot(new ApplyPowerAction(m, p, new SubmergePower(m, p, magicNumber), magicNumber, true));
        }
    }

    public AbstractCard makeCopy() {
        return new DragDown();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
