package demoted.spigot.wolfcore.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Subcommand {
    public List<Object> options = new ArrayList<>();
    public String command;

    public Subcommand(String commandArg) {
        command = commandArg;
    }

    public static Subcommand getCommand(List<Subcommand> subcommandList, String searchString) {
        for (Subcommand subcommand : subcommandList) {
            if (subcommand.command.equalsIgnoreCase(searchString))
                return subcommand;
        }
        return null;
    }

    public Subcommand addStaticOption(List<String> validOptions) {
        options.add(validOptions);
        return this;
    }

    public Subcommand addDynamicOption(Supplier<List<String>> validOptions) {
        options.add(validOptions);
        return this;
    }

    @SuppressWarnings("unchecked") // ["giveitem","d"]
    public List<String> returnData(String[] args) {
        if (args.length - 2 >= options.size())
            return null;
        Object optionList = options.get(args.length - 2);
        if (optionList instanceof Supplier<?>) {
            List<String> stringList = null;
            Object object = ((Supplier<?>) optionList).get();
            if (object instanceof List<?>) {
                stringList = (List<String>) object;
            }
            return listOption(args[args.length - 1], stringList);
        } else {
            return listOption(args[args.length - 1], (List<String>) optionList);
        }
    }

    public static List<String> listOption(String arg, List<String> validOptions) {
        List<String> ls = new ArrayList<String>();
        if (validOptions != null) {
            for (String string : validOptions) {
                if (string.toLowerCase().startsWith(arg.toLowerCase()))
                    ls.add(string);
            }
        }
        return ls;
    }
}
