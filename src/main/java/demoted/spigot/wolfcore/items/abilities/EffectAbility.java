package demoted.spigot.wolfcore.items.abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import demoted.spigot.wolfcore.utils;

public class EffectAbility extends Ability {
    public String effect = "wither";
    public Integer duration = 10;
    public Integer amplifier = 0;

    public EffectAbility() {
        super("effect");
    }

    public EffectAbility setEffect(String effect) {
        this.effect = utils.stringAllowedValues(effect.toUpperCase(), "wither", utils.validEffects);
        return this;
    }

    public EffectAbility setDuration(Integer duration) {
        this.duration = (Integer) utils.NullCheck(duration, 100);
        return this;
    }

    public EffectAbility setAmplifier(Integer amp) {
        this.amplifier = (Integer) utils.NullCheck(amplifier, 0);
        return this;
    }

    public String toString() {
        return "effect"+":"+target+":"+effect+":"+duration+":"+amplifier;
    }
    public void run(LivingEntity target) {
        target.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), duration, amplifier, false, false, false));
    }
}
