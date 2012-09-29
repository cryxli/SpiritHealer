package li.cryx.minecraft.util;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DummyPlayer implements Player {

	private final String name;

	public DummyPlayer(final String name) {
		this.name = name;
	}

	@Override
	public void abandonConversation(final Conversation conversation) {
	}

	@Override
	public void abandonConversation(final Conversation conversation,
			final ConversationAbandonedEvent details) {
	}

	@Override
	public void acceptConversationInput(final String input) {
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final int ticks) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final String name, final boolean value) {
		return null;
	}

	@Override
	public PermissionAttachment addAttachment(final Plugin plugin,
			final String name, final boolean value, final int ticks) {
		return null;
	}

	@Override
	public boolean addPotionEffect(final PotionEffect effect) {
		return false;
	}

	@Override
	public boolean addPotionEffect(final PotionEffect effect,
			final boolean force) {
		return false;
	}

	@Override
	public boolean addPotionEffects(final Collection<PotionEffect> effects) {
		return false;
	}

	@Override
	public void awardAchievement(final Achievement achievement) {
	}

	@Override
	public boolean beginConversation(final Conversation conversation) {
		return false;
	}

	@Override
	public boolean canSee(final Player player) {
		return false;
	}

	@Override
	public void chat(final String msg) {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public void damage(final int amount) {
	}

	@Override
	public void damage(final int amount, final Entity source) {
	}

	@Override
	public boolean eject() {
		return false;
	}

	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		return null;
	}

	@Override
	public InetSocketAddress getAddress() {
		return null;
	}

	@Override
	public boolean getAllowFlight() {
		return false;
	}

	@Override
	public Location getBedSpawnLocation() {
		return null;
	}

	@Override
	public Location getCompassTarget() {
		return null;
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return null;
	}

	@Override
	public Inventory getEnderChest() {
		return null;
	}

	@Override
	public int getEntityId() {
		return 0;
	}

	@Override
	public float getExhaustion() {
		return 0;
	}

	@Override
	public float getExp() {
		return 0;
	}

	@Override
	public int getExpToLevel() {
		return 0;
	}

	@Override
	public double getEyeHeight() {
		return 0;
	}

	@Override
	public double getEyeHeight(final boolean ignoreSneaking) {
		return 0;
	}

	@Override
	public Location getEyeLocation() {
		return null;
	}

	@Override
	public float getFallDistance() {
		return 0;
	}

	@Override
	public int getFireTicks() {
		return 0;
	}

	@Override
	public long getFirstPlayed() {
		return 0;
	}

	@Override
	public float getFlySpeed() {
		return 0;
	}

	@Override
	public int getFoodLevel() {
		return 0;
	}

	@Override
	public GameMode getGameMode() {
		return null;
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public PlayerInventory getInventory() {
		return null;
	}

	@Override
	public ItemStack getItemInHand() {
		return null;
	}

	@Override
	public ItemStack getItemOnCursor() {
		return null;
	}

	@Override
	public Player getKiller() {
		return null;
	}

	@Override
	public int getLastDamage() {
		return 0;
	}

	@Override
	public EntityDamageEvent getLastDamageCause() {
		return null;
	}

	@Override
	public long getLastPlayed() {
		return 0;
	}

	@Override
	public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent,
			final int maxDistance) {
		return null;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public List<Block> getLineOfSight(final HashSet<Byte> transparent,
			final int maxDistance) {
		return null;
	}

	@Override
	public Set<String> getListeningPluginChannels() {
		return null;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public int getMaxFireTicks() {
		return 0;
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public int getMaximumAir() {
		return 20;
	}

	@Override
	public int getMaximumNoDamageTicks() {
		return 0;
	}

	@Override
	public List<MetadataValue> getMetadata(final String metadataKey) {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Entity> getNearbyEntities(final double x, final double y,
			final double z) {
		return null;
	}

	@Override
	public int getNoDamageTicks() {
		return 0;
	}

	@Override
	public InventoryView getOpenInventory() {
		return null;
	}

	@Override
	public Entity getPassenger() {
		return null;
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public String getPlayerListName() {
		return null;
	}

	@Override
	public long getPlayerTime() {
		return 0;
	}

	@Override
	public long getPlayerTimeOffset() {
		return 0;
	}

	@Override
	public int getRemainingAir() {
		return 0;
	}

	@Override
	public float getSaturation() {
		return 0;
	}

	@Override
	public Server getServer() {
		return null;
	}

	@Override
	public int getSleepTicks() {
		return 0;
	}

	@Override
	public Block getTargetBlock(final HashSet<Byte> transparent,
			final int maxDistance) {
		return null;
	}

	@Override
	public int getTicksLived() {
		return 0;
	}

	@Override
	public int getTotalExperience() {
		return 0;
	}

	@Override
	public EntityType getType() {
		return null;
	}

	@Override
	public UUID getUniqueId() {
		return null;
	}

	@Override
	public Entity getVehicle() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}

	@Override
	public float getWalkSpeed() {
		return 0;
	}

	@Override
	public World getWorld() {
		return null;
	}

	@Override
	public void giveExp(final int amount) {
	}

	@Override
	public boolean hasLineOfSight(final Entity other) {
		return false;
	}

	@Override
	public boolean hasMetadata(final String metadataKey) {
		return false;
	}

	@Override
	public boolean hasPermission(final Permission perm) {
		return false;
	}

	@Override
	public boolean hasPermission(final String name) {
		return false;
	}

	@Override
	public boolean hasPlayedBefore() {
		return false;
	}

	@Override
	public boolean hasPotionEffect(final PotionEffectType type) {
		return false;
	}

	@Override
	public void hidePlayer(final Player player) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic, final int amount) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic,
			final Material material) {
	}

	@Override
	public void incrementStatistic(final Statistic statistic,
			final Material material, final int amount) {
	}

	@Override
	public boolean isBanned() {
		return false;
	}

	@Override
	public boolean isBlocking() {
		return false;
	}

	@Override
	public boolean isConversing() {
		return false;
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean isFlying() {
		return false;
	}

	@Override
	public boolean isInsideVehicle() {
		return false;
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	@Override
	public boolean isOp() {
		return false;
	}

	@Override
	public boolean isPermissionSet(final Permission perm) {
		return false;
	}

	@Override
	public boolean isPermissionSet(final String name) {
		return false;
	}

	@Override
	public boolean isPlayerTimeRelative() {
		return false;
	}

	@Override
	public boolean isSleeping() {
		return false;
	}

	@Override
	public boolean isSleepingIgnored() {
		return false;
	}

	@Override
	public boolean isSneaking() {
		return false;
	}

	@Override
	public boolean isSprinting() {
		return false;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isWhitelisted() {
		return false;
	}

	@Override
	public void kickPlayer(final String message) {
	}

	@Override
	public <T extends Projectile> T launchProjectile(
			final Class<? extends T> projectile) {
		return null;
	}

	@Override
	public boolean leaveVehicle() {
		return false;
	}

	@Override
	public void loadData() {
	}

	@Override
	public InventoryView openEnchanting(final Location location,
			final boolean force) {
		return null;
	}

	@Override
	public InventoryView openInventory(final Inventory inventory) {
		return null;
	}

	@Override
	public void openInventory(final InventoryView inventory) {

	}

	@Override
	public InventoryView openWorkbench(final Location location,
			final boolean force) {
		return null;
	}

	@Override
	public boolean performCommand(final String command) {
		return false;
	}

	@Override
	public void playEffect(final EntityEffect type) {
	}

	@Override
	public void playEffect(final Location loc, final Effect effect,
			final int data) {
	}

	@Override
	public <T> void playEffect(final Location loc, final Effect effect,
			final T data) {
	}

	@Override
	public void playNote(final Location loc, final byte instrument,
			final byte note) {
	}

	@Override
	public void playNote(final Location loc, final Instrument instrument,
			final Note note) {
	}

	@Override
	public void playSound(Location location, Sound sound, float volume,
			float pitch) {
	}

	@Override
	public void recalculatePermissions() {
	}

	@Override
	public void remove() {
	}

	@Override
	public void removeAttachment(final PermissionAttachment attachment) {
	}

	@Override
	public void removeMetadata(final String metadataKey,
			final Plugin owningPlugin) {
	}

	@Override
	public void removePotionEffect(final PotionEffectType type) {
	}

	@Override
	public void resetPlayerTime() {
	}

	@Override
	public void saveData() {
	}

	@Override
	public void sendBlockChange(final Location loc, final int material,
			final byte data) {
	}

	@Override
	public void sendBlockChange(final Location loc, final Material material,
			final byte data) {
	}

	@Override
	public boolean sendChunkChange(final Location loc, final int sx,
			final int sy, final int sz, final byte[] data) {
		return false;
	}

	@Override
	public void sendMap(final MapView map) {
	}

	@Override
	public void sendMessage(final String message) {
	}

	@Override
	public void sendMessage(final String[] messages) {
	}

	@Override
	public void sendPluginMessage(final Plugin source, final String channel,
			final byte[] message) {
	}

	@Override
	public void sendRawMessage(final String message) {
	}

	@Override
	public Map<String, Object> serialize() {
		return null;
	}

	@Override
	public void setAllowFlight(final boolean flight) {
	}

	@Override
	public void setBanned(final boolean banned) {
	}

	@Override
	public void setBedSpawnLocation(final Location location) {
	}

	@Override
	public void setCompassTarget(final Location loc) {
	}

	@Override
	public void setDisplayName(final String name) {
	}

	@Override
	public void setExhaustion(final float value) {
	}

	@Override
	public void setExp(final float exp) {
	}

	@Override
	public void setFallDistance(final float distance) {
	}

	@Override
	public void setFireTicks(final int ticks) {
	}

	@Override
	public void setFlying(final boolean value) {
	}

	@Override
	public void setFlySpeed(final float speed) throws IllegalArgumentException {
	}

	@Override
	public void setFoodLevel(final int value) {
	}

	@Override
	public void setGameMode(final GameMode mode) {
	}

	@Override
	public void setHealth(final int health) {
	}

	@Override
	public void setItemInHand(final ItemStack item) {
	}

	@Override
	public void setItemOnCursor(final ItemStack item) {
	}

	@Override
	public void setLastDamage(final int damage) {
	}

	@Override
	public void setLastDamageCause(final EntityDamageEvent event) {
	}

	@Override
	public void setLevel(final int level) {
	}

	@Override
	public void setMaximumAir(final int ticks) {
	}

	@Override
	public void setMaximumNoDamageTicks(final int ticks) {
	}

	@Override
	public void setMetadata(final String metadataKey,
			final MetadataValue newMetadataValue) {
	}

	@Override
	public void setNoDamageTicks(final int ticks) {
	}

	@Override
	public void setOp(final boolean value) {
	}

	@Override
	public boolean setPassenger(final Entity passenger) {
		return false;
	}

	@Override
	public void setPlayerListName(final String name) {
	}

	@Override
	public void setPlayerTime(final long time, final boolean relative) {
	}

	@Override
	public void setRemainingAir(final int ticks) {
	}

	@Override
	public void setSaturation(final float value) {
	}

	@Override
	public void setSleepingIgnored(final boolean isSleeping) {
	}

	@Override
	public void setSneaking(final boolean sneak) {
	}

	@Override
	public void setSprinting(final boolean sprinting) {
	}

	@Override
	public void setTicksLived(final int value) {
	}

	@Override
	public void setTotalExperience(final int exp) {
	}

	@Override
	public void setVelocity(final Vector velocity) {
	}

	@Override
	public void setWalkSpeed(final float speed) throws IllegalArgumentException {
	}

	@Override
	public void setWhitelisted(final boolean value) {
	}

	@Override
	public boolean setWindowProperty(final Property prop, final int value) {
		return false;
	}

	@Override
	public Arrow shootArrow() {
		return null;
	}

	@Override
	public void showPlayer(final Player player) {
	}

	@Override
	public boolean teleport(final Entity destination) {
		return false;
	}

	@Override
	public boolean teleport(final Entity destination, final TeleportCause cause) {
		return false;
	}

	@Override
	public boolean teleport(final Location location) {
		return false;
	}

	@Override
	public boolean teleport(final Location location, final TeleportCause cause) {
		return false;
	}

	@Override
	public Egg throwEgg() {
		return null;
	}

	@Override
	public Snowball throwSnowball() {
		return null;
	}

	@Override
	public void updateInventory() {
	}

}