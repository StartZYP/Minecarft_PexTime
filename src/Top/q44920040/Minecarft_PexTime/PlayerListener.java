package Top.q44920040.Minecarft_PexTime;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class PlayerListener implements Listener{

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        ArrayList<String> PexArray;
        Player player = event.getPlayer();
        if (player.isOnline()){
//            System.out.println("玩家进入事件,开始遍历玩家");
//            System.out.println(player.getName());
            PexArray = Dao.GetToDaySQLiteData(player.getName());
            if (!PexArray.isEmpty()){
                for (String res:PexArray){
                    System.out.println(res);
                    String[] TestArra = res.split("\\|");
                    Dao.DeleteSQlite(TestArra[0],TestArra[1]);
//                    System.out.println("清除玩家"+TestArra[0]+"权限:"+TestArra[1]);
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),"manudelp "+TestArra[0]+" "+TestArra[1]);
                }
            }
        }
    }
}
