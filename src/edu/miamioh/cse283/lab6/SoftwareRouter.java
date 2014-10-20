package edu.miamioh.cse283.lab6;

import java.util.HashMap;
import java.util.Set;

/**
 * Software router template for CSE283 Lab 6, FS2014.
 * 
 * @author dk
 */
public class SoftwareRouter {

	protected HashMap<Integer, Link> h = new HashMap<Integer, Link>();

	/**
	 * Adds the given link and (network, subnet mask) to this router's routing
	 * table.
	 * 
	 * @param link
	 *            is the link that packets should be forwarded to.
	 * @param network_address
	 *            is the network address for this link.
	 * @param subnet_mask
	 *            is the number of bits for the network prefix.
	 */
	public void addLink(Link link, Address network_address, int subnet_mask) {
		h.put(network_address.ip >> (32 - subnet_mask), link);
	}

	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link
	 *            is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {

		Set<Integer> k = h.keySet();
		for (Integer j : k) {
			if (h.get(j) == link)
				h.remove(j);
		}
	}

	/**
	 * Packets that are received in this method are to be forwarded to the
	 * correct outgoing link.
	 * 
	 * @param pkt
	 *            is the packet that needs to be forwarded.
	 */
	public void receivePacket(Packet pkt) {
		// once the correct outgoing link has been identified, call
		// link.send(pkt, this);
		for (int i = 0; i <= 32; i++) {
			
			System.out.println((pkt.dst.getIP() >> i) + "yeah ok" );
			if (h.get(pkt.dst.getIP() >> i) != null) {
				h.get(pkt.dst.getIP() >> i).send(pkt);
				break;
			}
			System.out.println(i);

		}
	}
}
