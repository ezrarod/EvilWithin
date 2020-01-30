package charbosses.core;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import charbosses.bosses.AbstractCharBoss;
import charbosses.ui.EnemyEnergyPanel;

public class EnemyEnergyManager extends EnergyManager {

	public EnemyEnergyManager(int e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	public void prep() {
        this.energy = this.energyMaster;
        EnemyEnergyPanel.totalCount = 0;
    }
    
    public void recharge() {
        if (AbstractCharBoss.boss.hasRelic("Ice Cream")) {
            if (EnemyEnergyPanel.totalCount > 0) {
            	AbstractCharBoss.boss.getRelic("Ice Cream").flash();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, AbstractCharBoss.boss.getRelic("Ice Cream")));
            }
            EnemyEnergyPanel.addEnergy(this.energy);
        }
        else if (AbstractCharBoss.boss.hasPower("Conserve")) {
            if (EnemyEnergyPanel.totalCount > 0) {
                AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractCharBoss.boss, AbstractCharBoss.boss, "Conserve", 1));
            }
            EnemyEnergyPanel.addEnergy(this.energy);
        }
        else {
        	EnemyEnergyPanel.setEnergy(this.energy);
        }
    }
    
    public void use(final int e) {
    	EnemyEnergyPanel.useEnergy(e);
    }
}
