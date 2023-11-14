package com.ascript.KappaMod.cards;

import basemod.abstracts.CustomCard;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.ActionPerBubbledCardAction;
import com.ascript.KappaMod.actions.BubbleFromHandAction;
import com.ascript.KappaMod.actions.BubbleTopCardAction;
import com.ascript.KappaMod.actions.WaterCarpetAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WaterCarpet extends CustomCard {
    public static final String ID = KappaMod.makeID(WaterCarpet.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = KappaMod.makeCardPath("WaterCarpet.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    public WaterCarpet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = 2;
        baseBlock = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BubbleFromHandAction(p, magicNumber, false, c -> new WaterCarpetAction(p, this, c)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
