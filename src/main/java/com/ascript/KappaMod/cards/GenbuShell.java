package com.ascript.KappaMod.cards;

import basemod.abstracts.CustomCard;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.enums.KappaTags;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GenbuShell extends CustomCard {
    public static final String ID = KappaMod.makeID(GenbuShell.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = KappaMod.makeCardPath("KappaBlock.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int RECEDING_PLUS_BLOCK = 3;

    public GenbuShell() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = 1;
        tags.add(KappaTags.RIPPLE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RippleAction(p, magicNumber));
        addToBot(new GainBlockAction(p, p, this.block));
        if (FloodPower.surging(p)) {
            addToBot(new DrawCardAction(magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        int realBase = baseBlock;
        if (FloodPower.receding(AbstractDungeon.player)) {
            baseBlock += RECEDING_PLUS_BLOCK;
        }
        super.applyPowers();
        baseBlock = realBase;
        isBlockModified = baseBlock != block;
    }

    public AbstractCard makeCopy() {
        return new GenbuShell();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
