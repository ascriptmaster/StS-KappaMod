package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.enums.KappaTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public class Hydrometry extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(Hydrometry.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;
    private static final CardStrings cardStrings = languagePack.getCardStrings(ID);

    private static final int COST = 1;

    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public Hydrometry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        addToBot(new RippleAction(p, 2));
        if (FloodPower.surging(p)) {
            addToBot(new GainBlockAction(p, block));
        }
        if (FloodPower.receding(p)) {
            addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, block), block));
        }
    }

    @Override
    public void applyPowers() {
        long count = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .filter(c -> c.hasTag(KappaTags.RIPPLE)).count();
        this.baseBlock = (int)count * this.magicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
