
public class Driver_ass3_4 {
	
	public static void main(String[] args) {
		
		binaryTree2 tree1 = new binaryTree2(5);
		
		tree1.addFirstNode('a');
		tree1.insertLeft(1, 'b');
		tree1.insertRight(1, 'c');
		tree1.insertRight(2, 'f');
		
		System.out.println("Testing (1) methods within arrayList");
		System.out.println("Initial test array");
		tree1.print();
		
		System.out.println("__________________");
		System.out.println("Insert and Expanding methods");
		tree1.insertLeft(2, 'd');
		tree1.insertRight(2, 'e');
		tree1.print();

		System.out.println("__________________");
		System.out.println("Has Left and Right methods");
		System.out.println(tree1.hasLeft(1));
		System.out.println(tree1.hasRight(1));
		
		System.out.println("__________________");
		System.out.println("Node Left and Right methods");
		System.out.println(tree1.left(1));
		System.out.println(tree1.right(1));
		
		binaryTree2 tree2 = new binaryTree2(5);
		
		tree2.addFirstNode('a');
		tree2.insertLeft(1, 'b');
		tree2.insertLeft(2, 'c');
		
		System.out.println("\nTesting (2) methods within arrayList");
		System.out.println("Initial test array");
		tree2.print();
		
		System.out.println("__________________");
		System.out.println("Insert and Expanding methods");
		tree2.insertLeft(4, 'd');
		tree2.print();

		System.out.println("__________________");
		System.out.println("Has Left and Right methods");
		System.out.println(tree2.hasLeft(2));
		System.out.println(tree2.hasRight(2));
		
		System.out.println("__________________");
		System.out.println("Node Left and Right methods");
		System.out.println(tree2.left(2));
		System.out.println(tree2.right(2));
		
		binaryTree2 tree3 = new binaryTree2(2);
		
		tree3.addFirstNode('a');
		tree3.insertLeft(1, 'b');
		tree3.insertRight(1, 'c');
		tree3.insertLeft(2, 'd');
		
		System.out.println("\nTesting (3) methods within arrayList");
		System.out.println("Initial test array");
		tree3.print();
		
		System.out.println("__________________");
		System.out.println("Insert and Expanding methods");
		tree3.insertRight(4, 'e');
		tree3.print();

		System.out.println("__________________");
		System.out.println("Has Left and Right methods");
		System.out.println(tree3.hasLeft(2));
		System.out.println(tree3.hasRight(2));
		
		System.out.println("__________________");
		System.out.println("Node Left and Right methods");
		System.out.println(tree3.left(2));
		System.out.println(tree3.right(2));
		
	}

}
