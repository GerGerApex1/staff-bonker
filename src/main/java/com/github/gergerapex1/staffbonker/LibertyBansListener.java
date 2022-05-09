package com.github.gergerapex1.staffbonker;

import com.github.gergerapex1.staffbonker.Discord.WebhookSender;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import space.arim.libertybans.api.event.PostPardonEvent;
import space.arim.libertybans.api.event.PunishEvent;
import space.arim.libertybans.api.punish.DraftPunishment;
import space.arim.omnibus.Omnibus;
import space.arim.omnibus.OmnibusProvider;
import space.arim.omnibus.events.EventConsumer;
import space.arim.omnibus.events.ListenerPriorities;
import space.arim.omnibus.events.RegisteredListener;

public class LibertyBansListener {
    private final Logger logger = StaffBonker.getLogger();
    RegisteredListener registeredPunishment;
    RegisteredListener registeredPardon;
    public LibertyBansListener() {
        Omnibus omnibus = OmnibusProvider.getOmnibus();
        registeredPunishment = omnibus.getEventBus().registerListener(PunishEvent.class, ListenerPriorities.NORMAL, new PunishmentListener());
        registeredPardon = omnibus.getEventBus().registerListener(PostPardonEvent.class, ListenerPriorities.NORMAL, new PardonPunishmentListener());
        logger.info("Registered the listeners.");
    }

    public class PunishmentListener implements EventConsumer<PunishEvent> {

        @Override
        public void accept(PunishEvent event) {
            handlePunishment(event.getDraftPunishment());
        }
        private void handlePunishment(DraftPunishment punishment) {
            MessageEmbed embed = WebhookSender.buildEmbed(punishment);
            WebhookSender.sendMessage(embed, StaffBonker.getClient());
        }
    }
    public static class PardonPunishmentListener implements EventConsumer<PostPardonEvent> {

        @Override
        public void accept(PostPardonEvent event) {
            handlePunishment(event);
        }
        private void handlePunishment(PostPardonEvent punishment) {
            MessageEmbed embed = WebhookSender.buildEmbedPardon(punishment);
            WebhookSender.sendMessage(embed, StaffBonker.getClient());
        }
    }
}
