package Top.q44920040.Minecarft_PexTime;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class PexTimeMain extends JavaPlugin {
    private Dao s;
    @Override
    public void onEnable() {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
        s = new Dao(getDataFolder().getPath()+File.separator+"PexTime");
        Bukkit.getPluginManager().registerEvents( new PlayerListener(),this);
        System.out.print("===========Pex程序启动成功==============");
        super.onEnable();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("PexTime")){
            if (sender instanceof Player){
                if(sender.isOp()){
//                    System.out.println("是OP添加");
                    AddPexTime(args, sender);
                    sender.sendMessage("§");
                }
                PexUserInfo(args,sender);
            }else {
//                System.out.println("是控制台添加");
                AddPexTime(args, sender);
            }
        }
        return super.onCommand(sender, command, label, args);
    }



    private void PexUserInfo(String[] args,CommandSender player){
//        System.out.println("进入函数了");
        if (args.length==2&&args[0].equalsIgnoreCase("info")){
//            System.out.println("进入内部了");
            ArrayList<String> TempUserdata = s.GetUserSQLiteData(args[1]);
            if (!TempUserdata.isEmpty()){
                for(String res:TempUserdata){
                    player.sendMessage("玩家名|权限|到期时间:"+res);
                }
            }else {
                player.sendMessage("未查到任何数据");
            }
        }
    }

    private void AddPexTime(String[] args,CommandSender sender){
        if (args.length==5&&args[1].equalsIgnoreCase("add")){
            if (args[3].split("-").length==3){
                s.AddSQLiteData(args[0],args[2],args[3],args[4]);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"manuaddp "+args[0]+" "+args[2]);
                sender.sendMessage("添加成功");
            }else {
                sender.sendMessage("[PexTime]你时间输入格式有误,如10-3-5,为10天3小时5分钟");
            }
        }
    }


    @Override
    public void onDisable() {
        super.onDisable();
    }

}
