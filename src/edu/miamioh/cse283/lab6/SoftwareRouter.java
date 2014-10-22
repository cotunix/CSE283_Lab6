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

	public class Pair<A, B> {
		public A K;
		public B T;

		public Pair(A K, B T) {
			this.K = K;
			this.T = T;
		}

	}

	protected HashMap<Integer, Pair<Link, Integer>> h = new HashMap<Integer, Pair<Link, Integer>>();

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
		h.put(network_address.getIP() >> (32 - subnet_mask),
				new Pair<Link, Integer>(link, subnet_mask));

	}

	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link
	 *            is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {

		Iterator<Entry<Integer, Pair<Link, Integer>>> entries = h.entrySet()
				.iterator();
		while (entries.hasNext()) {
			Entry<Integer, Pair<Link, Integer>> thisEntry = (Entry<Integer, Pair<Link, Integer>>) entries
					.next();
			if (thisEntry.getValue().K == link) {
				entries.remove();
				return;
			}

		}
		throw new IllegalArgumentException(
				"Error: Tried to remove nonexistant link");

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
		Pair<Link, Integer> temp;
		for (int i = 0; i <= 32; i++) {
			temp = h.get(pkt.dst.getIP() >> i);
			if (temp != null) {
				if (temp.T == (32 - i)) {
					temp.K.send(pkt);
					break;
				}

			}

		}
	}
}
