/*
 * MIT License
 *
 * Copyright (c) 2019 Primary Script, Inc <https://primaryscript.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Written by Matthew Hogan <matt@matthogan.co.uk> March 2019
 */
package org.shadowkingdom.commandfilter.command;

import co.uk.matthogan.command.Command;
import co.uk.matthogan.command.DynamicCommand;

import org.bukkit.command.CommandSender;

import org.shadowkingdom.commandfilter.CommandFilterPlugin;
import static org.shadowkingdom.commandfilter.CommandFilter.get;

/**
 * @author Matthew Hogan
 */
@DynamicCommand(
        name = "shadowcommandfilter",
        aliases = "commandfilter",
        description = "Reload ShadowCommandFilter configs",
        console = true
)
public class ReloadCmd extends Command {

    @Override
    public void execute(CommandSender sender, String... args) {
        if (!sender.hasPermission("shadowcommandfilter.command.reload")) {
            sender.sendMessage(get().getLang("no-permission"));
            return;
        }

        get().loadConfigs(CommandFilterPlugin.getPlugin());
        sender.sendMessage(get().getLang("cmd-reload"));
    }
}