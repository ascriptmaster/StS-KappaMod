package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.BubbleDragonAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BubbleDragon extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(BubbleDragon.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;

    public BubbleDragon() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        return AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().noneMatch(c -> c.cardID == cardID);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BubbleDragonAction(p));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
