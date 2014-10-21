package edu.miamioh.cse283.lab6;

import java.util.HashMap;



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
		public Pair() {
			this.K = null;
			this.T = null;
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
		int subnet = ~((int) (Math.pow(2, 32 - subnet_mask) - 1));
		h.put(network_address.ip & subnet, new Pair<Link, Integer>(link, subnet_mask));
	}

	/**
	 * Removes the given link from this router's routing table.
	 * 
	 * @param link
	 *            is the link to be removed from this router's routing table.
	 */
	public void removeLink(Link link) {


		h.values().remove(link);
		
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
		Pair<Link, Integer> temp = new Pair<Link, Integer>();
		for (int i = 0; i <= 32; i++) {
			temp = h.get(pkt.dst.getIP() & ~((int) (Math.pow(2, i) - 1)));
			if (temp != null) {
				if (temp.T == (32 - i))
					temp.K.send(pkt);
						
				break;
			}

		}
	}
}
