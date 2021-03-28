package poly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial { 
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		List<Node> nodes = new ArrayList<Node>();
		
		Node p1current = poly1;
		Node p2current = poly2;
		while(p1current != null && p2current != null) { 
			if(p1current.term.degree == p2current.term.degree) {
				if(p1current.term.coeff + p2current.term.coeff != 0){
					nodes.add(new Node(p1current.term.coeff+p2current.term.coeff,p1current.term.degree,null));
				}
				p1current = p1current.next;
				p2current = p2current.next;
			} else if(p1current.term.degree < p2current.term.degree) {
				nodes.add(new Node(p1current.term.coeff, p1current.term.degree, null));
				p1current = p1current.next;
			} else {
				System.out.println("p2 smaller");
				nodes.add(new Node(p2current.term.coeff, p2current.term.degree, null));
				p2current = p2current.next;
			}
		}
		
		
		while(p1current != null) {
			nodes.add(new Node(p1current.term.coeff, p1current.term.degree, null));
			p1current = p1current.next;
		}
		while(p2current != null) {
			nodes.add(new Node(p2current.term.coeff, p2current.term.degree, null));
			p2current = p2current.next;
		}
		
		for(int i = 0; i < nodes.size()-1; i++) {
			nodes.get(i).next = nodes.get(i+1);
		}
		if(nodes.size() > 0){
			return nodes.get(0);
		} else {
			return null;
		}
	
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		List<Node> polynomials = new ArrayList<Node>();
		Node p1current = poly1;
		Node p2current = poly2;
		while(p1current != null) {
			List<Node> nodes = new ArrayList<Node>();
			while(p2current != null) {
				nodes.add(new Node(p1current.term.coeff*p2current.term.coeff, p1current.term.degree+p2current.term.degree, null));
				p2current = p2current.next;
			}
			p1current = p1current.next;
			for(int i = 0; i < nodes.size()-1; i++) {
				nodes.get(i).next = nodes.get(i+1);
			}
			p2current = poly2;
			polynomials.add(nodes.get(0));
		}
		if(polynomials.size() == 0) {
			return null;
		}
		Node current = polynomials.get(0);
		for(int i = 1; i < polynomials.size(); i++){
			current = add(current, polynomials.get(i));
		}
		return current;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		float sum = 0;
		Node current = poly;
		while(current != null) {
			sum += current.term.coeff*(float)Math.pow(x, current.term.degree);
			current = current.next;
		}
		return sum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
