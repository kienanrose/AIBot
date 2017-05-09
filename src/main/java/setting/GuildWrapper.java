/* 
 * AIBot by AlienIdeology
 * 
 * GuildWrapper
 * Custom settings per guild
 */
package setting;

import audio.AudioPlayerSendHandler;
import audio.GuildPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 * Holder for both the player and a track guildPlayer for one guild.
 */
public class GuildWrapper {

  /**
   * JDA
   */
  private final JDA jda;

  /**
   * Audio players for the guild.
   */
  private final AudioPlayer player;
  private final GuildPlayer guildPlayer;
  /**
   * Binded Voice Channel for the guild.
   */
  private VoiceChannel vc;
  
  private TextChannel tc;

  /**
   * voteSkip The vote for skipping current song
   */
  private String guildId, prefix;
  
  /**
   * Creates a player and a track guildPlayer
   * @param manager audio player manager to use for creating the player.
   * @param guildId Guild ID
   * @param prefix Custom Guild prefix
   */
  public GuildWrapper(JDA jda, AudioPlayerManager manager, String guildId, String prefix) {
    this.jda = jda;
    player = manager.createPlayer();
    guildPlayer = new GuildPlayer(player, jda.getGuildById(guildId).getPublicChannel());
    player.addListener(guildPlayer);
    
    this.guildId = guildId;
    this.prefix = prefix;
  }

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    public AudioPlayerSendHandler getSendHandler() 
    {
      return new AudioPlayerSendHandler(player);
    }

    public GuildPlayer getGuildPlayer()
    {
        return guildPlayer;
    }

    public AudioPlayer getPlayer()
    {
        return player;
    }
    
    public VoiceChannel getVc() {
        return vc;
    }

    public void setVc(VoiceChannel vc) {
        this.vc = vc;
        guildPlayer.setVc(vc);
    }

    public TextChannel getTc() {
        return tc;
    }
    
    public void setTc(TextChannel tc) {
        this.tc = tc;
        guildPlayer.setTc(tc);
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix){
        this.prefix = prefix;
    }
    
}