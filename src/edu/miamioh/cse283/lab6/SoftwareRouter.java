package edu.miamioh.cse283.lab6;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

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
		int subnet = ~((int)(Math.pow(2, 32 - subnet_mask) - 1));
		h.put(network_address.ip & subnet, link);
	}

	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link
	 *            is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {

		// God bless stackoverflow
		Iterator<Entry<Integer, Link>> i = h.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Integer, Link> hEntry = i.next();
			if (hEntry.getValue() == link) {
				i.remove();
			}

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

			if (h.get(pkt.dst.getIP() & ~((int)(Math.pow(2, i) - 1))) != null) {
				h.get(pkt.dst.getIP() & ~((int)(Math.pow(2, i) - 1))).send(pkt);
				break;
			}

		}
	}
}
