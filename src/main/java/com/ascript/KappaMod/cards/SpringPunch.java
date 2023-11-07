package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.enums.KappaTags;
import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SpringPunch extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(SpringPunch.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 2;

    public SpringPunch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(KappaTags.RIPPLE);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new SubmergePower(m, p, magicNumber), magicNumber, true));
        addToBot(new RippleAction(p, 2));
        if (FloodPower.receding(p)) {
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber, true));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = FloodPower.receding(AbstractDungeon.player) ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
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
