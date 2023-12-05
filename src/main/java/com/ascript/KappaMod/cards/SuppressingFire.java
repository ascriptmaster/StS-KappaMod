package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.enums.KappaTags;
import com.ascript.KappaMod.powers.SubmergePower;
import com.ascript.KappaMod.ui.FloodPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class SuppressingFire extends AbstractKappaCard {

    public static final String ID = KappaMod.makeID(SuppressingFire.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int RIPPLE = 2;

    public SuppressingFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        tags.add(KappaTags.RIPPLE);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RippleAction(p, RIPPLE));
        addToBot(new DamageAllEnemiesAction(p, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        monsters.stream().forEach(this::addSubmergeToBot);
        if (FloodPanel.receding()) {
            monsters.stream().forEach(this::addSubmergeToBot);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = FloodPanel.receding() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    private void addSubmergeToBot(AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new SubmergePower(m, AbstractDungeon.player, magicNumber), magicNumber, true));
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
