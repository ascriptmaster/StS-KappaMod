package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.BubbleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.ascript.KappaMod.patches.KappaFields;
import com.ascript.KappaMod.util.BubbleUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KegonGun extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(KegonGun.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;

    public KegonGun() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), KappaAttackEffect.SPLASH));
        }
        magicNumber = baseMagicNumber = -1;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        long count = AbstractDungeon.player.hand.group.stream()
                            .filter(this::isOtherAtk).count() +
                        BubbleUtils.bubblesWithCards(AbstractDungeon.player).stream()
                            .map(b -> b.card)
                            .filter(this::isOtherAtk)
                            .count();
        if (count > Integer.MAX_VALUE) {
            KappaMod.logger.error("How did you get over 2 billion Attacks!?!?");
            magicNumber = Integer.MAX_VALUE;
        } else {
            magicNumber = (int) count;
        }
        baseMagicNumber = magicNumber;
        isMagicNumberModified = false;
    }

    @Override
    public void resetAttributes() {
        baseMagicNumber = -1;
        super.resetAttributes();
    }

    private boolean isOtherAtk(AbstractCard card) {
        return card != null && card != this && card.type == CardType.ATTACK;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
