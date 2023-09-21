package demoted.spigot.wolfcore.items.abilities;

import java.util.Arrays;

import demoted.spigot.wolfcore.utils;

public class Ability {
    public String title;
    public String target;

    public Ability(String title) {
        this.title = utils.NullCheck(title, "randomeffect");
    }

    public Ability setTarget(String target) {
        this.target = utils.stringAllowedValues(target,"self",Arrays.asList("self","enemy"));
        return this;
    }

    public String toDisplayString() {
        return title;
    }
}
