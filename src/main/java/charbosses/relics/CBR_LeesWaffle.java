package charbosses.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Strawberry;

public class CBR_LeesWaffle extends AbstractCharbossRelic {

    public CBR_LeesWaffle() {
        super(new Strawberry());
    }

    @Override
    public void onEquip() {
        this.owner.increaseMaxHp(7, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_LeesWaffle();
    }
}