/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Command.UtilityModule;

import Resource.Emoji;
import Resource.Web;
import Resource.Prefix;
import Resource.Info;
import Resource.SearchResult;
import Command.Command;
import Main.*;
import Setting.SmartLogger;
import java.awt.Color;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 *
 * @author liaoyilin
 */
public class ImageCommand implements Command{
    public final static  String HELP = "This command is for searching the ~~spicy memes~~ images.\n"
                                     + "Command Usage: `"+ Prefix.getDefaultPrefix() +"imgur` or `"+ Prefix.getDefaultPrefix() +"gif` or `"+ Prefix.getDefaultPrefix() +"meme`  `"+ Prefix.getDefaultPrefix() +"urban` or  `"+ Prefix.getDefaultPrefix() +"github`\n"
                                     + "Parameter: `-h | [Keywords] | null`\n\n"
                                     + "Related/Alter Commads:\n"
                                     + "**imgur** - Search `Imgur.com`.\n"
                                     + "**gif** - Search `Giphy.com` for gif.\n"
                                     + "**meme** - Search `knowyourmeme.com` for memes.\n"
            ;
    private final EmbedBuilder embed = new EmbedBuilder();
    private final String num = "&num=1";
    private String site = "&as_sitesearch=";
    
    public ImageCommand(String invoke)
    {
        if("image".equals(invoke)) site = "";
        else if("imgur".equals(invoke)) site += "imgur.com";
        else if("gif".equals(invoke))   site += "giphy.com";
        else if("meme".equals(invoke))   site += "knowyourmeme.com";
    }
    
    @Override
    public boolean called(String[] args, MessageReceivedEvent e) {
        return true;
    }

    @Override
    public void help(MessageReceivedEvent e) {
        embed.setColor(Color.red);
        embed.setTitle("Miscellaneous Module", null);
        embed.addField("Image -Help", HELP, true);
        embed.setFooter("Command Help/Usage", Info.I_HELP);
        embed.setTimestamp(Instant.now());

        MessageEmbed me = embed.build();
        e.getChannel().sendMessage(me).queue();
        embed.clearFields();
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        SmartLogger.commandLog(e.getGuild().getName(), "ImageCommand", "Called");
                
        if(args.length == 0 || "-h".equals(args[0]) || "".equals(site)) 
        {
            help(e);
        }
        else
        {
            String input = "";
            for(int i = 0; i < args.length; i++){ input += args[i] + " "; }
            
            try {
                System.out.println("Image Search");
                
                e.getChannel().sendMessage("Searching........").complete().editMessage(Emoji.search + " Image!").complete();
                List<SearchResult> result = Web.search(site, num, input);
                e.getChannel().sendMessage(result.get(0).getLink()).queue();
                
            } catch (IOException ioe) {
                SmartLogger.errorLog(ioe, e.getGuild().getName(), this.getClass().getName(), "IO Exception.");
            } catch (IndexOutOfBoundsException iobe) {
                e.getChannel().sendMessage(Emoji.error + " No result.").queue();
                SmartLogger.errorLog(iobe, e.getGuild().getName(), this.getClass().getName(), "Image Search \""+ args[0] +"\" No Result.");
            }
        }
        
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent e) {
        
    }
    
}

