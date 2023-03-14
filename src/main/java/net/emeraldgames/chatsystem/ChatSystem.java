package net.emeraldgames.chatsystem;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class ChatSystem extends PluginBase {
    @Override
    public void onLoad() {
        this.saveResource("group.yml");
    }

    @Override
    public void onEnable() {
        this.getLogger().info("§a插件开启成功");
        this.getLogger().info("§e作者： §d希辰");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().contains("chat")) {
            if (strings.length == 0) {
                commandSender.sendMessage("§b-----------------------------------");
                commandSender.sendMessage("§aChatSystem指令");
                commandSender.sendMessage("§a发送私聊 §e/chat single <玩家名> <内容>");
                commandSender.sendMessage("§a创建群聊 §e/chat group create <群聊名>");
                commandSender.sendMessage("§a删除群聊 §e/chat group del <群聊名>");
                commandSender.sendMessage("§a加入群聊 §e/chat group join <群聊名>");
                commandSender.sendMessage("§a退出群聊 §e/chat group quit <群聊名>");
                commandSender.sendMessage("§a发送群消息 §e/chat group chat <群聊名> <内容>");
                commandSender.sendMessage("§a简化指令");
                commandSender.sendMessage("§a发送私聊 §e/cs <玩家名> <内容>");
                commandSender.sendMessage("§a发送群消息 §e/cg <群聊名> <内容>");
                commandSender.sendMessage("§b-----------------------------------");
            } else if (strings[0].equals("single")) {
                if (strings.length == 3) {
                    this.getServer().getPlayer(strings[1]).sendMessage("§b收到来自 §e" + commandSender.getName() + " §b的私聊 §e" + strings[2]);
                    commandSender.sendMessage("§a发送成功！");
                }
            } else if (strings[0].equals("group")) {
                if (strings.length >= 3) {
                    Config group = new Config(this.getDataFolder() + "/group.yml");
                    if (strings[1].equals("create")) {
                        if (!group.getKeys().contains(strings[2])) {
                            group.set(strings[2], commandSender.getName());
                            group.save();
                            commandSender.sendMessage("§a群聊创建成功！");
                        } else {
                            commandSender.sendMessage("§c群名称已被使用！");
                        }
                    }
                    if (strings[1].equals("del")) {
                        if (group.getKeys().contains(strings[2])) {
                            if (group.getString(strings[2]).startsWith(commandSender.getName())) {
                                group.remove(strings[2]);
                                group.save();
                                commandSender.sendMessage("§a群聊删除成功！");
                            } else {
                                commandSender.sendMessage("§c您无权删除此群聊！");
                            }
                        } else {
                            commandSender.sendMessage("§c该群聊不存在！");
                        }
                    }
                    if (strings[1].equals("kick")) {
                        if (group.getKeys().contains(strings[2])) {
                            if (group.getString(strings[2]).startsWith(commandSender.getName())) {
                                if (group.getString(strings[2]).contains(strings[3])) {
                                    String GroupMember = group.getString(strings[2]);
                                    GroupMember = GroupMember.replaceAll(";" + strings[3], "");
                                    group.set(strings[2], GroupMember);
                                    group.save();
                                    commandSender.sendMessage("§a成功踢出该玩家！");
                                } else {
                                    commandSender.sendMessage("§c该玩家不在群聊中！");
                                }
                            } else {
                                commandSender.sendMessage("§c您无权踢出此成员");
                            }
                        } else {
                            commandSender.sendMessage("§c该群聊不存在！");
                        }
                    }
                    if (strings[1].equals("join")) {
                        if (group.getKeys().contains(strings[2])) {
                            String GroupMember = group.getString(strings[2]);
                            GroupMember = GroupMember + ";" + commandSender.getName();
                            group.set(strings[2], GroupMember);
                            group.save();
                            commandSender.sendMessage("§a成功加入群聊！");
                        } else {
                            commandSender.sendMessage("§c该群聊不存在！");
                        }
                    }
                    if (strings[1].equals("quit")) {
                        if (group.getKeys().contains(strings[2])) {
                            if (group.getString(strings[2]).contains(commandSender.getName())) {
                                String GroupMember = group.getString(strings[2]);
                                GroupMember = GroupMember.replaceAll(";" + commandSender, "");
                                group.set(strings[2], GroupMember);
                                group.save();
                                commandSender.sendMessage("§a成功退出群聊！");
                            } else {
                                commandSender.sendMessage("§c您不在该群聊中！");
                            }
                        } else {
                            commandSender.sendMessage("§c该群聊不存在！");
                        }
                    }
                    if (strings[1].equals("chat")) {
                        if (group.getKeys().contains(strings[2])) {
                            if (group.getString(strings[2]).contains(commandSender.getName())) {
                                String[] members = group.getString(strings[2]).split(";");
                                int i = 0;
                                while (i != members.length && members.length != 1) {
                                    this.getServer().getPlayer(members[i + 1]).sendMessage("§e" + commandSender.getName() + " §b在群聊 §e" + strings[2] + " §b说 §e" + strings[3]);
                                    i++;
                                }
                                commandSender.sendMessage("§a发送成功！");
                            } else {
                                commandSender.sendMessage("§c您不在该群聊中！");
                            }
                        } else {
                            commandSender.sendMessage("§c该群聊不存在！");
                        }
                    }
                }
            }
        }
        if (command.getName().contains("cs")) {
            if (strings.length == 2) {
                this.getServer().getPlayer(strings[0]).sendMessage("§b收到来自 §e" + commandSender.getName() + " §b的私聊 §e" + strings[1]);
                commandSender.sendMessage("§a发送成功！");
            } else {
                commandSender.sendMessage("§b-----------------------------------");
                commandSender.sendMessage("§aChatSystem指令");
                commandSender.sendMessage("§a发送私聊 §e/chat single <玩家名> <内容>");
                commandSender.sendMessage("§a创建群聊 §e/chat group create <群聊名>");
                commandSender.sendMessage("§a删除群聊 §e/chat group del <群聊名>");
                commandSender.sendMessage("§a加入群聊 §e/chat group join <群聊名>");
                commandSender.sendMessage("§a退出群聊 §e/chat group quit <群聊名>");
                commandSender.sendMessage("§a发送群消息 §e/chat group chat <群聊名> <内容>");
                commandSender.sendMessage("§a简化指令");
                commandSender.sendMessage("§a发送私聊 §e/cs <玩家名> <内容>");
                commandSender.sendMessage("§a发送群消息 §e/cg <群聊名> <内容>");
                commandSender.sendMessage("§b-----------------------------------");
            }
        }
        if (command.getName().contains("cg")) {
            if (strings.length == 2) {
                Config group = new Config(this.getDataFolder() + "/group.yml");
                if (group.getKeys().contains(strings[0])) {
                    if (group.getString(strings[0]).contains(commandSender.getName())) {
                        String[] members = group.getString(strings[0]).split(";");
                        int i = 0;
                        while (i != members.length && members.length != 1) {
                            this.getServer().getPlayer(members[i + 1]).sendMessage("§e" + commandSender.getName() + " §b在群聊 §e" + strings[0] + " §b说 §e" + strings[1]);
                            i++;
                        }
                        commandSender.sendMessage("§a发送成功！");
                    } else {
                        commandSender.sendMessage("§c您不在该群聊中！");
                    }
                } else {
                    commandSender.sendMessage("§c该群聊不存在！");
                }
            }
        } else {
            commandSender.sendMessage("§b-----------------------------------");
            commandSender.sendMessage("§aChatSystem指令");
            commandSender.sendMessage("§a发送私聊 §e/chat single <玩家名> <内容>");
            commandSender.sendMessage("§a创建群聊 §e/chat group create <群聊名>");
            commandSender.sendMessage("§a删除群聊 §e/chat group del <群聊名>");
            commandSender.sendMessage("§a加入群聊 §e/chat group join <群聊名>");
            commandSender.sendMessage("§a退出群聊 §e/chat group quit <群聊名>");
            commandSender.sendMessage("§a发送群消息 §e/chat group chat <群聊名> <内容>");
            commandSender.sendMessage("§a简化指令");
            commandSender.sendMessage("§a发送私聊 §e/cs <玩家名> <内容>");
            commandSender.sendMessage("§a发送群消息 §e/cg <群聊名> <内容>");
            commandSender.sendMessage("§b-----------------------------------");
        }
        return true;
    }
}
