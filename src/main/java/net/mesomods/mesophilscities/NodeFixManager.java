//unused for now. Would require refurbished_furniture to be loaded for compiling. To be replaced with mixins

/*package net.mesomods.mesophilscities;

import org.apache.logging.log4j.LogManager;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.mrcrayfish.furniture.refurbished.electricity.IElectricityNode;
import com.mrcrayfish.furniture.refurbished.electricity.Connection;

@Mod.EventBusSubscriber(modid = "refurbished_furniture")
public class NodeFixManager {
	public static boolean reconnectNodeIfPresent(BlockEntity blockEntity, BlockPos pos, ServerLevel level) {
		if (blockEntity instanceof IElectricityNode node) {
			Set<Connection> connections = node.getNodeConnections();
			List<BlockPos> connectToPositions = new ArrayList();
			for (Connection c : connections) {
				BlockPos connectToPos = (c.getPosA() == pos) ? c.getPosB() : c.getPosA();
				connectToPositions.add(connectToPos);
			}
			//node.removeAllNodeConnections();
			LogManager.getLogger().warn("Connecting node at pos " + pos.toString() + " to nodes at positions: " + connectToPositions.toString());
			for (BlockPos connectToPos : connectToPositions) {
				BlockEntity otherBlockEntity = level.getBlockEntity(connectToPos);
				if (otherBlockEntity instanceof IElectricityNode otherNode) {
					otherNode.connectToNode(node);
				}
			}
			return true;

		}
		return false;
	}
}
*/